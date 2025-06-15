package com.ahmedou.delevry.dto;

public class StatistiquesDTO {
    private long totalLivreurs;
    private long totalCommandes;
    private double commissionTotale;
    private double dettesTotales;

    // Getters & Setters

    public long getTotalLivreurs() {
        return totalLivreurs;
    }

    public void setTotalLivreurs(long totalLivreurs) {
        this.totalLivreurs = totalLivreurs;
    }

    public long getTotalCommandes() {
        return totalCommandes;
    }

    public void setTotalCommandes(long totalCommandes) {
        this.totalCommandes = totalCommandes;
    }

    public double getCommissionTotale() {
        return commissionTotale;
    }

    public void setCommissionTotale(double commissionTotale) {
        this.commissionTotale = commissionTotale;
    }

    public double getDettesTotales() {
        return dettesTotales;
    }

    public void setDettesTotales(double dettesTotales) {
        this.dettesTotales = dettesTotales;
    }
}
