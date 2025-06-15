package com.ahmedou.delevry.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ahmedou.delevry.model.Commande;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByUtilisateurId(Long utilisateurId);
    List<Commande> findByLivreurId(Long livreurId);
    List<Commande> findByStatut(String statut);
    List<Commande> findByUtilisateurIdAndStatut(Long utilisateurId, String statut);
    List<Commande> findByLivreurIdAndStatut(Long livreurId, String statut);
    List<Commande> findByLivreurIdAndStatutAndDateCreationBetween(
    Long livreurId, String statut, LocalDateTime start, LocalDateTime end);

}

