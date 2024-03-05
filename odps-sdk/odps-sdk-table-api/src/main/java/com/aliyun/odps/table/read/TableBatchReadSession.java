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

package com.aliyun.odps.table.read;

import com.aliyun.odps.data.ArrayRecord;
import com.aliyun.odps.table.SessionType;
import com.aliyun.odps.table.configuration.ReaderOptions;
import com.aliyun.odps.table.read.split.InputSplit;
import com.aliyun.odps.table.read.split.InputSplitAssigner;
import org.apache.arrow.vector.VectorSchemaRoot;

import java.io.IOException;

/**
 * A logical representation of a maxcompute table batch read session.
 */
public interface TableBatchReadSession extends TableReadSession {

    /**
     * Return the input split assigner {@link InputSplitAssigner} of this table batch read session.
     */
    InputSplitAssigner getInputSplitAssigner() throws IOException;

    /**
     * Returns a record data reader {@link SplitReader} to do the actual reading work.
     */
    default SplitReader<ArrayRecord> createRecordReader(InputSplit split, ReaderOptions options) throws IOException {
        throw new UnsupportedOperationException("Cannot create record reader.");
    }

    /**
     * Returns a arrow data reader {@link SplitReader} to do the actual reading work.
     */
    default SplitReader<VectorSchemaRoot> createArrowReader(InputSplit split, ReaderOptions options) throws IOException {
        throw new UnsupportedOperationException("Cannot create arrow reader.");
    }

    @Override
    default SessionType getType() {
        return SessionType.BATCH_READ;
    }
}
