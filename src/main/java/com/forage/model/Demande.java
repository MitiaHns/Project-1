package com.forage.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "demande")
public class Demande {

    private static final DateTimeFormatter DISPLAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datedemande", nullable = false)
    private LocalDate dateDemande;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "idlieu")
    private Lieu lieu;

    @ManyToOne
    @JoinColumn(name = "idclient")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "idstatut")
    private Statut statut;

    public Demande() {}

    public Demande(LocalDate dateDemande, Lieu lieu, Client client, Statut statut) {
        this.dateDemande = dateDemande;
        this.lieu = lieu;
        this.client = client;
        this.statut = statut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    @Transient
    public String getDateDemandeFormatted() {
        return dateDemande != null ? dateDemande.format(DISPLAY_DATE_FORMATTER) : "";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    @Transient
    public String getStatus() {
        return statut != null ? statut.getLibelle() : null;
    }

    @Transient
    public void setStatus(String status) {
        // Opaque: status libelle can être géré côté service via StatutRepository.
        // ce setter est utile pour la désérialisation JSON
        if (statut == null) {
            statut = new Statut();
        }
        statut.setLibelle(status);
    }

    @Override
    public String toString() {
        return "Demande{" +
                "id=" + id +
                ", dateDemande=" + dateDemande +
                ", lieu=" + lieu +
                ", client=" + client +
                ", statut=" + statut +
                '}';
    }
}
