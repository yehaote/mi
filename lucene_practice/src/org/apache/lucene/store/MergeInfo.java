package org.apache.lucene.store;
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

/**
 * <p>A MergeInfo provides information required for a MERGE context.
 *  It is used as part of an {@link org.apache.lucene.store.IOContext} in case of MERGE context.</p>
 *  
 *  MergeInfo提供了在MERGE Context需要的信息.
 *  它被用来做为IOContext中的一部分, 当是MERGE context的时候.
 */

public class MergeInfo {
  
  public final int totalDocCount; // doc的总数
  
  public final long estimatedMergeBytes; //估算的Merge需要的Bytes?
  
  public final boolean isExternal; // 外部的?
  
  public final int mergeMaxNumSegments; // merge的最大数量Segment的限制
  

  /**
   * <p>Creates a new {@link org.apache.lucene.store.MergeInfo} instance from
   * the values required for a MERGE {@link org.apache.lucene.store.IOContext} context.
   * 
   * These values are only estimates and are not the actual values.
   * 创建一个MergeInfo实例, 这些数值都是估算的并不一定是确切的数值.
   */

  public MergeInfo(int totalDocCount, long estimatedMergeBytes, boolean isExternal, int mergeMaxNumSegments) {
    this.totalDocCount = totalDocCount;
    this.estimatedMergeBytes = estimatedMergeBytes;
    this.isExternal = isExternal;
    this.mergeMaxNumSegments = mergeMaxNumSegments;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + (int) (estimatedMergeBytes ^ (estimatedMergeBytes >>> 32));
    result = prime * result + (isExternal ? 1231 : 1237);
    result = prime * result + mergeMaxNumSegments;
    result = prime * result + totalDocCount;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MergeInfo other = (MergeInfo) obj;
    if (estimatedMergeBytes != other.estimatedMergeBytes)
      return false;
    if (isExternal != other.isExternal)
      return false;
    if (mergeMaxNumSegments != other.mergeMaxNumSegments)
      return false;
    if (totalDocCount != other.totalDocCount)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "MergeInfo [totalDocCount=" + totalDocCount
        + ", estimatedMergeBytes=" + estimatedMergeBytes + ", isExternal="
        + isExternal + ", mergeMaxNumSegments=" + mergeMaxNumSegments + "]";
  }
}
