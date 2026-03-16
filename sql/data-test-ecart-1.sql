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
('JAVA');

INSERT INTO eleves (nom) VALUES
('Candidat 1');

INSERT INTO correcteurs (nom) VALUES
('Correcteur 1'),
('Correcteur 2');

INSERT INTO parametres (id_matiere,id_regle,valeur,id_operateur) VALUES
(1,1,2,3),
(1,2,5,1);

INSERT INTO notes (id_eleve,id_correcteur,id_matiere,note) VALUES
(1,1,1,10),
(1,2,1,14);