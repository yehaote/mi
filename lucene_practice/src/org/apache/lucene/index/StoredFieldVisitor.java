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

import org.apache.lucene.document.Document;
import org.apache.lucene.document.DocumentStoredFieldVisitor;

/**
 * Expert: provides a low-level means of accessing the stored field
 * values in an index.  See {@link org.apache.lucene.index.IndexReader#document(int,
 * org.apache.lucene.index.StoredFieldVisitor)}.
 *
 * <p><b>NOTE</b>: a {@code StoredFieldVisitor} implementation
 * should not try to load or visit other stored documents in
 * the same reader because the implementation of stored
 * fields for most codecs is not reeentrant and you will see
 * strange exceptions as a result.
 *
 * <p>See {@link DocumentStoredFieldVisitor}, which is a
 * <code>StoredFieldVisitor</code> that builds the
 * {@link Document} containing all stored fields.  This is
 * used by {@link org.apache.lucene.index.IndexReader#document(int)}.
 *
 * @lucene.experimental 
 * <p>
 * 高级: 提供访问索引中存储Field的低等级操作.
 * See {@link org.apache.lucene.index.IndexReader#document(int,org.apache.lucene.index.StoredFieldVisitor)}.
 * <p><b>注意</b>: 一个{@code StoreFieldVistor}实现, 不要去尝试加载或读取另外存储的document在相同Reader中,
 * 因为Stored的实现对于大多数codec是不reeentrant.
 * <p>DocumentStoredFieldVisitor构建一个Document包含所有的存储Field. 
 * 在{@link org.apache.lucene.index.IndexReader#document(int)}.
 * */

public abstract class StoredFieldVisitor {

  /** Sole constructor. (For invocation by subclass 
   * constructors, typically implicit.) */
  protected StoredFieldVisitor() {
  }
  
  /** Process a binary field. 
   * @param value newly allocated byte array with the binary contents.
   * 处理一个binary field 
   */
  public void binaryField(FieldInfo fieldInfo, byte[] value) throws IOException {
  }

  /** Process a string field 
   *  处理一个StringField
   * */
  public void stringField(FieldInfo fieldInfo, String value) throws IOException {
  }

  /** Process a int numeric field. 
   *  处理一个int 数值类型的field
   * */
  public void intField(FieldInfo fieldInfo, int value) throws IOException {
  }

  /** Process a long numeric field.
   *  处理一个long 数值类型的Field 
   * */
  public void longField(FieldInfo fieldInfo, long value) throws IOException {
  }

  /** Process a float numeric field. 
   *  处理一个float 数值类型的Field
   * */
  public void floatField(FieldInfo fieldInfo, float value) throws IOException {
  }

  /** Process a double numeric field. 
   *  处理一个document 数值类型的Field
   * */
  public void doubleField(FieldInfo fieldInfo, double value) throws IOException {
  }
  
  /**
   * Hook before processing a field.
   * Before a field is processed, this method is invoked so that
   * subclasses can return a {@link org.apache.lucene.index.StoredFieldVisitor.Status} representing whether
   * they need that particular field or not, or to stop processing
   * entirely.
   * <p>
   * 在处理Field前挂载, 这个方法被调用以后子类可以返回一个
   * {@link org.apache.lucene.index.StoredFieldVisitor.Status} 
   * 表现特定的Field是否需要读取, 或者全部停止处理.
   */
  public abstract Status needsField(FieldInfo fieldInfo) throws IOException;
  
  /**
   * Enumeration of possible return values for {@link #needsField}.
   * <p>
   * 列举{@link #needsField}可以返回的值
   */
  public static enum Status {
    /** YES: the field should be visited. */
    YES,
    /** NO: don't visit this field, but continue processing fields for this document. */
    NO,
    /** STOP: don't visit this field and stop processing any other fields for this document. */
    STOP
  }
}