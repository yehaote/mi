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

import java.util.ArrayList;

/** 
 * Represents a file in RAM as a list of byte[] buffers.
 * 
 * 在内存中以一个byte[]的List来表现一个文件.
 * @lucene.internal */
public class RAMFile {
  protected ArrayList<byte[]> buffers = new ArrayList<byte[]>(); // 储存内容的地方
  long length; // 记录当前文件的长度(单位byte)
  RAMDirectory directory; // 反向链接指向RAMDirectory
  protected long sizeInBytes; // 当前RAMFile的大小(以byte为单位)

  // File used as buffer, in no RAMDirectory
  /**
   * 使用无参的构造函数来实例化RAMFile的话,
   * 一般用于表现一个buffer而不是在RAMDirectory
   */
  public RAMFile() {}
  
  RAMFile(RAMDirectory directory) {
    this.directory = directory;
  }

  // For non-stream access from thread that might be concurrent with writing
  // 对于不是以流形式进行的访问
  // 可能是同时写入的线程
  public synchronized long getLength() {
    return length;
  }

  protected synchronized void setLength(long length) {
    this.length = length;
  }
  
  /**
   * 根据size添加一个空的buffer
   * @param size
   * @return
   */
  protected final byte[] addBuffer(int size) {
	// 产生一个新的buffer
    byte[] buffer = newBuffer(size);
    // 同步添加到当前的list中
    synchronized(this) {
      buffers.add(buffer);
      // 当前RAMFile的size增加
      sizeInBytes += size;
    }
    // 如果是RAMDirectory中的RAMFile的话,
    // 增加directory的size
    if (directory != null) {
      // RAMDirectory的sizeInBytes是一个Atomic类, 所以不需要同步
      directory.sizeInBytes.getAndAdd(size);
    }
    // 返回当前实例化出来的buffer
    return buffer;
  }

  // 根据index获取buffer
  protected final synchronized byte[] getBuffer(int index) {
    return buffers.get(index);
  }
  
  // 显示buffer总数
  protected final synchronized int numBuffers() {
    return buffers.size();
  }

  /**
   * Expert: allocate a new buffer. 
   * Subclasses can allocate differently. 
   * @param size size of allocated buffer.
   * @return allocated buffer.
   * 
   * 分配一个新的buffer.
   * 子类可以以不同的方式进行分配.
   */
  protected byte[] newBuffer(int size) {
    return new byte[size];
  }
  
  // 获取当前RAMFile的size
  public synchronized long getSizeInBytes() {
    return sizeInBytes;
  }
  
}
