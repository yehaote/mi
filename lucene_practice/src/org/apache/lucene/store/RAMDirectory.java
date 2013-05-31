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
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


/**
 * A memory-resident {@link org.apache.lucene.store.Directory} implementation.  Locking
 * implementation is by default the {@link SingleInstanceLockFactory}
 * but can be changed with {@link #setLockFactory}.
 * 
 * <p><b>Warning:</b> This class is not intended to work with huge
 * indexes. Everything beyond several hundred megabytes will waste
 * resources (GC cycles), because it uses an internal buffer size
 * of 1024 bytes, producing millions of {@code byte[1024]} arrays.
 * This class is optimized for small memory-resident indexes.
 * It also has bad concurrency on multithreaded environments.
 * 
 * <p>It is recommended to materialize large indexes on disk and use
 * {@link MMapDirectory}, which is a high-performance directory
 * implementation working directly on the file system cache of the
 * operating system, so copying data to Java heap space is not useful.
 * 
 * 驻在内存中的Directory的实现.
 * 锁实现使用默认的SingleInstanceLockFactory(单例锁工厂), 可以设置为不同的.
 * 
 * 注意: 这个类不能用来支持大的索引工作. 
 * 所有的大于几百MB的索引会非常消耗资源(GC循环), 因为在它的内部使用一个大小为1024 byte的缓存,
 * 产生数百万的byte[1024]的数组. 
 * 这个类对于小内存常驻的索引进行了优化. 当在多线程的情况下, 它的并发性能也很差.
 * 
 * 推荐使用MMapDirectory基于磁盘来实现的大的索引, MMapDirectory是一个高性能的directory实现,
 * 直接跟系统系统的文件缓存一起工作, 所以把数据拷贝到java heap是没有什么意义的.
 * 
 * RAMDirectory主要是一组RAMFile, 以Map的方式存储,
 * 而RAMFile是一组byte[], 以ArrayList的方式存储.
 */
public class RAMDirectory extends Directory {
  // 使用同步的HashMap
  protected final Map<String,RAMFile> fileMap = new ConcurrentHashMap<String,RAMFile>();
  protected final AtomicLong sizeInBytes = new AtomicLong(); // 计算总的byte大小
  
  // *****
  // Lock acquisition sequence:  RAMDirectory, then RAMFile
  // 锁获取顺序: RAMDirectory, 然后RAMFile
  // ***** 

  /** Constructs an empty {@link org.apache.lucene.store.Directory}. */
  public RAMDirectory() {
    try {
      // 默认使用SingleInstanceLockFactory
      setLockFactory(new SingleInstanceLockFactory());
    } catch (IOException e) {
      // Cannot happen
    }
  }

  /**
   * Creates a new <code>RAMDirectory</code> instance from a different
   * <code>Directory</code> implementation.  This can be used to load
   * a disk-based index into memory.
   * 
   * <p><b>Warning:</b> This class is not intended to work with huge
   * indexes. Everything beyond several hundred megabytes will waste
   * resources (GC cycles), because it uses an internal buffer size
   * of 1024 bytes, producing millions of {@code byte[1024]} arrays.
   * This class is optimized for small memory-resident indexes.
   * It also has bad concurrency on multithreaded environments.
   * 
   * <p>For disk-based indexes it is recommended to use
   * {@link MMapDirectory}, which is a high-performance directory
   * implementation working directly on the file system cache of the
   * operating system, so copying data to Java heap space is not useful.
   * 
   * <p>Note that the resulting <code>RAMDirectory</code> instance is fully
   * independent from the original <code>Directory</code> (it is a
   * complete copy).  Any subsequent changes to the
   * original <code>Directory</code> will not be visible in the
   * <code>RAMDirectory</code> instance.
   *
   * @param dir a <code>Directory</code> value
   * @exception java.io.IOException if an error occurs
   * 
   * 从一个指定的Directory创建一个新的RAMDirectory实例.
   * 这个方法可以用来拷贝一个基于磁盘的索引到内存中.
   * 
   * 注意: 这个类不能用来支持大的索引工作. 
   * 所有的大于几百MB的索引会非常消耗资源(GC循环), 因为在它的内部使用一个大小为1024 byte的缓存,
   * 产生数百万的byte[1024]的数组. 
   * 这个类对于小内存常驻的索引进行了优化. 当在多线程的情况下, 它的并发性能也很差.
   * 
   * 注意: 这样实例化出来的RAMDirectory是完全独立的, 它仅仅是原来的DIrectory的一个拷贝.
   * 所有对于它们的更改都不会在对方上起作用.
   */
  public RAMDirectory(Directory dir, IOContext context) throws IOException {
    this(dir, false, context);
  }
  
  /**
   * 
   * @param dir 需要拷贝的索引的Dir(Src)
   * @param closeDir 是否在拷贝完成以后, 关闭源索引Directory
   * @param context 
   * @throws IOException
   */
  private RAMDirectory(Directory dir, boolean closeDir, IOContext context) throws IOException {
    // 使用SingleInstanceLockFactory做为锁工厂
	this();
    // 迭代出src Directory中的中的所有文件, 然后拷贝到当前的Directory中
	// 文件名还是保持一致
    for (String file : dir.listAll()) {
      dir.copy(this, file, file, context);
    }
    if (closeDir) {
      dir.close();
    }
  }

  @Override
  public final String[] listAll() {
    ensureOpen();
    // NOTE: fileMap.keySet().toArray(new String[0]) is broken in non Sun JDKs,
    // and the code below is resilient to map changes during the array population.
    Set<String> fileNames = fileMap.keySet();
    List<String> names = new ArrayList<String>(fileNames.size());
    for (String name : fileNames) names.add(name);
    return names.toArray(new String[names.size()]);
  }

  /** Returns true iff the named file exists in this directory. 
   *  当且仅当指定name的文件存在directory中的时候返回true
   * */
  @Override
  public final boolean fileExists(String name) {
    ensureOpen();
    return fileMap.containsKey(name);
  }

  /** Returns the length in bytes of a file in the directory.
   * @throws java.io.IOException if the file does not exist
   * 
   * 根据文件的name, 返回对应文件的长度(单位:byte)
   */
  @Override
  public final long fileLength(String name) throws IOException {
    ensureOpen();
    RAMFile file = fileMap.get(name);
    if (file == null) {
      throw new FileNotFoundException(name);
    }
    return file.getLength();
  }
  
  /**
   * Return total size in bytes of all files in this directory. This is
   * currently quantized to RAMOutputStream.BUFFER_SIZE.
   * 
   * 返回所有文件在这个Directory中的大小总和(单位byte).
   */
  public final long sizeInBytes() {
    ensureOpen();
    return sizeInBytes.get();
  }
  
  /** Removes an existing file in the directory.
   * @throws java.io.IOException if the file does not exist
   * 
   * 删除一个指定的文件
   */
  @Override
  public void deleteFile(String name) throws IOException {
    ensureOpen();
    RAMFile file = fileMap.remove(name);
    if (file != null) {
      file.directory = null;
      sizeInBytes.addAndGet(-file.sizeInBytes);
    } else {
      throw new FileNotFoundException(name);
    }
  }

  /** Creates a new, empty file in the directory with the given name. Returns a stream writing this file. 
   *  根据指定的文件名在当前的Directory中创建一个新的空的文件.
   *  返回这个创建的文件的写入流.
   * */
  @Override
  public IndexOutput createOutput(String name, IOContext context) throws IOException {
    ensureOpen();
    // 实例化一个新的RAMFile
    RAMFile file = newRAMFile();
    // 去获取是否有当前name存在的RAMFile
    RAMFile existing = fileMap.remove(name);
    if (existing != null) {
      // 如果当前的name已经存在的话, 把这个已经存在的RAMFile的文件的大小从sizeInBytes中删除
      // 类似于删除这个已经存在的文件
      sizeInBytes.addAndGet(-existing.sizeInBytes);
      existing.directory = null;
    }
    // 放入新生成的文件
    fileMap.put(name, file);
    // 并返回这个RAMFile的OutputStream
    return new RAMOutputStream(file);
  }

  /**
   * Returns a new {@link org.apache.lucene.store.RAMFile} for storing data. This method can be
   * overridden to return different {@link org.apache.lucene.store.RAMFile} impls, that e.g. override
   * {@link org.apache.lucene.store.RAMFile#newBuffer(int)}.
   * 
   * 实例化一个新的newRAMFile实例用于存储数据, 并返回.
   * 这个方法可以被覆盖用于返回不同的RAMFile的实现.
   * 比如覆盖RAMFile.newBuffer()的实现.
   */
  protected RAMFile newRAMFile() {
    return new RAMFile(this);
  }

  @Override
  public void sync(Collection<String> names) throws IOException {
	  // 因为都是RAM中, 所以不需要sync
  }

  /** Returns a stream reading an existing file. 
   *  根据文件名, 打开一个当前Directory中的文件
   * */
  @Override
  public IndexInput openInput(String name, IOContext context) throws IOException {
    ensureOpen();
    RAMFile file = fileMap.get(name);
    if (file == null) {
      throw new FileNotFoundException(name);
    }
    return new RAMInputStream(name, file);
  }

  /** Closes the store to future operations, releasing associated memory. 
   *  关闭索引, 释放相关联的内存
   * */
  @Override
  public void close() {
    isOpen = false;
    fileMap.clear();
  }
}
