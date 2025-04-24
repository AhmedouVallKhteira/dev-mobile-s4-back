package com.ahmedou.delevry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ahmedou.delevry.model.Livreur;

@Repository
public interface LivreurRepository extends JpaRepository<Livreur, Long> {
    Livreur findByTelAndMotDePasse(String tel, String motDePasse);
    boolean existsByTel(String tel);
}
