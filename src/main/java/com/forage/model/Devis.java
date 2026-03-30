package com.forage.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "devis")
public class Devis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datedevis", nullable = false)
    private LocalDate dateDevis;

    @Transient
    private BigDecimal montantTotal;

    @ManyToOne
    @JoinColumn(name = "idtypedevis")
    private TypeDevis typeDevis;

    @ManyToOne
    @JoinColumn(name = "iddemande")
    private Demande demande;

    @ManyToOne
    @JoinColumn(name = "idstatut")
    private Statut statut;

    @OneToMany(mappedBy = "devis", cascade = CascadeType.ALL)
    private List<DetailDevis> details;

    public Devis() {}

    public Devis(LocalDate dateDevis, BigDecimal montantTotal, TypeDevis typeDevis, Demande demande, Statut statut) {
        this.dateDevis = dateDevis;
        this.montantTotal = montantTotal;
        this.typeDevis = typeDevis;
        this.demande = demande;
        this.statut = statut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDevis() {
        return dateDevis;
    }

    public void setDateDevis(LocalDate dateDevis) {
        this.dateDevis = dateDevis;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public TypeDevis getTypeDevis() {
        return typeDevis;
    }

    public void setTypeDevis(TypeDevis typeDevis) {
        this.typeDevis = typeDevis;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public List<DetailDevis> getDetails() {
        return details;
    }

    public void setDetails(List<DetailDevis> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Devis{" +
                "id=" + id +
                ", dateDevis=" + dateDevis +
                ", typeDevis=" + typeDevis +
                ", demande=" + demande +
                ", statut=" + statut +
                '}';
    }
}
