package com.forage.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Request class for creating a Devis with its details
 */
public class DevisRequest {
    
    private LocalDate dateDevis;
    private com.forage.model.TypeDevis typeDevis;
    private Long demandeId;
    private List<DetailDevisDTO> details;
    
    public LocalDate getDateDevis() {
        return dateDevis;
    }
    
    public void setDateDevis(LocalDate dateDevis) {
        this.dateDevis = dateDevis;
    }
    
    public com.forage.model.TypeDevis getTypeDevis() {
        return typeDevis;
    }
    
    public void setTypeDevis(com.forage.model.TypeDevis typeDevis) {
        this.typeDevis = typeDevis;
    }
    
    public Long getDemandeId() {
        return demandeId;
    }
    
    public void setDemandeId(Long demandeId) {
        this.demandeId = demandeId;
    }
    
    public List<DetailDevisDTO> getDetails() {
        return details;
    }
    
    public void setDetails(List<DetailDevisDTO> details) {
        this.details = details;
    }
    
    /**
     * DTO for DetailDevis
     */
    public static class DetailDevisDTO {
        private String libelle;
        private Integer quantite;
        private BigDecimal prixUnitaire;
        
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
    }
}