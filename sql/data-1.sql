INSERT INTO operateur (id, nom) VALUES
(1,'<'),
(2,'<='),
(3,'>'),
(4,'>=');

INSERT INTO regles (id, nom) VALUES
(1,'Petit'),
(2,'Grand'),
(3,'Moyenne');

INSERT INTO matieres (nom) VALUES
('JAVA'),
('PHP');

INSERT INTO eleves (nom) VALUES
('Candidat 1'),
('Candidat 2');

INSERT INTO correcteurs (nom) VALUES
('Correcteur 1'),
('Correcteur 2'),
('Correcteur 3');

INSERT INTO parametres (id_matiere,id_regle,valeur,id_operateur) VALUES
(1, 2, 3, 1),
(1, 3, 3, 4),
(2, 1, 2, 2),
(2, 2, 2, 3);

INSERT INTO notes (id_eleve,id_correcteur,id_matiere,note) VALUES
(1, 1, 1, 12),
(1, 2, 1, 11),
(1, 1, 2, 7),
(1, 2, 2, 11);

INSERT INTO notes (id_eleve,id_correcteur,id_matiere,note) VALUES
(2, 1, 1, 13),
(2, 2, 1, 10),
(2, 1, 2, 14),
(2, 2, 2, 16);