package com.project.scanner.controller;

import com.project.scanner.model.ComplianceStatus;
import com.project.scanner.service.ComplianceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/compliance")
public class ComplianceStatusController {

    @Autowired
    private ComplianceStatusService complianceService;

    /**
     * GET /api/compliance/statuses
     * Retrieves all compliance status records.
     */
    @GetMapping("/statuses")
    public ResponseEntity<List<ComplianceStatus>> getAllComplianceStatuses() {
        List<ComplianceStatus> statuses = complianceService.getAllComplianceStatuses();
        return ResponseEntity.ok(statuses);
    }

    /**
     * POST /api/compliance/run-playbook
     * Triggers the Ansible playbook execution.
     */
    @PostMapping("/run-playbook")
    public ResponseEntity<String> runPlaybook() {
        try {
            complianceService.runAnsiblePlaybook();
            return ResponseEntity.ok("Ansible playbook execution started successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error running playbook: " + e.getMessage());
        }
    }
}

