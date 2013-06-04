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

import org.apache.lucene.analysis.Analyzer; // javadocs
import org.apache.lucene.index.FieldInfo.DocValuesType;
import org.apache.lucene.index.FieldInfo.IndexOptions;

/** 
 * Describes the properties of a field.
 * @lucene.experimental 
 * <p/>
 * 描述一个Field的属性
 */
public interface IndexableFieldType {

  /** True if this field should be indexed (inverted) 
   *  <p>
   *  返回true, 如果当前的field是需要索引的(倒排) 
   * */
  public boolean indexed();

  /** True if the field's value should be stored 
   *  <p>
   *  返回true, 如果当前Field的值是需要被存储的
   * */
  public boolean stored();

  /** 
   * True if this field's value should be analyzed by the
   * {@link Analyzer}.
   * <p>
   * This has no effect if {@link #indexed()} returns false.
   * <p>
   * 返回true, 如果当前field的值是需要被{@link Analyzer}分词的话.
   * 注意: 如果{@link #indexed()}返回false的话, 这将不起作用.
   */
  public boolean tokenized();

  /** 
   * True if this field's indexed form should be also stored 
   * into term vectors.
   * <p>
   * This builds a miniature inverted-index for this field which
   * can be accessed in a document-oriented way from 
   * {@link org.apache.lucene.index.IndexReader#getTermVector(int,String)}.
   * <p>
   * This option is illegal if {@link #indexed()} returns false.
   * <p>
   * 返回true, 如果当前field的索引格式需要被以TermVector的形式存储的话.
   * 这个方法给当前的Field建立一个小型的倒排索引, 
   * 可以通过基于document的方式来进行访问{@link org.apache.lucene.index.IndexReader#getTermVector(int,String)}.
   * 如果当前的{@link #indexed()}返回false的话, 这个选项是非法的.
   */
  public boolean storeTermVectors();

  /** 
   * True if this field's token character offsets should also
   * be stored into term vectors.
   * <p>
   * This option is illegal if term vectors are not enabled for the field
   * ({@link #storeTermVectors()} is false)
   * <p>
   * 返回true, 如果当前field的token的character offset(基于分词前的字符的偏移量?)也需要被
   * 存储到term vector中.
   * 注意: 如果term vector没有被打开的话({@link #storeTermVectors()} is false), 
   * 这个选项是不合法的.
   */
  public boolean storeTermVectorOffsets();

  /** 
   * True if this field's token positions should also be stored
   * into the term vectors.
   * <p>
   * This option is illegal if term vectors are not enabled for the field
   * ({@link #storeTermVectors()} is false).
   * <p>
   * 返回true, 如果当前field的token的positions(注意跟character offset的区别)也需要被
   * 存储到term vector中.
   * 注意: 如果term vector没有被打开的话({@link #storeTermVectors()} is false), 
   * 这个选项是不合法的.
   */
  public boolean storeTermVectorPositions();
  
  /** 
   * True if this field's token payloads should also be stored
   * into the term vectors.
   * <p>
   * This option is illegal if term vector positions are not enabled 
   * for the field ({@link #storeTermVectors()} is false).
   * <p>
   * 返回true, 如果当前field的token的payloads也需要被存储到term vector中.
   * 注意: 如果term vector没有被打开的话({@link #storeTermVectors()} is false), 
   * 这个选项是不合法的.
   */
  public boolean storeTermVectorPayloads();

  /**
   * True if normalization values should be omitted for the field.
   * <p>
   * This saves memory, but at the expense of scoring quality (length normalization
   * will be disabled), and if you omit norms, you cannot use index-time boosts.
   * <p>
   * 返回true, 如果当前Field的normalization值需要被忽略的话. 
   * 忽略以后可以节约内存的开销, 但是在计算的分的质量上会有损失
   * (length normalization 就不能使用了), 还有也不能使用索引时的boost.
   */
  public boolean omitNorms();

  /** {@link org.apache.lucene.index.FieldInfo.IndexOptions}, describing what should be
   * recorded into the inverted index 
   * <p>
   * 描述当前索引的选项, 哪些东西应该被储存到倒排索引中.
   * */
  public IndexOptions indexOptions();

  /** 
   * DocValues {@link org.apache.lucene.index.FieldInfo.DocValuesType}: if non-null then the field's value
   * will be indexed into docValues.
   * <p>
   * 如果不是null的话, field的值会被存储到索引当中?
   */
  public DocValuesType docValueType();  
}
