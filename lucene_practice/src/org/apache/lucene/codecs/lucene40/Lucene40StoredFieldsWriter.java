package org.apache.lucene.codecs.lucene40;

/**
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import java.io.Closeable;
import java.io.IOException;

import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.codecs.StoredFieldsReader;
import org.apache.lucene.codecs.StoredFieldsWriter;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.FieldInfos;
import org.apache.lucene.index.IndexFileNames;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.MergeState;
import org.apache.lucene.index.SegmentReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.store.IndexOutput;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IOUtils;

/** 
 * Class responsible for writing stored document fields.
 * <p/>
 * It uses &lt;segment&gt;.fdt and &lt;segment&gt;.fdx; files.
 * 
 * @see org.apache.lucene.codecs.lucene40.Lucene40StoredFieldsFormat
 * @lucene.experimental 
 * <p>
 * 负责写入Document的存储Field的类.
 * 它使用&lt;segment&gt;.fdt 和 &lt;segment&gt;.fdx 文件.
 */
public final class Lucene40StoredFieldsWriter extends StoredFieldsWriter {
  // NOTE: bit 0 is free here!  You can steal it!
  static final int FIELD_IS_BINARY = 1 << 1;

  // the old bit 1 << 2 was compressed, is now left out

  private static final int _NUMERIC_BIT_SHIFT = 3;
  static final int FIELD_IS_NUMERIC_MASK = 0x07 << _NUMERIC_BIT_SHIFT;

  static final int FIELD_IS_NUMERIC_INT = 1 << _NUMERIC_BIT_SHIFT;
  static final int FIELD_IS_NUMERIC_LONG = 2 << _NUMERIC_BIT_SHIFT;
  static final int FIELD_IS_NUMERIC_FLOAT = 3 << _NUMERIC_BIT_SHIFT;
  static final int FIELD_IS_NUMERIC_DOUBLE = 4 << _NUMERIC_BIT_SHIFT;

  // the next possible bits are: 1 << 6; 1 << 7
  // currently unused: static final int FIELD_IS_NUMERIC_SHORT = 5 << _NUMERIC_BIT_SHIFT;
  // currently unused: static final int FIELD_IS_NUMERIC_BYTE = 6 << _NUMERIC_BIT_SHIFT;

  static final String CODEC_NAME_IDX = "Lucene40StoredFieldsIndex";
  static final String CODEC_NAME_DAT = "Lucene40StoredFieldsData";
  static final int VERSION_START = 0;
  static final int VERSION_CURRENT = VERSION_START;
  static final long HEADER_LENGTH_IDX = CodecUtil.headerLength(CODEC_NAME_IDX);
  static final long HEADER_LENGTH_DAT = CodecUtil.headerLength(CODEC_NAME_DAT);



  /** Extension of stored fields file */
  public static final String FIELDS_EXTENSION = "fdt";
  
  /** Extension of stored fields index file */
  public static final String FIELDS_INDEX_EXTENSION = "fdx";

  private final Directory directory; // 当前的directory 
  private final String segment; // segment标识
  private IndexOutput fieldsStream; //fieldsData  fdt
  private IndexOutput indexStream; // fieldsIndex fdx

  /** Sole constructor. */
  public Lucene40StoredFieldsWriter(Directory directory, String segment, IOContext context) throws IOException {
    assert directory != null;
    this.directory = directory;
    this.segment = segment;

    boolean success = false;
    try {
      // 创建输出的文件
      fieldsStream = directory.createOutput(IndexFileNames.segmentFileName(segment, "", FIELDS_EXTENSION), context);
      indexStream = directory.createOutput(IndexFileNames.segmentFileName(segment, "", FIELDS_INDEX_EXTENSION), context);
      
      // 写入头
      CodecUtil.writeHeader(fieldsStream, CODEC_NAME_DAT, VERSION_CURRENT);
      CodecUtil.writeHeader(indexStream, CODEC_NAME_IDX, VERSION_CURRENT);
      // 检验一些当前在文件中的位置是否是写入头以后的位置
      assert HEADER_LENGTH_DAT == fieldsStream.getFilePointer();
      assert HEADER_LENGTH_IDX == indexStream.getFilePointer();
      success = true;
    } finally {
      if (!success) {
        abort();
      }
    }
  }

  // Writes the contents of buffer into the fields stream
  // and adds a new entry for this document into the index
  // stream.  This assumes the buffer was already written
  // in the correct fields format.
  @Override
  public void startDocument(int numStoredFields) throws IOException {
	// 在FieldIndex中写入FieldData中开始存数据的位置
    indexStream.writeLong(fieldsStream.getFilePointer());
    // 在FieldData中写入需要存储的fields的数量
    fieldsStream.writeVInt(numStoredFields);
  }
  
  /**
   * 关闭
   * 直接关闭.fdt 和.fdx的流
   * 并把相对应的引用设置成null, 方便GC
   */
  @Override
  public void close() throws IOException {
    try {
      IOUtils.close(fieldsStream, indexStream);
    } finally {
      fieldsStream = indexStream = null;
    }
  }
  
  /**
   * 中断
   * 1. 关闭.fdt和.fdx的流
   * 2. 删除.fdt和.fdx文件
   */
  @Override
  public void abort() {
    try {
      close();
    } catch (Throwable ignored) {}
    IOUtils.deleteFilesIgnoringExceptions(directory,
        IndexFileNames.segmentFileName(segment, "", FIELDS_EXTENSION),
        IndexFileNames.segmentFileName(segment, "", FIELDS_INDEX_EXTENSION));
  }
  
  @Override
  public void writeField(FieldInfo info, IndexableField field) throws IOException {
	// 写如一个Field
	// 在FieldData写入FieldInfo.number(这个number是根据FIeldName指定的)
    fieldsStream.writeVInt(info.number);
    int bits = 0;
    final BytesRef bytes;
    final String string;
    // TODO: maybe a field should serialize itself?
    // this way we don't bake into indexer all these
    // specific encodings for different fields?  and apps
    // can customize...
    // TODO: 或许一个Field可以直接序列化. 
    // 不要在Index的各个阶段都要加这样的代码
    
    // 看看是不是numeric类型的值
    Number number = field.numericValue();
    if (number != null) {
      // 数值类型
      if (number instanceof Byte || number instanceof Short || number instanceof Integer) {
        bits |= FIELD_IS_NUMERIC_INT;
      } else if (number instanceof Long) {
        bits |= FIELD_IS_NUMERIC_LONG;
      } else if (number instanceof Float) {
        bits |= FIELD_IS_NUMERIC_FLOAT;
      } else if (number instanceof Double) {
        bits |= FIELD_IS_NUMERIC_DOUBLE;
      } else {
        throw new IllegalArgumentException("cannot store numeric type " + number.getClass());
      }
      string = null;
      bytes = null;
    } else {
      // binary值跟StringValue的区别在哪里?
      bytes = field.binaryValue();
      if (bytes != null) {
        bits |= FIELD_IS_BINARY;
        string = null;
      } else {
        string = field.stringValue();
        if (string == null) {
          throw new IllegalArgumentException("field " + field.name() + " is stored but does not have binaryValue, stringValue nor numericValue");
        }
      }
    }
    
    // 写入数据类型
    fieldsStream.writeByte((byte) bits);
     
    // 写入数据
    if (bytes != null) {
      fieldsStream.writeVInt(bytes.length);
      fieldsStream.writeBytes(bytes.bytes, bytes.offset, bytes.length);
    } else if (string != null) {
      fieldsStream.writeString(field.stringValue());
    } else {
      if (number instanceof Byte || number instanceof Short || number instanceof Integer) {
        fieldsStream.writeInt(number.intValue());
      } else if (number instanceof Long) {
        fieldsStream.writeLong(number.longValue());
      } else if (number instanceof Float) {
        fieldsStream.writeInt(Float.floatToIntBits(number.floatValue()));
      } else if (number instanceof Double) {
        fieldsStream.writeLong(Double.doubleToLongBits(number.doubleValue()));
      } else {
        throw new AssertionError("Cannot get here");
      }
    }
  }

  /** Bulk write a contiguous series of documents.  The
   *  lengths array is the length (in bytes) of each raw
   *  document.  The stream IndexInput is the
   *  fieldsStream from which we should bulk-copy all
   *  bytes. */
  public void addRawDocuments(IndexInput stream, int[] lengths, int numDocs) throws IOException {
    long position = fieldsStream.getFilePointer();
    long start = position;
    for(int i=0;i<numDocs;i++) {
      indexStream.writeLong(position);
      position += lengths[i];
    }
    fieldsStream.copyBytes(stream, position-start);
    assert fieldsStream.getFilePointer() == position;
  }

  @Override
  public void finish(FieldInfos fis, int numDocs) {
    if (HEADER_LENGTH_IDX+((long) numDocs)*8 != indexStream.getFilePointer())
      // This is most likely a bug in Sun JRE 1.6.0_04/_05;
      // we detect that the bug has struck, here, and
      // throw an exception to prevent the corruption from
      // entering the index.  See LUCENE-1282 for
      // details.
      throw new RuntimeException("fdx size mismatch: docCount is " + numDocs + " but fdx file size is " + indexStream.getFilePointer() + " file=" + indexStream.toString() + "; now aborting this merge to prevent index corruption");
  }
  
  /**
   * 合并
   */
  @Override
  public int merge(MergeState mergeState) throws IOException {
    int docCount = 0;
    // Used for bulk-reading raw bytes for stored fields
    int rawDocLengths[] = new int[MAX_RAW_MERGE_DOCS];
    int idx = 0;
    
    /**
     * 迭代mergeState中所有的reader
     */
    for (AtomicReader reader : mergeState.readers) {
      // 
      final SegmentReader matchingSegmentReader = mergeState.matchingSegmentReaders[idx++];
      Lucene40StoredFieldsReader matchingFieldsReader = null;
      if (matchingSegmentReader != null) {
        final StoredFieldsReader fieldsReader = matchingSegmentReader.getFieldsReader();
        // we can only bulk-copy if the matching reader is also a Lucene40FieldsReader
        if (fieldsReader != null && fieldsReader instanceof Lucene40StoredFieldsReader) {
          matchingFieldsReader = (Lucene40StoredFieldsReader) fieldsReader;
        }
      }
    
      if (reader.getLiveDocs() != null) {
        docCount += copyFieldsWithDeletions(mergeState,
                                            reader, matchingFieldsReader, rawDocLengths);
      } else {
        docCount += copyFieldsNoDeletions(mergeState,
                                          reader, matchingFieldsReader, rawDocLengths);
      }
    }
    finish(mergeState.fieldInfos, docCount);
    return docCount;
  }

  /** Maximum number of contiguous documents to bulk-copy
      when merging stored fields */
  private final static int MAX_RAW_MERGE_DOCS = 4192;

  private int copyFieldsWithDeletions(MergeState mergeState, final AtomicReader reader,
                                      final Lucene40StoredFieldsReader matchingFieldsReader, int rawDocLengths[])
    throws IOException {
    int docCount = 0;
    final int maxDoc = reader.maxDoc();
    final Bits liveDocs = reader.getLiveDocs();
    assert liveDocs != null;
    if (matchingFieldsReader != null) {
      // We can bulk-copy because the fieldInfos are "congruent"
      for (int j = 0; j < maxDoc;) {
        if (!liveDocs.get(j)) {
          // skip deleted docs
          ++j;
          continue;
        }
        // We can optimize this case (doing a bulk byte copy) since the field
        // numbers are identical
        int start = j, numDocs = 0;
        do {
          j++;
          numDocs++;
          if (j >= maxDoc) break;
          if (!liveDocs.get(j)) {
            j++;
            break;
          }
        } while(numDocs < MAX_RAW_MERGE_DOCS);

        IndexInput stream = matchingFieldsReader.rawDocs(rawDocLengths, start, numDocs);
        addRawDocuments(stream, rawDocLengths, numDocs);
        docCount += numDocs;
        mergeState.checkAbort.work(300 * numDocs);
      }
    } else {
      for (int j = 0; j < maxDoc; j++) {
        if (!liveDocs.get(j)) {
          // skip deleted docs
          continue;
        }
        // TODO: this could be more efficient using
        // FieldVisitor instead of loading/writing entire
        // doc; ie we just have to renumber the field number
        // on the fly?
        // NOTE: it's very important to first assign to doc then pass it to
        // fieldsWriter.addDocument; see LUCENE-1282
        Document doc = reader.document(j);
        addDocument(doc, mergeState.fieldInfos);
        docCount++;
        mergeState.checkAbort.work(300);
      }
    }
    return docCount;
  }
  
  /**
   * 拷贝Fields数据, 没有删除
   * @param mergeState(merge的信息)
   * @param reader(merge的输入)
   * @param matchingFieldsReader(命中的Fields的输入)
   * @param rawDocLengths
   * @return
   * @throws IOException
   */
  private int copyFieldsNoDeletions(MergeState mergeState, final AtomicReader reader,
                                    final Lucene40StoredFieldsReader matchingFieldsReader, int rawDocLengths[])
    throws IOException {
	// 读取文档的最大数据
    final int maxDoc = reader.maxDoc();
    int docCount = 0;
    // 有没有命中的FieldsReader有什么区别?
    if (matchingFieldsReader != null) {
      // We can bulk-copy because the fieldInfos are "congruent"
      while (docCount < maxDoc) {
        int len = Math.min(MAX_RAW_MERGE_DOCS, maxDoc - docCount);
        IndexInput stream = matchingFieldsReader.rawDocs(rawDocLengths, docCount, len);
        addRawDocuments(stream, rawDocLengths, len);
        docCount += len;
        mergeState.checkAbort.work(300 * len);
      }
    } else {
      for (; docCount < maxDoc; docCount++) {
        // NOTE: it's very important to first assign to doc then pass it to
        // fieldsWriter.addDocument; see LUCENE-1282
        Document doc = reader.document(docCount);
        addDocument(doc, mergeState.fieldInfos);
        mergeState.checkAbort.work(300);
      }
    }
    return docCount;
  }
}
