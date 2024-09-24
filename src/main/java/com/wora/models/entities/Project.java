package com.wora.models.entities;

import com.wora.models.enums.ProjectStatus;

import java.util.List;
import java.util.UUID;

public class Project {

    private UUID id;
    private String projectName;
    private Double profitMargin;
    private Double totalCost;
    private ProjectStatus projectStatus;
    private Client clientId;
    private Double discount;

    public Project(){
    }

    public Project(UUID id, String projectName, Double profitMargin, Double totalCost, ProjectStatus projectStatus, Client clientId, Double discount) {
        this.id = id;
        this.projectName = projectName;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.projectStatus = projectStatus;
        this.clientId = clientId;
        this.discount = discount;
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(Double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
