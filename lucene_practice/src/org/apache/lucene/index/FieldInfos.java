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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.lucene.index.FieldInfo.DocValuesType;
import org.apache.lucene.index.FieldInfo.IndexOptions;

/** 
 * Collection of {@link org.apache.lucene.index.FieldInfo}s (accessible by number or by name).
 *  @lucene.experimental
 *  <p>
 *  FieldInfo的集合, 可以通过name或者number(index)进行访问
 */
public class FieldInfos implements Iterable<FieldInfo> {
  private final boolean hasFreq;
  private final boolean hasProx;
  private final boolean hasPayloads;
  private final boolean hasOffsets;
  private final boolean hasVectors;
  private final boolean hasNorms;
  private final boolean hasDocValues;
  
  // 使用treeMap来进行存储按数值查找的map, 2分法
  private final SortedMap<Integer,FieldInfo> byNumber = new TreeMap<Integer,FieldInfo>();
  // 使用hashMap来进行存储按name查找的map, hash table
  // 这里的name指的是FieldName
  private final HashMap<String,FieldInfo> byName = new HashMap<String,FieldInfo>();
  // 保留对构造的FieldInfo[]的储存, 以unmodifiableCollection的形式
  private final Collection<FieldInfo> values; // for an unmodifiable iterator
  
  /**
   * Constructs a new FieldInfos from an array of FieldInfo objects
   */
  public FieldInfos(FieldInfo[] infos) {
    boolean hasVectors = false;
    boolean hasProx = false;
    boolean hasPayloads = false;
    boolean hasOffsets = false;
    boolean hasFreq = false;
    boolean hasNorms = false;
    boolean hasDocValues = false;
    
    for (FieldInfo info : infos) {
      // 对map进行put的时候, 返回值是一个value类型的引用, 如果不为null的话说明里面按这个key已经存了一个值
      FieldInfo previous = byNumber.put(info.number, info);
      if (previous != null) {
        throw new IllegalArgumentException("duplicate field numbers: " + previous.name + " and " + info.name + " have: " + info.number);
      }
      previous = byName.put(info.name, info);
      if (previous != null) {
        throw new IllegalArgumentException("duplicate field names: " + previous.number + " and " + info.number + " have: " + info.name);
      }
      
      // 如果有一个fieldInfo把其中一项设置成true的话, 当前的就会是true
      hasVectors |= info.hasVectors();
      hasProx |= info.isIndexed() && info.getIndexOptions().compareTo(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS) >= 0;
      hasFreq |= info.isIndexed() && info.getIndexOptions() != IndexOptions.DOCS_ONLY;
      hasOffsets |= info.isIndexed() && info.getIndexOptions().compareTo(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS) >= 0;
      hasNorms |= info.hasNorms();
      hasDocValues |= info.hasDocValues();
      hasPayloads |= info.hasPayloads();
    }
    
    this.hasVectors = hasVectors;
    this.hasProx = hasProx;
    this.hasPayloads = hasPayloads;
    this.hasOffsets = hasOffsets;
    this.hasFreq = hasFreq;
    this.hasNorms = hasNorms;
    this.hasDocValues = hasDocValues;
    // 保留初始化的时候的数据
    this.values = Collections.unmodifiableCollection(byNumber.values());
  }
  
  /** Returns true if any fields have freqs */
  public boolean hasFreq() {
    return hasFreq;
  }
  
  /** Returns true if any fields have positions */
  public boolean hasProx() {
    return hasProx;
  }

  /** Returns true if any fields have payloads */
  public boolean hasPayloads() {
    return hasPayloads;
  }

  /** Returns true if any fields have offsets */
  public boolean hasOffsets() {
    return hasOffsets;
  }
  
  /** Returns true if any fields have vectors */
  public boolean hasVectors() {
    return hasVectors;
  }
  
  /** Returns true if any fields have norms */
  public boolean hasNorms() {
    return hasNorms;
  }
  
  /** Returns true if any fields have DocValues */
  public boolean hasDocValues() {
    return hasDocValues;
  }
  
  /** Returns the number of fields */
  public int size() {
	// byNumber和byName里的数量应该一致
    assert byNumber.size() == byName.size();
    return byNumber.size();
  }
  
  /**
   * Returns an iterator over all the fieldinfo objects present,
   * ordered by ascending field number
   */
  // TODO: what happens if in fact a different order is used?
  @Override
  public Iterator<FieldInfo> iterator() {
    return values.iterator();
  }

  /**
   * Return the fieldinfo object referenced by the field name
   * @return the FieldInfo object or null when the given fieldName
   * doesn't exist.
   */  
  public FieldInfo fieldInfo(String fieldName) {
    return byName.get(fieldName);
  }

  /**
   * Return the fieldinfo object referenced by the fieldNumber.
   * @param fieldNumber field's number. if this is negative, this method
   *        always returns null.
   * @return the FieldInfo object or null when the given fieldNumber
   * doesn't exist.
   */  
  // TODO: fix this negative behavior, this was something related to Lucene3x?
  // if the field name is empty, i think it writes the fieldNumber as -1
  public FieldInfo fieldInfo(int fieldNumber) {
    return (fieldNumber >= 0) ? byNumber.get(fieldNumber) : null;
  }
  
  /**
   * 全局存储Field name跟Field Number的关系,
   * 还储存了Field的DocValuesType来做约束.
   * 这个类的所有的对外的方法都是同步的.
   */
  static final class FieldNumbers {
    
    private final Map<Integer,String> numberToName; //从Field number->name的map
    private final Map<String,Integer> nameToNumber; //从Filed name->number的map
    // We use this to enforce that a given field never
    // changes DV type, even across segments / IndexWriter
    // sessions:
    // 我们使用这个map来强制一个已经分配过DocValuesType的field, 
    // 不会去更改它的类型, 即使是在不同的segment和IndexWriter中
    private final Map<String,DocValuesType> docValuesType;

    // TODO: we should similarly catch an attempt to turn
    // norms back on after they were already ommitted; today
    // we silently discard the norm but this is badly trappy
    private int lowestUnassignedFieldNumber = -1;
    
    /**
     * 初始化, 内部的3个map都初始化为HashMap
     */
    FieldNumbers() {
      this.nameToNumber = new HashMap<String, Integer>();
      this.numberToName = new HashMap<Integer, String>();
      this.docValuesType = new HashMap<String,DocValuesType>();
    }
    
    /**
     * Returns the global field number for the given field name. If the name
     * does not exist yet it tries to add it with the given preferred field
     * number assigned if possible otherwise the first unassigned field number
     * is used as the field number.
     * <p>
     * 根据一个指定的FieldName返回一个全局的field number.
     * 如果name不存在的话, 它尝试去根据指定的preferredFieldNumber, 添加当前Field到集合中.
     * 也有可能会给当前没添加过的field name 分配一个还没有被分配过的field number.
     */
    synchronized int addOrGet(String fieldName, int preferredFieldNumber, DocValuesType dvType) {
      /* 分配DocValuesType */
      // 如果docValuesType 不为空的话
      if (dvType != null) {
    	// 先去根据fieldName去获取是否已经存放了一个指定的DocValuesType
        DocValuesType currentDVType = docValuesType.get(fieldName);
        if (currentDVType == null) {
          // 如果还没有被添加过的话, 指定当前指定的docValuesType到集合中
          docValuesType.put(fieldName, dvType);
        } else if (currentDVType != null && currentDVType != dvType) {
          // 如果已经指定过了一个DocValuesType还指定另外一个的话, 抛出异常
          throw new IllegalArgumentException("cannot change DocValues type from " + currentDVType + " to " + dvType + " for field \"" + fieldName + "\"");
        }
      }
      
      /* 分配fieldNumber */
      // 先根据当前指定的fieldName去获取fieldNumber, 拿得到就直接返回
      Integer fieldNumber = nameToNumber.get(fieldName);
      // 如果当前还没有分配过fieldNumber的话
      if (fieldNumber == null) {
        final Integer preferredBoxed = Integer.valueOf(preferredFieldNumber);
        
        if (preferredFieldNumber != -1 && !numberToName.containsKey(preferredBoxed)) {
          // 如果当前的preferredFieldNumber还没有被分配的话, 直接使用这个做为当前field的number
          // cool - we can use this number globally
          fieldNumber = preferredBoxed;
        } else {
          // 如果偏好值被指定了的话, 不断自增lowestUnassignedFieldNumber, 来获取一个最小的还未分配的number
          // find a new FieldNumber
          while (numberToName.containsKey(++lowestUnassignedFieldNumber)) {
            // might not be up to date - lets do the work once needed
          }
          fieldNumber = lowestUnassignedFieldNumber;
        }
        
        // 把当前field number 跟 name的对应关系存入map
        numberToName.put(fieldNumber, fieldName);
        nameToNumber.put(fieldName, fieldNumber);
      }

      return fieldNumber.intValue();
    }

    // used by assert
    synchronized boolean containsConsistent(Integer number, String name, DocValuesType dvType) {
      // 这里关于dvType的判断是直接判断引用相等
      return name.equals(numberToName.get(number))
          && number.equals(nameToNumber.get(name)) &&
        (dvType == null || docValuesType.get(name) == null || dvType == docValuesType.get(name));
    }

    synchronized void clear() {
      numberToName.clear();
      nameToNumber.clear();
      docValuesType.clear();
    }
  }
  
  /**
   * 包含对FieldNumbers引用
   * 和一个FieldName->FieldInfo的map 
   *
   */
  static final class Builder {
	// 一个FieldName -> FieldInfo的HashMap
    private final HashMap<String,FieldInfo> byName = new HashMap<String,FieldInfo>();
    final FieldNumbers globalFieldNumbers;

    Builder() {
      this(new FieldNumbers());
    }
    
    /**
     * Creates a new instance with the given {@link org.apache.lucene.index.FieldInfos.FieldNumbers}.
     * <p>
     * 根据指定的globalFieldNumbers实例化一个Builder
     */
    Builder(FieldNumbers globalFieldNumbers) {
      assert globalFieldNumbers != null;
      this.globalFieldNumbers = globalFieldNumbers;
    }

    public void add(FieldInfos other) {
      for(FieldInfo fieldInfo : other){ 
        add(fieldInfo);
      }
    }
   
    /** NOTE: this method does not carry over termVector
     *  booleans nor docValuesType; the indexer chain
     *  (TermVectorsConsumerPerField, DocFieldProcessor) must
     *  set these fields when they succeed in consuming
     *  the document 
     *  <p>
     *  注意: 这个方法不会去设置storeTermVector和storePayloads.
     *  索引链(TermVectorConsumerPerField和DocFieldProcess)在成功消耗document以后,
     *  必须设置这些Field.
     *  */
    public FieldInfo addOrUpdate(String name, IndexableFieldType fieldType) {
      // TODO: really, indexer shouldn't even call this
      // method (it's only called from DocFieldProcessor);
      // rather, each component in the chain should update
      // what it "owns".  EG fieldType.indexOptions() should
      // be updated by maybe FreqProxTermsWriterPerField:
      return addOrUpdateInternal(name, -1, fieldType.indexed(), false,
                                 fieldType.omitNorms(), false,
                                 fieldType.indexOptions(), fieldType.docValueType(), null);
    }
    
    /**
     * 
     */
    private FieldInfo addOrUpdateInternal(String name, int preferredFieldNumber, boolean isIndexed,
        boolean storeTermVector,
        boolean omitNorms, boolean storePayloads, IndexOptions indexOptions, DocValuesType docValues, DocValuesType normType) {
      // 去查找当前name的FieldInfo是否已经存储过
      FieldInfo fi = fieldInfo(name);
      
      if (fi == null) {
        // This field wasn't yet added to this in-RAM
        // segment's FieldInfo, so now we get a global
        // number for this field.  If the field was seen
        // before then we'll get the same name and number,
        // else we'll allocate a new one:

    	// 当前Field还没有被添加到当前的RAM segment的FieldInfo中,
    	// 所以给这个field分配一个全局的FieldNumber.
    	// 如果这个Field已经出现过的话, 我们会得到相同的FieldName和FiledNumber,
    	// 没有的话就重新分配一个:
    	
    	// 分配一个FieldNumber
        final int fieldNumber = globalFieldNumbers.addOrGet(name, preferredFieldNumber, docValues);
        // 实例化一个新的FieldInfo
        fi = new FieldInfo(name, isIndexed, fieldNumber, storeTermVector, omitNorms, storePayloads, indexOptions, docValues, normType, null);
        assert !byName.containsKey(fi.name);
        assert globalFieldNumbers.containsConsistent(Integer.valueOf(fi.number), fi.name, fi.getDocValuesType());
        // 添加到FieldName->FieldInfo Map中来
        byName.put(fi.name, fi);
      } else {
    	// 更新当前的FieldInfo
        fi.update(isIndexed, storeTermVector, omitNorms, storePayloads, indexOptions);
        // 更新DocValuesType
        if (docValues != null) {
          fi.setDocValuesType(docValues);
        }

        if (!fi.omitsNorms() && normType != null) {
          fi.setNormValueType(normType);
        }
      }
      return fi;
    }
    
    public FieldInfo add(FieldInfo fi) {
      // IMPORTANT - reuse the field number if possible for consistent field numbers across segments
      return addOrUpdateInternal(fi.name, fi.number, fi.isIndexed(), fi.hasVectors(),
                 fi.omitsNorms(), fi.hasPayloads(),
                 fi.getIndexOptions(), fi.getDocValuesType(), fi.getNormType());
    }
    
    /**
     * 根据指定的fieldName去查找当前的FieldName->FieldInfo Map
     * @param fieldName
     * @return
     */
    public FieldInfo fieldInfo(String fieldName) {
      return byName.get(fieldName);
    }
    
    /**
     * 把当前的FieldName->FiledInfo的map转换成FieldInfos输出
     * @return
     */
    final FieldInfos finish() {
      return new FieldInfos(byName.values().toArray(new FieldInfo[byName.size()]));
    }
  }
}
