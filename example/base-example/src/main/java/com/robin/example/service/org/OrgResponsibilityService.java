package com.robin.example.service.org;

import com.robin.core.base.service.BaseAnnotationJdbcService;
import com.robin.core.base.service.IBaseAnnotationJdbcService;
import com.robin.example.model.org.OrgResponsibility;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class OrgResponsibilityService extends BaseAnnotationJdbcService<OrgResponsibility,Long> implements IBaseAnnotationJdbcService<OrgResponsibility,Long> {
}
