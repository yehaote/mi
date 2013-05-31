package org.apache.lucene.index;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;

import org.apache.lucene.store.DataInput;
import org.apache.lucene.store.DataOutput;
import org.apache.lucene.util.ByteBlockPool;

/* IndexInput that knows how to read the byte slices written
 * by Posting and PostingVector.  We read the bytes in
 * each slice until we hit the end of that slice at which
 * point we read the forwarding address of the next slice
 * and then jump to it.
 * 
 * 知道怎么去读取posting和postingVector写入的byte slice的IndexInput.
 * 读取从每个slice读取数据, 直到slice结束.
 * 如果是分slice存储的话, 会自动跳转到下一个silece.
 * */
final class ByteSliceReader extends DataInput {
  ByteBlockPool pool; // Byte池 
  int bufferUpto; // 当前buffer在pool中的index
  byte[] buffer; // 当前的buffer
  public int upto; // 想对于当前buffer的offset
  int limit; // 当前slice待读取数据结束的位置, 如果还有下一个slice的话减去地址占用的位置
  int level; // level
  public int bufferOffset; // 当前buffer开始位置想对于pool的offset

  public int endIndex;// 结束的位置(相对于pool)
  
  /**
   * 初始化
   * 一定要同时指定startIndex和endIndex?
   * 这样的话读取一个数据的话不是同时需要知道它开始和结束的位置?
   * @param pool
   * @param startIndex
   * @param endIndex
   */
  public void init(ByteBlockPool pool, int startIndex, int endIndex) {
	// 检验参数是否正确
    assert endIndex-startIndex >= 0;
    assert startIndex >= 0;
    assert endIndex >= 0;
    // 使用指定的ByteBlockPool做为本地的存储
    this.pool = pool;
    this.endIndex = endIndex;
    
    level = 0;
    // 根据startIndex跳转到指定的buffer
    bufferUpto = startIndex / ByteBlockPool.BYTE_BLOCK_SIZE;
    // 计算当前buffer开始位置想对于整个池的offset
    bufferOffset = bufferUpto * ByteBlockPool.BYTE_BLOCK_SIZE;
    // 根据bufferUpto获取当前的buffer
    buffer = pool.buffers[bufferUpto];
    //upto 计算在当前buffer中的offset
    upto = startIndex & ByteBlockPool.BYTE_BLOCK_MASK;
    // 获取第一个slice的size
    final int firstSize = ByteBlockPool.LEVEL_SIZE_ARRAY[0];
    
    // 如果startIndex+firstSize >= endIndex的话说明数据都在当前的slice中, 没有下一个扩展的slice
    if (startIndex+firstSize >= endIndex) {
      // There is only this one slice to read
      // 直接把限制设置成endIndex在当前buffer中的position就好了
      limit = endIndex & ByteBlockPool.BYTE_BLOCK_MASK;
    } else
      // 为什么要减去4?
      limit = upto+firstSize-4;
  }
  
  /**
   * 查看是否结束
   * eof(end of file)
   * @return
   */
  public boolean eof() {
    assert upto + bufferOffset <= endIndex;
    // 当前在buffer中的offset加上buffer开始位置想对于pool的offset
    return upto + bufferOffset == endIndex;
  }

  @Override
  public byte readByte() {
	// 从当前的upto开始读取一个byte
    assert !eof();
    assert upto <= limit;
    // 如果upto==limit的话, 跳转到下一个slice
    if (upto == limit)
      nextSlice();
    return buffer[upto++];
  }
  
  /**
   * 输出到DataOutput
   * @param out
   * @return
   * @throws IOException
   */
  public long writeTo(DataOutput out) throws IOException {
    long size = 0;
    while(true) {
      if (limit + bufferOffset == endIndex) {
    	// 如果limit+bufferOffset==endIndex的话, 说明数据都在当前的slice中
    	// 直接输出就好了
        assert endIndex - bufferOffset >= upto;
        out.writeBytes(buffer, upto, limit-upto);
        size += limit-upto;
        break;
      } else {
    	// 输出当前slice中的内容, 并跳到下一个slice继续输出
        out.writeBytes(buffer, upto, limit-upto);
        size += limit-upto;
        nextSlice();
      }
    }

    return size;
  }
  
  /**
   * 跳到下一个silce
   */
  public void nextSlice() {
    // Skip to our next slice
	// 根据limit位置以及后面的3个byte的数据计算出下一个slice的地址
    final int nextIndex = ((buffer[limit]&0xff)<<24) + ((buffer[1+limit]&0xff)<<16) + ((buffer[2+limit]&0xff)<<8) + (buffer[3+limit]&0xff);
    // 根据当前的level获取下一个level
    level = ByteBlockPool.NEXT_LEVEL_ARRAY[level];
    // 根据下一个level获取对应的size
    final int newSize = ByteBlockPool.LEVEL_SIZE_ARRAY[level];
    // 根据一下个slice的index计算出所属的buffer
    bufferUpto = nextIndex / ByteBlockPool.BYTE_BLOCK_SIZE; // 根据position获取到buffer在池中的index
    bufferOffset = bufferUpto * ByteBlockPool.BYTE_BLOCK_SIZE; // 根据buffer在pool的index计算当前buffer开始的offset
    
    //根据pool中index获取buffer
    buffer = pool.buffers[bufferUpto];
    // 根据nextIndex获取在下一个buffer中的upto地址
    upto = nextIndex & ByteBlockPool.BYTE_BLOCK_MASK;
    // 如果nextIndex+newSize地址大于endIndex的话说明我们已经在当前的最后一个slice中了
    if (nextIndex + newSize >= endIndex) {
      // We are advancing to the final slice
      assert endIndex - nextIndex > 0;
      // limit设置成根据当前buffer的地址
      limit = endIndex - bufferOffset;
    } else {
      // This is not the final slice (subtract 4 for the
      // forwarding address at the end of this new slice)
      // 如果不是的话, 说明还不是最后一个slice
      // (减去4个byte, 是因为这4个byte存的是下一个slice的地址)
      limit = upto+newSize-4;
    }
  }
  
  @Override
  public void readBytes(byte[] b, int offset, int len) {
	// offset指的是读取过去从指定byte[]的哪个位置开始写入
    while(len > 0) {
      // 计算当前slice还有多少数据可以读取
      final int numLeft = limit-upto;
      // 如果需要的数据比当前slice中遗留的要少
      if (numLeft < len) {
        // Read entire slice
    	// 读取当前slice中所有的数据
        System.arraycopy(buffer, upto, b, offset, numLeft);
        offset += numLeft;
        len -= numLeft;
        // 切换到下一个silce继续读取
        nextSlice();
      } else {
    	// 如果当前slice数据够的话, 直接从当前slice读取并跳出
        // This slice is the last one
        System.arraycopy(buffer, upto, b, offset, len);
        upto += len;
        break;
      }
    }
  }
}