CREATE DATABASE db_gestion_forage;
\c db_gestion_forage;

CREATE TABLE Client (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    mail VARCHAR(150),
    telephone VARCHAR(20)
);

CREATE TABLE Lieu (
    id SERIAL PRIMARY KEY,
    adresse TEXT,
    district VARCHAR(100)
);

CREATE TABLE Statut (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL
);

CREATE TABLE Demande (
    id SERIAL PRIMARY KEY,
    dateDemande DATE NOT NULL,
    description VARCHAR(1000),
    idLieu INT,
    idClient INT,
    idStatut INT,
    FOREIGN KEY (idLieu) REFERENCES Lieu(id),
    FOREIGN KEY (idClient) REFERENCES Client(id),
    FOREIGN KEY (idStatut) REFERENCES Statut(id)
);

CREATE TABLE DemandeStatut (
    id SERIAL PRIMARY KEY,
    dateStatut TIMESTAMP NOT NULL,
    idStatut INT,
    idDemande INT,
    FOREIGN KEY (idStatut) REFERENCES Statut(id),
    FOREIGN KEY (idDemande) REFERENCES Demande(id)
);

CREATE TABLE TypeDevis (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL
);

CREATE TABLE Devis (
    id SERIAL PRIMARY KEY,
    dateDevis DATE NOT NULL,
    -- montantTotal NUMERIC(12,2),
    idTypeDevis INT,
    idDemande INT,
    idStatut INT,
    FOREIGN KEY (idTypeDevis) REFERENCES TypeDevis(id),
    FOREIGN KEY (idDemande) REFERENCES Demande(id),
    FOREIGN KEY (idStatut) REFERENCES Statut(id)
);

CREATE TABLE DetailDevis (
    id SERIAL PRIMARY KEY,
    idDevis INT,
    libelle VARCHAR(100),
    quantite INT,
    prixUnitaire NUMERIC(10,2),
    FOREIGN KEY (idDevis) REFERENCES Devis(id)
);


