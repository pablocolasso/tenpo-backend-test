package com.tenpo.tenpobackendtest.repositories;

import com.tenpo.tenpobackendtest.entities.Audit;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends PagingAndSortingRepository<Audit, Integer> {
}