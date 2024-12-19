package com.seven.schoolapi.service.userServices;

import com.seven.schoolapi.dtos.UserDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.seven.schoolapi.entity.AuthRequest;
import com.seven.schoolapi.entity.UserInfo;
import com.seven.schoolapi.repositories.UserInfoRepository;
import com.seven.schoolapi.service.JwtService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userDetail = repository.findByUsername(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public String addUser(UserInfo userInfo) {
        if (repository.existsByUsername(userInfo.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User added successfully";
    }

    public Map<String, Object> login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getUsername());
            UserInfoDetails userDetails = (UserInfoDetails) authentication.getPrincipal();

            Map<String, Object> response = new HashMap<>();
            Map<String, Object> userDetailsMap = new HashMap<>();

            userDetailsMap.put("id", userDetails.getId());
            userDetailsMap.put("name", userDetails.getName());
            userDetailsMap.put("username", userDetails.getUsername());
            userDetailsMap.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
            userDetailsMap.put("email", userDetails.getEmail());

            response.put("user", userDetailsMap);
            response.put("token", token);

            return response;
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    @Transactional
    public String updateUser(Long id, UserDTO userDTO) {
        Optional<UserInfo> existingUser = repository.findById(id);
        if (existingUser.isEmpty()) {
            return "User not found";
        }

        UserInfo userToUpdate = existingUser.get();
        userToUpdate.setName(userDTO.name());
        userToUpdate.setUsername(userDTO.username());
        userToUpdate.setEmail(userDTO.email());

        repository.save(userToUpdate);
        return userToUpdate.getId() + " updated successfully";
    }
    @Transactional
    public String patchUser(Long id, UserDTO userDTO) {
        Optional<UserInfo> existingUserOptional = repository.findById(id);
        if (existingUserOptional.isEmpty()) {
            return "User not found";
        }

        UserInfo existingUser = existingUserOptional.get();
        if (userDTO.name() != null) {
            existingUser.setName(userDTO.name());
        }
        if (userDTO.username() != null) {
            existingUser.setUsername(userDTO.username());
        }
        if (userDTO.email() != null) {
            existingUser.setEmail(userDTO.email());
        }
        if (userDTO.password() != null) {
            existingUser.setPassword(encoder.encode(userDTO.password()));
        }

        repository.save(existingUser);
        return "User updated successfully";
    }

    public List<UserInfo> getAllUsers() {
        return repository.findAll();
    }

    public UserInfo getUserById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public UserInfo getUserByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }
    
    public List<UserInfo> getUsersByRole(String role) {
        return repository.findByRoles(role);
    }

    // Get all teachers
    public List<UserInfo> getAllTeachers() {
        return repository.findByRoles("ROLE_TEACHER");
    }

    // Get all secretaries
    public List<UserInfo> getAllSecretaries() {
        return repository.findByRoles("ROLE_SECRETARY");
    }

    public String deleteUser(Long userId) {
        Optional<UserInfo> userToDelete = repository.findById(userId);
        if (userToDelete.isPresent()) {
            UserInfo user = userToDelete.get();
            if (user.getRoles().contains("ROLE_ADMIN")) {
                return "Cannot delete an admin";
            }
            if (user.getRoles().contains("ROLE_SECRETARY") || user.getRoles().contains("ROLE_TEACHER")) {
                repository.deleteById(userId);
                return "User deleted successfully";
            }

            return "Insufficient permissions";
        }

        return "User not found";
    }
}