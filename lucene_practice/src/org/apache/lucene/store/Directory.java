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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Closeable;
import java.util.Collection; // for javadocs

import org.apache.lucene.util.IOUtils;

/** A Directory is a flat list of files.  Files may be written once, when they
 * are created.  Once a file is created it may only be opened for read, or
 * deleted.  Random access is permitted both when reading and writing.
 *
 * <p> Java's i/o APIs not used directly, but rather all i/o is
 * through this API.  This permits things such as: <ul>
 * <li> implementation of RAM-based indices;
 * <li> implementation indices stored in a database, via JDBC;
 * <li> implementation of an index as a single file;
 * </ul>
 *
 * Directory locking is implemented by an instance of {@link
 * LockFactory}, and can be changed for each Directory
 * instance using {@link #setLockFactory}.
 *
 * 一个Directory是一组文件.
 * 文件在创建的时候可以写入一次.
 * 一旦文件已经创建, 很有可能这个文件就只能被读或者删除.
 * 随机读取在写入或者读取的时候都允许.
 *
 * Java的IO没有直接被使用, 所有的i/o都通过这个API.
 * 这样可以允许一些实现, 比如:
 * 1.实现基于内存的索引
 * 2.实现把索引存在一个数据库当中, 通过JDBC
 * 3.实现把一个索引在一个文件中
 * (一个索引库可能会有好几个索引组成)
 *
 * 每一个Directory都有一个锁工厂, 可以通过setLockFactory来进行设置.
 */
public abstract class Directory implements Closeable {

  volatile protected boolean isOpen = true; // 是否被打开

  /** Holds the LockFactory instance (implements locking for
   * this Directory instance). */
  protected LockFactory lockFactory; // 锁工厂

  /**
   * Returns an array of strings, one for each file in the directory.
   * 
   * @throws NoSuchDirectoryException if the directory is not prepared for any
   *         write operations (such as {@link #createOutput(String, IOContext)}).
   * @throws java.io.IOException in case of other IO errors
   * 
   * 返回一个String[], 每一项都是一个filename?
   */
  public abstract String[] listAll() throws IOException;

  /** Returns true if a file with the given name exists. */
  // 根据name查看文件是否存在, 可以理解成对文件数组String[]的查找
  public abstract boolean fileExists(String name)
       throws IOException;

  /** Removes an existing file in the directory. */
  // 根据name删除文件
  public abstract void deleteFile(String name)
       throws IOException;

  /**
   * Returns the length of a file in the directory. This method follows the
   * following contract:
   * <ul>
   * <li>Throws {@link java.io.FileNotFoundException} if the file does not exist
   * <li>Returns a value &ge;0 if the file exists, which specifies its length.
   * </ul>
   * 
   * @param name the name of the file for which to return the length.
   * @throws java.io.FileNotFoundException if the file does not exist.
   * @throws java.io.IOException if there was an IO error while retrieving the file's
   *         length
   *         
   * 根据name返回Directory中一个文件的长度.
   * 这个方法遵循以下的合同(约定):
   * 1. 抛出java.io.FileNotFoundException当文件不存在的时候.
   * 2. 当文件存在的时候, 返回它的长度.
   */
  public abstract long fileLength(String name) throws IOException;


  /** Creates a new, empty file in the directory with the given name.
      Returns a stream writing this file.
      
      根据指定的name创建一个新的空的文件, 返回一个写入这个文件的流.
   */
  public abstract IndexOutput createOutput(String name, IOContext context)
       throws IOException;

  /**
   * Ensure that any writes to these files are moved to
   * stable storage.  Lucene uses this to properly commit
   * changes to the index, to prevent a machine/OS crash
   * from corrupting the index.<br/>
   * <br/>
   * NOTE: Clients may call this method for same files over
   * and over again, so some impls might optimize for that.
   * For other impls the operation can be a noop, for various
   * reasons.
   * 
   * 确实对所有文件的写入已经输出到稳定的存储(比如有些内容如果还在buffer中的话,
   * 需要flush). Lucene使用这个方法把提交的更改输出到索引, 为了防止因为不正确的
   * 索引而产生机器或者系统级别的错误.
   */
  public abstract void sync(Collection<String> names) throws IOException;

  /** Returns a stream reading an existing file, with the
   * specified read buffer size.  The particular Directory
   * implementation may ignore the buffer size.  Currently
   * the only Directory implementations that respect this
   * parameter are {@link FSDirectory} and {@link
   * CompoundFileDirectory}.
   * 
   * 打开一个文件返回一个读取的流, 可以指定读取的buffer大小.
   * 具体的Directory实现可能会忽略这个buffer大小.
   * 在现在的实现中只有FSDirectory关心这个参数.
  */
  public abstract IndexInput openInput(String name, IOContext context) throws IOException; 
  
  /** Construct a {@link org.apache.lucene.store.Lock}.
   * @param name the name of the lock file
   * 
   * 生成一个指定的锁
   * 产生一个Lock实例, 指定lockName(标识符)
   */
  public Lock makeLock(String name) {
      return lockFactory.makeLock(name);
  }
  /**
   * Attempt to clear (forcefully unlock and remove) the
   * specified lock.  Only call this at a time when you are
   * certain this lock is no longer in use.
   * @param name name of the lock to be cleared.
   * 
   * 尝试去清除指定的锁(强制解锁并删除).
   * 只有当你确实不会再使用这个锁的时候调用.
   */
  public void clearLock(String name) throws IOException {
    if (lockFactory != null) {
      lockFactory.clearLock(name);
    }
  }

  /** Closes the store. */
  /** 关闭 **/
  @Override
  public abstract void close()
       throws IOException;

  /**
   * Set the LockFactory that this Directory instance should
   * use for its locking implementation.  Each * instance of
   * LockFactory should only be used for one directory (ie,
   * do not share a single instance across multiple
   * Directories).
   *
   * @param lockFactory instance of {@link LockFactory}.
   * 
   * 设置LockFactory.
   * 每一个LockFactoy的实例应该只被一个directory使用.
   * (不要多个Directories共享一个实例)
   */
  public void setLockFactory(LockFactory lockFactory) throws IOException {
    assert lockFactory != null;
    this.lockFactory = lockFactory;
    lockFactory.setLockPrefix(this.getLockID());
  }

  /**
   * Get the LockFactory that this Directory instance is
   * using for its locking implementation.  Note that this
   * may be null for Directory implementations that provide
   * their own locking implementation.
   * 
   * 获取当前Directory的LockFactory.
   * 注意如果Directory采用自己的锁实现的话, 这个方法可能会返回null
   */
  public LockFactory getLockFactory() {
    return this.lockFactory;
  }

  /**
   * Return a string identifier that uniquely differentiates
   * this Directory instance from other Directory instances.
   * This ID should be the same if two Directory instances
   * (even in different JVMs and/or on different machines)
   * are considered "the same index".  This is how locking
   * "scopes" to the right index.
   * 
   * 返回一个标志符, 用户区分不同的Directory实例.
   * 如果两个Directory都是打开相同的索引的话, ID应该要相同.
   * 这样才能锁定索引正确的范围.
   */
  public String getLockID() {
      return this.toString();
  }
  
  /**
   * 加上锁工厂的toString()
   * 不覆盖的话, 直接拿这串来做为标识符
   */
  @Override
  public String toString() {
    return super.toString() + " lockFactory=" + getLockFactory();
  }

  /**
   * Copies the file <i>src</i> to {@link org.apache.lucene.store.Directory} <i>to</i> under the new
   * file name <i>dest</i>.
   * <p>
   * If you want to copy the entire source directory to the destination one, you
   * can do so like this:
   * 
   * <pre class="prettyprint">
   * Directory to; // the directory to copy to
   * for (String file : dir.listAll()) {
   *   dir.copy(to, file, newFile, IOContext.DEFAULT); // newFile can be either file, or a new name
   * }
   * </pre>
   * <p>
   * <b>NOTE:</b> this method does not check whether <i>dest</i> exist and will
   * overwrite it if it does.
   * 
   * 从当前Directory拷贝一个文件(src)到to(directory)下(dest).
   * 如果你想拷贝整个Directory,可以这么多
   * for(String file: dir.listAll(){
   * 	dir.copy(to, file, newFile, IOContext.DeFAULT);
   * }
   * 注意: 这个方法不会去检查dest是否已经存在, 如果存在的话会直接覆盖它.
   */
  public void copy(Directory to, String src, String dest, IOContext context) throws IOException {
    IndexOutput os = null;
    IndexInput is = null;
    IOException priorException = null;
    try {
      // 在to(Directory下面创建一个新的文件)
      os = to.createOutput(dest, context);
      // 打开当前下的src文件
      is = openInput(src, context);
      // 执行拷贝
      os.copyBytes(is, is.length());
    } catch (IOException ioe) {
      // 捕获异常
      priorException = ioe;
    } finally {
      boolean success = false;
      try {
        IOUtils.closeWhileHandlingException(priorException, os, is);
        // 如果处理失败, 则success不会等于true
        success = true;
      } finally {
        if (!success) {
          try {
        	// 如果处理不成功, 尝试去删除文件
            to.deleteFile(dest);
          } catch (Throwable t) {
          }
        }
      }
    }
  }

  /**
   * Creates an {@link org.apache.lucene.store.Directory.IndexInputSlicer} for the given file name.
   * IndexInputSlicer allows other {@link org.apache.lucene.store.Directory} implementations to
   * efficiently open one or more sliced {@link IndexInput} instances from a
   * single file handle. The underlying file handle is kept open until the
   * {@link org.apache.lucene.store.Directory.IndexInputSlicer} is closed.
   *
   * @throws java.io.IOException
   *           if an {@link java.io.IOException} occurs
   * @lucene.internal
   * @lucene.experimental
   * 
   * 创建一个分片器
   * 给当前制定file name的文件创建一个分片.
   * IndexInputSlicer允许Directory实现去有效地打开一个或者多个分配实例(基于IndexInput)从一个单一的文件.
   * 底层文件处理一直被打开直到分片器关闭.
   */
  public IndexInputSlicer createSlicer(final String name, final IOContext context) throws IOException {
    ensureOpen();
    return new IndexInputSlicer() {
      // 根据name找到文件
      private final IndexInput base = Directory.this.openInput(name, context);
      @Override
      public IndexInput openSlice(String sliceDescription, long offset, long length) {
    	// 返回一个分片的输入
        return new SlicedIndexInput("SlicedIndexInput(" + sliceDescription + " in " + base + ")", base, offset, length);
      }
      @Override
      public void close() throws IOException {
    	// 关闭文件
        base.close();
      }
      @Override
      public IndexInput openFullSlice() {
        return base.clone();
      }
    };
  }

  /**
   * @throws AlreadyClosedException if this Directory is closed
   */
  protected final void ensureOpen() throws AlreadyClosedException {
    if (!isOpen)
      throw new AlreadyClosedException("this Directory is closed");
  }
  
  /**
   * Allows to create one or more sliced {@link IndexInput} instances from a single 
   * file handle. Some {@link org.apache.lucene.store.Directory} implementations may be able to efficiently map slices of a file
   * into memory when only certain parts of a file are required.   
   * @lucene.internal
   * @lucene.experimental
   * 
   * 对于一个文件允许建立一个或者多个分片(slice).
   * 有一些Directory的实现可以有效对一个文件map几个分片到内存, 当部分文件需要的时候.
   */
  public abstract class IndexInputSlicer implements Closeable {
    /**
     * Returns an {@link IndexInput} slice starting at the given offset with the given length.
     * 返回一个分片, 从offset开始, 长度为length
     */
    public abstract IndexInput openSlice(String sliceDescription, long offset, long length) throws IOException;

    /**
     * Returns an {@link IndexInput} slice starting at offset <i>0</i> with a
     * length equal to the length of the underlying file
     * @deprecated Only for reading CFS files from 3.x indexes.
     */
    @Deprecated
    // can we remove this somehow?
    public abstract IndexInput openFullSlice() throws IOException;
  }
  
  /** Implementation of an IndexInput that reads from a portion of
   *  a file.
   *  一个IndexInput的实现, 读取文件的一部分
   */
  private static final class SlicedIndexInput extends BufferedIndexInput {
    IndexInput base;
    long fileOffset;
    long length;
    
    SlicedIndexInput(final String sliceDescription, final IndexInput base, final long fileOffset, final long length) {
      this(sliceDescription, base, fileOffset, length, BufferedIndexInput.BUFFER_SIZE);
    }
    
    SlicedIndexInput(final String sliceDescription, final IndexInput base, final long fileOffset, final long length, int readBufferSize) {
      super("SlicedIndexInput(" + sliceDescription + " in " + base + " slice=" + fileOffset + ":" + (fileOffset+length) + ")", readBufferSize);
      this.base = base.clone();
      this.fileOffset = fileOffset;
      this.length = length;
    }
    
    @Override
    public SlicedIndexInput clone() {
      SlicedIndexInput clone = (SlicedIndexInput)super.clone();
      clone.base = base.clone();
      clone.fileOffset = fileOffset;
      clone.length = length;
      return clone;
    }
    
    /** Expert: implements buffer refill.  Reads bytes from the current
     *  position in the input.
     * @param b the array to read bytes into
     * @param offset the offset in the array to start storing bytes
     * @param len the number of bytes to read
     */
    @Override
    protected void readInternal(byte[] b, int offset, int len) throws IOException {
      long start = getFilePointer();
      if(start + len > length)
        throw new EOFException("read past EOF: " + this);
      base.seek(fileOffset + start);
      base.readBytes(b, offset, len, false);
    }
    
    /** Expert: implements seek.  Sets current position in this file, where
     *  the next {@link #readInternal(byte[],int,int)} will occur.
     * @see #readInternal(byte[],int,int)
     */
    @Override
    protected void seekInternal(long pos) {}
    
    /** Closes the stream to further operations. */
    @Override
    public void close() throws IOException {
      base.close();
    }
    
    @Override
    public long length() {
      return length;
    }
  }
}
