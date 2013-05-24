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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.util.IOUtils;

/**
 * Abstract base class for performing read operations of Lucene's low-level
 * data types.
 *
 * <p>{@code DataInput} may only be used from one thread, because it is not
 * thread safe (it keeps internal state like file position). To allow
 * multithreaded use, every {@code DataInput} instance must be cloned before
 * used in another thread. Subclasses must therefore implement {@link #clone()},
 * returning a new {@code DataInput} which operates on the same underlying
 * resource, but positioned independently.
 * 
 * Lucene底层地读取数据的基类
 * 
 * 它不是线程安全的, 所以需要在一个线程中去使用,
 * 因为它会在类里面保存状态, 比如文件的位置(position).
 * 为了在多线程中使用, 每一个DataInput在被另外一个线程使用之前, 
 * 必须先Clone.
 * 子类因此在实现的时候, 必须写一个clone()方法返回一个新的DataInput,
 * 这个新的DataInput跟原先的使用相同的底层资源. 不过位置参数这个信息是
 * 相互独立的.
 */
public abstract class DataInput implements Cloneable {
  /** Reads and returns a single byte.
   * @see DataOutput#writeByte(byte)
   * 
   * 读取一个byte
   */
  public abstract byte readByte() throws IOException;

  /** Reads a specified number of bytes into an array at the specified offset.
   * @param b the array to read bytes into
   * @param offset the offset in the array to start storing bytes
   * @param len the number of bytes to read
   * @see DataOutput#writeBytes(byte[],int)
   * 
   * 读取指定数量的byte到一个数组从offset位置开始存放.
   */
  public abstract void readBytes(byte[] b, int offset, int len)
    throws IOException;

  /** Reads a specified number of bytes into an array at the
   * specified offset with control over whether the read
   * should be buffered (callers who have their own buffer
   * should pass in "false" for useBuffer).  Currently only
   * {@link BufferedIndexInput} respects this parameter.
   * @param b the array to read bytes into
   * @param offset the offset in the array to start storing bytes
   * @param len the number of bytes to read
   * @param useBuffer set to false if the caller will handle
   * buffering.
   * @see DataOutput#writeBytes(byte[],int)
   * 
   * 读取指定数量的byte到一个数组从offset位置开始存放.
   * 可以指定是否使用缓存来进行去读,  默认的实现是不管缓存的.
   * 现在只有BufferedIndexInput对这个userBuffer参数起效.
   */
  public void readBytes(byte[] b, int offset, int len, boolean useBuffer)
    throws IOException
  {
    // Default to ignoring useBuffer entirely
    readBytes(b, offset, len);
  }

  /** Reads two bytes and returns a short.
   * @see DataOutput#writeByte(byte)
   * 
   * 读取两个byte, 返回一个short
   */
  public short readShort() throws IOException {
    return (short) (((readByte() & 0xFF) <<  8) |  (readByte() & 0xFF));
  }

  /** Reads four bytes and returns an int.
   * @see DataOutput#writeInt(int)
   * 
   * 读取4个byte, 返回一个int
   */
  public int readInt() throws IOException {
    return ((readByte() & 0xFF) << 24) | ((readByte() & 0xFF) << 16)
         | ((readByte() & 0xFF) <<  8) |  (readByte() & 0xFF);
  }

  /** Reads an int stored in variable-length format.  Reads between one and
   * five bytes.  Smaller values take fewer bytes.  Negative numbers are not
   * supported.
   * <p>
   * The format is described further in {@link DataOutput#writeVInt(int)}.
   * 
   * 读取一个变长的int.
   * 长度在1-5个byte之间.
   * 越小的值使用的byte数目越少.
   * 负数不支持.
   * @see DataOutput#writeVInt(int)
   */
  public int readVInt() throws IOException {
    /* This is the original code of this method,
     * but a Hotspot bug (see LUCENE-2975) corrupts the for-loop if
     * readByte() is inlined. So the loop was unwinded!
    byte b = readByte();
    int i = b & 0x7F;
    for (int shift = 7; (b & 0x80) != 0; shift += 7) {
      b = readByte();
      i |= (b & 0x7F) << shift;
    }
    return i;
    */
	/**
	 * 读取一个byte, 如果>=0直接返回.
	 * 0x7F(0111 1111)
	 * vint使用高第一位作为判断是否是vint结束.
	 * 如果小于0, 说明还有数据要读取.
	 * 整个vint是低位编码的方式, 就是说越后面读取的byte其实是
	 * vint越前面的byte.
	 * 保留前7位, 并左移7位, 继续读取一个byte并累加上.
	 * 最多读取5个byte.
	 * 
	 * 因为一个int最多是32位, 所以如果是第五个byte的时候,
	 * 只读取后4位, 并判断剩下的前4位是否等于0, 来做校验.
	 */
    byte b = readByte();
    if (b >= 0) return b;
    int i = b & 0x7F;
    b = readByte();
    i |= (b & 0x7F) << 7;
    if (b >= 0) return i;
    b = readByte();
    i |= (b & 0x7F) << 14;
    if (b >= 0) return i;
    b = readByte();
    i |= (b & 0x7F) << 21;
    if (b >= 0) return i;
    b = readByte();
    // Warning: the next ands use 0x0F / 0xF0 - beware copy/paste errors:
    i |= (b & 0x0F) << 28;
    if ((b & 0xF0) == 0) return i;
    throw new IOException("Invalid vInt detected (too many bits)");
  }

  /** Reads eight bytes and returns a long.
   * @see DataOutput#writeLong(long)
   * 
   * 读取8个byte返回一个long
   */
  public long readLong() throws IOException {
    return (((long)readInt()) << 32) | (readInt() & 0xFFFFFFFFL);
  }

  /** Reads a long stored in variable-length format.  Reads between one and
   * nine bytes.  Smaller values take fewer bytes.  Negative numbers are not
   * supported.
   * <p>
   * The format is described further in {@link DataOutput#writeVInt(int)}.
   * 
   * 跟vint类似
   * @see DataOutput#writeVLong(long)
   */
  public long readVLong() throws IOException {
    /* This is the original code of this method,
     * but a Hotspot bug (see LUCENE-2975) corrupts the for-loop if
     * readByte() is inlined. So the loop was unwinded!
    byte b = readByte();
    long i = b & 0x7F;
    for (int shift = 7; (b & 0x80) != 0; shift += 7) {
      b = readByte();
      i |= (b & 0x7FL) << shift;
    }
    return i;
    */
    byte b = readByte();
    if (b >= 0) return b;
    long i = b & 0x7FL;
    b = readByte();
    i |= (b & 0x7FL) << 7;
    if (b >= 0) return i;
    b = readByte();
    i |= (b & 0x7FL) << 14;
    if (b >= 0) return i;
    b = readByte();
    i |= (b & 0x7FL) << 21;
    if (b >= 0) return i;
    b = readByte();
    i |= (b & 0x7FL) << 28;
    if (b >= 0) return i;
    b = readByte();
    i |= (b & 0x7FL) << 35;
    if (b >= 0) return i;
    b = readByte();
    i |= (b & 0x7FL) << 42;
    if (b >= 0) return i;
    b = readByte();
    i |= (b & 0x7FL) << 49;
    if (b >= 0) return i;
    b = readByte();
    i |= (b & 0x7FL) << 56;
    if (b >= 0) return i;
    throw new IOException("Invalid vLong detected (negative values disallowed)");
  }

  /** Reads a string.
   * 
   * 读取一个String, 这个String最前面存储的是一个vint, 代表String的长度(单位为byte).
   * @see DataOutput#writeString(String)
   */
  public String readString() throws IOException {
    int length = readVInt();
    final byte[] bytes = new byte[length];
    readBytes(bytes, 0, length);
    return new String(bytes, 0, length, IOUtils.CHARSET_UTF_8); // 注意指定编码方式为UTF-8
  }

  /** Returns a clone of this stream.
   *
   * <p>Clones of a stream access the same data, and are positioned at the same
   * point as the stream they were cloned from.
   *
   * <p>Expert: Subclasses must ensure that clones may be positioned at
   * different points in the input from each other and from the stream they
   * were cloned from.
   * 
   * 返回一个clone的流
   * clone一个流, 访问相同的数据, 并跳到当前的DataInput指向的position.
   */
  @Override
  public DataInput clone() {
    try {
      return (DataInput) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new Error("This cannot happen: Failing to clone DataInput");
    }
  }

  /** Reads a Map&lt;String,String&gt; previously written
   *  with {@link DataOutput#writeStringStringMap(java.util.Map)}.
   *  读取一个Map, 这个map数据流第一位存的是一个int代表有多少entity,
   *  接下来都是以String对的形式进行存储的.
   *  原先的写入的方法是 DataOutput.writeStringStringMap
   */
  public Map<String,String> readStringStringMap() throws IOException {
    final Map<String,String> map = new HashMap<String,String>();
    final int count = readInt();
    for(int i=0;i<count;i++) {
      final String key = readString();
      final String val = readString();
      map.put(key, val);
    }

    return map;
  }

  /** Reads a Set&lt;String&gt; previously written
   *  with {@link DataOutput#writeStringSet(java.util.Set)}.
   *  读取一个Set, 这个Set数据流第一位存的是一个int代表有多少entity,
   *  接下来都是以String的形式进行存储的.
   *  原先的写入的方法是 DataOutput.writeStringStringSet
   */
  public Set<String> readStringSet() throws IOException {
    final Set<String> set = new HashSet<String>();
    final int count = readInt();
    for(int i=0;i<count;i++) {
      set.add(readString());
    }

    return set;
  }
}
