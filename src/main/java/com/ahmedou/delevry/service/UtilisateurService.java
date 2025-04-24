package com.ahmedou.delevry.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ahmedou.delevry.model.Utilisateur;
import com.ahmedou.delevry.repository.UtilisateurRepository;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public Utilisateur login(String tel, String motDePasse) {
        return utilisateurRepository.findByTelAndMotDePasse(tel, motDePasse);
    }

    public boolean emailExiste(String tel) {
        return utilisateurRepository.existsByTel(tel);
    }

    public Utilisateur enregistrer(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public Optional<Utilisateur> getById(Long id) {
        return utilisateurRepository.findById(id);
    }

    public List<Utilisateur> getAll() {
        return utilisateurRepository.findAll();
    }

    public void supprimer(Long id) {
        utilisateurRepository.deleteById(id);
    }
}
