package org.apache.lucene.analysis;

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

import java.io.Reader;

/**
 * Extension to {@link org.apache.lucene.analysis.Analyzer} suitable for Analyzers which wrap
 * other Analyzers.
 * <p/>
 * {@link #getWrappedAnalyzer(String)} allows the Analyzer
 * to wrap multiple Analyzers which are selected on a per field basis.
 * <p/>
 * {@link #wrapComponents(String, org.apache.lucene.analysis.Analyzer.TokenStreamComponents)} allows the
 * TokenStreamComponents of the wrapped Analyzer to then be wrapped
 * (such as adding a new {@link org.apache.lucene.analysis.TokenFilter} to form new TokenStreamComponents.
 */
public abstract class AnalyzerWrapper extends Analyzer {

  /**
   * Creates a new AnalyzerWrapper.  Since the {@link org.apache.lucene.analysis.Analyzer.ReuseStrategy} of
   * the wrapped Analyzers are unknown, {@link org.apache.lucene.analysis.Analyzer.PerFieldReuseStrategy} is assumed
   */
  protected AnalyzerWrapper() {
    super(new PerFieldReuseStrategy());
  }

  /**
   * Retrieves the wrapped Analyzer appropriate for analyzing the field with
   * the given name
   *
   * @param fieldName Name of the field which is to be analyzed
   * @return Analyzer for the field with the given name.  Assumed to be non-null
   */
  protected abstract Analyzer getWrappedAnalyzer(String fieldName);

  /**
   * Wraps / alters the given TokenStreamComponents, taken from the wrapped
   * Analyzer, to form new components.  It is through this method that new
   * TokenFilters can be added by AnalyzerWrappers.
   *
   *
   * @param fieldName Name of the field which is to be analyzed
   * @param components TokenStreamComponents taken from the wrapped Analyzer
   * @return Wrapped / altered TokenStreamComponents.
   */
  protected abstract TokenStreamComponents wrapComponents(String fieldName, TokenStreamComponents components);

  @Override
  protected final TokenStreamComponents createComponents(String fieldName, Reader aReader) {
    return wrapComponents(fieldName, getWrappedAnalyzer(fieldName).createComponents(fieldName, aReader));
  }

  @Override
  public final int getPositionIncrementGap(String fieldName) {
    return getWrappedAnalyzer(fieldName).getPositionIncrementGap(fieldName);
  }

  @Override
  public final int getOffsetGap(String fieldName) {
    return getWrappedAnalyzer(fieldName).getOffsetGap(fieldName);
  }

  @Override
  public final Reader initReader(String fieldName, Reader reader) {
    return getWrappedAnalyzer(fieldName).initReader(fieldName, reader);
  }
}
