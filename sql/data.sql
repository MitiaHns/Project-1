INSERT INTO Client (nom, mail, telephone) VALUES
('Mitia', 'test@test.com', '0123456789');

INSERT INTO Statut (libelle) VALUES
('Demande creee'),
('Demande acceptee'),
('Demande refusee'),
('Devis cree'),
('Devis Etude accepte'),
('Devis Etude refuse'),
('Devis Forage accepte'),
('Devis Forage refuse');

INSERT INTO TypeDevis (libelle) VALUES
('Devis Etude'),
('Devis Forage');

INSERT INTO Lieu (adresse, district) VALUES
('adresse 1', 'district 1'),
('adresse 2', 'district 2');
