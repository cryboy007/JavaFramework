package com.robin.core.fileaccess.pool;

import com.robin.core.fileaccess.holder.InputStreamHolder;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class InputStreamPoolFactory implements PooledObjectFactory<InputStreamHolder> {


    @Override
    public PooledObject<InputStreamHolder> makeObject() throws Exception {
        InputStreamHolder holder=new InputStreamHolder();
        return new DefaultPooledObject<>(holder);
    }

    @Override
    public void destroyObject(PooledObject<InputStreamHolder> pooledObject) throws Exception {
        pooledObject.getObject().setBusyTag(false);
    }

    @Override
    public boolean validateObject(PooledObject<InputStreamHolder> pooledObject) {
        return pooledObject.getObject().isBusyTag();
    }

    @Override
    public void activateObject(PooledObject<InputStreamHolder> pooledObject) throws Exception {
        pooledObject.getObject().setBusyTag(true);
    }

    @Override
    public void passivateObject(PooledObject<InputStreamHolder> pooledObject) throws Exception {

    }
}
