package org.apache.lucene.codecs;

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

import org.apache.lucene.document.Document;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.FieldInfos;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.MergeState;
import org.apache.lucene.util.Bits;

/**
 * Codec API for writing stored fields:
 * <p>
 * <ol>
 *   <li>For every document, {@link #startDocument(int)} is called,
 *       informing the Codec how many fields will be written.
 *   <li>{@link #writeField(org.apache.lucene.index.FieldInfo, org.apache.lucene.index.IndexableField)} is called for
 *       each field in the document.
 *   <li>After all documents have been written, {@link #finish(org.apache.lucene.index.FieldInfos, int)}
 *       is called for verification/sanity-checks.
 *   <li>Finally the writer is closed ({@link #close()})
 * </ol>
 * 
 * @lucene.experimental
 * <p>
 * 
 * 写入存储field的编码器:
 * <p>
 * <ol>
 *   <li>对于每一个文档, 当{@link #startDocument(int)}被调用的时候,
 *   	 记录当前文档有多少Field需要被写入.
 *   <li>对于文档中的每一个Field调用
 *   	 {@link #writeField(org.apache.lucene.index.FieldInfo, org.apache.lucene.index.IndexableField)}
 *   <li>在所有的文档写入完毕以后, 调用{@link #finish(org.apache.lucene.index.FieldInfos, int)}
 *   	 去检查下当前的状态.
 *   <li>最后关闭writer ({@link #close()})
 * </ol>
 */
public abstract class StoredFieldsWriter implements Closeable {
  
  /** Sole constructor. (For invocation by subclass 
   *  constructors, typically implicit.) */
  protected StoredFieldsWriter() {
  }

  /** Called before writing the stored fields of the document.
   *  {@link #writeField(org.apache.lucene.index.FieldInfo, org.apache.lucene.index.IndexableField)} will be called
   *  <code>numStoredFields</code> times. Note that this is
   *  called even if the document has no stored fields, in
   *  this case <code>numStoredFields</code> will be zero. 
   *  <p>
   *  在处理一个文档开始的时候调用.
   *  在把当前文档的存储的Field写入以前调用这个方法.
   *  {@link #writeField(org.apache.lucene.index.FieldInfo, org.apache.lucene.index.IndexableField)}会被调用
   *  <code>numStoredFields</code>次.
   *  注意: 即使当前文档没有存储Field(<code>numStoredFields</code>为0), 这个方法也会被调用.
   *  
   *  */
  public abstract void startDocument(int numStoredFields) throws IOException;

  /** Called when a document and all its fields have been added. 
   *  <p>
   *  当一个文档的所有的Store Field 都被写入以后, 调用这个方法.
   *  表示处理文档结束?
   * */
  public void finishDocument() throws IOException {}

  /** Writes a single stored field. 
   *  <p>
   *  写如一个存储的Field
   * */
  public abstract void writeField(FieldInfo info, IndexableField field) throws IOException;

  /** Aborts writing entirely, implementation should remove
   *  any partially-written files, etc. 
   *  <p>
   *  完全中止写入, 实现需要删除那些已经写了一半的文件
   *  */
  public abstract void abort();
  
  /** Called before {@link #close()}, passing in the number
   *  of documents that were written. Note that this is 
   *  intentionally redundant (equivalent to the number of
   *  calls to {@link #startDocument(int)}, but a Codec should
   *  check that this is the case to detect the JRE bug described 
   *  in LUCENE-1282. 
   *  <p>
   *  在调用{@link #close()}之前进行调用, 传入已经写入的文档数.
   *  注意: 这里这样做是故意再检查一次的(跟调用{@link #startDocument(int)}的数目相同),
   *  不过一个编码器学要去检查JRE的一个bug, LUCENE-1282
   *  */
  public abstract void finish(FieldInfos fis, int numDocs) throws IOException;
  
  /** Merges in the stored fields from the readers in 
   *  <code>mergeState</code>. The default implementation skips
   *  over deleted documents, and uses {@link #startDocument(int)},
   *  {@link #writeField(org.apache.lucene.index.FieldInfo, org.apache.lucene.index.IndexableField)}, and {@link #finish(org.apache.lucene.index.FieldInfos, int)},
   *  returning the number of documents that were written.
   *  Implementations can override this method for more sophisticated
   *  merging (bulk-byte copying, etc). 
   *  <p>
   *  合并在(MergeState)中那个的存储Field.
   *  默认的实现会自动跳过被删除的文档, 
   *  然后调用and uses {@link #startDocument(int)},
   *  {@link #writeField(org.apache.lucene.index.FieldInfo, org.apache.lucene.index.IndexableField)}, 
   *  和{@link #finish(org.apache.lucene.index.FieldInfos, int)},
   *  然后返回已经写入的文档数目.
   *  实现可以覆盖这个方法来提供更加精妙的合并方法(比如: bulk-byte coping)
   *  */
  public int merge(MergeState mergeState) throws IOException {
    int docCount = 0;
    // 迭代MergeState中的Reader
    for (AtomicReader reader : mergeState.readers) {
      final int maxDoc = reader.maxDoc();
      // 一个位图, 代表这个文档在不在
      final Bits liveDocs = reader.getLiveDocs();
      // 从0开始根据docId进行迭代
      for (int i = 0; i < maxDoc; i++) {
        if (liveDocs != null && !liveDocs.get(i)) {
          // skip deleted docs
          continue;
        }
        // TODO: this could be more efficient using
        // FieldVisitor instead of loading/writing entire
        // doc; ie we just have to renumber the field number
        // on the fly?
        // TODO: 应该使用更有效率的FieldVisitor, 而不是整个读取文档;
        // 比如我们只需要个field number 重新分配一个值?
        // NOTE: it's very important to first assign to doc then pass it to
        // fieldsWriter.addDocument; see LUCENE-1282
        // 注意: 一定要先定义一个引用指向根据docId获取的文档, 在把它做为参数传给
        // fieldWriter之前. LUCENE-1282
        // 获取文档
        Document doc = reader.document(i);
        // 添加文档
        addDocument(doc, mergeState.fieldInfos);
        // 添加文档数
        docCount++;
        mergeState.checkAbort.work(300);
      }
    }
    finish(mergeState.fieldInfos, docCount);
    return docCount;
  }
  
  /** sugar method for startDocument() + writeField() for every stored field in the document 
   *  <p>
   *  蜜糖方法根据一个document去调用startDocument()和wirteField()
   * */
  protected final void addDocument(Iterable<? extends IndexableField> doc, FieldInfos fieldInfos) throws IOException {
    int storedCount = 0; // 记录存储的Field的数目
    for (IndexableField field : doc) {
      if (field.fieldType().stored()) {
        storedCount++;
      }
    }
    // 开始写入文档
    startDocument(storedCount);
    
    // 迭代field, 依次写入
    for (IndexableField field : doc) {
      if (field.fieldType().stored()) {
      writeField(fieldInfos.fieldInfo(field.name()), field);
      }
    }
    
    // 完成文档写入
    finishDocument();
  }

  @Override
  public abstract void close() throws IOException;
}
