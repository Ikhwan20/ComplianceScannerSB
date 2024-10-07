package com.project.scanner.service;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.project.scanner.model.ComplianceStatus;
import com.project.scanner.repository.ComplianceStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class ComplianceStatusService {

    @Autowired
    private ComplianceStatusRepository complianceStatusRepository;

    public List<ComplianceStatus> getAllComplianceStatuses() {
        return complianceStatusRepository.findAll();
    }

    public void runAnsiblePlaybookViaSSH() throws Exception {
        String user = "ikhwanmazlan20";
        String host = "10.128.0.2";
        int port = 22; // Default SSH port
        String privateKey = "/home/ikhwanmazlan20/.ssh/id_rsa"; 

        JSch jsch = new JSch();
        jsch.addIdentity(privateKey);

        Session session = null;
        ChannelExec channel = null;

        try {
            session = jsch.getSession(user, host, port);

            // Recommended: set known hosts for security
            jsch.setKnownHosts("/home/ikhwanmazlan20/.ssh/known_hosts");

            // Disable strict host key checking (not recommended for production)
            session.setConfig("StrictHostKeyChecking", "no");

            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand("cd /home/ikhwanmazlan20/linux-report && /home/ikhwanmazlan20/.local/bin/ansible-playbook linux-report-playbook.yml --ask-vault-pass");
            channel.setErrStream(System.err);
            InputStream in = channel.getInputStream();
            channel.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitStatus = channel.getExitStatus();
            if (exitStatus != 0) {
                throw new RuntimeException("Ansible playbook failed with exit status " + exitStatus);
            }

        } catch (JSchException | IOException e) {
            throw new Exception("SSH Execution failed: " + e.getMessage(), e);
        } finally {
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    // Execute playbook via REST API on core server
    // public void runAnsiblePlaybook() throws Exception {
    //     RestTemplate restTemplate = new RestTemplate();
    //     String coreServerUrl = "http://coreserver:8080/api/ansible/run-playbook"; // Adjust the URL accordingly

    //     // If the API requires authentication, set up headers here

    //     try {
    //         restTemplate.postForObject(coreServerUrl, null, String.class);
    //     } catch (Exception e) {
    //         throw new Exception("Failed to trigger Ansible playbook: " + e.getMessage(), e);
    //     }
    // }
}

