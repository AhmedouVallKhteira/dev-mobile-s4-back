package com.ahmedou.delevry.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ahmedou.delevry.model.Commande;
import com.ahmedou.delevry.service.CommandeService;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @PostMapping("/{utilisateurId}")
    public ResponseEntity<Commande> creerCommande(
            @RequestBody Commande commande,
            @PathVariable("utilisateurId") Long utilisateurId) {
           
        Commande saved = commandeService.creerCommande(commande, utilisateurId);
        return ResponseEntity.ok(saved);
    }


    @GetMapping("/utilisateur/{utilisateurId}")
    public List<Commande> getByUtilisateur(@PathVariable Long utilisateurId) {
        return commandeService.getByUtilisateur(utilisateurId);
    }

    @GetMapping("/livreur/{livreurId}")
    public List<Commande> getByLivreur(@PathVariable Long livreurId) {
        return commandeService.getByLivreur(livreurId);
    }

    @GetMapping("/en-attente")
    public List<Commande> getCommandesEnAttente() {
        return commandeService.getEnAttente();
    }

    @PutMapping("/{id}/livrer")
    public ResponseEntity<String> livrerCommande(
            @PathVariable Long id,
            @RequestParam("code") String codeSecret) {
        try {
            commandeService.livrerCommande(id, codeSecret);
            return ResponseEntity.ok("Commande livrée avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/assigner")
    public ResponseEntity<String> assignerLivreur(
            @PathVariable Long id,
            @RequestParam("livreurId") Long livreurId) {
        commandeService.affecterLivreur(id, livreurId);
        return ResponseEntity.ok("Livreur affecté à la commande.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> getById(@PathVariable Long id) {
        return commandeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/annuler")
    public ResponseEntity<String> annulerCommande(
            @PathVariable Long id,
            @RequestParam("utilisateurId") Long utilisateurId) {
        try {
            commandeService.annulerCommande(id, utilisateurId);
            return ResponseEntity.ok("Commande annulée avec succès.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/utilisateur/{utilisateurId}/statut")
    public List<Commande> getCommandesByUtilisateurAndStatut(
            @PathVariable Long utilisateurId,
            @RequestParam String statut) {
        return commandeService.getByUtilisateurAndStatut(utilisateurId, statut);
    }


}
