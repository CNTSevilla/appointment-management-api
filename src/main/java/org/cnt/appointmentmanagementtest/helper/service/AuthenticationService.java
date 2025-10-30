package org.cnt.appointmentmanagementtest.helper.service;

import jakarta.servlet.http.HttpServletRequest;
import org.cnt.appointmentmanagementtest.helper.model.api.in.LoginDTO;
import org.cnt.appointmentmanagementtest.helper.model.api.in.RegisterDTO;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Helper;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Role;
import org.cnt.appointmentmanagementtest.helper.model.db.repositories.HelperRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthenticationService {
    private final HelperRepository helperRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            HelperRepository helperRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.helperRepository = helperRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Helper signup(RegisterDTO input) {
        // Comprobación de duplicados
        if (helperRepository.existsByUsername(input.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
        }

        if (helperRepository.existsByEmail(input.getEmail())) {
            throw new IllegalArgumentException("El email ya está en uso.");
        }

        Helper user = new Helper();
        user.setUsername(input.getUsername());
        user.setName(input.getName());
        user.setPhone(input.getPhone());
        user.setEmail(input.getEmail());
        user.setRoles(Set.of(Role.ADMIN));
        user.setPasswordHashed(passwordEncoder.encode(input.getPassword()));

        return helperRepository.save(user);
    }

    public Helper authenticate(LoginDTO input) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getUsername(),
                            input.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("User doesnt exists or password is wrong");
        }

        return helperRepository.getByUsername(input.getUsername()).get();
    }

    public Helper getAuthenticatedHelper(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

        if (!(authentication.getPrincipal() instanceof Helper)) {
            throw new RuntimeException("User is not a Helper");
        }

        if (authentication.getPrincipal() == null) {
            throw new RuntimeException("User is null");
        }

        return (Helper) authentication.getPrincipal();
    }
}
