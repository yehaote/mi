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
import org.apache.lucene.codecs.StoredFieldsReader;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.FieldInfos;
import org.apache.lucene.index.IndexFileNames;
import org.apache.lucene.index.SegmentInfo;
import org.apache.lucene.index.StoredFieldVisitor;
import org.apache.lucene.store.AlreadyClosedException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.util.IOUtils;

import java.io.Closeable;

import static org.apache.lucene.codecs.lucene40.Lucene40StoredFieldsWriter.*;

/**
 * Class responsible for access to stored document fields.
 * <p/>
 * It uses &lt;segment&gt;.fdt and &lt;segment&gt;.fdx; files.
 * 
 * @see org.apache.lucene.codecs.lucene40.Lucene40StoredFieldsFormat
 * @lucene.internal
 * 
 * <p>
 * 负责读取Document的存储Field的类.
 * 它使用&lt;segment&gt;.fdt 和 &lt;segment&gt;.fdx 文件.
 */
public final class Lucene40StoredFieldsReader extends StoredFieldsReader implements Cloneable, Closeable {
  private final FieldInfos fieldInfos;
  private final IndexInput fieldsStream; // FieldsData数据
  private final IndexInput indexStream; // FieldIndex数据
  private int numTotalDocs; // 总文档数, 跟size有什么区别
  private int size;
  private boolean closed;

  /** Returns a cloned FieldsReader that shares open
   *  IndexInputs with the original one.  It is the caller's
   *  job not to close the original FieldsReader until all
   *  clones are called (eg, currently SegmentReader manages
   *  this logic).
   *  <p>
   *  克隆一个FieldsReader共享打开的IndexInput.
   *  调用这个方法需要调用者自己去确保不要关闭当前打开的IndexInput直到
   *  所有克隆出来的流都已经调用完毕(现在SegmentReader管理这个逻辑).
   *  */
  @Override
  public Lucene40StoredFieldsReader clone() {
    ensureOpen();
    return new Lucene40StoredFieldsReader(fieldInfos, numTotalDocs, size, fieldsStream.clone(), indexStream.clone());
  }
  
  /** Used only by clone. 
   *  <p>
   *  用来克隆的构造函数, 私有构造函数
   * */
  private Lucene40StoredFieldsReader(FieldInfos fieldInfos, int numTotalDocs, int size, IndexInput fieldsStream, IndexInput indexStream) {
    this.fieldInfos = fieldInfos;
    this.numTotalDocs = numTotalDocs;
    this.size = size;
    this.fieldsStream = fieldsStream;
    this.indexStream = indexStream;
  }

  /** Sole constructor. 
   *  <p>
   *  对开公开的唯一构造函数
   * */
  public Lucene40StoredFieldsReader(Directory d, SegmentInfo si, FieldInfos fn, IOContext context) throws IOException {
    // 根据SegmentInfo获取当前的Segment的name
	final String segment = si.name;
    boolean success = false;
    fieldInfos = fn;
    try {
      // 打开.fdt文件
      fieldsStream = d.openInput(IndexFileNames.segmentFileName(segment, "", FIELDS_EXTENSION), context);
      // 打开.fdx文件
      final String indexStreamFN = IndexFileNames.segmentFileName(segment, "", FIELDS_INDEX_EXTENSION);
      indexStream = d.openInput(indexStreamFN, context);
      
      // 分别检查两个文件的, 文件头
      CodecUtil.checkHeader(indexStream, CODEC_NAME_IDX, VERSION_START, VERSION_CURRENT);
      CodecUtil.checkHeader(fieldsStream, CODEC_NAME_DAT, VERSION_START, VERSION_CURRENT);
      assert HEADER_LENGTH_DAT == fieldsStream.getFilePointer();
      assert HEADER_LENGTH_IDX == indexStream.getFilePointer();
      // 获取index的大小(扣除头长度以外的长度)
      final long indexSize = indexStream.length() - HEADER_LENGTH_IDX;
      // 因为对于每一个Document都会在.fdx中写入一个long类型的pointer, 
      // 而每个long占用8个字节,所以indexSize/8就是doc的数量
      this.size = (int) (indexSize >> 3);
      // Verify two sources of "maxDoc" agree:
      // 验证当前计算出来的doucument数量是不是跟SegmentInfo中存的一样
      if (this.size != si.getDocCount()) {
        throw new CorruptIndexException("doc counts differ for segment " + segment + ": fieldsReader shows " + this.size + " but segmentInfo shows " + si.getDocCount());
      }
      // 为什么不直接使用size? 不是计算过一遍了?
      numTotalDocs = (int) (indexSize >> 3);
      success = true;
    } finally {
      // With lock-less commits, it's entirely possible (and
      // fine) to hit a FileNotFound exception above. In
      // this case, we want to explicitly close any subset
      // of things that were opened so that we don't have to
      // wait for a GC to do so.
      if (!success) {
        try {
          close();
        } catch (Throwable t) {} // ensure we throw our original exception
      }
    }
  }

  /**
   * @throws org.apache.lucene.store.AlreadyClosedException if this FieldsReader is closed
   */
  private void ensureOpen() throws AlreadyClosedException {
    if (closed) {
      throw new AlreadyClosedException("this FieldsReader is closed");
    }
  }

  /**
   * Closes the underlying {@link org.apache.lucene.store.IndexInput} streams.
   * This means that the Fields values will not be accessible.
   *
   * @throws java.io.IOException If an I/O error occurs
   */
  @Override
  public final void close() throws IOException {
    if (!closed) {
      IOUtils.close(fieldsStream, indexStream);
      closed = true;
    }
  }

  /** Returns number of documents. */
  public final int size() {
    return size;
  }
  
  /**
   * 根据document去seek .fdx文件, 从而去读document在.fdt中的数据的位置
   * @param docID
   * @throws IOException
   */
  private void seekIndex(int docID) throws IOException {
    indexStream.seek(HEADER_LENGTH_IDX + docID * 8L);
  }
  
  /**
   * 返回的Document被设置在StoredFieldVisitor中
   */
  @Override
  public final void visitDocument(int n, StoredFieldVisitor visitor) throws IOException {
    // 定位到数据在FieldsData(.fdt)中的位置
	seekIndex(n);
    fieldsStream.seek(indexStream.readLong());
    // 读取当前Document的Field的数量
    final int numFields = fieldsStream.readVInt();
    // 根据Field的数量依次去读取每一个Field
    for (int fieldIDX = 0; fieldIDX < numFields; fieldIDX++) {
      // 读取Field的field number, 然后通过全局的FieldInfos去获取对应的FieldInfo
      int fieldNumber = fieldsStream.readVInt();
      FieldInfo fieldInfo = fieldInfos.fieldInfo(fieldNumber);
      // 读取一个byte大小的bit位, 用于判断当前的Field类型
      int bits = fieldsStream.readByte() & 0xFF;
      assert bits <= (FIELD_IS_NUMERIC_MASK | FIELD_IS_BINARY): "bits=" + Integer.toHexString(bits);
      // 根据当前Field是否需要读取, 进行后续的工作
      switch(visitor.needsField(fieldInfo)) {
        case YES:
          readField(visitor, fieldInfo, bits);
          break;
        case NO:
          // 需要跳过Field是因为要让文件指针往前走, 以读取下一个Field.
          // 现在源码里好像是通过读取方式进行的, 只是没有存下读取出来的值,
          // 采用seek而不是读取数据的话会加快效率吗?
          skipField(bits);
          break;
        case STOP: 
          return;
      }
    }
  }
  
  /***
   * 根据bits(Field类型标识符), FieldInfo, 读取当前的Document到visitor
   * @param visitor
   * @param info
   * @param bits
   * @throws IOException
   */
  private void readField(StoredFieldVisitor visitor, FieldInfo info, int bits) throws IOException {
    final int numeric = bits & FIELD_IS_NUMERIC_MASK;
    if (numeric != 0) {
      switch(numeric) {
        case FIELD_IS_NUMERIC_INT:
          visitor.intField(info, fieldsStream.readInt());
          return;
        case FIELD_IS_NUMERIC_LONG:
          visitor.longField(info, fieldsStream.readLong());
          return;
        case FIELD_IS_NUMERIC_FLOAT:
          visitor.floatField(info, Float.intBitsToFloat(fieldsStream.readInt()));
          return;
        case FIELD_IS_NUMERIC_DOUBLE:
          visitor.doubleField(info, Double.longBitsToDouble(fieldsStream.readLong()));
          return;
        default:
          throw new CorruptIndexException("Invalid numeric type: " + Integer.toHexString(numeric));
      }
    } else { 
      // 如果是binaryField或者StringField的话, 都需要先读取length
      final int length = fieldsStream.readVInt();
      byte bytes[] = new byte[length];
      fieldsStream.readBytes(bytes, 0, length);
      if ((bits & FIELD_IS_BINARY) != 0) {
        visitor.binaryField(info, bytes);
      } else {
        visitor.stringField(info, new String(bytes, 0, bytes.length, IOUtils.CHARSET_UTF_8));
      }
    }
  }
  
  /**
   * 跳过当前的Field
   * 根据当前的Field的类型, 跳过特定的长度.
   * 如果是数值类型的话通过读取的方式进行(不保存读取的值),
   * 如果是字符类型的数据的话, 使用seek.
   * @param bits
   * @throws IOException
   */
  private void skipField(int bits) throws IOException {
	final int numeric = bits & FIELD_IS_NUMERIC_MASK;
    if (numeric != 0) {
      switch(numeric) {
        case FIELD_IS_NUMERIC_INT:
        case FIELD_IS_NUMERIC_FLOAT:
          fieldsStream.readInt();
          return;
        case FIELD_IS_NUMERIC_LONG:
        case FIELD_IS_NUMERIC_DOUBLE:
          fieldsStream.readLong();
          return;
        default: 
          throw new CorruptIndexException("Invalid numeric type: " + Integer.toHexString(numeric));
      }
    } else {
      final int length = fieldsStream.readVInt();
      fieldsStream.seek(fieldsStream.getFilePointer() + length);
    }
  }

  /** Returns the length in bytes of each raw document in a
   *  contiguous range of length numDocs starting with
   *  startDocID.  Returns the IndexInput (the fieldStream),
   *  already seeked to the starting point for startDocID.
   *  <p>
   *  把从startDocID开始numDocs个document的长度写入到lengths数组中.
   *  返回打开的fieldData流已经定位到startDocID的位置.
   *  */
  public final IndexInput rawDocs(int[] lengths, int startDocID, int numDocs) throws IOException {
    seekIndex(startDocID);
    long startOffset = indexStream.readLong();
    long lastOffset = startOffset;
    int count = 0;
    while (count < numDocs) {
      final long offset;
      final int docID = startDocID + count + 1;
      assert docID <= numTotalDocs;
      if (docID < numTotalDocs) 
        offset = indexStream.readLong();
      else
        offset = fieldsStream.length();
      lengths[count++] = (int) (offset-lastOffset);
      lastOffset = offset;
    }

    fieldsStream.seek(startOffset);

    return fieldsStream;
  }
}
