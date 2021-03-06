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
import java.util.Map;
import java.util.Set;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.UnicodeUtil;

/**
 * Abstract base class for performing write operations of Lucene's low-level
 * data types.
 
 * <p>{@code DataOutput} may only be used from one thread, because it is not
 * thread safe (it keeps internal state like file position).
 * 
 * Lucene底层写入操作的抽象类
 * 不是线程安全的, 应为会在class本地记录position的信息.
 */
public abstract class DataOutput {

  /** Writes a single byte.
   * <p>
   * The most primitive data type is an eight-bit byte. Files are 
   * accessed as sequences of bytes. All other data types are defined 
   * as sequences of bytes, so file formats are byte-order independent.
   * 
   * @see IndexInput#readByte()
   * 
   * 写入一个Byte
   * 最主要的数据类型, 8bit长.
   * 文件被访问的时候是byte数组. 其他的数据类型也可以定义成byte数组.
   */
  public abstract void writeByte(byte b) throws IOException;

  /** Writes an array of bytes.
   * @param b the bytes to write
   * @param length the number of bytes to write
   * @see org.apache.lucene.store.DataInput#readBytes(byte[],int,int)
   * 
   * 写入一个byte数组, 从0开始写入length长度
   */
  public void writeBytes(byte[] b, int length) throws IOException {
    writeBytes(b, 0, length);
  }

  /** Writes an array of bytes.
   * @param b the bytes to write
   * @param offset the offset in the byte array
   * @param length the number of bytes to write
   * @see org.apache.lucene.store.DataInput#readBytes(byte[],int,int)
   * 
   * 写入一个byte数组, 从offset开始写入length长度
   */
  public abstract void writeBytes(byte[] b, int offset, int length) throws IOException;

  /** Writes an int as four bytes.
   * <p>
   * 32-bit unsigned integer written as four bytes, high-order bytes first.
   * 
   * @see org.apache.lucene.store.DataInput#readInt()
   * 
   * 写入一个int, 4次写入byte
   */
  public void writeInt(int i) throws IOException {
    writeByte((byte)(i >> 24));
    writeByte((byte)(i >> 16));
    writeByte((byte)(i >>  8));
    writeByte((byte) i);
  }
  
  /** Writes a short as two bytes.
   * @see org.apache.lucene.store.DataInput#readShort()
   * 
   * 写入一个short, 两个byte
   */
  public void writeShort(short i) throws IOException {
    writeByte((byte)(i >>  8));
    writeByte((byte) i);
  }

  /** Writes an int in a variable-length format.  Writes between one and
   * five bytes.  Smaller values take fewer bytes.  Negative numbers are
   * supported, but should be avoided.
   * <p>VByte is a variable-length format for positive integers is defined where the
   * high-order bit of each byte indicates whether more bytes remain to be read. The
   * low-order seven bits are appended as increasingly more significant bits in the
   * resulting integer value. Thus values from zero to 127 may be stored in a single
   * byte, values from 128 to 16,383 may be stored in two bytes, and so on.</p>
   * 
   * 写入一个变长编码的int. 占用1-5个byte.
   * 越小的值占用越少的byte, 负数不支持.
   * 一个byte. 高第一位储存是否还有其他的byte, 低1-7位储存值.
   * <p>VByte Encoding Example</p>
   * <table cellspacing="0" cellpadding="2" border="0">
   * <col width="64*">
   * <col width="64*">
   * <col width="64*">
   * <col width="64*">
   * <tr valign="top">
   *   <th align="left" width="25%">Value</th>
   *   <th align="left" width="25%">Byte 1</th>
   *   <th align="left" width="25%">Byte 2</th>
   *   <th align="left" width="25%">Byte 3</th>
   * </tr>
   * <tr valign="bottom">
   *   <td width="25%">0</td>
   *   <td width="25%"><kbd>00000000</kbd></td>
   *   <td width="25%"></td>
   *   <td width="25%"></td>
   * </tr>
   * <tr valign="bottom">
   *   <td width="25%">1</td>
   *   <td width="25%"><kbd>00000001</kbd></td>
   *   <td width="25%"></td>
   *   <td width="25%"></td>
   * </tr>
   * <tr valign="bottom">
   *   <td width="25%">2</td>
   *   <td width="25%"><kbd>00000010</kbd></td>
   *   <td width="25%"></td>
   *   <td width="25%"></td>
   * </tr>
   * <tr>
   *   <td valign="top" width="25%">...</td>
   *   <td valign="bottom" width="25%"></td>
   *   <td valign="bottom" width="25%"></td>
   *   <td valign="bottom" width="25%"></td>
   * </tr>
   * <tr valign="bottom">
   *   <td width="25%">127</td>
   *   <td width="25%"><kbd>01111111</kbd></td>
   *   <td width="25%"></td>
   *   <td width="25%"></td>
   * </tr>
   * <tr valign="bottom">
   *   <td width="25%">128</td>
   *   <td width="25%"><kbd>10000000</kbd></td>
   *   <td width="25%"><kbd>00000001</kbd></td>
   *   <td width="25%"></td>
   * </tr>
   * <tr valign="bottom">
   *   <td width="25%">129</td>
   *   <td width="25%"><kbd>10000001</kbd></td>
   *   <td width="25%"><kbd>00000001</kbd></td>
   *   <td width="25%"></td>
   * </tr>
   * <tr valign="bottom">
   *   <td width="25%">130</td>
   *   <td width="25%"><kbd>10000010</kbd></td>
   *   <td width="25%"><kbd>00000001</kbd></td>
   *   <td width="25%"></td>
   * </tr>
   * <tr>
   *   <td valign="top" width="25%">...</td>
   *   <td width="25%"></td>
   *   <td width="25%"></td>
   *   <td width="25%"></td>
   * </tr>
   * <tr valign="bottom">
   *   <td width="25%">16,383</td>
   *   <td width="25%"><kbd>11111111</kbd></td>
   *   <td width="25%"><kbd>01111111</kbd></td>
   *   <td width="25%"></td>
   * </tr>
   * <tr valign="bottom">
   *   <td width="25%">16,384</td>
   *   <td width="25%"><kbd>10000000</kbd></td>
   *   <td width="25%"><kbd>10000000</kbd></td>
   *   <td width="25%"><kbd>00000001</kbd></td>
   * </tr>
   * <tr valign="bottom">
   *   <td width="25%">16,385</td>
   *   <td width="25%"><kbd>10000001</kbd></td>
   *   <td width="25%"><kbd>10000000</kbd></td>
   *   <td width="25%"><kbd>00000001</kbd></td>
   * </tr>
   * <tr>
   *   <td valign="top" width="25%">...</td>
   *   <td valign="bottom" width="25%"></td>
   *   <td valign="bottom" width="25%"></td>
   *   <td valign="bottom" width="25%"></td>
   * </tr>
   * </table>
   * <p>This provides compression while still being efficient to decode.</p>
   * 使用这个方法解压也非常快
   * 
   * @param i Smaller values take fewer bytes.  Negative numbers are
   * supported, but should be avoided.
   * @throws java.io.IOException If there is an I/O error writing to the underlying medium.
   * @see org.apache.lucene.store.DataInput#readVInt()
   */
  public final void writeVInt(int i) throws IOException {
	// i & ~0x7F, 不管最后的7位, 看是否有值
    while ((i & ~0x7F) != 0) {
      // 如果有值的话, 先写入低7位, 并把第8位置为1
      writeByte((byte)((i & 0x7F) | 0x80));
      // 右移7位
      i >>>= 7;
    }
    // 写入
    writeByte((byte)i);
  }

  /** Writes a long as eight bytes.
   * <p>
   * 64-bit unsigned integer written as eight bytes, high-order bytes first.
   * 
   * @see org.apache.lucene.store.DataInput#readLong()
   * 
   * 写入1个long, 分成两个int写入, 高位在前
   */
  public void writeLong(long i) throws IOException {
    writeInt((int) (i >> 32));
    writeInt((int) i);
  }

  /** Writes an long in a variable-length format.  Writes between one and nine
   * bytes.  Smaller values take fewer bytes.  Negative numbers are not
   * supported.
   * <p>
   * The format is described further in {@link org.apache.lucene.store.DataOutput#writeVInt(int)}.
   * @see org.apache.lucene.store.DataInput#readVLong()
   */
  public final void writeVLong(long i) throws IOException {
    assert i >= 0L;
    while ((i & ~0x7FL) != 0L) {
      writeByte((byte)((i & 0x7FL) | 0x80L));
      i >>>= 7;
    }
    writeByte((byte)i);
  }

  /** Writes a string.
   * <p>
   * Writes strings as UTF-8 encoded bytes. First the length, in bytes, is
   * written as a {@link #writeVInt VInt}, followed by the bytes.
   * 
   * @see org.apache.lucene.store.DataInput#readString()
   * 
   * 写入一个String
   * 以UTF-8编码写入字符串. 首先写入是一个vint类型的字符串长度, 
   * 再写入字符串的内容.
   */
  public void writeString(String s) throws IOException {
	// 把String转换成UTF-8编码的byte[]
    final BytesRef utf8Result = new BytesRef(10);
    UnicodeUtil.UTF16toUTF8(s, 0, s.length(), utf8Result);
    // 写入数组长度
    writeVInt(utf8Result.length);
    // 写入字符串转换出来的byte[]
    writeBytes(utf8Result.bytes, 0, utf8Result.length);
  }

  private static int COPY_BUFFER_SIZE = 16384;
  private byte[] copyBuffer; // 拷贝的缓存

  /** Copy numBytes bytes from input to ourself. 
   * 拷贝numBytes数量的byte, 到这里
   * */
  public void copyBytes(DataInput input, long numBytes) throws IOException {
    assert numBytes >= 0: "numBytes=" + numBytes;
    long left = numBytes;
    if (copyBuffer == null)
      copyBuffer = new byte[COPY_BUFFER_SIZE];
    // 一直拷贝直到没有剩余
    while(left > 0) {
      final int toCopy;
      if (left > COPY_BUFFER_SIZE)
        toCopy = COPY_BUFFER_SIZE;
      else
        toCopy = (int) left;
      // 读取toCopy数量的byte到缓存
      input.readBytes(copyBuffer, 0, toCopy);
      // 把读入缓存的byte写入
      writeBytes(copyBuffer, 0, toCopy);
      // 减去当前已经读取的大小
      left -= toCopy;
    }
  }

  /**
   * Writes a String map.
   * <p>
   * First the size is written as an {@link #writeInt(int) Int32},
   * followed by each key-value pair written as two consecutive 
   * {@link #writeString(String) String}s.
   * 
   * 写入一个String map
   * 先写一个int类型的长度, 再写入key-value对
   * @param map Input map. May be null (equivalent to an empty map)
   */
  public void writeStringStringMap(Map<String,String> map) throws IOException {
    if (map == null) {
      writeInt(0);
    } else {
      writeInt(map.size());
      for(final Map.Entry<String, String> entry: map.entrySet()) {
        writeString(entry.getKey());
        writeString(entry.getValue());
      }
    }
  }

  /**
   * Writes a String set.
   * <p>
   * First the size is written as an {@link #writeInt(int) Int32},
   * followed by each value written as a
   * {@link #writeString(String) String}.
   * 
   * @param set Input set. May be null (equivalent to an empty set)
   * 
   * 先写一个int类型的长度, 再写入值
   */
  public void writeStringSet(Set<String> set) throws IOException {
    if (set == null) {
      writeInt(0);
    } else {
      writeInt(set.size());
      for(String value : set) {
        writeString(value);
      }
    }
  }
}
