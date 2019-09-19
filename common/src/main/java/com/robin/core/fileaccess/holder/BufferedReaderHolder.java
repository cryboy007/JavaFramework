/*
 * Copyright (c) 2015,robinjim(robinjim@126.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robin.core.fileaccess.holder;

import com.robin.core.fileaccess.iterator.AbstractFileIterator;
import com.robin.core.fileaccess.iterator.TextFileIteratorFactory;
import com.robin.core.fileaccess.meta.DataCollectionMeta;
import com.robin.core.fileaccess.pool.ResourceAccessHolder;
import com.robin.core.fileaccess.util.AbstractResourceAccessUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Slf4j
public class BufferedReaderHolder extends AbstractResourceHolder {
    protected BufferedReader reader;
    protected AbstractFileIterator iterator;

    @Override
    public void init(DataCollectionMeta colmeta) throws Exception {
        String[] tag = AbstractResourceAccessUtil.retrieveResource(colmeta.getPath());
        AbstractResourceAccessUtil util = ResourceAccessHolder.getAccessUtilByProtocol(tag[0].toLowerCase());
        InputStream inputStream = util.getInResourceByStream(colmeta);
        iterator = TextFileIteratorFactory.getProcessIteratorByPath(colmeta, inputStream);
    }


    public boolean hasNext() {
        return iterator.hasNext();
    }

    public Map<String, Object> getRecords() {
        return iterator.next();
    }

    @Override
    public void close() throws IOException {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            reader = null;
            setBusyTag(false);
        }
    }


}
