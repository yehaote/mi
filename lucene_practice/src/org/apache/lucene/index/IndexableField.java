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
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.search.similarities.DefaultSimilarity; // javadocs
import org.apache.lucene.search.similarities.Similarity; // javadocs
import org.apache.lucene.util.BytesRef;

// TODO: how to handle versioning here...?

// TODO: we need to break out separate StoredField...

/** Represents a single field for indexing.  IndexWriter
 *  consumes Iterable&lt;IndexableField&gt; as a document.
 *
 *  @lucene.experimental */

public interface IndexableField {

  /** Field name */
  public String name(); // Field的名称

  /** {@link IndexableFieldType} describing the properties
   * of this field.
   * 描述这个FieldType的属性
   * */
  public IndexableFieldType fieldType();
  
  /** 
   * Returns the field's index-time boost.
   * <p>
   * Only fields can have an index-time boost, if you want to simulate
   * a "document boost", then you must pre-multiply it across all the
   * relevant fields yourself. 
   * <p>The boost is used to compute the norm factor for the field.  By
   * default, in the {@link Similarity#computeNorm(FieldInvertState)} method, 
   * the boost value is multiplied by the length normalization factor and then
   * rounded by {@link DefaultSimilarity#encodeNormValue(float)} before it is stored in the
   * index.  One should attempt to ensure that this product does not overflow
   * the range of that encoding.
   * <p>
   * It is illegal to return a boost other than 1.0f for a field that is not
   * indexed ({@link IndexableFieldType#indexed()} is false) or omits normalization values
   * ({@link IndexableFieldType#omitNorms()} returns true).
   *
   * 返回这个Field的索引时级别的boost.
   * 只有Field能加boost, 所以如果你想要一个document的boost的话, 你可以给它所有的field都加上boost.
   * 这个boost是用来计算field的norm factor的.
   * 这个boost在存到索引中之前先乘以length normalization factor, 再rounded(取整? 应该算是一种平滑处理).
   * rounded的原因是防止这个数组溢出.
   * 对于一个不索引的Field的话, 加boost和省略(omit)normalization值, 都是不合法的.
   * @see Similarity#computeNorm(FieldInvertState)
   * @see DefaultSimilarity#encodeNormValue(float)
   */
  public float boost();

  /** Non-null if this field has a binary value */
  public BytesRef binaryValue();  // 返回BytesRef类型的值

  /** Non-null if this field has a string value */
  public String stringValue();  // 返回String类型的值

  /** Non-null if this field has a Reader value */
  public Reader readerValue(); // 返回Reader类型的值

  /** Non-null if this field has a numeric value */
  public Number numericValue();   // 返回Number类型的值

  /**
   * Creates the TokenStream used for indexing this field.  If appropriate,
   * implementations should use the given Analyzer to create the TokenStreams.
   * 创建一个TokenStream用来为这个field建立索引.
   * 如果合适的话, 实现类需要使用给出的analyzer去创建这个TokenStreams.
   * 
   * 具体分词方法调用的地方, Field调用这个方法来根据当前的field信息和analyzer产生token
   * @param analyzer Analyzer that should be used to create the TokenStreams from
   * @return TokenStream value for indexing the document.  Should always return
   *         a non-null value if the field is to be indexed
   * @throws java.io.IOException Can be thrown while creating the TokenStream
   */
  public TokenStream tokenStream(Analyzer analyzer) throws IOException;
}
