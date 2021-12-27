package com.robin.comm.fileaccess.iterator;

import com.github.os72.protobuf.dynamic.DynamicSchema;
import com.github.os72.protobuf.dynamic.MessageDefinition;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.ExtensionRegistry;
import com.robin.comm.fileaccess.util.ProtoBufUtil;
import com.robin.core.base.util.Const;
import com.robin.core.fileaccess.iterator.AbstractFileIterator;
import com.robin.core.fileaccess.meta.DataCollectionMeta;
import com.robin.core.fileaccess.meta.DataSetColumnMeta;
import com.robin.core.fileaccess.util.ResourceUtil;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.robin.core.fileaccess.util.ResourceUtil.getProcessPath;

public class ProtoBufFileIterator extends AbstractFileIterator {
    private DynamicSchema schema;
    private DynamicMessage message;
    private ExtensionRegistry registry;
    private DynamicSchema.Builder schemaBuilder;
    private DynamicMessage.Builder mesgBuilder;

    public ProtoBufFileIterator(DataCollectionMeta colmeta) {
        super(colmeta);
    }



    @Override
    public void init()  {
        try {
            if (!CollectionUtils.isEmpty(colmeta.getColumnList())) {
                schemaBuilder = DynamicSchema.newBuilder();
                schemaBuilder.setName(colmeta.getClassNamespace() + ".proto");
                MessageDefinition.Builder msgBuilder = MessageDefinition.newBuilder(colmeta.getValueClassName());
                for (int i = 0; i < colmeta.getColumnList().size(); i++) {
                    DataSetColumnMeta column = colmeta.getColumnList().get(i);
                    msgBuilder = msgBuilder.addField(column.isRequired() ? "required" : "optional", ProtoBufUtil.translateType(column), column.getColumnName(), i + 1);
                }
                MessageDefinition definition = msgBuilder.build();
                schemaBuilder.addMessageDefinition(definition);
                schema = schemaBuilder.build();
                mesgBuilder=DynamicMessage.newBuilder(schema.getMessageDescriptor(colmeta.getValueClassName()));
                registry=getExtension(schema,colmeta);
            }else{
                checkAccessUtil(null);
                instream = accessUtil.getInResourceByStream(colmeta, ResourceUtil.getProcessPath(colmeta.getPath()));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }


    }
    public static  ExtensionRegistry getExtension(DynamicSchema schema, DataCollectionMeta colmeta){
        ExtensionRegistry extensionRegistry = ExtensionRegistry.newInstance();
        for(Descriptors.FieldDescriptor descriptor:schema.getMessageDescriptor(colmeta.getValueClassName()).getFields()){
            extensionRegistry.add(descriptor);
        }
        return extensionRegistry;
    }

    @Override
    public boolean hasNext() {
        try {
            if (mesgBuilder.mergeDelimitedFrom(instream)) {
                message=mesgBuilder.build();
                return true;
            }else {
                message=null;
                return false;
            }
        }catch (Exception ex){

        }
        return false;
    }

    @Override
    public Map<String, Object> next() {
        if(message==null){
            throw new NoSuchElementException("");
        }
        Map<String,Object> tmap=new HashMap<String, Object>();
        for(Descriptors.FieldDescriptor descriptor:schema.getMessageDescriptor(colmeta.getValueClassName()).getFields()){
            tmap.put(descriptor.getName(),message.getField(descriptor));
        }
        return tmap;
    }

    @Override
    public void remove() {
        try {
            if (mesgBuilder.mergeDelimitedFrom(instream, registry)) {
                mesgBuilder.build();
            }
        }catch (Exception ex){

        }
    }
}
