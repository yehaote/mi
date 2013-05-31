package org.apache.lucene.index;

import org.apache.lucene.store.DataOutput;
import org.apache.lucene.util.ByteBlockPool;

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


/**
 * Class to write byte streams into slices of shared
 * byte[].  This is used by DocumentsWriter to hold the
 * posting list for many terms in RAM.
 * 
 * 写入byte流到分配共享的byte数组的类.
 * 这被DocumentWriter用来在内存中存储term的posting list.
 */

final class ByteSliceWriter extends DataOutput {

  private byte[] slice; //当前的分片
  private int upto; // 在slice中的offset
  private final ByteBlockPool pool;

  int offset0; // 原始的address, 整块的offset, 而不是当前块的

  public ByteSliceWriter(ByteBlockPool pool) {
    this.pool = pool;
  }

  /**
   * Set up the writer to write at address.
   * 
   * 设置writer到写入的地址
   */
  public void init(int address) {
	// 定位到指定的分片?
    slice = pool.buffers[address >> ByteBlockPool.BYTE_BLOCK_SHIFT];
    // 判断分片不为空
    assert slice != null;
    // 取掩码, 根据address获取在当前slice中的位置
    upto = address & ByteBlockPool.BYTE_BLOCK_MASK;
    // offset0记录原始的address
    offset0 = address;
    assert upto < slice.length;
  }

  /** Write byte into byte slice stream 
   *  写入一个byte
   * */
  @Override
  public void writeByte(byte b) {
	// 如果当前的分片不是null
    assert slice != null;
    // 如果当前的那位不是零的话, 说明是块结束符?
    if (slice[upto] != 0) {
      // 重新分配块
      upto = pool.allocSlice(slice, upto);
      // 切换块
      slice = pool.buffer;
      offset0 = pool.byteOffset;
      assert slice != null;
    }
    // 写入数据 
    slice[upto++] = b;
    assert upto != slice.length;
  }

  @Override
  public void writeBytes(final byte[] b, int offset, final int len) {
    final int offsetEnd = offset + len;
    while(offset < offsetEnd) {
      if (slice[upto] != 0) {
        // End marker
        upto = pool.allocSlice(slice, upto);
        slice = pool.buffer;
        offset0 = pool.byteOffset;
      }

      slice[upto++] = b[offset++];
      assert upto != slice.length;
    }
  }

  public int getAddress() {
    return upto + (offset0 & DocumentsWriterPerThread.BYTE_BLOCK_NOT_MASK);
  }
}