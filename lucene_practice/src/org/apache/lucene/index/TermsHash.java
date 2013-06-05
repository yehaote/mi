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
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.util.ByteBlockPool;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Counter;
import org.apache.lucene.util.IntBlockPool;

/** This class implements {@link InvertedDocConsumer}, which
 *  is passed each token produced by the analyzer on each
 *  field.  It stores these tokens in a hash table, and
 *  allocates separate byte streams per token.  Consumers of
 *  this class, eg {@link FreqProxTermsWriter} and {@link
 *  TermVectorsConsumer}, write their own byte streams
 *  under each term.
 *  
 *  这个类实现InvertedDocConsumer, 接收每个Field被分词器解析出来的token.
 *  它把这些token存储在一个hash table当中, 为每个token分配单独的byte stream.
 *  这个类的消费者(FreqProxTermsWriter和TermVectorsConsumer), 
 *  根据这些term写入到他们自己的stream.
 *  
 *  在Lucene自带的IndexingChain中, 
 *  consumer 为 FreqProxTermsWriter (主要的)
 *  nextTermsHash 为 TermVectorsConsumer
 */
final class TermsHash extends InvertedDocConsumer {
  
  final TermsHashConsumer consumer; // (TermVectorsConsumer或者FreqProxTermsWriter)
  // 链表结构(比如FreqProxTermsWriter后的next是TermVectorsConsumer, TermVectorsConsumer)
  // 而TermVectorsConsumer的后面就没有了, 单向链表, 不是双向的
  final TermsHash nextTermsHash; 

  final IntBlockPool intPool; // 存储int的池
  final ByteBlockPool bytePool; // 存储byte的池
  // 这个词是存什么呢? 还共享的
  ByteBlockPool termBytePool; //  对于一条链上的TermHash是可以共享的
  final Counter bytesUsed;

  final boolean primary; // 是否是主要的?
  final DocumentsWriterPerThread.DocState docState; //  

  // Used when comparing postings via termRefComp, in TermsHashPerField
  final BytesRef tr1 = new BytesRef();
  final BytesRef tr2 = new BytesRef();

  // Used by perField to obtain terms from the analysis chain
  final BytesRef termBytesRef = new BytesRef(10);

  final boolean trackAllocations; // 是否跟踪所有的byte使用

  public TermsHash(final DocumentsWriterPerThread docWriter, final TermsHashConsumer consumer, boolean trackAllocations, final TermsHash nextTermsHash) {
    this.docState = docWriter.docState;
    this.consumer = consumer;
    this.trackAllocations = trackAllocations; 
    this.nextTermsHash = nextTermsHash;
    this.bytesUsed = trackAllocations ? docWriter.bytesUsed : Counter.newCounter();
    intPool = new IntBlockPool(docWriter.intBlockAllocator);
    bytePool = new ByteBlockPool(docWriter.byteBlockAllocator);

    if (nextTermsHash != null) {
      // We are primary
      // 如果还有nextTermHash就说明当前这个是primary,
      // 这样的话就让nextTermHash共用自己的TermBytePool
      primary = true;
      termBytePool = bytePool;
      nextTermsHash.termBytePool = bytePool;
    } else {
      primary = false;
    }
  }

  @Override
  public void abort() {
    reset();
    try {
      consumer.abort();
    } finally {
      if (nextTermsHash != null) {
        nextTermsHash.abort();
      }
    }
  }

  // Clear all state
  void reset() {
    // we don't reuse so we drop everything and don't fill with 0
	// 因为不需要重用, 所有丢弃所有的数据, 也不需要置为0
    intPool.reset(false, false); 
    bytePool.reset(false, false);
  }

  @Override
  void flush(Map<String,InvertedDocConsumerPerField> fieldsToFlush, final SegmentWriteState state) throws IOException {
    Map<String,TermsHashConsumerPerField> childFields = new HashMap<String,TermsHashConsumerPerField>();
    Map<String,InvertedDocConsumerPerField> nextChildFields;

    if (nextTermsHash != null) {
      nextChildFields = new HashMap<String,InvertedDocConsumerPerField>();
    } else {
      nextChildFields = null;
    }

    for (final Map.Entry<String,InvertedDocConsumerPerField> entry : fieldsToFlush.entrySet()) {
      TermsHashPerField perField = (TermsHashPerField) entry.getValue();
      childFields.put(entry.getKey(), perField.consumer);
      if (nextTermsHash != null) {
        nextChildFields.put(entry.getKey(), perField.nextPerField);
      }
    }

    consumer.flush(childFields, state);

    if (nextTermsHash != null) {
      nextTermsHash.flush(nextChildFields, state);
    }
  }

  @Override
  InvertedDocConsumerPerField addField(DocInverterPerField docInverterPerField, final FieldInfo fieldInfo) {
	  // 每次都产生一个新的InvertedDocConsumerPerField(TermsHashPerField)
    return new TermsHashPerField(docInverterPerField, this, nextTermsHash, fieldInfo);
  }

  @Override
  void finishDocument() throws IOException {
    consumer.finishDocument(this);
    if (nextTermsHash != null) {
      nextTermsHash.consumer.finishDocument(nextTermsHash);
    }
  }

  @Override
  void startDocument() throws IOException {
    consumer.startDocument();
    if (nextTermsHash != null) {
      nextTermsHash.consumer.startDocument();
    }
  }
}
