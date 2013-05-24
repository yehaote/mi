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
 * IOContext holds additional details on the merge/search context. A IOContext
 * object can never be initialized as null as passed as a parameter to either
 * {@link Directory#openInput(String, org.apache.lucene.store.IOContext)} or
 * {@link Directory#createOutput(String, org.apache.lucene.store.IOContext)}
 * 
 * IOContext包含额外的信息在merge/search context.
 * 一个IOContexnt不会被出是化为null做为一个参数传入Directory.openInput()或者
 * Directory.createOutput()
 */
public class IOContext {

  /**
   * Context is a enumerator which specifies the context in which the Directory
   * is being used for.
   * Context是一个枚举, 指定当前的Directory是用来做什么的.
   */
  public enum Context {
    MERGE, READ, FLUSH, DEFAULT
  };

  /**
   * An object of a enumerator Context type
   */
  public final Context context; // 当前Directory的用途

  public final MergeInfo mergeInfo; // merge需要用的信息

  public final FlushInfo flushInfo; // flush需要用的信息

  public final boolean readOnce; // 只读一次?

  public static final IOContext DEFAULT = new IOContext(Context.DEFAULT);

  public static final IOContext READONCE = new IOContext(true); // 读, 并且只读一次

  public static final IOContext READ = new IOContext(false); // 读, 可能不只读一次
  
  /**
   * 默认是读的参数, 并且不限制读的次数
   */
  public IOContext() {
    this(false);
  }

  /**
   * 实例化一个用于flush的IOContext
   * @param flushInfo
   */
  public IOContext(FlushInfo flushInfo) {
    assert flushInfo != null;
    this.context = Context.FLUSH;
    this.mergeInfo = null;
    this.readOnce = false;
    this.flushInfo = flushInfo;
  }

  public IOContext(Context context) {
    this(context, null);
  }

  /**
   * 实例化一个读的IOContext
   * 可以指定是否为readOnce
   * @param readOnce
   */
  private IOContext(boolean readOnce) {
    this.context = Context.READ;
    this.mergeInfo = null;
    this.readOnce = readOnce;
    this.flushInfo = null;
  }

  public IOContext(MergeInfo mergeInfo) {
    this(Context.MERGE, mergeInfo);
  }
  
  /**
   * 实例化一个Merge的IOContext
   * @param context
   * @param mergeInfo
   */
  private IOContext(Context context, MergeInfo mergeInfo) {
    assert context != Context.MERGE || mergeInfo != null : "MergeInfo must not be null if context is MERGE";
    assert context != Context.FLUSH : "Use IOContext(FlushInfo) to create a FLUSH IOContext";
    this.context = context;
    this.readOnce = false;
    this.mergeInfo = mergeInfo;
    this.flushInfo = null;
  }
  
  /**
   * This constructor is used to initialize a {@link org.apache.lucene.store.IOContext} instance with a new value for the readOnce variable.
   * @param ctxt {@link org.apache.lucene.store.IOContext} object whose information is used to create the new instance except the readOnce variable.
   * @param readOnce The new {@link org.apache.lucene.store.IOContext} object will use this value for readOnce.
   */
  public IOContext(IOContext ctxt, boolean readOnce) {
    this.context = ctxt.context;
    this.mergeInfo = ctxt.mergeInfo;
    this.flushInfo = ctxt.flushInfo;
    this.readOnce = readOnce;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((context == null) ? 0 : context.hashCode());
    result = prime * result + ((flushInfo == null) ? 0 : flushInfo.hashCode());
    result = prime * result + ((mergeInfo == null) ? 0 : mergeInfo.hashCode());
    result = prime * result + (readOnce ? 1231 : 1237);
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
    IOContext other = (IOContext) obj;
    if (context != other.context)
      return false;
    if (flushInfo == null) {
      if (other.flushInfo != null)
        return false;
    } else if (!flushInfo.equals(other.flushInfo))
      return false;
    if (mergeInfo == null) {
      if (other.mergeInfo != null)
        return false;
    } else if (!mergeInfo.equals(other.mergeInfo))
      return false;
    if (readOnce != other.readOnce)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "IOContext [context=" + context + ", mergeInfo=" + mergeInfo
        + ", flushInfo=" + flushInfo + ", readOnce=" + readOnce + "]";
  }

}