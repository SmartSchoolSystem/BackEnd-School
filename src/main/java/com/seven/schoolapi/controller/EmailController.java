package com.seven.schoolapi.controller;
import org.springframework.context.annotation.Lazy;

import com.seven.schoolapi.dtos.EmailDTO;
import com.seven.schoolapi.dtos.PasswordResetDTO;
import com.seven.schoolapi.repositories.UserInfoRepository;
import com.seven.schoolapi.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/change")
public class EmailController {

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @PostMapping("/request-reset")
    public ResponseEntity<String> requestPasswordReset(@RequestBody EmailDTO emailDTO) {
        if (userInfoRepository.findByEmail(emailDTO.to()).isPresent()) {
            passwordResetService.sendPasswordResetCode(emailDTO.to());
            return ResponseEntity.ok("Código de recuperação enviado para o e-mail.");
        } else {
            return ResponseEntity.status(404).body("E-mail não encontrado.");
        }
    }

    @PostMapping("/verify-reset")
    public ResponseEntity<String> verifyResetCode(@RequestParam String email, @RequestParam String token) {
        boolean isValid = passwordResetService.verifyToken(email, token);
        if (isValid) {
            return ResponseEntity.ok("Código válido. Você pode redefinir sua senha.");
        } else {
            return ResponseEntity.status(401).body("Código inválido ou expirado.");
        }
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetDTO passwordResetDTO) {
        Optional<String> emailOptional = passwordResetService.findEmailByToken(passwordResetDTO.token());

        if (emailOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Código inválido ou expirado.");
        }

        String email = emailOptional.get();
        String encryptedPassword = passwordEncoder.encode(passwordResetDTO.newPassword());

        userInfoRepository.updatePassword(email, encryptedPassword);
        passwordResetService.deleteToken(email);

        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }
}