package com.tenpo.tenpobackendtest.services;

import com.tenpo.tenpobackendtest.entities.Audit;
import org.springframework.data.domain.Page;

public interface AuditService {

    Audit saveAudit(String url);

    Page<Audit> getHistory(Integer pageNumber, Integer size);

}
