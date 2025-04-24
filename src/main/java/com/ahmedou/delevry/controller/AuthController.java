package com.ahmedou.delevry.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahmedou.delevry.model.Livreur;
import com.ahmedou.delevry.model.Utilisateur;
import com.ahmedou.delevry.service.LivreurService;
import com.ahmedou.delevry.service.UtilisateurService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UtilisateurService utilisateurService;
    private final LivreurService livreurService;

    public AuthController(UtilisateurService utilisateurService, LivreurService livreurService) {
        this.utilisateurService = utilisateurService;
        this.livreurService = livreurService;
    }

    @PostMapping("/login/utilisateur")
    public ResponseEntity<?> loginUtilisateur(@RequestBody LoginRequest request) {
        Utilisateur utilisateur = utilisateurService.login(request.getTel(), request.getMotDePasse());
        if (utilisateur == null) {
            return ResponseEntity.status(401).body("Email ou mot de passe invalide.");
        }
        return ResponseEntity.ok(utilisateur);
    }

    @PostMapping("/login/livreur")
    public ResponseEntity<?> loginLivreur(@RequestBody LoginRequest request) {
        Livreur livreur = livreurService.login(request.getTel(), request.getMotDePasse());
        if (livreur == null) {
            return ResponseEntity.status(401).body("Email ou mot de passe invalide.");
        }
        if (!livreur.isApprouve()) {
            return ResponseEntity.status(403).body("Votre compte livreur n'est pas encore approuvé.");
        }
        return ResponseEntity.ok(livreur);
    }

    public static class LoginRequest {
        private String tel;
        private String motDePasse;

        public LoginRequest(String motDePasse, String tel) {
            this.motDePasse = motDePasse;
            this.tel = tel;
        }

        public LoginRequest(){}

        public String getMotDePasse() {
            return motDePasse;
        }

        public void setMotDePasse(String motDePasse) {
            this.motDePasse = motDePasse;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
        
    }
}
