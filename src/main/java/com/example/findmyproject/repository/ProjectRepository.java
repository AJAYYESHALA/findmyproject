/*
 * You can use the following import statements
 *
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

package com.example.findmyproject.repository;

import java.util.ArrayList;
import java.util.List;
import com.example.findmyproject.model.*;

public interface ProjectRepository {
    ArrayList<Project> getProjects();

    Project getProjectById(int projectId);

    Project addProject(Project project);

    Project updateProject(int projectId, Project project);

    void deleteProject(int projectId);

    ArrayList<Researcher> getResearchersByProjectId(int projectId);

}