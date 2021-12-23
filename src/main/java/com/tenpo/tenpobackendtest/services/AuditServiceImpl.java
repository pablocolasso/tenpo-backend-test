package com.tenpo.tenpobackendtest.services;

import com.tenpo.tenpobackendtest.entities.Audit;
import com.tenpo.tenpobackendtest.repositories.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AuditServiceImpl implements AuditService {

    @Autowired
    AuditRepository auditRepository;

    @Override
    public Audit saveAudit(final String url) {
        Audit audit = new Audit();
        audit.setUrl(url);
        return auditRepository.save(audit);
    }

    @Override
    public Page<Audit> getHistory(Integer pageNumber, Integer size) {
        int pS, pN;
        if (size != null && size != 0) {
            pS = size;
        } else {
            pS = 10;
        }
        if (pageNumber != null && pageNumber != 0) {
            pN = pageNumber;
        } else {
            pN = 0;
        }
        Pageable pageable = PageRequest.of(pN, pS, Sort.by("id"));
        Page<Audit> auditHostory = auditRepository.findAll(pageable);

        return auditHostory;    }
}
