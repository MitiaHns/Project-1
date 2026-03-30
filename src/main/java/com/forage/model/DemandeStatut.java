package com.forage.model;

import jakarta.persistence.*;
// import java.time.LocalDate;

@Entity
@Table(name = "demandestatut")
public class DemandeStatut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datestatut", nullable = false)
    private java.time.LocalDateTime dateStatut;

    @ManyToOne
    @JoinColumn(name = "idstatut")
    private Statut statut;

    @ManyToOne
    @JoinColumn(name = "iddemande")
    private Demande demande;

    public DemandeStatut() {}

    public DemandeStatut(java.time.LocalDateTime dateStatut, Statut statut, Demande demande) {
        this.dateStatut = dateStatut;
        this.statut = statut;
        this.demande = demande;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.time.LocalDateTime getDateStatut() {
        return dateStatut;
    }

    public void setDateStatut(java.time.LocalDateTime dateStatut) {
        this.dateStatut = dateStatut;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    @Override
    public String toString() {
        return "DemandeStatut{" +
                "id=" + id +
                ", dateStatut=" + dateStatut +
                ", statut=" + statut +
                ", demande=" + demande +
                '}';
    }
}
