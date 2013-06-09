package org.apache.lucene.codecs;

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

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexFormatTooNewException;
import org.apache.lucene.index.IndexFormatTooOldException;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.store.DataOutput;
import org.apache.lucene.util.BytesRef;

/**
 * Utility class for reading and writing versioned headers.
 * <p>
 * Writing codec headers is useful to ensure that a file is in 
 * the format you think it is.
 * 
 * @lucene.experimental
 * <p>
 * 读取写入 版本头信息 的工具类
 * 写入编码头信息用来确认你打开的那个文件格式跟你想的一样.
 */

public final class CodecUtil {
  private CodecUtil() {} // no instance

  /**
   * Constant to identify the start of a codec header.
   * <p>
   * 常量用来标识一个编码头的开始
   */
  public final static int CODEC_MAGIC = 0x3fd76c17;

  /**
   * Writes a codec header, which records both a string to
   * identify the file and a version number. This header can
   * be parsed and validated with 
   * {@link #checkHeader(org.apache.lucene.store.DataInput, String, int, int) checkHeader()}.
   * <p>
   * CodecHeader --&gt; Magic,CodecName,Version
   * <ul>
   *    <li>Magic --&gt; {@link org.apache.lucene.store.DataOutput#writeInt Uint32}. This
   *        identifies the start of the header. It is always {@value #CODEC_MAGIC}.
   *    <li>CodecName --&gt; {@link org.apache.lucene.store.DataOutput#writeString String}. This
   *        is a string to identify this file.
   *    <li>Version --&gt; {@link org.apache.lucene.store.DataOutput#writeInt Uint32}. Records
   *        the version of the file.
   * </ul>
   * <p>
   * Note that the length of a codec header depends only upon the
   * name of the codec, so this length can be computed at any time
   * with {@link #headerLength(String)}.
   * 
   * @param out Output stream
   * @param codec String to identify this file. It should be simple ASCII, 
   *              less than 128 characters in length.
   * @param version Version number
   * @throws java.io.IOException If there is an I/O error writing to the underlying medium.
   * <p>
   * 写入一个编码头, 包含一个String和一个版本号.
   * 这个头可以被{@link #checkHeader(org.apache.lucene.store.DataInput, String, int, int) checkHeader()}
   * 解析和验证. 
   * 编码头 --&gt; Magic(常量),编码名称,版本
   * <ul>
   *    <li>Magic --&gt; {@link org.apache.lucene.store.DataOutput#writeInt Uint32}. 
   *    用来标识是一个编码头的开始, 它的值永远都是 {@value #CODEC_MAGIC}.
   *    <li>编码名称 --&gt; {@link org.apache.lucene.store.DataOutput#writeString String}.
   *    一个用来标识这个文件的String.
   *    <li>版本号 --&gt; {@link org.apache.lucene.store.DataOutput#writeInt Uint32}. 
   *    记录文件的版本号
   * </ul>
   * <p>
   * 注意编码头的大小跟编码器的名称有很大的关系, 不是固定大小的, 
   * 不过可以通过{@link #headerLength(String)}获取到.
   * 
   * @param out Output stream 输出流
   * @param codec String to identify this file. It should be simple ASCII, 
   *              less than 128 characters in length.
   *              用来标识编码器的.
   * @param version Version number 版本号
   * @throws java.io.IOException If there is an I/O error writing to the underlying medium.
   */
  public static void writeHeader(DataOutput out, String codec, int version)
    throws IOException {
	// 把String转换成BytesRef
    BytesRef bytes = new BytesRef(codec);
    // bytes.length != codec.length() 说明不是简单的ASCII编码
    if (bytes.length != codec.length() || bytes.length >= 128) {
      throw new IllegalArgumentException("codec must be simple ASCII, less than 128 characters in length [got " + codec + "]");
    }
    // 写入CODEC_MAGIC
    out.writeInt(CODEC_MAGIC);
    // 写入String
    out.writeString(codec);
    // 写本版本
    out.writeInt(version);
  }

  /**
   * Computes the length of a codec header.
   * 
   * @param codec Codec name.
   * @return length of the entire codec header.
   * @see #writeHeader(org.apache.lucene.store.DataOutput, String, int)
   * <p>
   * 计算一个编码头的长度, 9+codec长度,
   * 虽说codec跟存储的是不一样的编码方式(utf-8, urf-16)不过因为我们限制codec必须是
   * 简单ASCII所以这两种编码方式的长度是一样的. 
   */
  public static int headerLength(String codec) {
    return 9+codec.length();
  }

  /**
   * Reads and validates a header previously written with 
   * {@link #writeHeader(org.apache.lucene.store.DataOutput, String, int)}.
   * <p>
   * When reading a file, supply the expected <code>codec</code> and
   * an expected version range (<code>minVersion to maxVersion</code>).
   * 
   * <p>
   * 读取并验证先前使用{@link #writeHeader(org.apache.lucene.store.DataOutput, String, int)}
   * 写入的编码头是否正确.
   * 当读取一个文件的时候, 提供对应的<code>codec</code>和预期的版本范围
   * (<code>minVersion to maxVersion</code>).
   * 
   * @param in Input stream, positioned at the point where the
   *        header was previously written. Typically this is located
   *        at the beginning of the file.
   * @param codec The expected codec name.
   * @param minVersion The minimum supported expected version number.
   * @param maxVersion The maximum supported expected version number.
   * @return The actual version found, when a valid header is found 
   *         that matches <code>codec</code>, with an actual version 
   *         where <code>minVersion <= actual <= maxVersion</code>.
   *         Otherwise an exception is thrown.
   * @throws org.apache.lucene.index.CorruptIndexException If the first four bytes are not
   *         {@link #CODEC_MAGIC}, or if the actual codec found is
   *         not <code>codec</code>.
   * @throws org.apache.lucene.index.IndexFormatTooOldException If the actual version is less
   *         than <code>minVersion</code>.
   * @throws org.apache.lucene.index.IndexFormatTooNewException If the actual version is greater
   *         than <code>maxVersion</code>.
   * @throws java.io.IOException If there is an I/O error reading from the underlying medium.
   * @see #writeHeader(org.apache.lucene.store.DataOutput, String, int)
   */
  public static int checkHeader(DataInput in, String codec, int minVersion, int maxVersion)
    throws IOException {

    // Safety to guard against reading a bogus string:
    final int actualHeader = in.readInt();
    if (actualHeader != CODEC_MAGIC) {
      throw new CorruptIndexException("codec header mismatch: actual header=" + actualHeader + " vs expected header=" + CODEC_MAGIC + " (resource: " + in + ")");
    }
    return checkHeaderNoMagic(in, codec, minVersion, maxVersion);
  }

  /** Like {@link
   *  #checkHeader(org.apache.lucene.store.DataInput,String,int,int)} except this
   *  version assumes the first int has already been read
   *  and validated from the input.
   *  
   *  */
  public static int checkHeaderNoMagic(DataInput in, String codec, int minVersion, int maxVersion) throws IOException {
    final String actualCodec = in.readString();
    if (!actualCodec.equals(codec)) {
      throw new CorruptIndexException("codec mismatch: actual codec=" + actualCodec + " vs expected codec=" + codec + " (resource: " + in + ")");
    }

    final int actualVersion = in.readInt();
    if (actualVersion < minVersion) {
      throw new IndexFormatTooOldException(in, actualVersion, minVersion, maxVersion);
    }
    if (actualVersion > maxVersion) {
      throw new IndexFormatTooNewException(in, actualVersion, minVersion, maxVersion);
    }

    return actualVersion;
  }
}
