package com.project.scanner.repository;

import com.project.scanner.model.ComplianceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplianceStatusRepository extends JpaRepository<ComplianceStatus, Long>{
    
}





