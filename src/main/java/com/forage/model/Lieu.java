package com.forage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lieu")
public class Lieu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String adresse;

    @Column(length = 100)
    private String district;

    public Lieu() {}

    public Lieu(String adresse, String district) {
        this.adresse = adresse;
        this.district = district;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return "Lieu{" +
                "id=" + id +
                ", adresse='" + adresse + '\'' +
                ", district='" + district + '\'' +
                '}';
    }
}
