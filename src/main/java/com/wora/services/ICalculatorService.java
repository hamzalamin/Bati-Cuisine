package com.wora.services;

import com.wora.models.entities.Project;

public interface ICalculatorService {
    Double calculateTotalForProject(Project project);
    Double calculateTotalWithTvaForProject(Project project);
}
