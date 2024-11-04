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
import com.example.findmyproject.service.ResearcherJpaService;

@RestController
public class ResearcherController {

    @Autowired
    private ResearcherJpaService researcherService;

    @GetMapping("/researchers")
    public ArrayList<Researcher> getResearchers() {
        return researcherService.getResearchers();
    }

    @GetMapping("/researchers/{researcherId}")
    public Researcher getResearcherById(@PathVariable("researcherId") int researcherId) {
        return researcherService.getResearcherById(researcherId);
    }

    @PostMapping("/researchers")
    public Researcher addResearcher(@RequestBody Researcher researcher) {
        return researcherService.addResearcher(researcher);
    }

    @PutMapping("/researchers/{researcherId}")
    public Researcher updateResearcher(@PathVariable("researcherId") int researcherId,
            @RequestBody Researcher researcher) {
        return researcherService.updateResearcher(researcherId, researcher);

    }

    @DeleteMapping("/researchers/{researcherId}")
    public void deleteResearcher(@PathVariable("researcherId") int researcherId) {
        researcherService.deleteResearcher(researcherId);

    }

    @GetMapping("/researchers/{researcherId}/projects")
    public List<Project> getProjectsByIdResearcherId(@PathVariable("researcherId") int researcherId) {
        return researcherService.getProjectsByIdResearcherId(researcherId);
    }

}