package org.apache.lucene.util;

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

import java.util.Arrays;
import java.util.List;

import static org.apache.lucene.util.RamUsageEstimator.NUM_BYTES_OBJECT_REF;

/** 
 * Class that Posting and PostingVector use to write byte
 * streams into shared fixed-size byte[] arrays.  The idea
 * is to allocate slices of increasing lengths For
 * example, the first slice is 5 bytes, the next slice is
 * 14, etc.  We start by writing our bytes into the first
 * 5 bytes.  When we hit the end of the slice, we allocate
 * the next slice and then write the address of the new
 * slice into the last 4 bytes of the previous slice (the
 * "forwarding address").
 *
 * Each slice is filled with 0's initially, and we mark
 * the end with a non-zero byte.  This way the methods
 * that are writing into the slice don't need to record
 * its length and instead allocate a new slice once they
 * hit a non-zero byte. 
 * 
 * @lucene.internal
 * 
 * Posting和PostingVector用这个类来写字节流到一个共享的固定大小的byte[]的数组.
 * 核心思想: 分配不同的分配大小, 递增
 * 比如: 第一个分配5 byte, 下一个分片14 byte.
 * 开始我们先把数据写入到5个byte的分片, 当数据写不下了, 分配一下个分片, 并把下一个分片的地址
 * (4个byte长度)写入到前一个分片.
 * 
 * 每一个分片初始化的时候都被指定为0, 标记最后一个为非0. 这样我们写入分片的时候不需要去记录
 * 它的大小. 当碰到一个非0的byte的时候就分配一个新的分片.
 **/
public final class ByteBlockPool {
  public final static int BYTE_BLOCK_SHIFT = 15;
  public final static int BYTE_BLOCK_SIZE = 1 << BYTE_BLOCK_SHIFT; // 约等于32m?
  public final static int BYTE_BLOCK_MASK = BYTE_BLOCK_SIZE - 1;

  /** Abstract class for allocating and freeing byte
   *  blocks. 
   *  抽象类Allocator(分配器)
   *  负责分配和释放byte block(块)
   *  */
  public abstract static class Allocator {
    protected final int blockSize; // 每块的大小, 这样分配器分出来的块大小都是一样的

    public Allocator(int blockSize) {
      this.blockSize = blockSize;
    }
    
    /**
     * 回收数据块
     * @param blocks
     * @param start
     * @param end
     */
    public abstract void recycleByteBlocks(byte[][] blocks, int start, int end);
    
    /**
     * 回收数据块
     * @param blocks
     */
    public void recycleByteBlocks(List<byte[]> blocks) {
      final byte[][] b = blocks.toArray(new byte[blocks.size()][]);
      recycleByteBlocks(b, 0, b.length);
    }
    
    /**
     * 获得新的数据块
     * @return
     */
    public byte[] getByteBlock() {
      return new byte[blockSize]; // 是new出来的? 不应该是直接在一块很大的byte[]上写的
    }
  }
  
  /** A simple {@link org.apache.lucene.util.ByteBlockPool.Allocator} that never recycles. 
   * 一个很简单的Allocator实现, 永远不会回收
   * */
  public static final class DirectAllocator extends Allocator {
    
	// 不指定块大小的话, 默认使用BYTE_BLOCK_SIZE, 32m
    public DirectAllocator() {
      this(BYTE_BLOCK_SIZE);
    }

    public DirectAllocator(int blockSize) {
      super(blockSize);
    }

    @Override
    public void recycleByteBlocks(byte[][] blocks, int start, int end) {
    }
  }
  
  /** A simple {@link org.apache.lucene.util.ByteBlockPool.Allocator} that never recycles, but
   *  tracks how much total RAM is in use. 
   *  一个很简单的Allocator实现, 永远不会回收, 但是会跟踪RAM的使用情况
   *  不指定块大小的话, 使用BYTE_BLOCK_SIZE作为每块的大小
   *  */
  public static class DirectTrackingAllocator extends Allocator {
    private final Counter bytesUsed; // 使用的计数器
    
    public DirectTrackingAllocator(Counter bytesUsed) {
      this(BYTE_BLOCK_SIZE, bytesUsed);
    }
    
    /**
     * 根据指定大小生产分配器
     * @param blockSize
     * @param bytesUsed
     */
    public DirectTrackingAllocator(int blockSize, Counter bytesUsed) {
      super(blockSize);
      this.bytesUsed = bytesUsed;
    }

    @Override
    public byte[] getByteBlock() {
      // 分配一个新的byte[], 并把开销累加到统计里面
      bytesUsed.addAndGet(blockSize);
      return new byte[blockSize];
    }
    
    @Override
    public void recycleByteBlocks(byte[][] blocks, int start, int end) {
      bytesUsed.addAndGet(-((end-start)* blockSize));
      for (int i = start; i < end; i++) {
        blocks[i] = null;
      }
    }
  };

  /**
   * array of buffers currently used in the pool. Buffers are allocated if
   * needed don't modify this outside of this class.
   * 
   * 记录目前用于缓存的byte[].
   * Buffers是分配好的, 如果没必须要的话不要在这个类以后修改它.
   */
  public byte[][] buffers = new byte[10][];
  
  /** index into the buffers array pointing to the current buffer used as the head 
   *  指向buffers中当前哪个数组在头上?
   * */
  private int bufferUpto = -1;                        // Which buffer we are upto, bufferUpto是指当前有多少个buffer?
  /** Where we are in head buffer */
  public int byteUpto = BYTE_BLOCK_SIZE;

  /** Current head buffer
   *  当前的head buffer 
   * */
  public byte[] buffer;
  /** Current head offset 
   * 当前head buffer的offset
   * */
  public int byteOffset = -BYTE_BLOCK_SIZE;

  private final Allocator allocator; // 当前的分配器

  public ByteBlockPool(Allocator allocator) {
    this.allocator = allocator;
  }
  
  /**
   * Resets the pool to its initial state reusing the first buffer and fills all
   * buffers with <tt>0</tt> bytes before they reused or passed to
   * {@link org.apache.lucene.util.ByteBlockPool.Allocator#recycleByteBlocks(byte[][], int, int)}. Calling
   * {@link org.apache.lucene.util.ByteBlockPool#nextBuffer()} is not needed after reset.
   * 
   * 
   */
  public void reset() {
    reset(true, true);
  }
  
  /**
   * Expert: Resets the pool to its initial state reusing the first buffer. Calling
   * {@link org.apache.lucene.util.ByteBlockPool#nextBuffer()} is not needed after reset.
   * @param zeroFillBuffers if <code>true</code> the buffers are filled with <tt>0</tt>. 
   *        This should be set to <code>true</code> if this pool is used with slices.
   *        如果是true的话, buffer会被设置为0. 如果这个池是跟slices一起使用的话, 需要为true.
   * @param reuseFirst if <code>true</code> the first buffer will be reused and calling
   *        {@link org.apache.lucene.util.ByteBlockPool#nextBuffer()} is not needed after reset iff the
   *        block pool was used before ie. {@link org.apache.lucene.util.ByteBlockPool#nextBuffer()} was called before.
   *        如果是true的话, 第一个buffer被会重用, 不需要调用nextBuffer()在rest.
   *        当且仅当block pool已经在nextBufffer()被调用以前使用过. 
   * 
   * 重置当前的缓存池, 重用第一个buffer.
   * 在调用rest以后是不需要调用nextBuffer()的.    
   * 但是既然都要消掉了, 为什么还要把数据都重置为0呢?     
   */
  public void reset(boolean zeroFillBuffers, boolean reuseFirst) {
	// 如果没有buffer被使用过, 就什么都不住
    if (bufferUpto != -1) {
      // We allocated at least one buffer
      // 如果需要置为0的话
      if (zeroFillBuffers) {
    	// 迭代现在有的buffer, 全部置为0, bufferUpto是指当前有多少个buffer?
        for(int i=0;i<bufferUpto;i++) {
          // bufferUpto以下的buffer全部置为0
          // Fully zero fill buffers that we fully used
          Arrays.fill(buffers[i], (byte) 0);
        }
        // bufferUpto这个buffer把有写入的置为0?
        // Partial zero fill the final buffer
        Arrays.fill(buffers[bufferUpto], 0, byteUpto, (byte) 0);
      }
     // 如果bufferUpto>0说明当前buffer有使用过,
     // 或者不需要重用第一个buffer的话
     if (bufferUpto > 0 || !reuseFirst) {
       // 如果需用重用就留下第一个buffer
       final int offset = reuseFirst ? 1 : 0;  
       // Recycle all but the first buffer
       // 回收所有的块, 如果制定了重用第一块的话, 就留下第一块
       allocator.recycleByteBlocks(buffers, offset, 1+bufferUpto);
       // 把引用置为null
       Arrays.fill(buffers, offset, 1+bufferUpto, null);
     }
     // 重新设置对应的参数 ??为什么要这样设置
     if (reuseFirst) {
       // Re-use the first buffer
       // 重用第一块
       bufferUpto = 0;
       byteUpto = 0;
       byteOffset = 0;
       buffer = buffers[0];
     } else {
       bufferUpto = -1;
       byteUpto = BYTE_BLOCK_SIZE;
       byteOffset = -BYTE_BLOCK_SIZE;
       buffer = null;
     }
    }
  }
  /**
   * Advances the pool to its next buffer. This method should be called once
   * after the constructor to initialize the pool. In contrast to the
   * constructor a {@link org.apache.lucene.util.ByteBlockPool#reset()} call will advance the pool to
   * its first buffer immediately.
   * 
   * 从池里前进到到下一组.
   * 这个方法在构造器初始化pool的时候需要被调用一次.
   * 调用rest()方法的话会让pool直接前进到第一个buffer.
   */
  public void nextBuffer() {
	// 如果bufferUpto+1 == buffers.length的话, 说明在pool中还有空余的buffer
    if (1+bufferUpto == buffers.length) {
      byte[][] newBuffers = new byte[ArrayUtil.oversize(buffers.length+1,
                                                        NUM_BYTES_OBJECT_REF)][];
      System.arraycopy(buffers, 0, newBuffers, 0, buffers.length);
      buffers = newBuffers;
    }
    buffer = buffers[1+bufferUpto] = allocator.getByteBlock();
    bufferUpto++;

    byteUpto = 0;
    byteOffset += BYTE_BLOCK_SIZE;
  }
  
  /**
   * Allocates a new slice with the given size. 
   * @see org.apache.lucene.util.ByteBlockPool#FIRST_LEVEL_SIZE
   */
  public int newSlice(final int size) {
    if (byteUpto > BYTE_BLOCK_SIZE-size)
      nextBuffer();
    final int upto = byteUpto;
    byteUpto += size;
    buffer[byteUpto-1] = 16;
    return upto;
  }

  // Size of each slice.  These arrays should be at most 16
  // elements (index is encoded with 4 bits).  First array
  // is just a compact way to encode X+1 with a max.  Second
  // array is the length of each slice, ie first slice is 5
  // bytes, next slice is 14 bytes, etc.
  
  /**
   * An array holding the offset into the {@link org.apache.lucene.util.ByteBlockPool#LEVEL_SIZE_ARRAY}
   * to quickly navigate to the next slice level.
   */
  public final static int[] NEXT_LEVEL_ARRAY = {1, 2, 3, 4, 5, 6, 7, 8, 9, 9};
  
  /**
   * An array holding the level sizes for byte slices.
   */
  public final static int[] LEVEL_SIZE_ARRAY = {5, 14, 20, 30, 40, 40, 80, 80, 120, 200};
  
  /**
   * The first level size for new slices
   * @see org.apache.lucene.util.ByteBlockPool#newSlice(int)
   */
  public final static int FIRST_LEVEL_SIZE = LEVEL_SIZE_ARRAY[0];

  /**
   * Creates a new byte slice with the given starting size and 
   * returns the slices offset in the pool.
   */
  public int allocSlice(final byte[] slice, final int upto) {

    final int level = slice[upto] & 15;
    final int newLevel = NEXT_LEVEL_ARRAY[level];
    final int newSize = LEVEL_SIZE_ARRAY[newLevel];

    // Maybe allocate another block
    if (byteUpto > BYTE_BLOCK_SIZE-newSize) {
      nextBuffer();
    }

    final int newUpto = byteUpto;
    final int offset = newUpto + byteOffset;
    byteUpto += newSize;

    // Copy forward the past 3 bytes (which we are about
    // to overwrite with the forwarding address):
    buffer[newUpto] = slice[upto-3];
    buffer[newUpto+1] = slice[upto-2];
    buffer[newUpto+2] = slice[upto-1];

    // Write forwarding address at end of last slice:
    slice[upto-3] = (byte) (offset >>> 24);
    slice[upto-2] = (byte) (offset >>> 16);
    slice[upto-1] = (byte) (offset >>> 8);
    slice[upto] = (byte) offset;
        
    // Write new level:
    buffer[byteUpto-1] = (byte) (16|newLevel);

    return newUpto+3;
  }

  // Fill in a BytesRef from term's length & bytes encoded in
  // byte block
  public void setBytesRef(BytesRef term, int textStart) {
    final byte[] bytes = term.bytes = buffers[textStart >> BYTE_BLOCK_SHIFT];
    int pos = textStart & BYTE_BLOCK_MASK;
    if ((bytes[pos] & 0x80) == 0) {
      // length is 1 byte
      term.length = bytes[pos];
      term.offset = pos+1;
    } else {
      // length is 2 bytes
      term.length = (bytes[pos]&0x7f) + ((bytes[pos+1]&0xff)<<7);
      term.offset = pos+2;
    }
    assert term.length >= 0;
  }
  
  /**
   * Appends the bytes in the provided {@link org.apache.lucene.util.BytesRef} at
   * the current position.
   */
  public void append(final BytesRef bytes) {
    int length = bytes.length;
    if (length == 0) {
      return;
    }
    int offset = bytes.offset;
    int overflow = (length + byteUpto) - BYTE_BLOCK_SIZE;
    do {
      if (overflow <= 0) { 
        System.arraycopy(bytes.bytes, offset, buffer, byteUpto, length);
        byteUpto += length;
        break;
      } else {
        final int bytesToCopy = length-overflow;
        if (bytesToCopy > 0) {
          System.arraycopy(bytes.bytes, offset, buffer, byteUpto, bytesToCopy);
          offset += bytesToCopy;
          length -= bytesToCopy;
        }
        nextBuffer();
        overflow = overflow - BYTE_BLOCK_SIZE;
      }
    }  while(true);
  }
  
  /**
   * Reads bytes bytes out of the pool starting at the given offset with the given  
   * length into the given byte array at offset <tt>off</tt>.
   * <p>Note: this method allows to copy across block boundaries.</p>
   */
  public void readBytes(final long offset, final byte bytes[], final int off, final int length) {
    if (length == 0) {
      return;
    }
    int bytesOffset = off;
    int bytesLength = length;
    int bufferIndex = (int) (offset >> BYTE_BLOCK_SHIFT);
    byte[] buffer = buffers[bufferIndex];
    int pos = (int) (offset & BYTE_BLOCK_MASK);
    int overflow = (pos + length) - BYTE_BLOCK_SIZE;
    do {
      if (overflow <= 0) {
        System.arraycopy(buffer, pos, bytes, bytesOffset, bytesLength);
        break;
      } else {
        final int bytesToCopy = length - overflow;
        System.arraycopy(buffer, pos, bytes, bytesOffset, bytesToCopy);
        pos = 0;
        bytesLength -= bytesToCopy;
        bytesOffset += bytesToCopy;
        buffer = buffers[++bufferIndex];
        overflow = overflow - BYTE_BLOCK_SIZE;
      }
    } while (true);
  }
}

