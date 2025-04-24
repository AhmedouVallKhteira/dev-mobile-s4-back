package com.ahmedou.delevry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ahmedou.delevry.model.Utilisateur;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByTelAndMotDePasse(String tel, String motDePasse);
    boolean existsByTel(String tel);
}
