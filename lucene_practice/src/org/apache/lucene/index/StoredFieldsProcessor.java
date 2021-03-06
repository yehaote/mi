package org.apache.lucene.index;

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

import org.apache.lucene.codecs.Codec;
import org.apache.lucene.codecs.StoredFieldsWriter;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.util.ArrayUtil;
import org.apache.lucene.util.RamUsageEstimator;

/** This is a StoredFieldsConsumer that writes stored fields. */
final class StoredFieldsProcessor extends StoredFieldsConsumer {

  StoredFieldsWriter fieldsWriter; // 输出到I/O写入器
  final DocumentsWriterPerThread docWriter; // 对调用它的docWriter的引用
  int lastDocID;

  int freeCount;

  final DocumentsWriterPerThread.DocState docState;
  final Codec codec;

  public StoredFieldsProcessor(DocumentsWriterPerThread docWriter) {
    this.docWriter = docWriter;
    this.docState = docWriter.docState;
    this.codec = docWriter.codec;
  }

  private int numStoredFields; // 当前记录的StoreField数
  private IndexableField[] storedFields; // 用于存储需要Store的Fields
  private FieldInfo[] fieldInfos; // 用于存储对应StoreField的FieldInfo信息
  
  /**
   * 重新初始化
   */
  public void reset() {
    numStoredFields = 0;
    storedFields = new IndexableField[1];
    fieldInfos = new FieldInfo[1];
  }
  
  @Override
  public void startDocument() {
	// 每一个开始一个新文档的时候都reset
    reset();
  }

  @Override
  public void flush(SegmentWriteState state) throws IOException {
	// 刷新输出 
	// 获取当前段的docCount
    int numDocs = state.segmentInfo.getDocCount();
    
    if (numDocs > 0) {
      // It's possible that all documents seen in this segment
      // hit non-aborting exceptions, in which case we will
      // not have yet init'd the FieldsWriter:
      // 有可能在这个segment中的所有的document都碰到一个非中断的异常,
      // 在这样的情况下我们还还没有初始化FieldsWriter.
      initFieldsWriter(state.context);
      // 填充文档?
      fill(numDocs);
    }
    
    if (fieldsWriter != null) {
      try {
        fieldsWriter.finish(state.fieldInfos, numDocs);
      } finally {
        fieldsWriter.close();
        fieldsWriter = null;
        lastDocID = 0;
      }
    }
  }
  
  /**
   * 初始化fieldsWriter
   * @param context
   * @throws IOException
   */
  private synchronized void initFieldsWriter(IOContext context) throws IOException {
    if (fieldsWriter == null) {
      fieldsWriter = codec.storedFieldsFormat().fieldsWriter(docWriter.directory, docWriter.getSegmentInfo(), context);
      lastDocID = 0;
    }
  }

  int allocCount;

  @Override
  void abort() {
    reset();

    if (fieldsWriter != null) {
      fieldsWriter.abort();
      fieldsWriter = null;
      lastDocID = 0;
    }
  }

  /** Fills in any hole in the docIDs
   *   
   * */
  void fill(int docID) throws IOException {
    // We must "catch up" for all docs before us
    // that had no stored fields:
	// 我们必须补充上所有没有存储Field的文档
    while(lastDocID < docID) {
      fieldsWriter.startDocument(0);
      lastDocID++;
      fieldsWriter.finishDocument();
    }
  }

  @Override
  void finishDocument() throws IOException {
    assert docWriter.writer.testPoint("StoredFieldsWriter.finishDocument start");

    initFieldsWriter(IOContext.DEFAULT);
    fill(docState.docID);

    if (fieldsWriter != null && numStoredFields > 0) {
      fieldsWriter.startDocument(numStoredFields);
      for (int i = 0; i < numStoredFields; i++) {
        fieldsWriter.writeField(fieldInfos[i], storedFields[i]);
      }
      fieldsWriter.finishDocument();
      lastDocID++;
    }

    reset();
    assert docWriter.writer.testPoint("StoredFieldsWriter.finishDocument end");
  }

  @Override
  public void addField(int docID, IndexableField field, FieldInfo fieldInfo) {
	// 如果该Field是可以存储的话
    if (field.fieldType().stored()) {
      // 如果当前已经添加的Field满了, 则重新分配存储空间
      if (numStoredFields == storedFields.length) {
    	// 计算需要分配的新的大小
        int newSize = ArrayUtil.oversize(numStoredFields + 1, RamUsageEstimator.NUM_BYTES_OBJECT_REF);
        
        IndexableField[] newArray = new IndexableField[newSize];
        System.arraycopy(storedFields, 0, newArray, 0, numStoredFields);
        storedFields = newArray;
      
        FieldInfo[] newInfoArray = new FieldInfo[newSize];
        System.arraycopy(fieldInfos, 0, newInfoArray, 0, numStoredFields);
        fieldInfos = newInfoArray;
      }

      storedFields[numStoredFields] = field;
      fieldInfos[numStoredFields] = fieldInfo;
      numStoredFields++;

      assert docState.testPoint("StoredFieldsWriterPerThread.processFields.writeField");
    }
  }
}
