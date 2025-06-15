package com.ahmedou.delevry.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ahmedou.delevry.model.Commande;
import com.ahmedou.delevry.model.Livreur;
import com.ahmedou.delevry.model.Parametres;
import com.ahmedou.delevry.repository.CommandeRepository;
import com.ahmedou.delevry.repository.LivreurRepository;
import com.ahmedou.delevry.utils.StaticContextAccessor;

@Service
public class LivreurService {

    private final LivreurRepository livreurRepository;
    private final CommandeRepository commandeRepository;

    public LivreurService(LivreurRepository livreurRepository ,CommandeRepository commandeRepository) {
        this.livreurRepository = livreurRepository;
        this.commandeRepository=commandeRepository;
    }

    public Livreur login(String tel, String motDePasse) {
        return livreurRepository.findByTelAndMotDePasse(tel, motDePasse);
    }

    public boolean telExiste(String tel) {
        return livreurRepository.existsByTel(tel);
    }

    public Livreur enregistrer(Livreur livreur) {
        livreur.setApprouve(false);
        livreur.setDette(0.0);
        return livreurRepository.save(livreur);
    }

    public List<Livreur> getTousLesLivreurs() {
        return livreurRepository.findAll();
    }

    public Optional<Livreur> getById(Long id) {
        return livreurRepository.findById(id);
    }

    public List<Livreur> getNonApprouves() {
        return livreurRepository.findAll()
                .stream()
                .filter(l -> !l.isApprouve())
                .toList();
    }

    public void approuverLivreur(Long id) {
        Livreur livreur = livreurRepository.findById(id).orElseThrow();
        livreur.setApprouve(true);
        livreurRepository.save(livreur);
    }

    public Double getDette(Long livreurId) {
        Livreur livreur = livreurRepository.findById(livreurId).orElseThrow();
        return livreur.getDette();
    }

    public void ajouterDette(Long livreurId, Double montant) {
        Livreur livreur = livreurRepository.findById(livreurId).orElseThrow();
        livreur.setDette(livreur.getDette() + montant);
        livreurRepository.save(livreur);
    }

    public void payerDette(Long livreurId, Double montant) {
        Livreur livreur = livreurRepository.findById(livreurId).orElseThrow();
        double nouvelleDette = livreur.getDette() - montant;
        livreur.setDette(nouvelleDette);
        livreurRepository.save(livreur);
    }
    
    public void mettreAJourPosition(Long livreurId, Double lat, Double lon) {
        Livreur livreur = livreurRepository.findById(livreurId)
                .orElseThrow(() -> new RuntimeException("Livreur introuvable"));
        livreur.setLatitude(lat);
        livreur.setLongitude(lon);
        livreurRepository.save(livreur);
    }

    public Map<String, Object> calculerTransactions(Long livreurId, String debutStr, String finStr) {
        Livreur livreur = livreurRepository.findById(livreurId)
                .orElseThrow(() -> new RuntimeException("Livreur introuvable"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime debut = LocalDate.parse(debutStr, formatter).atStartOfDay();
        LocalDateTime fin = LocalDate.parse(finStr, formatter).atTime(23, 59, 59);

        List<Commande> livrees = commandeRepository
                .findByLivreurIdAndStatutAndDateCreationBetween(livreurId, "livree", debut, fin);
        List<Commande> annulees = commandeRepository
                .findByLivreurIdAndStatutAndDateCreationBetween(livreurId, "annulee", debut, fin);

        Parametres param = StaticContextAccessor.parametresRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Paramètres non définis"));

        double total = 0;
        double commission = 0;

        for (Commande c : livrees) {
            double prix = c.getPrix();
            total += prix;
            commission += prix * param.getTauxCommission();
        }

        double gainNet = total - commission;

        Map<String, Object> result = new HashMap<>();
        result.put("livraisons_effectuees", livrees.size());
        result.put("commandes_annulees", annulees.size());
        result.put("revenu_total", total);
        result.put("commission_totale", commission);
        result.put("gain_net", gainNet);
        result.put("dette_actuelle", livreur.getDette());
        result.put("periode", Map.of("du", debutStr, "au", finStr));
        return result;
    }

    public Double getSoldeById(Long id) {
        Livreur livreur = livreurRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Livreur introuvable"));
        return livreur.getDette() * -1;
    }
}
