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
import java.io.EOFException;

/** A memory-resident {@link org.apache.lucene.store.IndexInput} implementation.
 *  
 *  常驻内存的IndexInput实现(输入).
 *  @lucene.internal */
public class RAMInputStream extends IndexInput implements Cloneable {
  static final int BUFFER_SIZE = RAMOutputStream.BUFFER_SIZE; //跟输出流的bufferSIZE保持一致 

  private RAMFile file;
  private long length; // 使用file.length()做为输入的长度

  private byte[] currentBuffer;
  private int currentBufferIndex;
  
  private int bufferPosition;
  private long bufferStart;
  private int bufferLength;
  
  public RAMInputStream(String name, RAMFile f) throws IOException {
    super("RAMInputStream(name=" + name + ")");
    file = f;
    length = file.length;
    if (length/BUFFER_SIZE >= Integer.MAX_VALUE) {
      throw new IOException("RAMInputStream too large length=" + length + ": " + name); 
    }

    // make sure that we switch to the
    // first needed buffer lazily
    // 懒加载切换到地一个buffer
    currentBufferIndex = -1;
    currentBuffer = null;
  }

  @Override
  public void close() {
    // nothing to do here
  }

  @Override
  public long length() {
    return length;
  }

  @Override
  public byte readByte() throws IOException {
	// 读取一个byte, 如果当前已经是buffer的最后一位的话, 切换buffer
    if (bufferPosition >= bufferLength) {
      currentBufferIndex++;
      switchCurrentBuffer(true);
    }
    return currentBuffer[bufferPosition++];
  }

  @Override
  public void readBytes(byte[] b, int offset, int len) throws IOException {
	// 读取数据存入b
    while (len > 0) {
      if (bufferPosition >= bufferLength) {
        currentBufferIndex++;
        switchCurrentBuffer(true);
      }

      int remainInBuffer = bufferLength - bufferPosition;
      int bytesToCopy = len < remainInBuffer ? len : remainInBuffer;
      System.arraycopy(currentBuffer, bufferPosition, b, offset, bytesToCopy);
      offset += bytesToCopy;
      len -= bytesToCopy;
      bufferPosition += bytesToCopy;
    }
  }
  
  /**
   * 切换当前的buffer
   * @param enforceEOF 
   * @throws IOException
   */
  private final void switchCurrentBuffer(boolean enforceEOF) throws IOException {
	// 切换bufferStart到指定的currentBufferIndex的buffer
    bufferStart = (long) BUFFER_SIZE * (long) currentBufferIndex;
    // 是否已经没有buffer了
    if (currentBufferIndex >= file.numBuffers()) {
      // end of file reached, no more buffers left
      // 已经到文件末尾, 没有更多的buffer了
      // 如果指定了enforceEOF的话, 抛出EOFException
      if (enforceEOF) {
        throw new EOFException("read past EOF: " + this);
      } else {
        // Force EOF if a read takes place at this position
    	// 指定到文件末尾
        currentBufferIndex--;
        bufferPosition = BUFFER_SIZE;
      }
    } else {
      // 如果合法的话, 进行切换
      currentBuffer = file.getBuffer(currentBufferIndex);
      bufferPosition = 0;
      long buflen = length - bufferStart;
      bufferLength = buflen > BUFFER_SIZE ? BUFFER_SIZE : (int) buflen;
    }
  }

  @Override
  public long getFilePointer() {
    return currentBufferIndex < 0 ? 0 : bufferStart + bufferPosition;
  }

  @Override
  public void seek(long pos) throws IOException {
	// seek到特定的位置
    if (currentBuffer==null || pos < bufferStart || pos >= bufferStart + BUFFER_SIZE) {
      // 如果当前buffer没有引用, 或者pos的位置不在当前buffer中, 进行buffer切换
      currentBufferIndex = (int) (pos / BUFFER_SIZE);
      switchCurrentBuffer(false);
    }
    bufferPosition = (int) (pos % BUFFER_SIZE);
  }
}
