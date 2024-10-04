package com.project.scanner.service;

import com.project.scanner.model.ComplianceStatus;
import com.project.scanner.repository.ComplianceStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class ComplianceStatusService {

    @Autowired
    private ComplianceStatusRepository complianceStatusRepository;

    // Option 1: Execute playbook via REST API on core server
    public void runAnsiblePlaybook() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String coreServerUrl = "http://coreserver:8080/api/ansible/run-playbook"; // Adjust the URL accordingly

        // If the API requires authentication, set up headers here

        try {
            restTemplate.postForObject(coreServerUrl, null, String.class);
        } catch (Exception e) {
            throw new Exception("Failed to trigger Ansible playbook: " + e.getMessage(), e);
        }
    }

    // Option 2: Execute playbook via SSH (alternative approach)
    public void runAnsiblePlaybookViaSSH() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        // Example command; adjust paths and arguments as needed
        processBuilder.command("bash", "-c", "ansible-playbook /path/to/playbook.yml");

        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Ansible playbook execution failed with exit code " + exitCode);
        }
    }

    public List<ComplianceStatus> getAllComplianceStatuses() {
        return complianceStatusRepository.findAll();
    }

    // Additional methods for business logic can be added here
}

