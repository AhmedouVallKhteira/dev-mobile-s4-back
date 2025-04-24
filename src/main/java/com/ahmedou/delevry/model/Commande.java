package com.ahmedou.delevry.model;

import java.time.LocalDateTime;

import com.ahmedou.delevry.utils.GeoUtils;
import com.ahmedou.delevry.utils.StaticContextAccessor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromLocation;
    private String toLocation;
    private String description;
    private String codeSecret;
    private String statut;
    private LocalDateTime dateCreation;
    private boolean isActive; 

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToOne
    private Livreur livreur;

    // Constructeur sans arguments (requis par JPA)
    public Commande() {
    }
    
    // Constructeur avec tous les champs
    public Commande(Long id, String fromLocation, String toLocation, String description, String codeSecret,
                    String statut, LocalDateTime dateCreation, Utilisateur utilisateur, Livreur livreur) {
        this.id = id;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.description = description;
        this.codeSecret = codeSecret;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.utilisateur = utilisateur;
        this.livreur = livreur;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public String getDescription() {
        return description;
    }

    public String getCodeSecret() {
        return codeSecret;
    }

    public String getStatut() {
        return statut;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public Livreur getLivreur() {
        return livreur;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCodeSecret(String codeSecret) {
        this.codeSecret = codeSecret;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

    @SuppressWarnings("UseSpecificCatch")
    public Double getDistance() {
        try {
            String[] fromCoords = this.fromLocation.split(",");
            String[] toCoords = this.toLocation.split(",");

            double fromLat = Double.parseDouble(fromCoords[0].trim());
            double fromLon = Double.parseDouble(fromCoords[1].trim());
            double toLat = Double.parseDouble(toCoords[0].trim());
            double toLon = Double.parseDouble(toCoords[1].trim());

            return GeoUtils.calculerDistanceEnKm(fromLat, fromLon, toLat, toLon);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul de la distance: " + e.getMessage());
        }
    }

    public Double getPrix() {
        Parametres param = StaticContextAccessor.parametresRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Aucun paramètre défini"));

        double distance = getDistance();
        return param.getPrixFixe() + (distance * param.getPrixParKm());
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", fromLocation='" + fromLocation + '\'' +
                ", toLocation='" + toLocation + '\'' +
                ", description='" + description + '\'' +
                ", codeSecret='" + codeSecret + '\'' +
                ", statut='" + statut + '\'' +
                ", dateCreation=" + dateCreation +
                ", utilisateur=" + utilisateur +
                ", livreur=" + livreur +
                '}';
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
