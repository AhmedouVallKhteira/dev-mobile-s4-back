package com.ahmedou.delevry.dto;

import org.springframework.web.multipart.MultipartFile;

public class RegisterLivreurRequest {

    private String nom;
    private String tel;
    private String motDePasse;
    private String numeroTelephone;
    private String vehicule;
    private MultipartFile photoCin;
    private MultipartFile photoVehicule;

    // Getters
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

    public MultipartFile getPhotoCin() {
        return photoCin;
    }

    public MultipartFile getPhotoVehicule() {
        return photoVehicule;
    }

    // Setters
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

    public void setPhotoCin(MultipartFile photoCin) {
        this.photoCin = photoCin;
    }

    public void setPhotoVehicule(MultipartFile photoVehicule) {
        this.photoVehicule = photoVehicule;
    }

    @Override
    public String toString() {
        return "RegisterLivreurRequest{" +
                "nom='" + nom + '\'' +
                ", tel='" + tel + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", numeroTelephone='" + numeroTelephone + '\'' +
                ", vehicule='" + vehicule + '\'' +
                ", photoCin=" + (photoCin != null ? photoCin.getOriginalFilename() : "null") +
                ", photoVehicule=" + (photoVehicule != null ? photoVehicule.getOriginalFilename() : "null") +
                '}';
    }
}
