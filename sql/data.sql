INSERT INTO operateur (nom) VALUES
('>'),
('<'),
('>='),
('<=');

INSERT INTO regles (nom) VALUES
('moyenne'),
('plus petit'),
('plus grand');

INSERT INTO matieres (nom) VALUES
('Maths'),
('Anglais');

INSERT INTO eleves (nom) VALUES
('Jean'),
('Marie'),
('Paul');

INSERT INTO correcteurs (nom) VALUES
('Correcteur 1'),
('Correcteur 2'),
('Correcteur 3');

INSERT INTO parametres (id_matiere,id_regle,valeur,id_operateur) VALUES
(1,2,3,3),
(1,3,3,2),
(2,3,3,2);

INSERT INTO notes (id_eleve,id_correcteur,id_matiere,note) VALUES
(1,1,1,10),
(1,2,1,14),
(1,3,1,15);

INSERT INTO notes (id_eleve,id_correcteur,id_matiere,note) VALUES
(2,1,1,12),
(2,2,1,13),
(2,3,1,14);

INSERT INTO notes (id_eleve,id_correcteur,id_matiere,note) VALUES
(1,1,2,8),
(1,2,2,9),
(1,3,2,10);

INSERT INTO notes (id_eleve,id_correcteur,id_matiere,note) VALUES
(3,1,2,16),
(3,2,2,17),
(3,3,2,18);

