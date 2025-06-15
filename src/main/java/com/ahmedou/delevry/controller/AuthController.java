package com.ahmedou.delevry.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ahmedou.delevry.model.Livreur;
import com.ahmedou.delevry.model.Utilisateur;
import com.ahmedou.delevry.service.LivreurService;
import com.ahmedou.delevry.service.UtilisateurService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UtilisateurService utilisateurService;
    private final LivreurService livreurService;

    public AuthController(UtilisateurService utilisateurService, LivreurService livreurService) {
        this.utilisateurService = utilisateurService;
        this.livreurService = livreurService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // 1. Tester comme livreur
        Livreur livreur = livreurService.login(request.getTel(), request.getMotDePasse());
        if (livreur != null) {
            if (!livreur.isApprouve()) {
                return ResponseEntity.status(403).body("Votre compte livreur n'est pas encore approuvé.");
            }
            Map<String, Object> response = new HashMap<>();
            response.put("role", "LIVREUR");
            response.put("data", livreur);
            return ResponseEntity.ok(response);
        }

        // 2. Sinon tester comme utilisateur
        Utilisateur utilisateur = utilisateurService.login(request.getTel(), request.getMotDePasse());
        if (utilisateur != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("role", utilisateur.getRole()); // Peut être ADMIN ou UTILISATEUR
            response.put("data", utilisateur);
            return ResponseEntity.ok(response);
        }

        // 3. Aucun trouvé
        return ResponseEntity.status(401).body("Numéro ou mot de passe invalide.");
    }

    // DTO interne
    public static class LoginRequest {
        private String tel;
        private String motDePasse;

        public LoginRequest(String tel, String motDePasse) {
            this.tel = tel;
            this.motDePasse = motDePasse;
        }

        public LoginRequest() {}

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getMotDePasse() {
            return motDePasse;
        }

        public void setMotDePasse(String motDePasse) {
            this.motDePasse = motDePasse;
        }
    }
}
