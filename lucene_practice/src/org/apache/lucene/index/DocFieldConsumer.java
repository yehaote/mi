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
import java.util.Map;

/**
 * Document Field 的消费者
 */
abstract class DocFieldConsumer {
  /** Called when DocumentsWriterPerThread decides to create a new
   *  segment 
   *  <p>
   *  当DocumentsWriterPerThread决定产生一个新的segment的时候调用这个方法
   *  */
  abstract void flush(Map<String, DocFieldConsumerPerField> fieldsToFlush, SegmentWriteState state) throws IOException;

  /** Called when an aborting exception is hit 
   *  当一个忽略的异常碰到的时候调用   
   * */
  abstract void abort();

  abstract void startDocument() throws IOException;
  
  /**
   * 添加一个Field, 返回一个DocFieldConsumerPerField的实现
   * @param fi
   * @return
   */
  abstract DocFieldConsumerPerField addField(FieldInfo fi);

  abstract void finishDocument() throws IOException;
}
