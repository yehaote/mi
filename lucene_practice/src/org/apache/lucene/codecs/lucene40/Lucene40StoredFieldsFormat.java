package org.apache.lucene.codecs.lucene40;

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

import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.codecs.StoredFieldsFormat;
import org.apache.lucene.codecs.StoredFieldsReader;
import org.apache.lucene.codecs.StoredFieldsWriter;
import org.apache.lucene.index.FieldInfos;
import org.apache.lucene.index.SegmentInfo;
import org.apache.lucene.store.DataOutput; // javadocs
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;

/** 
 * Lucene 4.0 Stored Fields Format.
 * <p>Stored fields are represented by two files:</p>
 * <ol>
 * <li><a name="field_index" id="field_index"></a>
 * <p>The field index, or <tt>.fdx</tt> file.</p>
 * <p>This is used to find the location within the field data file of the fields
 * of a particular document. Because it contains fixed-length data, this file may
 * be easily randomly accessed. The position of document <i>n</i> 's field data is
 * the {@link org.apache.lucene.store.DataOutput#writeLong Uint64} at <i>n*8</i> in this file.</p>
 * <p>This contains, for each document, a pointer to its field data, as
 * follows:</p>
 * <ul>
 * <li>FieldIndex (.fdx) --&gt; &lt;Header&gt;, &lt;FieldValuesPosition&gt; <sup>SegSize</sup></li>
 * <li>Header --&gt; {@link CodecUtil#writeHeader CodecHeader}</li>
 * <li>FieldValuesPosition --&gt; {@link org.apache.lucene.store.DataOutput#writeLong Uint64}</li>
 * </ul>
 * </li>
 * <li>
 * <p><a name="field_data" id="field_data"></a>The field data, or <tt>.fdt</tt> file.</p>
 * <p>This contains the stored fields of each document, as follows:</p>
 * <ul>
 * <li>FieldData (.fdt) --&gt; &lt;Header&gt;, &lt;DocFieldData&gt; <sup>SegSize</sup></li>
 * <li>Header --&gt; {@link CodecUtil#writeHeader CodecHeader}</li>
 * <li>DocFieldData --&gt; FieldCount, &lt;FieldNum, Bits, Value&gt;
 * <sup>FieldCount</sup></li>
 * <li>FieldCount --&gt; {@link org.apache.lucene.store.DataOutput#writeVInt VInt}</li>
 * <li>FieldNum --&gt; {@link org.apache.lucene.store.DataOutput#writeVInt VInt}</li>
 * <li>Bits --&gt; {@link org.apache.lucene.store.DataOutput#writeByte Byte}</li>
 * <ul>
 * <li>low order bit reserved.</li>
 * <li>second bit is one for fields containing binary data</li>
 * <li>third bit reserved.</li>
 * <li>4th to 6th bit (mask: 0x7&lt;&lt;3) define the type of a numeric field:
 * <ul>
 * <li>all bits in mask are cleared if no numeric field at all</li>
 * <li>1&lt;&lt;3: Value is Int</li>
 * <li>2&lt;&lt;3: Value is Long</li>
 * <li>3&lt;&lt;3: Value is Int as Float (as of {@link Float#intBitsToFloat(int)}</li>
 * <li>4&lt;&lt;3: Value is Long as Double (as of {@link Double#longBitsToDouble(long)}</li>
 * </ul>
 * </li>
 * </ul>
 * <li>Value --&gt; String | BinaryValue | Int | Long (depending on Bits)</li>
 * <li>BinaryValue --&gt; ValueSize, &lt;{@link org.apache.lucene.store.DataOutput#writeByte Byte}&gt;^ValueSize</li>
 * <li>ValueSize --&gt; {@link org.apache.lucene.store.DataOutput#writeVInt VInt}</li>
 * </li>
 * </ul>
 * </ol>
 * @lucene.experimental
 * <p>
 * 
 * Lucene存储Filed格式.
 * <p>存储Field被写入两个文件:</p>
 * <ol>
 * <li><a name="field_index" id="field_index"></a>
 * <p>Field的索引 或者 <tt>.fdx</tt>文件</p>
 * <p>这个被用来定位一个指定的document在Field数据文件中的位置. 因为它里面包含的是固定大小的数据,
 * 用来做随机访问会简单很多(比如根据docId, 可以直接计算出需要获取数据的位置). 
 * 对于文档n, 它的field数据{@link org.apache.lucene.store.DataOutput#writeLong Uint64}
 * 是在文件中<i>n*8</i>的位置.</p>
 * <p>对与每一个Document, 它包含一个对field数据的指针, 如下:</p>
 * <ul>
 * <li>FieldIndex (.fdx) --&gt; &lt;头&gt;, &lt;Field值的位置&gt; <sup>SegSize</sup></li>
 * <li>Header --&gt; {@link CodecUtil#writeHeader CodecHeader}</li>
 * <li>Field值的位置 --&gt; {@link org.apache.lucene.store.DataOutput#writeLong Uint64}</li>
 * </ul>
 * </li>
 * <li>
 * <p><a name="field_data" id="field_data"></a>Field数据, 或者<tt>.fdt</tt>文件.</p>
 * <p>包含每个文档的存储Field的数据, 具体如下:</p>
 * <ul>
 * <li>Field数据 (.fdt) --&gt; &lt;头&gt;, &lt;文档Field数据&gt; <sup>SegSize</sup></li>
 * <li>头 --&gt; {@link CodecUtil#writeHeader CodecHeader}</li>
 * <li>文档Field数据 --&gt; Field数目, &lt;FieldNumber(标识号), Bits, Value&gt;
 * <sup>Field数目</sup></li>
 * <li>Field数目 --&gt; {@link org.apache.lucene.store.DataOutput#writeVInt VInt}</li>
 * <li>FieldNumber(标识号) --&gt; {@link org.apache.lucene.store.DataOutput#writeVInt VInt}</li>
 * <li>Bits --&gt; {@link org.apache.lucene.store.DataOutput#writeByte Byte}</li>
 * <ul>
 * <li>低位保留</li>
 * <li>第2位如果是1的话, 说明fields包含二进制数据</li>
 * <li>第3位保留</li>
 * <li>第4位到第6位 (mask: 0x7&lt;&lt;3)定义数值类Field的类型:
 * <ul>
 * <li>如果mask的值是0的话, 说明没有数值型数据</li>
 * <li>1&lt;&lt;3: 值是Int</li>
 * <li>2&lt;&lt;3: 值是Long</li>
 * <li>3&lt;&lt;3: 值是以Int存储的Float (as of {@link Float#intBitsToFloat(int)}</li>
 * <li>4&lt;&lt;3: 值是以Long存储的Double (as of {@link Double#longBitsToDouble(long)}</li>
 * </ul>
 * </li>
 * </ul>
 * <li>值 --&gt; String | BinaryValue | Int | Long (depending on Bits)</li>
 * <li>二进制值 --&gt; ValueSize, &lt;{@link org.apache.lucene.store.DataOutput#writeByte Byte}&gt;^ValueSize</li>
 * <li>值大小 --&gt; {@link org.apache.lucene.store.DataOutput#writeVInt VInt}</li>
 * </li>
 * </ul>
 * </ol>
 * */
public class Lucene40StoredFieldsFormat extends StoredFieldsFormat {

  /** Sole constructor. */
  public Lucene40StoredFieldsFormat() {
  }

  @Override
  public StoredFieldsReader fieldsReader(Directory directory, SegmentInfo si,
      FieldInfos fn, IOContext context) throws IOException {
    return new Lucene40StoredFieldsReader(directory, si, fn, context);
  }

  @Override
  public StoredFieldsWriter fieldsWriter(Directory directory, SegmentInfo si,
      IOContext context) throws IOException {
    return new Lucene40StoredFieldsWriter(directory, si.name, context);
  }
}
