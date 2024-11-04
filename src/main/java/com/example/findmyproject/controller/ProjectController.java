/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.web.bind.annotation.*;
 * 
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

package com.example.findmyproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.example.findmyproject.model.*;
import com.example.findmyproject.service.ProjectJpaService;

@RestController
public class ProjectController {

    @Autowired
    private ProjectJpaService projectService;

    @GetMapping("/researchers/projects")
    public ArrayList<Project> getProjects() {
        return projectService.getProjects();
    }

    @GetMapping("/researchers/projects/{projectId}")
    public Project getProjectById(@PathVariable("projectId") int projectId) {
        return projectService.getProjectById(projectId);
    }

    @PostMapping("/researchers/projects")
    public Project addProject(@RequestBody Project project) {
        return projectService.addProject(project);
    }

    @PutMapping("/researchers/projects/{projectId}")
    public Project updateProject(@PathVariable("projectId") int projectId, @RequestBody Project project) {
        return projectService.updateProject(projectId, project);
    }

    @DeleteMapping("/researchers/projects/{projectId}")
    public void deleteProject(@PathVariable("projectId") int projectId) {
        projectService.deleteProject(projectId);
    }

    @GetMapping("/projects/{projectId}/researchers")
    public ArrayList<Researcher> getResearchersByProjectId(@PathVariable("projectId") int projectId) {
        return projectService.getResearchersByProjectId(projectId);
    }

}