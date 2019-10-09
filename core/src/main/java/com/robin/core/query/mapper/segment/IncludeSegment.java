package com.robin.core.query.mapper.segment;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;
import java.util.Map;

/**
 * <p>Created at: 2019-09-29 11:02:30</p>
 *
 * @author robinjim
 * @version 1.0
 */
public class IncludeSegment extends AbstractSegment {

    public IncludeSegment(String nameSpace,String id, String inputStr) {
        super(nameSpace,id,inputStr);
    }


    @Override
    public String getSqlPart(Map<String,Object> params,Map<String, ImmutablePair<String, List<AbstractSegment>>> segmentsMap) {
        StringBuilder builder=new StringBuilder();
        if(segmentsMap.containsKey(value)){
            ImmutablePair<String,List<AbstractSegment>> pair=segmentsMap.get(value);
            for(AbstractSegment segment:pair.right){
                builder.append(segment.getSqlPart(params,segmentsMap));
            }
        }
        return builder.toString();
    }
}
