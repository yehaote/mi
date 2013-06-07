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

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.util.ArrayUtil;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Counter;
import org.apache.lucene.util.RamUsageEstimator;

/**
 * Holds all per thread, per field state.
 * 存储所有per thread, per field的状态.
 * (针对相同的name的field. 相同name的Field都必须有相同的配置? 只有一个FiledInfo进行存储)
 * <p>
 * 针对有相同FieldName的集合.
 */

final class DocFieldProcessorPerField {

  final DocFieldConsumerPerField consumer; // DocInverterPerField
  final FieldInfo fieldInfo; // 当前Field的fieldInfo
  private final Counter bytesUsed;

  DocFieldProcessorPerField next; // hash表单元的链式结构
  int lastGen = -1;

  int fieldCount; // 记录当前有多少Field
  IndexableField[] fields = new IndexableField[1]; // 用于记录field的数组
  private final Map<FieldInfo,String> dvFields = new HashMap<FieldInfo,String>();

  public DocFieldProcessorPerField(final DocFieldProcessor docFieldProcessor, final FieldInfo fieldInfo) {
    this.consumer = docFieldProcessor.consumer.addField(fieldInfo);
    this.fieldInfo = fieldInfo;
    this.bytesUsed = docFieldProcessor.bytesUsed;
  }
  
  /**
   * 添加一个Filed
   * @param field
   */
  public void addField(IndexableField field) {
    if (fieldCount == fields.length) {
      int newSize = ArrayUtil.oversize(fieldCount + 1, RamUsageEstimator.NUM_BYTES_OBJECT_REF);
      IndexableField[] newArray = new IndexableField[newSize];
      System.arraycopy(fields, 0, newArray, 0, fieldCount);
      fields = newArray;
    }

    fields[fieldCount++] = field;
  }

  public void abort() {
    consumer.abort();
  }
}
