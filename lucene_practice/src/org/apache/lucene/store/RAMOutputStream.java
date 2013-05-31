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

import java.io.IOException;

/**
 * A memory-resident {@link org.apache.lucene.store.IndexOutput} implementation.
 *
 * 常驻内存的IndexOutput的实现(输出).
 * @lucene.internal
 */
public class RAMOutputStream extends IndexOutput {
  static final int BUFFER_SIZE = 1024; // 缓存的大小

  private RAMFile file; //指定的输出为一个RAMFile

  private byte[] currentBuffer; //当前的缓存
  private int currentBufferIndex; // 当前缓存的索引
  
  private int bufferPosition; // 当前buffer中的位置信息
  private long bufferStart; // 当前buffer的头在整个RAMFile中的位置
  private int bufferLength; // 当前buffer的长度

  /** Construct an empty output buffer. */
  public RAMOutputStream() {
    this(new RAMFile());
  }

  public RAMOutputStream(RAMFile f) {
    file = f;

    // make sure that we switch to the
    // first needed buffer lazily
    // 是以懒加载的方式切换到第一个buffer的
    currentBufferIndex = -1;
    currentBuffer = null;
  }

  /** Copy the current contents of this buffer to the named output. 
   *  拷贝当前的buffer内容到指定的IndexOutput(RAMFile中所有的内容)
   * */
  public void writeTo(IndexOutput out) throws IOException {
    flush();
    final long end = file.length;
    long pos = 0;
    int buffer = 0;
    while (pos < end) {
      int length = BUFFER_SIZE;
      // 计算下次要拷贝到的位置, 最大为当前整个buffer
      long nextPos = pos + length;
      // 如果越界了的话, 直接截取到end
      if (nextPos > end) {                        // at the last buffer
        length = (int)(end - pos);
      }
      // 输出
      out.writeBytes(file.getBuffer(buffer++), length);
      pos = nextPos;
    }
  }

  /** Copy the current contents of this buffer to output
   *  byte array 
   *  这个方法没有做目标byte[]的长度判断
   *  */
  public void writeTo(byte[] bytes, int offset) throws IOException {
    flush();
    final long end = file.length;
    long pos = 0;
    int buffer = 0;
    int bytesUpto = offset;
    while (pos < end) {
      int length = BUFFER_SIZE;
      long nextPos = pos + length;
      if (nextPos > end) {                        // at the last buffer
        length = (int)(end - pos);
      }
      System.arraycopy(file.getBuffer(buffer++), 0, bytes, bytesUpto, length);
      bytesUpto += length;
      pos = nextPos;
    }
  }

  /** Resets this to an empty file. 
   *  重新设置当前的OutputStream到一个空的文件.
   *  (其实直接把位置信息都设置成头, 然后把file的length设置成0)
   * */
  public void reset() {
    currentBuffer = null;
    currentBufferIndex = -1;
    bufferPosition = 0;
    bufferStart = 0;
    bufferLength = 0;
    file.setLength(0);
  }

  @Override
  public void close() throws IOException {
    flush();
  }

  @Override
  public void seek(long pos) throws IOException {
    // set the file length in case we seek back
    // and flush() has not been called yet
	// 设置文件长度, 这样我们可以seek回来
    setFileLength();
    // 找到指定的buffer的index 并跳转到那个位置
    if (pos < bufferStart || pos >= bufferStart + bufferLength) {
      currentBufferIndex = (int) (pos / BUFFER_SIZE);
      switchCurrentBuffer();
    }

    bufferPosition = (int) (pos % BUFFER_SIZE);
  }

  @Override
  public long length() {
    return file.length;
  }
  
  @Override
  public void writeByte(byte b) throws IOException {
	// 写入一个byte
    if (bufferPosition == bufferLength) {
      currentBufferIndex++;
      switchCurrentBuffer();
    }
    currentBuffer[bufferPosition++] = b;
  }

  @Override
  public void writeBytes(byte[] b, int offset, int len) throws IOException {
    assert b != null;
    while (len > 0) {
      // bufferPosition ==  bufferLength有两种情况
      // 1. 当前的buffer已经写满了
      // 2. 刚刚实例化完的时候bufferPosition = bufferLength = 0
      if (bufferPosition ==  bufferLength) {
        currentBufferIndex++; // 当没有实例化的时候为-1 ,然后切换到0
        switchCurrentBuffer(); // 切换当前buffer, 如果没buffer了会申请新的空间
      }
      // 获取当前buffer还可以写入多少数据
      int remainInBuffer = currentBuffer.length - bufferPosition;
      // 看还可以拷贝多少数据, 如果不够一次性拷贝的话, 先拷贝满当前的buffer, 然后继续循环
      int bytesToCopy = len < remainInBuffer ? len : remainInBuffer;
      System.arraycopy(b, offset, currentBuffer, bufferPosition, bytesToCopy);
      offset += bytesToCopy;
      len -= bytesToCopy;
      bufferPosition += bytesToCopy;
    }
  }
  
  /**
   * 切换当前的buffer
   * 1. 如果是一个buffer满了切换到下一个
   * 2. 如果是刚刚实例化的话, 初始化成第一个buffer的状态
   * 
   * switchCurrentBuffer是根据当前的currentBufferIndex进行切换的
   */
  private final void switchCurrentBuffer() {
	// 如果currentBuffer==file.numBuffers()说明没有多余的buffer了
    if (currentBufferIndex == file.numBuffers()) {
      // 默认往RAMFile里面添加一个BUFFER_SIZE(1024)大小的byte[] 
      currentBuffer = file.addBuffer(BUFFER_SIZE);
    } else {
      // 还有多余的话, 直接切换到下一个
      currentBuffer = file.getBuffer(currentBufferIndex);
    }
    // 指定位置到buffer的头上
    bufferPosition = 0;
    // bufferStart记录的是buffer的start在整个RAMFile中的位置
    bufferStart = (long) BUFFER_SIZE * (long) currentBufferIndex;
    // bufferLength代表当前的buffer长度
    bufferLength = currentBuffer.length;
  }
  
  /**
   * 设置文件的长度? 什么用?
   * 根据当前的buffer状态刷新文件的长度?
   * 是不是现在其实是直接写入的, 但是只有设置以后在RAMFile中才对外可见?
   */
  private void setFileLength() {
	// pointer获取当前buffer所指的RAMFile中的位置?
    long pointer = bufferStart + bufferPosition;
    // 如果point比fileLength大的话, 就把point设置成file的length
    if (pointer > file.length) {
      file.setLength(pointer);
    }
  }

  @Override
  public void flush() throws IOException {
    setFileLength();
  }

  @Override
  public long getFilePointer() {
	// 可用大小
    return currentBufferIndex < 0 ? 0 : bufferStart + bufferPosition;
  }

  /** Returns byte usage of all buffers. */
  public long sizeInBytes() {
	// 分配的大小
    return (long) file.numBuffers() * (long) BUFFER_SIZE;
  }  
}
