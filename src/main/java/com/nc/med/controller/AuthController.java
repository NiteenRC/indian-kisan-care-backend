package com.nc.med.controller;

import com.nc.med.auth.jwt.JwtUtils;
import com.nc.med.auth.payload.JwtResponse;
import com.nc.med.auth.payload.LoginRequest;
import com.nc.med.auth.payload.MessageResponse;
import com.nc.med.auth.payload.SignupRequest;
import com.nc.med.model.ERole;
import com.nc.med.model.Role;
import com.nc.med.model.User;
import com.nc.med.repo.RoleRepository;
import com.nc.med.repo.UserRepository;
import com.nc.med.service.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        if (userRepository.findAll().isEmpty()) {
            User user = new User(loginRequest.getUsername(), encoder.encode(loginRequest.getPassword()), "image".getBytes());

            Role adminRole = roleRepository.findByName(ERole.ROLE_SUPER_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            user.setRoles(roles);
            userRepository.save(user);
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles, userDetails.getImage()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsernameContainingIgnoreCase(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()), "image".getBytes());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody SignupRequest signUpRequest) {
        Optional<User> userOptional = userRepository.findByUsername(signUpRequest.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(encoder.encode(signUpRequest.getPassword()));
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        }
        return ResponseEntity.ok(new MessageResponse("User doesn't exist"));
    }

    @GetMapping
    public ResponseEntity<?> fetchAllUser() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}
