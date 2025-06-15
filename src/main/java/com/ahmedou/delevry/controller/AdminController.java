package com.ahmedou.delevry.controller;

import com.ahmedou.delevry.dto.StatistiquesDTO;
import com.ahmedou.delevry.repository.CommandeRepository;
import com.ahmedou.delevry.repository.LivreurRepository;
import com.ahmedou.delevry.service.ParametresService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final LivreurRepository livreurRepo;
    private final CommandeRepository commandeRepo;
    private final ParametresService parametresService;

    public AdminController(LivreurRepository livreurRepo,
                           CommandeRepository commandeRepo,
                           ParametresService parametresService) {
        this.livreurRepo = livreurRepo;
        this.commandeRepo = commandeRepo;
        this.parametresService = parametresService;
    }

    @DeleteMapping("/livreurs/{id}")
    public ResponseEntity<Void> deleteLivreur(@PathVariable Long id) {
        if (!livreurRepo.existsById(id)) return ResponseEntity.notFound().build();
        livreurRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/commandes/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        if (!commandeRepo.existsById(id)) return ResponseEntity.notFound().build();
        commandeRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/livreurs/{id}/refuser")
    public ResponseEntity<String> refuserLivreur(@PathVariable Long id) {
        return livreurRepo.findById(id).map(l -> {
            l.setApprouve(false);
            livreurRepo.save(l);
            return ResponseEntity.ok("Livreur refusé.");
        }).orElse(ResponseEntity.notFound().build());
    }

    

    @GetMapping("/statistiques")
    public StatistiquesDTO getStatistiques() {
        StatistiquesDTO dto = new StatistiquesDTO();

        dto.setTotalLivreurs(livreurRepo.count());
        dto.setTotalCommandes(commandeRepo.count());

        dto.setDettesTotales(
                livreurRepo.findAll()
                        .stream()
                        .mapToDouble(l -> Math.max(0, -l.getDette()))
                        .sum()
        );

        dto.setCommissionTotale(
                commandeRepo.findAll()
                        .stream()
                        .mapToDouble(c -> c.getPrix() * parametresService.getTauxCommission())
                        .sum()
        );

        return dto;
    }
}
