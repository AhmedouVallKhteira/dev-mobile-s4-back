package com.ahmedou.delevry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ahmedou.delevry.model.Parametres;

@Repository
public interface ParametresRepository extends JpaRepository<Parametres, Long> {
}

