package com.ahmedou.delevry.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ahmedou.delevry.model.Livreur;
import com.ahmedou.delevry.repository.LivreurRepository;

@Service
public class LivreurService {

    private final LivreurRepository livreurRepository;

    public LivreurService(LivreurRepository livreurRepository) {
        this.livreurRepository = livreurRepository;
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
        livreur.setDette(Math.max(nouvelleDette, 0.0));
        livreurRepository.save(livreur);
    }
}
