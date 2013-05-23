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
import java.util.Arrays;

import org.apache.lucene.util.BytesRef;

/**
 * Maintains a {@link org.apache.lucene.index.IndexReader} {@link org.apache.lucene.index.TermState} view over
 * {@link org.apache.lucene.index.IndexReader} instances containing a single term. The
 * {@link org.apache.lucene.index.TermContext} doesn't track if the given {@link org.apache.lucene.index.TermState}
 * objects are valid, neither if the {@link org.apache.lucene.index.TermState} instances refer to the
 * same terms in the associated readers.
 * 
 * @lucene.experimental
 */
public final class TermContext {

  /** Holds the {@link IndexReaderContext} of the top-level
   *  {@link org.apache.lucene.index.IndexReader}, used internally only for
   *  asserting.
   *
   *  @lucene.internal */
  public final IndexReaderContext topReaderContext;
  private final TermState[] states;
  private int docFreq;
  private long totalTermFreq;

  //public static boolean DEBUG = BlockTreeTermsWriter.DEBUG;

  /**
   * Creates an empty {@link org.apache.lucene.index.TermContext} from a {@link IndexReaderContext}
   */
  public TermContext(IndexReaderContext context) {
    assert context != null && context.isTopLevel;
    topReaderContext = context;
    docFreq = 0;
    final int len;
    if (context.leaves() == null) {
      len = 1;
    } else {
      len = context.leaves().size();
    }
    states = new TermState[len];
  }
  
  /**
   * Creates a {@link org.apache.lucene.index.TermContext} with an initial {@link org.apache.lucene.index.TermState},
   * {@link org.apache.lucene.index.IndexReader} pair.
   */
  public TermContext(IndexReaderContext context, TermState state, int ord, int docFreq, long totalTermFreq) {
    this(context);
    register(state, ord, docFreq, totalTermFreq);
  }

  /**
   * Creates a {@link org.apache.lucene.index.TermContext} from a top-level {@link IndexReaderContext} and the
   * given {@link org.apache.lucene.index.Term}. This method will lookup the given term in all context's leaf readers
   * and register each of the readers containing the term in the returned {@link org.apache.lucene.index.TermContext}
   * using the leaf reader's ordinal.
   * <p>
   * Note: the given context must be a top-level context.
   */
  public static TermContext build(IndexReaderContext context, Term term, boolean cache)
      throws IOException {
    assert context != null && context.isTopLevel;
    final String field = term.field();
    final BytesRef bytes = term.bytes();
    final TermContext perReaderTermState = new TermContext(context);
    //if (DEBUG) System.out.println("prts.build term=" + term);
    for (final AtomicReaderContext ctx : context.leaves()) {
      //if (DEBUG) System.out.println("  r=" + leaves[i].reader);
      final Fields fields = ctx.reader().fields();
      if (fields != null) {
        final Terms terms = fields.terms(field);
        if (terms != null) {
          final TermsEnum termsEnum = terms.iterator(null);
          if (termsEnum.seekExact(bytes, cache)) { 
            final TermState termState = termsEnum.termState();
            //if (DEBUG) System.out.println("    found");
            perReaderTermState.register(termState, ctx.ord, termsEnum.docFreq(), termsEnum.totalTermFreq());
          }
        }
      }
    }
    return perReaderTermState;
  }

  /**
   * Clears the {@link org.apache.lucene.index.TermContext} internal state and removes all
   * registered {@link org.apache.lucene.index.TermState}s
   */
  public void clear() {
    docFreq = 0;
    Arrays.fill(states, null);
  }

  /**
   * Registers and associates a {@link org.apache.lucene.index.TermState} with an leaf ordinal. The leaf ordinal
   * should be derived from a {@link IndexReaderContext}'s leaf ord.
   */
  public void register(TermState state, final int ord, final int docFreq, final long totalTermFreq) {
    assert state != null : "state must not be null";
    assert ord >= 0 && ord < states.length;
    assert states[ord] == null : "state for ord: " + ord
        + " already registered";
    this.docFreq += docFreq;
    if (this.totalTermFreq >= 0 && totalTermFreq >= 0)
      this.totalTermFreq += totalTermFreq;
    else
      this.totalTermFreq = -1;
    states[ord] = state;
  }

  /**
   * Returns the {@link org.apache.lucene.index.TermState} for an leaf ordinal or <code>null</code> if no
   * {@link org.apache.lucene.index.TermState} for the ordinal was registered.
   * 
   * @param ord
   *          the readers leaf ordinal to get the {@link org.apache.lucene.index.TermState} for.
   * @return the {@link org.apache.lucene.index.TermState} for the given readers ord or <code>null</code> if no
   *         {@link org.apache.lucene.index.TermState} for the reader was registered
   */
  public TermState get(int ord) {
    assert ord >= 0 && ord < states.length;
    return states[ord];
  }

  /**
   *  Returns the accumulated document frequency of all {@link org.apache.lucene.index.TermState}
   *         instances passed to {@link #register(org.apache.lucene.index.TermState, int, int, long)}.
   * @return the accumulated document frequency of all {@link org.apache.lucene.index.TermState}
   *         instances passed to {@link #register(org.apache.lucene.index.TermState, int, int, long)}.
   */
  public int docFreq() {
    return docFreq;
  }
  
  /**
   *  Returns the accumulated term frequency of all {@link org.apache.lucene.index.TermState}
   *         instances passed to {@link #register(org.apache.lucene.index.TermState, int, int, long)}.
   * @return the accumulated term frequency of all {@link org.apache.lucene.index.TermState}
   *         instances passed to {@link #register(org.apache.lucene.index.TermState, int, int, long)}.
   */
  public long totalTermFreq() {
    return totalTermFreq;
  }
  
  /** expert: only available for queries that want to lie about docfreq
   * @lucene.internal */
  public void setDocFreq(int docFreq) {
    this.docFreq = docFreq;
  }
}