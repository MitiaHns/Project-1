CREATE DATABASE db_gestion_examen;
\c db_gestion_examen;

CREATE TABLE eleves (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL
);

CREATE TABLE correcteurs (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL
);

CREATE TABLE operateur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(2) NOT NULL
);

CREATE TABLE matieres (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL
);

CREATE TABLE regles (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL
);

CREATE TABLE parametres (
    id SERIAL PRIMARY KEY,
    id_matiere INT NOT NULL REFERENCES matieres(id),
    id_regle INT NOT NULL REFERENCES regles(id),
    valeur NUMERIC NOT NULL,
    id_operateur INT NOT NULL REFERENCES operateur(id)
);

CREATE TABLE notes (
    id SERIAL PRIMARY KEY,
    id_eleve INT NOT NULL REFERENCES eleves(id),
    id_correcteur INT NOT NULL REFERENCES correcteurs(id),
    id_matiere INT NOT NULL REFERENCES matieres(id),
    note NUMERIC(5,2) NOT NULL
);

CREATE OR REPLACE VIEW notefinales AS
SELECT
    ROW_NUMBER() OVER() AS id,
    n.id_eleve,
    e.nom AS nom_eleve,
    n.id_matiere,
    m.nom AS nom_matiere,

    ROUND(
        CASE

            WHEN EXISTS (
                SELECT 1
                FROM parametres p
                JOIN operateur o ON o.id = p.id_operateur
                WHERE p.id_matiere = n.id_matiere
                AND (
                        (o.nom = '>'  AND diff.total_diff >  p.valeur)
                     OR (o.nom = '<'  AND diff.total_diff <  p.valeur)
                     OR (o.nom = '>=' AND diff.total_diff >= p.valeur)
                     OR (o.nom = '<=' AND diff.total_diff <= p.valeur)
                )
                AND p.id_regle = (
                    SELECT id FROM regles WHERE nom = 'Petit'
                )
            )

            THEN MIN(n.note)

            WHEN EXISTS (
                SELECT 1
                FROM parametres p
                JOIN operateur o ON o.id = p.id_operateur
                WHERE p.id_matiere = n.id_matiere
                AND (
                        (o.nom = '>'  AND diff.total_diff >  p.valeur)
                     OR (o.nom = '<'  AND diff.total_diff <  p.valeur)
                     OR (o.nom = '>=' AND diff.total_diff >= p.valeur)
                     OR (o.nom = '<=' AND diff.total_diff <= p.valeur)
                )
                AND p.id_regle = (
                    SELECT id FROM regles WHERE nom = 'Grand'
                )
            )

            THEN MAX(n.note)

            WHEN EXISTS (
                SELECT 1
                FROM parametres p
                JOIN operateur o ON o.id = p.id_operateur
                WHERE p.id_matiere = n.id_matiere
                AND (
                        (o.nom = '>'  AND diff.total_diff >  p.valeur)
                     OR (o.nom = '<'  AND diff.total_diff <  p.valeur)
                     OR (o.nom = '>=' AND diff.total_diff >= p.valeur)
                     OR (o.nom = '<=' AND diff.total_diff <= p.valeur)
                )
                AND p.id_regle = (
                    SELECT id FROM regles WHERE nom = 'Moyenne'
                )
            )

            THEN AVG(n.note)

            ELSE AVG(n.note)

        END
    ,2) AS notefinale

FROM notes n
JOIN eleves e ON e.id = n.id_eleve
JOIN matieres m ON m.id = n.id_matiere

LEFT JOIN (
    SELECT
        n1.id_eleve,
        n1.id_matiere,
        SUM(ABS(n1.note - n2.note)) AS total_diff
    FROM notes n1
    JOIN notes n2
        ON n1.id_eleve = n2.id_eleve
        AND n1.id_matiere = n2.id_matiere
        AND n1.id < n2.id
    GROUP BY
        n1.id_eleve,
        n1.id_matiere
) diff
ON diff.id_eleve = n.id_eleve
AND diff.id_matiere = n.id_matiere

GROUP BY
    n.id_eleve,
    e.nom,
    n.id_matiere,
    m.nom,
    diff.total_diff;