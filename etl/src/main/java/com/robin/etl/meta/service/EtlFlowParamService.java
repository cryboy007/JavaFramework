package com.robin.etl.meta.service;

import com.robin.core.base.service.BaseAnnotationJdbcService;
import com.robin.etl.meta.model.EtlFlowParam;
import org.springframework.stereotype.Service;

@Service
public class EtlFlowParamService extends BaseAnnotationJdbcService<EtlFlowParam,Long> {
}
