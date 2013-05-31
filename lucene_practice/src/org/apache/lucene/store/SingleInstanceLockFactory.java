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
import java.util.HashSet;

/**
 * Implements {@link org.apache.lucene.store.LockFactory} for a single in-process instance,
 * meaning all locking will take place through this one instance.
 * Only use this {@link org.apache.lucene.store.LockFactory} when you are certain all
 * IndexReaders and IndexWriters for a given index are running
 * against a single shared in-process Directory instance.  This is
 * currently the default locking for RAMDirectory.
 *
 * @see org.apache.lucene.store.LockFactory
 * 
 * 单例的锁工厂实现, 意味着所有的锁都通过这一个实例.
 * 只有当你确定一个给定的索引跑在一个共享的Directory下, IndexReaders和IndexWriters使用这个实例的时候
 * 使用这个锁. 因为这个锁是基于当前Directory下内存锁, 而不像其他的基于物理的文件锁.
 * 这个锁是RAMDirectory的默认锁实现.
 */

public class SingleInstanceLockFactory extends LockFactory {

  private HashSet<String> locks = new HashSet<String>(); // 总的锁的集合

  @Override
  public Lock makeLock(String lockName) {
    // We do not use the LockPrefix at all, because the private
    // HashSet instance effectively scopes the locking to this
    // single Directory instance.
	// 根据指定的锁的名称产生一个新的锁对象, 仅仅是产生对应的锁对象,
	// 不会去直接获取锁.
    return new SingleInstanceLock(locks, lockName);
  }

  @Override
  public void clearLock(String lockName) throws IOException {
	// 清除锁, 如果当前是有锁的状态的话, 会强制清楚.
    synchronized(locks) {
      if (locks.contains(lockName)) {
        locks.remove(lockName);
      }
    }
  }
}

/**
 * 单例锁
 */
class SingleInstanceLock extends Lock {

  String lockName; // 当前锁的唯一标识符 
  private HashSet<String> locks;
  
  public SingleInstanceLock(HashSet<String> locks, String lockName) {
    this.locks = locks;
    this.lockName = lockName;
  }

  @Override
  public boolean obtain() throws IOException {
	// 获取锁, 把当前的lockName加入到指定的HashSet当中
	// 内部同步的机制, 使用HashSet的对象锁
    synchronized(locks) {
      return locks.add(lockName);
    }
  }

  @Override
  public void release() {
	// 释放锁
    synchronized(locks) {
      locks.remove(lockName);
    }
  }

  @Override
  public boolean isLocked() {
	// 判断是否锁着
    synchronized(locks) {
      return locks.contains(lockName);
    }
  }

  @Override
  public String toString() {
    return super.toString() + ": " + lockName;
  }
}
