/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.aliyun.odps.table.metrics;

/**
 * A Counter is a {@link Metric} that measures a count.
 */
public interface Counter extends Metric {

    /**
     * Increment the current count by 1.
     */
    void inc();

    /**
     * Increment the current count by n.
     */
    void inc(long n);

    /**
     * Decrement the current count by 1.
     */
    void dec();

    /**
     * Decrement the current count by n.
     */
    void dec(long n);

    /**
     * Returns the current count.
     */
    long getCount();
}
