package com.ahmedou.delevry.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Parametres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double tauxCommission;
    private Double prixParKm;
    private Double prixFixe;

    // Constructeur vide (obligatoire pour JPA)
    public Parametres() {
    }

    // Constructeur avec tous les champs
    public Parametres(Long id, Double tauxCommission, Double prixParKm, Double prixFixe) {
        this.id = id;
        this.tauxCommission = tauxCommission;
        this.prixParKm = prixParKm;
        this.prixFixe = prixFixe;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Double getTauxCommission() {
        return tauxCommission;
    }

    public Double getPrixParKm() {
        return prixParKm;
    }

    public Double getPrixFixe() {
        return prixFixe;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTauxCommission(Double tauxCommission) {
        this.tauxCommission = tauxCommission;
    }

    public void setPrixParKm(Double prixParKm) {
        this.prixParKm = prixParKm;
    }

    public void setPrixFixe(Double prixFixe) {
        this.prixFixe = prixFixe;
    }

    @Override
    public String toString() {
        return "Parametres{" +
                "id=" + id +
                ", tauxCommission=" + tauxCommission +
                ", prixParKm=" + prixParKm +
                ", prixFixe=" + prixFixe +
                '}';
    }
}
