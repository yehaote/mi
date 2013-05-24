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

import java.io.Closeable;
import java.io.IOException;

/** Abstract base class for input from a file in a {@link org.apache.lucene.store.Directory}.  A
 * random-access input stream.  Used for all Lucene index input operations.
 *
 * <p>{@code IndexInput} may only be used from one thread, because it is not
 * thread safe (it keeps internal state like file position). To allow
 * multithreaded use, every {@code IndexInput} instance must be cloned before
 * used in another thread. Subclasses must therefore implement {@link #clone()},
 * returning a new {@code IndexInput} which operates on the same underlying
 * resource, but positioned independently. Lucene never closes cloned
 * {@code IndexInput}s, it will only do this on the original one.
 * The original instance must take care that cloned instances throw
 * {@link AlreadyClosedException} when the original one is closed.
 
 * @see org.apache.lucene.store.Directory
 * 
 * 在Directory用于表现从一个文件的收入的抽象类.
 * 一个可以随机访问的输入流. 用于所有Lucene索引输入操作.
 * 
 * 跟DataInput一样单线程使用, 多线程使用的时候需要使用clone来new出一个新的实例.
 * Lucene永远不会关闭它clone出来的其他实例, 只会关闭当前它自己.
 * 所以原始的IndexInput必须注意它clone出来的, 不然的话原始的IndexInput关闭以后, 
 * 会抛出AlreadyClosedException.
 */
public abstract class IndexInput extends DataInput implements Cloneable,Closeable {
	
  private final String resourceDescription; // 资源描述符号

  /** resourceDescription should be a non-null, opaque string
   *  describing this resource; it's returned from
   *  {@link #toString}.
   *  
   *  资源描述符不能为空, 
   *  */
  protected IndexInput(String resourceDescription) {
    if (resourceDescription == null) {
      throw new IllegalArgumentException("resourceDescription must not be null");
    }
    this.resourceDescription = resourceDescription;
  }

  /** Closes the stream to further operations.
   *  关闭流
   *  */
  @Override
  public abstract void close() throws IOException;

  /** Returns the current position in this file, where the next read will
   * occur.
   * 返回当前在文件中的位置, 就是下次读取开始的位置
   * @see #seek(long)
   */
  public abstract long getFilePointer();

  /** Sets current position in this file, where the next read will occur.
   * @see #getFilePointer()
   * 设置当前的position到一个特定的位置.
   */
  public abstract void seek(long pos) throws IOException;

  /** The number of bytes in the file. 
   * 当前文件的长度(byte)
   * */
  public abstract long length();

  @Override
  public String toString() {
	  // 直接返回资源描述符
    return resourceDescription;
  }
  
  /** {@inheritDoc}
   * <p><b>Warning:</b> Lucene never closes cloned
   * {@code IndexInput}s, it will only do this on the original one.
   * The original instance must take care that cloned instances throw
   * {@link AlreadyClosedException} when the original one is closed.
   * 
   * 注意Lucene不会去关闭clone出来的流.
   */
  @Override
  public IndexInput clone() {
    return (IndexInput) super.clone();
  }
}
