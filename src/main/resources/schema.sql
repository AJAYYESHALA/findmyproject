CREATE TABLE if not exists researcher(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(255),
    specialization varchar(255)
);


CREATE TABLE if not exists project(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(255),
    budget double
);

CREATE TABLE if not exists researcher_project(
    researcherId INT,
    projectId INT,
    PRIMARY KEY(researcherId, projectId),
    FOREIGN KEY(researcherId) REFERENCES researcher(id),
    FOREIGN KEY(projectId) REFERENCES project(id)
);

