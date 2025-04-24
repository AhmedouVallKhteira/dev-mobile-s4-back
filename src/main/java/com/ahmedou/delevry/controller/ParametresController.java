package com.ahmedou.delevry.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahmedou.delevry.model.Parametres;
import com.ahmedou.delevry.service.ParametresService;

@RestController
@RequestMapping("/api/parametres")
public class ParametresController {

    private final ParametresService parametresService;

    public ParametresController(ParametresService parametresService) {
        this.parametresService = parametresService;
    }

    @GetMapping
    public ResponseEntity<Parametres> getParametres() {
        Parametres parametres = parametresService.getParametresActuels();
        if (parametres == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(parametres);
    }

    @PutMapping
    public ResponseEntity<Parametres> updateParametres(@RequestBody Parametres updated) {
        Parametres saved = parametresService.mettreAJour(updated);
        return ResponseEntity.ok(saved);
    }
}
