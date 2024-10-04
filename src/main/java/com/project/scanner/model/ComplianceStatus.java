package com.project.scanner.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_status")
public class ComplianceStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_name", nullable = false, length = 255)
    private String deviceName;

    @Column(name = "cpu_compliance", length = 50)
    private String cpuCompliance;

    @Column(name = "memory_compliance", length = 50)
    private String memoryCompliance;

    @Column(name = "disk_compliance", length = 50)
    private String diskCompliance;

    @Column(name = "uptime_compliance", length = 50)
    private String uptimeCompliance;

    @Column(name = "overall_compliance", length = 50)
    private String overallCompliance;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public void setId(Long id) {
        this.id = id;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setCpuCompliance(String cpuCompliance) {
        this.cpuCompliance = cpuCompliance;
    }

    public void setMemoryCompliance(String memoryCompliance) {
        this.memoryCompliance = memoryCompliance;
    }

    public void setDiskCompliance(String diskCompliance) {
        this.diskCompliance = diskCompliance;
    }

    public void setUptimeCompliance(String uptimeCompliance) {
        this.uptimeCompliance = uptimeCompliance;
    }

    public void setOverallCompliance(String overallCompliance) {
        this.overallCompliance = overallCompliance;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getCpuCompliance() {
        return cpuCompliance;
    }

    public String getMemoryCompliance() {
        return memoryCompliance;
    }

    public String getDiskCompliance() {
        return diskCompliance;
    }

    public String getUptimeCompliance() {
        return uptimeCompliance;
    }

    public String getOverallCompliance() {
        return overallCompliance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Constructors
    public ComplianceStatus() {
    }
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
