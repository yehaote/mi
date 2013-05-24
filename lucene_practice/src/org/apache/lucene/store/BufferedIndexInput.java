package org.apache.lucene.store;

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

import java.io.EOFException;
import java.io.IOException;

/** Base implementation class for buffered {@link org.apache.lucene.store.IndexInput}.
 *  带缓存的IndexInput抽象类
 * */
public abstract class BufferedIndexInput extends IndexInput {

  /** Default buffer size set to 1024*/
  public static final int BUFFER_SIZE = 1024; // 默认的缓存大小
  
  // The normal read buffer size defaults to 1024, but
  // increasing this during merging seems to yield
  // performance gains.  However we don't want to increase
  // it too much because there are quite a few
  // BufferedIndexInputs created during merging.  See
  // LUCENE-888 for details.
  /**
   * A buffer size for merges set to 4096
   * Merge缓存的默认大小为4096
   */
  public static final int MERGE_BUFFER_SIZE = 4096;

  private int bufferSize = BUFFER_SIZE;
  
  protected byte[] buffer; // 缓存实际存在的数组
  
  private long bufferStart = 0;       // position in file of buffer 记录buffer在文件中的位置(相当于输入)
  private int bufferLength = 0;       // end of valid bytes 长度(相对于缓存)
  private int bufferPosition = 0;     // next byte to read 下一次读取发生的位置(相对于缓存)

  @Override
  public final byte readByte() throws IOException {
	// 如果缓存的位置已经在缓存之外
    if (bufferPosition >= bufferLength)
      refill();
    // 因为refill()以后, 一般bufferPositioon会为0
    return buffer[bufferPosition++];
  }
  
  /**
   * 使用默认的BUFFER_SIZE大小初始化
   * @param resourceDesc
   */
  public BufferedIndexInput(String resourceDesc) {
    this(resourceDesc, BUFFER_SIZE);
  }

  /**
   * 根据不同的IOContext实例化不同大小的buffer
   * @param resourceDesc
   * @param context
   */
  public BufferedIndexInput(String resourceDesc, IOContext context) {
    this(resourceDesc, bufferSize(context));
  }

  /** Inits BufferedIndexInput with a specific bufferSize 
   *  根据指定的bufferSize初始化BufferedIndexInput
   * */
  public BufferedIndexInput(String resourceDesc, int bufferSize) {
    super(resourceDesc); // 指定IndexInput的标识符
    checkBufferSize(bufferSize); // 检查参数
    this.bufferSize = bufferSize;
  }

  /** Change the buffer size used by this IndexInput 
   *  改变IndexInput的缓存大小
   *  注意这个方法不会从输入读取更多的数据到buffer, 
   *  只会拷贝原先buffer的数据到新的buffer.
   * */
  public final void setBufferSize(int newSize) {
    assert buffer == null || bufferSize == buffer.length: "buffer=" + buffer + " bufferSize=" + bufferSize + " buffer.length=" + (buffer != null ? buffer.length : 0);
    // 如果newSize跟原先的bufferSize
    if (newSize != bufferSize) {
      checkBufferSize(newSize);
      bufferSize = newSize;
      if (buffer != null) {
        // Resize the existing buffer and carefully save as
        // many bytes as possible starting from the current
        // bufferPosition
    	// 改变当前buffer的大小. 实例化一个新的byte数.
    	// 并且很小心地把许多参数都切换过去.
        byte[] newBuffer = new byte[newSize];
        // 查看当前还留下多少位byte没有被使用
        final int leftInBuffer = bufferLength-bufferPosition;
        // 计算需要从老buffer拷贝到新buffer的byte数量
        final int numToCopy;
        if (leftInBuffer > newSize)
          numToCopy = newSize;
        else
          numToCopy = leftInBuffer;
        // 使用java native的方法拷贝数组
        System.arraycopy(buffer, bufferPosition, newBuffer, 0, numToCopy);
        bufferStart += bufferPosition; // 重新计算buffer在文件中的开始位置
        bufferPosition = 0; // 把buffer的指针定位到buffer的头
        bufferLength = numToCopy; // 长度指定为当前拷贝过去的数量
        newBuffer(newBuffer); //切换buffer实例
      }
    }
  }
  
  /**
   * 切换buffer到新的buffer
   * @param newBuffer
   */
  protected void newBuffer(byte[] newBuffer) {
    // Subclasses can do something here
    buffer = newBuffer;
  }

  /** Returns buffer size.  @see #setBufferSize */
  public final int getBufferSize() {
    return bufferSize;
  }
  
  /**
   * 检查缓存大小(不能小于等于0)
   * @param bufferSize
   */
  private void checkBufferSize(int bufferSize) {
    if (bufferSize <= 0)
      throw new IllegalArgumentException("bufferSize must be greater than 0 (got " + bufferSize + ")");
  }

  @Override
  public final void readBytes(byte[] b, int offset, int len) throws IOException {
    readBytes(b, offset, len, true);
  }

  @Override
  public final void readBytes(byte[] b, int offset, int len, boolean useBuffer) throws IOException {
	// 如果当前缓存里的有效数据大小可以满足需要的大小的话
    if(len <= (bufferLength-bufferPosition)){
      // 直接从缓存里面返回
      // the buffer contains enough data to satisfy this request
      if(len>0) // to allow b to be null if len is 0...
        System.arraycopy(buffer, bufferPosition, b, offset, len);
      bufferPosition+=len;
    } else {
      // 如果数据不够的话
      // the buffer does not have enough data. First serve all we've got.
      // 看看还有多少数据可以读
      int available = bufferLength - bufferPosition;
      // 把可以读的数据拷贝过去
      if(available > 0){
        System.arraycopy(buffer, bufferPosition, b, offset, available);
        offset += available;
        len -= available;
        bufferPosition += available;
      }
      // 现在处理剩下的那些需要从输入获取的数据
      // and now, read the remaining 'len' bytes:
      // 如果我们指定了使用缓存进行读取, 并且剩下需要的长度比bufferSize要小的话
      if (useBuffer && len<bufferSize){
        // If the amount left to read is small enough, and
        // we are allowed to use our buffer, do it in the usual
        // buffered way: fill the buffer and copy from it:
    	// 重新装载缓存
        refill();
        // 如果重新装载过来的数据不够输出? 输出剩下的数据, 并抛出异常
        if(bufferLength<len){
          // Throw an exception when refill() could not read len bytes:
          System.arraycopy(buffer, 0, b, offset, bufferLength);
          throw new EOFException("read past EOF: " + this);
        } else {
          // 重新装载后的数据可以支持这次读取, 就直接输出
          System.arraycopy(buffer, 0, b, offset, len);
          bufferPosition=len;
        }
      } else {
        // The amount left to read is larger than the buffer
        // or we've been asked to not use our buffer -
        // there's no performance reason not to read it all
        // at once. Note that unlike the previous code of
        // this function, there is no need to do a seek
        // here, because there's no need to reread what we
        // had in the buffer.
    	// 如果剩下需要读取的数据比缓存大小大, 或者指定了在缓存数据不够的时候直接从输入读取
        long after = bufferStart+bufferPosition+len; //
        if(after > length())
          throw new EOFException("read past EOF: " + this);
        readInternal(b, offset, len); // 直接从输入读取数据
        bufferStart = after;
        bufferPosition = 0;
        bufferLength = 0;                    // trigger refill() on read 在读取的时候触发refill()
      }
    }
  }

  @Override
  public final short readShort() throws IOException {
	// 读取一个short, 如果当前缓存还有2个byte可以读取的话, 从缓存读取返回
    if (2 <= (bufferLength-bufferPosition)) {
      return (short) (((buffer[bufferPosition++] & 0xFF) <<  8) |  (buffer[bufferPosition++] & 0xFF));
    } else {
      // 如果当前缓存不够的话直接调用IndexInput的readShort()
      // 这个readShort()实际上是触发两次当前的readByte()
      return super.readShort();
    }
  }
  
  @Override
  public final int readInt() throws IOException {
	// 跟readShort()的方式差不多相同
    if (4 <= (bufferLength-bufferPosition)) {
      return ((buffer[bufferPosition++] & 0xFF) << 24) | ((buffer[bufferPosition++] & 0xFF) << 16)
        | ((buffer[bufferPosition++] & 0xFF) <<  8) |  (buffer[bufferPosition++] & 0xFF);
    } else {
      return super.readInt();
    }
  }
  
  @Override
  public final long readLong() throws IOException {
	// 跟readShort()的方式差不多相同
    if (8 <= (bufferLength-bufferPosition)) {
      final int i1 = ((buffer[bufferPosition++] & 0xff) << 24) | ((buffer[bufferPosition++] & 0xff) << 16) |
        ((buffer[bufferPosition++] & 0xff) << 8) | (buffer[bufferPosition++] & 0xff);
      final int i2 = ((buffer[bufferPosition++] & 0xff) << 24) | ((buffer[bufferPosition++] & 0xff) << 16) |
        ((buffer[bufferPosition++] & 0xff) << 8) | (buffer[bufferPosition++] & 0xff);
      return (((long)i1) << 32) | (i2 & 0xFFFFFFFFL);
    } else {
      return super.readLong();
    }
  }

  @Override
  public final int readVInt() throws IOException {
	// 直接按最大vint长度5来进行判断, 跟readShort()差不多
    if (5 <= (bufferLength-bufferPosition)) {
      byte b = buffer[bufferPosition++];
      if (b >= 0) return b;
      int i = b & 0x7F;
      b = buffer[bufferPosition++];
      i |= (b & 0x7F) << 7;
      if (b >= 0) return i;
      b = buffer[bufferPosition++];
      i |= (b & 0x7F) << 14;
      if (b >= 0) return i;
      b = buffer[bufferPosition++];
      i |= (b & 0x7F) << 21;
      if (b >= 0) return i;
      b = buffer[bufferPosition++];
      // Warning: the next ands use 0x0F / 0xF0 - beware copy/paste errors:
      i |= (b & 0x0F) << 28;
      if ((b & 0xF0) == 0) return i;
      throw new IOException("Invalid vInt detected (too many bits)");
    } else {
      return super.readVInt();
    }
  }
  
  @Override
  public final long readVLong() throws IOException {
	// 直接按最大vlong长度9来进行判断, 跟readShort()差不多
    if (9 <= bufferLength-bufferPosition) {
      byte b = buffer[bufferPosition++];
      if (b >= 0) return b;
      long i = b & 0x7FL;
      b = buffer[bufferPosition++];
      i |= (b & 0x7FL) << 7;
      if (b >= 0) return i;
      b = buffer[bufferPosition++];
      i |= (b & 0x7FL) << 14;
      if (b >= 0) return i;
      b = buffer[bufferPosition++];
      i |= (b & 0x7FL) << 21;
      if (b >= 0) return i;
      b = buffer[bufferPosition++];
      i |= (b & 0x7FL) << 28;
      if (b >= 0) return i;
      b = buffer[bufferPosition++];
      i |= (b & 0x7FL) << 35;
      if (b >= 0) return i;
      b = buffer[bufferPosition++];
      i |= (b & 0x7FL) << 42;
      if (b >= 0) return i;
      b = buffer[bufferPosition++];
      i |= (b & 0x7FL) << 49;
      if (b >= 0) return i;
      b = buffer[bufferPosition++];
      i |= (b & 0x7FL) << 56;
      if (b >= 0) return i;
      throw new IOException("Invalid vLong detected (negative values disallowed)");
    } else {
      return super.readVLong();
    }
  }
  
  /**
   * 重新装载
   * @throws IOException
   */
  private void refill() throws IOException {
	// 开始位置等于buffer开始的位置加buffer中当前的位置
	// 类似于把现在在缓存中的那个位置设置为新的开始位置
    long start = bufferStart + bufferPosition;
    // 新的end位置为新的开始位置+bufferSize
    long end = start + bufferSize;
    // 如果end的位置超出文件最大长度, 则设置为最大长度
    if (end > length())  // don't read past EOF
      end = length();
    // 当前整个buffer的长度为end-start
    int newLength = (int)(end - start);
    if (newLength <= 0)
      throw new EOFException("read past EOF: " + this);
    // 如果buffer还没有初始化的话, 进行初始化
    if (buffer == null) {
      // 懒加载
      newBuffer(new byte[bufferSize]);  // allocate buffer lazily
      seekInternal(bufferStart);
    }
    readInternal(buffer, 0, newLength);
    bufferLength = newLength;
    bufferStart = start;
    bufferPosition = 0;
  }

  /** Expert: implements buffer refill.  Reads bytes from the current position
   * in the input.
   * @param b the array to read bytes into 数据读入的地方
   * @param offset the offset in the array to start storing bytes 当前指向的位置
   * @param length the number of bytes to read 读取的大小
   * 
   * 实现buffer的refill(重新填充). 并直接从输入读取当前位置开始的数据.
   */
  protected abstract void readInternal(byte[] b, int offset, int length)
          throws IOException;

  @Override
  public final long getFilePointer() { return bufferStart + bufferPosition; }

  @Override
  public final void seek(long pos) throws IOException {
	// 如果当前buffer中包含的数据, 能满足position(大于小于都不行)的话, 直接修改bufferPosition
    if (pos >= bufferStart && pos < (bufferStart + bufferLength))
      bufferPosition = (int)(pos - bufferStart);  // seek within buffer
    else {
      // 不能满足的话更改bufferStart到position
      bufferStart = pos;
      bufferPosition = 0;
      bufferLength = 0;  // trigger refill() on read() //在下次读取的时候触发undrefill()
      seekInternal(pos);
    }
  }

  /** Expert: implements seek.  Sets current position in this file, where the
   * next {@link #readInternal(byte[],int,int)} will occur.
   * @see #readInternal(byte[],int,int)
   * 
   * 实现seek. 设置在文件中当前的位置, 下一次读取发生的地方.
   */
  protected abstract void seekInternal(long pos) throws IOException;

  @Override
  public BufferedIndexInput clone() {
	// 调用super.clone()
    BufferedIndexInput clone = (BufferedIndexInput)super.clone();

    clone.buffer = null; // buffer设置为null
    clone.bufferLength = 0; // 初始化为0
    clone.bufferPosition = 0; // 初始化为0
    clone.bufferStart = getFilePointer(); // 获取当前的FilePoint做为buffer的起始位置

    return clone;
  }

  /**
   * Flushes the in-memory buffer to the given output, copying at most
   * <code>numBytes</code>.
   * <p>
   * <b>NOTE:</b> this method does not refill the buffer, however it does
   * advance the buffer position.
   * 
   * @return the number of bytes actually flushed from the in-memory buffer.
   * 
   * 刷新(flush)当前内存中的buffer到指定的output, 拷贝最多numBytes字节
   * 注意: 这个方法不会重新装载buffer, 虽然会让buffer的position向前移动
   * 
   * 返回实际上flush了多少byte的数据
   */
  protected final int flushBuffer(IndexOutput out, long numBytes) throws IOException {
	// 计算当前还有多少数据可以拷贝
    int toCopy = bufferLength - bufferPosition;
    // 如果比要求的最大值要大的话, 返回最大值
    if (toCopy > numBytes) {
      toCopy = (int) numBytes;
    }
    if (toCopy > 0) {
      // 写入到输出
      out.writeBytes(buffer, bufferPosition, toCopy);
      bufferPosition += toCopy; // 移动拷贝了的数量
    }
    return toCopy; // 返回拷贝了的数量
  }
  
  /**
   * Returns default buffer sizes for the given {@link org.apache.lucene.store.IOContext}
   * 根据当前的IOContext来返回不同的buffer大小
   * 如果是MERGE的情况的话返回MERGE_BUFFER_SIZE,
   * 不是的话就返回一般默认的BUFFER_SIZE
   */
  public static int bufferSize(IOContext context) {
    switch (context.context) {
    case MERGE:
      return MERGE_BUFFER_SIZE;
    default:
      return BUFFER_SIZE;
    }
  }
  
}
