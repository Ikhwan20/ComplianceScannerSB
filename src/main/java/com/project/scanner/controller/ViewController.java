package com.project.scanner.controller;

import com.project.scanner.service.ComplianceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller to serve the dashboard view.
 */
@Controller
public class ViewController {

    @Autowired
    private ComplianceStatusService complianceService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("complianceStatuses", complianceService.getAllComplianceStatuses());
        return "dashboard";
    }
}

