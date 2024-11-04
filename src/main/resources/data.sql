INSERT INTO researcher(name, specialization) values('Marie Curie','Radioactivity');
INSERT INTO researcher(name, specialization) values('Albert Einstein', 'Relativity');
INSERT INTO researcher(name, specialization) values('Isaac Newton', 'Classical Mechanics');
INSERT INTO researcher(name, specialization) values('Niels Bohr', 'Quantum Mechanics');

INSERT INTO project(name, budget) values('Project Alpha', 50000.00);
INSERT INTO project(name, budget) values('Project Beta', 100000.00);
INSERT INTO project(name, budget) values('Project Gamma', 150000.00);
INSERT INTO project(name, budget) values('Project Delta', 75000.00);


INSERT INTO researcher_project(researcherId, projectId) values(1, 1);
INSERT INTO researcher_project(researcherId, projectId) values(1, 2);
INSERT INTO researcher_project(researcherId, projectId) values(2, 2);
INSERT INTO researcher_project(researcherId, projectId) values(3, 3);
INSERT INTO researcher_project(researcherId, projectId) values(3, 4);
INSERT INTO researcher_project(researcherId, projectId) values(4, 4);