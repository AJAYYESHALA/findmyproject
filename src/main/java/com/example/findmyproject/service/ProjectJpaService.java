/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * 
 * import java.util.*;
 *
 */

package com.example.findmyproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import com.example.findmyproject.model.*;
import com.example.findmyproject.repository.*;

@Service
public class ProjectJpaService implements ProjectRepository {

    @Autowired
    private ProjectJpaRepository projectJpaRepository;

    @Autowired
    private ResearcherJpaRepository researcherJpaRepository;

    @Override
    public ArrayList<Project> getProjects() {
        List<Project> projectList = projectJpaRepository.findAll();
        ArrayList<Project> projects = new ArrayList<>(projectList);
        return projects;
    }

    @Override
    public Project getProjectById(int projectId) {
        try {
            Project project = projectJpaRepository.findById(projectId).get();
            return project;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Project addProject(Project project) {

        List<Integer> researcherIds = new ArrayList<>();

        for (Researcher researcher : project.getResearchers()) {
            researcherIds.add(researcher.getResearcherId());
        }

        List<Researcher> researchersList = researcherJpaRepository.findAllById(researcherIds);

        if (researcherIds.size() != researchersList.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "some researchers are found");
        }

        project.setResearchers(researchersList);

        for (Researcher researcher : researchersList) {
            researcher.getProjects().add(project);
        }

        Project savedProject = projectJpaRepository.save(project);
        researcherJpaRepository.saveAll(researchersList);

        return savedProject;
    }

    @Override
    public Project updateProject(int projectId, Project project) {
        try {
            Project newProject = projectJpaRepository.findById(projectId).get();

            if (project.getProjectName() != null) {
                newProject.setProjectName(project.getProjectName());
            }

            if (project.getBudget() != 0) {
                newProject.setBudget(project.getBudget());
            }

            projectJpaRepository.save(newProject);

            if (project.getResearchers() != null) {

                List<Researcher> researchers = newProject.getResearchers();

                for (Researcher researcher : researchers) {
                    researcher.getProjects().remove(newProject);
                }

                List<Integer> researcherIds = new ArrayList<>();

                for (Researcher researcher : project.getResearchers()) {
                    researcherIds.add(researcher.getResearcherId());
                }

                List<Researcher> researchersList = researcherJpaRepository.findAllById(researcherIds);

                if (researcherIds.size() != researchersList.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "some researchers are found");
                }

                newProject.setResearchers(researchersList);

                for (Researcher researcher : researchersList) {
                    researcher.getProjects().add(newProject);
                }
                researcherJpaRepository.saveAll(researchersList);

                newProject.setResearchers(researchersList);

            }

            return projectJpaRepository.save(newProject);

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteProject(int projectId) {
        try {
            Project project = projectJpaRepository.findById(projectId).get();
            List<Researcher> researchers = project.getResearchers();

            for (Researcher researcher : researchers) {
                researcher.getProjects().remove(project);
            }

            researcherJpaRepository.saveAll(researchers);
            projectJpaRepository.deleteById(projectId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public ArrayList<Researcher> getResearchersByProjectId(int projectId) {

        try {
            Project project = projectJpaRepository.findById(projectId).get();
            List<Researcher> researchersList = project.getResearchers();
            ArrayList<Researcher> researchers = new ArrayList<>(researchersList);

            return researchers;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
}