package com.forage.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detaildevis")
public class DetailDevis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "iddevis")
    private Devis devis;

    @Column(length = 100)
    private String libelle;

    @Column(name = "quantite")
    private Integer quantite;

    @Column(name = "prixunitaire", precision = 10, scale = 2)
    private BigDecimal prixUnitaire;

    public DetailDevis() {}

    public DetailDevis(Devis devis, String libelle, Integer quantite, BigDecimal prixUnitaire) {
        this.devis = devis;
        this.libelle = libelle;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Devis getDevis() {
        return devis;
    }

    public void setDevis(Devis devis) {
        this.devis = devis;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getMontant() {
        if (quantite != null && prixUnitaire != null) {
            return prixUnitaire.multiply(new BigDecimal(quantite));
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return "DetailDevis{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", quantite=" + quantite +
                ", prixUnitaire=" + prixUnitaire +
                ", montant=" + getMontant() +
                '}';
    }
}
