package com.ahmedou.delevry.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ahmedou.delevry.model.Livreur;
import com.ahmedou.delevry.service.FileStorageService;
import com.ahmedou.delevry.service.LivreurService;

@RestController
@RequestMapping("/api/livreurs")
public class LivreurController {

    private final LivreurService livreurService;
    private final FileStorageService fileStorageService;

    public LivreurController(LivreurService livreurService,FileStorageService fileStorageService) {
        this.livreurService = livreurService;
        this.fileStorageService=fileStorageService;
    }

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<?> registerLivreur(
            @RequestPart("nom") String nom,
            @RequestPart("tel") String tel,
            @RequestPart("motDePasse") String motDePasse,
            @RequestPart("numeroTelephone") String numeroTelephone,
            @RequestPart("vehicule") String vehicule,
            @RequestPart("photoCin") MultipartFile photoCin,
            @RequestPart("photoVehicule") MultipartFile photoVehicule
    ) {
        if (livreurService.telExiste(tel)) {
            return ResponseEntity.badRequest().body("Numéro déjà utilisé.");
        }

        Livreur livreur = new Livreur();
        livreur.setNom(nom);
        livreur.setTel(tel);
        livreur.setMotDePasse(motDePasse);
        livreur.setNumeroTelephone(numeroTelephone);
        livreur.setVehicule(vehicule);

        try {
            String filenameCin = "cin_" + System.currentTimeMillis() + ".jpg";
            String filenameVehicule = "vehicule_" + System.currentTimeMillis() + ".jpg";

            fileStorageService.saveFile(filenameCin, photoCin);
            fileStorageService.saveFile(filenameVehicule, photoVehicule);

            livreur.setPhotoCin(filenameCin);
            livreur.setPhotoVehicule(filenameVehicule);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erreur lors de l'upload des fichiers.");
        }

        Livreur saved = livreurService.enregistrer(livreur);
        return ResponseEntity.ok(saved);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Livreur> getLivreurById(@PathVariable Long id) {
        return livreurService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Livreur> getAllLivreurs() {
        return livreurService.getTousLesLivreurs();
    }

    @GetMapping("/pending")
    public List<Livreur> getLivreursNonApprouves() {
        return livreurService.getNonApprouves();
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<String> approuverLivreur(@PathVariable Long id) {
        livreurService.approuverLivreur(id);
        return ResponseEntity.ok("Livreur approuvé avec succès.");
    }

    @GetMapping("/{id}/dette")
    public ResponseEntity<Double> getDette(@PathVariable Long id) {
        return ResponseEntity.ok(livreurService.getDette(id));
    }

    @PutMapping("/{id}/dette/ajouter")
    public ResponseEntity<String> ajouterDette(@PathVariable Long id, @RequestParam Double montant) {
        livreurService.ajouterDette(id, montant);
        return ResponseEntity.ok("Dette ajoutée.");
    }

    @PutMapping("/{id}/dette/payer")
    public ResponseEntity<String> payerDette(@PathVariable Long id, @RequestParam Double montant) {
        livreurService.payerDette(id, montant);
        return ResponseEntity.ok("Dette payée.");
    }

    @PutMapping("/{id}/position")
    public ResponseEntity<String> updatePosition(
            @PathVariable Long id,
            @RequestParam Double latitude,
            @RequestParam Double longitude) {
        livreurService.mettreAJourPosition(id, latitude, longitude);
        return ResponseEntity.ok("Position mise à jour.");
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<?> getTransactionsPourPeriode(
            @PathVariable Long id,
            @RequestParam String debut,
            @RequestParam String fin) {
        try {
            return ResponseEntity.ok(livreurService.calculerTransactions(id, debut, fin));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/solde")
    public ResponseEntity<Double> getSolde(@PathVariable Long id) {
        Double solde = livreurService.getSoldeById(id);
        return ResponseEntity.ok(solde);
    }


}
