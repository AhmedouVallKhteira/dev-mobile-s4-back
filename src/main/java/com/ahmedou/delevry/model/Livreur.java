package com.ahmedou.delevry.model;

import com.ahmedou.delevry.service.FileStorageService;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Livreur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String tel;
    private String motDePasse;
    private String numeroTelephone;
    private String vehicule;
    private String photoCin;
    private String photoVehicule;

    private boolean approuve;

    private Double dette = 0.0;

    private Double latitude;
    private Double longitude;

    // Constructeur sans argument (requis par JPA)
    public Livreur() {
    }

    // Constructeur avec tous les champs sauf latitude/longitude/dette
    public Livreur(Long id, String nom, String tel, String motDePasse, String numeroTelephone,
                   String vehicule, String photoCin, String photoVehicule, boolean approuve) {
        this.id = id;
        this.nom = nom;
        this.tel = tel;
        this.motDePasse = motDePasse;
        this.numeroTelephone = numeroTelephone;
        this.vehicule = vehicule;
        this.photoCin = photoCin;
        this.photoVehicule = photoVehicule;
        this.approuve = approuve;
        this.dette = 0.0;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getTel() {
        return tel;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public String getVehicule() {
        return vehicule;
    }

    public String getPhotoCin() {
        return FileStorageService.getFileUrl(photoCin);
    }

    public String getPhotoVehicule() {
        return FileStorageService.getFileUrl(photoVehicule);
    }

    public boolean isApprouve() {
        return approuve;
    }

    public Double getDette() {
        return dette;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public void setVehicule(String vehicule) {
        this.vehicule = vehicule;
    }

    public void setPhotoCin(String photoCin) {
        this.photoCin = photoCin;
    }

    public void setPhotoVehicule(String photoVehicule) {
        this.photoVehicule = photoVehicule;
    }

    public void setApprouve(boolean approuve) {
        this.approuve = approuve;
    }

    public void setDette(Double dette) {
        this.dette = dette;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Livreur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", tel='" + tel + '\'' +
                ", numeroTelephone='" + numeroTelephone + '\'' +
                ", vehicule='" + vehicule + '\'' +
                ", approuve=" + approuve +
                ", dette=" + dette +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
