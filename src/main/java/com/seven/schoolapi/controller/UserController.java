package com.seven.schoolapi.controller;

import java.util.HashMap;
import java.util.Map;

import com.seven.schoolapi.service.userServices.UserInfoDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.seven.schoolapi.dtos.UserDTO;
import com.seven.schoolapi.entity.AuthRequest;
import com.seven.schoolapi.entity.UserInfo;
import com.seven.schoolapi.service.JwtService;
import com.seven.schoolapi.service.userServices.UserInfoService;

@RestController
@RequestMapping("/auth")
public class            UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // POST METHODS

    // Add new user
    @PostMapping("/adduser")
    public ResponseEntity<String> addNewUser(@Valid @RequestBody UserDTO userDTO) {
        UserInfo userInfo = new UserInfo(userDTO.id(), userDTO.name(), userDTO.username(), userDTO.email(), userDTO.password(), userDTO.role());
        return ResponseEntity.ok(service.addUser(userInfo));
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(service.login(authRequest));
    }

    // Logout
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Boolean>> logout() {
        Map<String, Boolean> response = new HashMap<>();
        response.put("status", true);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateuser/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        String result = service.updateUser(id, userDTO);
        return ResponseEntity.ok(result);
    }
    @PatchMapping("/updateusersingle/{id}")
    public ResponseEntity<String> patchUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        // Verifica se a senha foi fornecida e chama o serviço para atualizar a senha
        if (userDTO.password() == null) {
            return ResponseEntity.badRequest().body("Password is required for update");
        }
        String result = service.patchUser(id, userDTO);
        return ResponseEntity.ok(result);
    }


    // Authenticate and get token
    @PostMapping("/generatetoken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername()); // Generate token
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    // Validate token
    @PostMapping("/validatetoken")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestBody Map<String, String> body) {
        String token = body.get("token");

        String username = jwtService.extractUsername(token);

        UserInfo userInfo = service.getUserByUsername(username);
        if (userInfo == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "User not found");
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> userDetailsMap = new HashMap<>();
        userDetailsMap.put("name", userInfo.getName());
        userDetailsMap.put("email", userInfo.getEmail());
        userDetailsMap.put("role", userInfo.getRoles());

        Map<String, Object> response = new HashMap<>();
        response.put("user", userDetailsMap);

        return ResponseEntity.ok(response);
    }

    // Delete user
    @DeleteMapping("/admin/deleteuser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        String response = service.deleteUser(userId);
        System.out.println("Response from deleteUser: " + response); // Debug log

        if ("User deleted successfully".equals(response)) {
            return ResponseEntity.ok(response);
        } else if ("User not found".equals(response)) {
            return ResponseEntity.status(404).body(response);
        } else if ("Secretary can only delete teachers".equals(response) || "Insufficient permissions".equals(response)) {
            return ResponseEntity.status(403).body(response);
        } else {
            return ResponseEntity.status(400).body("An error occurred: " + response);
        }
    }
    @GetMapping("/me")
    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoDetails userDetails = (UserInfoDetails) authentication.getPrincipal();
        return new UserDTO(
                userDetails.getId(),
                userDetails.getName(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                null, // Não retorna a senha
                userDetails.getAuthorities().iterator().next().getAuthority()
        );
    }
}