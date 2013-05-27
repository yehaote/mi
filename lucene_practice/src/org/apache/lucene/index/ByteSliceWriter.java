package org.apache.lucene.index;

import org.apache.lucene.store.DataOutput;
import org.apache.lucene.util.ByteBlockPool;

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
 * Class to write byte streams into slices of shared
 * byte[].  This is used by DocumentsWriter to hold the
 * posting list for many terms in RAM.
 * 
 * 写入byte流到分配共享的byte数组的类.
 * 这被DocumentWriter用来在内存中存储term的posting list.
 */

final class ByteSliceWriter extends DataOutput {

  private byte[] slice;
  private int upto;
  private final ByteBlockPool pool;

  int offset0; // 什么用?

  public ByteSliceWriter(ByteBlockPool pool) {
    this.pool = pool;
  }

  /**
   * Set up the writer to write at address.
   * 
   * 设置writer到写入的地址
   */
  public void init(int address) {
	// 定位到指定的分片?
    slice = pool.buffers[address >> ByteBlockPool.BYTE_BLOCK_SHIFT];
    // 判断分片不为空
    assert slice != null;
    // 取掩码?
    upto = address & ByteBlockPool.BYTE_BLOCK_MASK;
    // 
    offset0 = address;
    assert upto < slice.length;
  }

  /** Write byte into byte slice stream */
  @Override
  public void writeByte(byte b) {
    assert slice != null;
    if (slice[upto] != 0) {
      upto = pool.allocSlice(slice, upto);
      slice = pool.buffer;
      offset0 = pool.byteOffset;
      assert slice != null;
    }
    slice[upto++] = b;
    assert upto != slice.length;
  }

  @Override
  public void writeBytes(final byte[] b, int offset, final int len) {
    final int offsetEnd = offset + len;
    while(offset < offsetEnd) {
      if (slice[upto] != 0) {
        // End marker
        upto = pool.allocSlice(slice, upto);
        slice = pool.buffer;
        offset0 = pool.byteOffset;
      }

      slice[upto++] = b[offset++];
      assert upto != slice.length;
    }
  }

  public int getAddress() {
    return upto + (offset0 & DocumentsWriterPerThread.BYTE_BLOCK_NOT_MASK);
  }
}