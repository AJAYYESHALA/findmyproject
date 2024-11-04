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

import com.example.findmyproject.repository.*;
import com.example.findmyproject.model.*;

@Service
public class ResearcherJpaService implements ResearcherRepository {

    @Autowired
    private ResearcherJpaRepository researcherJpaRepository;

    @Autowired
    private ProjectJpaRepository projectJpaRepository;

    @Override
    public ArrayList<Researcher> getResearchers() {
        List<Researcher> researchersList = researcherJpaRepository.findAll();
        ArrayList<Researcher> researchers = new ArrayList<>(researchersList);
        return researchers;
    }

    @Override
    public Researcher getResearcherById(int researcherId) {
        try {
            Researcher researcher = researcherJpaRepository.findById(researcherId).get();

            return researcher;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Researcher addResearcher(Researcher researcher) {

        List<Integer> projectIds = new ArrayList<>();

        for (Project project : researcher.getProjects()) {
            projectIds.add(project.getProjectId());
        }

        List<Project> projectList = projectJpaRepository.findAllById(projectIds);

        if (projectIds.size() != projectList.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "some projects are found");
        }

        researcher.setProjects(projectList);
        return researcherJpaRepository.save(researcher);

    }

    @Override
    public Researcher updateResearcher(int researcherId, Researcher researcher) {
        try {
            Researcher newResearcher = researcherJpaRepository.findById(researcherId).get();

            if (researcher.getResearcherName() != null) {
                newResearcher.setResearcherName(researcher.getResearcherName());
            }

            if (researcher.getSpecialization() != null) {
                newResearcher.setSpecialization(researcher.getSpecialization());
            }

            researcherJpaRepository.save(newResearcher);

            if (researcher.getProjects() != null) {

                List<Integer> projectIds = new ArrayList<>();

                for (Project project : researcher.getProjects()) {
                    projectIds.add(project.getProjectId());
                }

                List<Project> projectList = projectJpaRepository.findAllById(projectIds);

                if (projectIds.size() != projectList.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "some projects are found");
                }

                newResearcher.setProjects(projectList);

            }

            return researcherJpaRepository.save(newResearcher);

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteResearcher(int researcherId) {
        try {
            Researcher researcher = researcherJpaRepository.findById(researcherId).get();

            List<Project> projects = researcher.getProjects();

            for (Project project : projects) {
                project.getResearchers().remove(researcher);
            }

            projectJpaRepository.saveAll(projects);

            researcherJpaRepository.deleteById(researcherId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);

    }

    @Override
    public ArrayList<Project> getProjectsByIdResearcherId(int researcherId) {

        try {
            Researcher researcher = researcherJpaRepository.findById(researcherId).get();

            List<Project> projectList = researcher.getProjects();
            ArrayList<Project> projects = new ArrayList<>(projectList);
            return projects;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
}