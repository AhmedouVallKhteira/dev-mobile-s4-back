package com.ahmedou.delevry.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import com.ahmedou.delevry.model.Commande;
import com.ahmedou.delevry.model.Livreur;
import com.ahmedou.delevry.model.Parametres;
import com.ahmedou.delevry.model.Utilisateur;
import com.ahmedou.delevry.repository.CommandeRepository;
import com.ahmedou.delevry.repository.LivreurRepository;
import com.ahmedou.delevry.repository.ParametresRepository;
import com.ahmedou.delevry.repository.UtilisateurRepository;
import com.ahmedou.delevry.utils.GeoUtils;

@Service
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final LivreurRepository livreurRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ParametresRepository parametresRepository;

    public CommandeService(CommandeRepository commandeRepository, LivreurRepository livreurRepository ,
                           UtilisateurRepository utilisateurRepository, ParametresRepository parametresRepository) {
        this.commandeRepository = commandeRepository;
        this.livreurRepository = livreurRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.parametresRepository = parametresRepository;
    }

    public Commande creerCommande(Commande commande ,Long utilisateurId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        commande.setUtilisateur(utilisateur);

        String code = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        commande.setCodeSecret(code);
        commande.setDateCreation(LocalDateTime.now());
        commande.setStatut("en_attente");
        
        return commandeRepository.save(commande);
    }

    public List<Commande> getByUtilisateur(Long utilisateurId) {
        return commandeRepository.findByUtilisateurId(utilisateurId);
    }

    public List<Commande> getByLivreur(Long livreurId) {
        return commandeRepository.findByLivreurIdAndStatut(livreurId, "en_cours");
    }

    public List<Commande> getEnAttente() {
        return commandeRepository.findByStatut("en_attente");
    }

    public List<Commande> getCommandePlusProcheEnAttente(Long livreurId) {
        Livreur livreur = livreurRepository.findById(livreurId)
                .orElseThrow(() -> new RuntimeException("Livreur introuvable"));

        if (livreur.getLatitude() == null || livreur.getLongitude() == null) {
            return new ArrayList<>();
        }

        List<Commande> enAttente = commandeRepository.findByStatut("en_attente");
        return enAttente;

        // return enAttente.stream()
        //         .sorted(Comparator.comparingDouble(c -> {
        //             try {
        //                 String[] fromCoords = c.getFromLocation().split(",");
        //                 double fromLat = Double.parseDouble(fromCoords[0].trim());
        //                 double fromLon = Double.parseDouble(fromCoords[1].trim());

        //                 return GeoUtils.calculerDistanceEnKm(
        //                         livreur.getLatitude(), livreur.getLongitude(),
        //                         fromLat, fromLon
        //                 );
        //             } catch (NumberFormatException e) {
        //                 return Double.MAX_VALUE; 
        //             }
        //         }))
        //         .collect(Collectors.toList());
    }

    public Optional<Commande> getById(Long id) {
        return commandeRepository.findById(id);
    }

    public void livrerCommande(Long commandeId, String codeSaisi) {
        Commande commande = commandeRepository.findById(commandeId).orElseThrow();
        if (commande.getCodeSecret().equalsIgnoreCase(codeSaisi)) {
            commande.setStatut("livree");
            commandeRepository.save(commande);
        } else {
            throw new IllegalArgumentException("Code de réception invalide !");
        }
    }

    public void affecterLivreur(Long commandeId, Long livreurId) {
        Commande commande = commandeRepository.findById(commandeId).orElseThrow();
        commande.getLivreur().setId(livreurId);
        commandeRepository.save(commande);
    }

    public List<Commande> getByUtilisateurAndStatut(Long utilisateurId, String statut) {
        return commandeRepository.findByUtilisateurIdAndStatut(utilisateurId, statut);
    }

    public List<Commande> getCommandesLivreesParLivreur(Long livreurId) {
        return commandeRepository.findByLivreurIdAndStatut(livreurId, "livree");
    }


    public void annulerCommande(Long commandeId, Long utilisateurId) {
        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));

        if (!commande.getUtilisateur().getId().equals(utilisateurId)) {
            throw new IllegalArgumentException("Vous n'avez pas le droit d'annuler cette commande.");
        }

        if (commande.getLivreur() != null) {
            throw new IllegalStateException("Impossible d'annuler : la commande a déjà été assignée à un livreur.");
        }

        commande.setStatut("annulee");
        commandeRepository.save(commande);
    }

    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    public void accepterCommande(Long commandeId, Long livreurId) {
        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));
    
        if (commande.getLivreur() != null) {
            throw new IllegalStateException("Commande déjà acceptée.");
        }
    
        Livreur livreur = livreurRepository.findById(livreurId)
                .orElseThrow(() -> new RuntimeException("Livreur introuvable"));


        Parametres parametres = parametresRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);
        double commission = parametres != null ? parametres.getTauxCommission() : 0.1;
        
        double dette = livreur.getDette() + (commande.getPrix() * commission);
        livreur.setDette(dette);
        livreurRepository.save(livreur);
    
        commande.setLivreur(livreur);
        commande.setStatut("en_cours");
        commandeRepository.save(commande);
    }
    

    
    

}
