INSERT INTO operateur (nom) VALUES
('<'),
('<='),
('>'),
('>=');

INSERT INTO regles (nom) VALUES
('Petit'),
('Grand'),
('Moyenne');

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
(1,2,5,4),
(1,3,9,1);

INSERT INTO notes (id_eleve,id_correcteur,id_matiere,note) VALUES
(1,1,1,10),
(1,2,1,18);