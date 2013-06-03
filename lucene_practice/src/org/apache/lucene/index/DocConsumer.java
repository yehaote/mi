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

/**
 * 文档消费者 
 */
abstract class DocConsumer {
  /**
   * 处理文档
   * @param fieldInfos
   * @throws IOException
   */
  abstract void processDocument(FieldInfos.Builder fieldInfos) throws IOException;
  
  /**
   * 完成处理文档
   * @throws IOException
   */
  abstract void finishDocument() throws IOException; 
  
  /**
   * 刷新缓存输出 
   * @param state
   * @throws IOException
   */
  abstract void flush(final SegmentWriteState state) throws IOException;
  
  /**
   * 中止
   */
  abstract void abort();
  
  /**
   * 在刷新缓存输出以后做的工作
   */
  abstract void doAfterFlush();
}
