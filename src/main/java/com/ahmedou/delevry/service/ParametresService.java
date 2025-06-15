package com.ahmedou.delevry.service;

import org.springframework.stereotype.Service;

import com.ahmedou.delevry.model.Parametres;
import com.ahmedou.delevry.repository.ParametresRepository;

@Service
public class ParametresService {

    private final ParametresRepository parametresRepository;

    public ParametresService(ParametresRepository parametresRepository) {
        this.parametresRepository = parametresRepository;
    }

    public Parametres getParametresActuels() {
        return parametresRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public Parametres mettreAJour(Parametres p) {
        if (p.getId() == null && parametresRepository.count() > 0) {
            Parametres existant = getParametresActuels();
            p.setId(existant.getId());
        }
        return parametresRepository.save(p);
    }

    public double getTauxCommission() {
        Parametres p = getParametresActuels();
        return p != null ? p.getTauxCommission() : 0.1; 
    }
}
