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

import java.io.IOException;

/**
 * <p>Base class for Locking implementation.  {@link org.apache.lucene.store.Directory} uses
 * instances of this class to implement locking.</p>
 *
 * <p>Note that there are some useful tools to verify that
 * your LockFactory is working correctly: {@link
 * VerifyingLockFactory}, {@link LockStressTest}, {@link
 * LockVerifyServer}.</p>
 * 锁工厂的抽象类
 * 这里有一些实用的工具来验证你的LockFactory是不是运行正常. 比如:VerifyingLockFactory
 * @see LockVerifyServer
 * @see LockStressTest
 * @see VerifyingLockFactory
 */

public abstract class LockFactory {

  protected String lockPrefix = null;

  /**
   * Set the prefix in use for all locks created in this
   * LockFactory.  This is normally called once, when a
   * Directory gets this LockFactory instance.  However, you
   * can also call this (after this instance is assigned to
   * a Directory) to override the prefix in use.  This
   * is helpful if you're running Lucene on machines that
   * have different mount points for the same shared
   * directory.
   * 指定一个前缀为这个锁工厂创建的锁.
   * 这个一般方法在实例化一个锁工厂的时候调用一次.
   * 虽然你也可以在以后调用一个方法去覆盖原先的前缀.
   * 当在一个相同分片的directory有多个不同的mount点的时候,
   * 这个会很有帮助.
   */
  public void setLockPrefix(String lockPrefix) {
    this.lockPrefix = lockPrefix;
  }

  /**
   * Get the prefix in use for all locks created in this LockFactory.
   */
  public String getLockPrefix() {
    return this.lockPrefix;
  }

  /**
   * Return a new Lock instance identified by lockName.
   * @param lockName name of the lock to be created.
   * 产生一个Lock实例, 指定lockName(标识符)
   */
  public abstract Lock makeLock(String lockName);

  /**
   * Attempt to clear (forcefully unlock and remove) the
   * specified lock.  Only call this at a time when you are
   * certain this lock is no longer in use.
   * @param lockName name of the lock to be cleared.
   * 尝试去清楚指定的锁(强制解锁并且删除).
   * 只有在你确定这个锁不再使用的情况下才调用.
   */
  abstract public void clearLock(String lockName) throws IOException;
}
