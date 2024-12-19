package com.seven.schoolapi.service;

import com.seven.schoolapi.entity.PasswordResetToken;
import com.seven.schoolapi.repositories.PasswordResetTokenRepository;
import com.seven.schoolapi.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordResetService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;
    public Optional<String> findEmailByToken(String token) {
        Optional<PasswordResetToken> resetToken = passwordResetTokenRepository.findByToken(token);
        if (resetToken.isPresent() && resetToken.get().getExpiryDate().isAfter(LocalDateTime.now())) {
            return Optional.of(resetToken.get().getEmail());
        }
        return Optional.empty();
    }

    public void sendPasswordResetCode(String email) {
        if (!userInfoRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("E-mail não registrado.");
        }

        String token = generateResetToken();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(5);

        PasswordResetToken resetToken = new PasswordResetToken(email, token, expiryDate);
        passwordResetTokenRepository.save(resetToken);

        String message = "Seu código de recuperação de senha é: " + token;
        emailService.sendSimpleEmail(email, "Recuperação de Senha", message);
    }

    private String generateResetToken() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Gera um número entre 100000 e 999999
        return String.valueOf(code);
    }

    public boolean verifyToken(String email, String token) {
        Optional<PasswordResetToken> resetToken = passwordResetTokenRepository.findByEmailAndToken(email, token);
        return resetToken.isPresent() && resetToken.get().getExpiryDate().isAfter(LocalDateTime.now());
    }

    public void deleteToken(String email) {
        passwordResetTokenRepository.deleteByEmail(email);
    }
}