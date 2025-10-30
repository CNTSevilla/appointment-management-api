package org.cnt.appointmentmanagementtest.helper.controllers;

import org.cnt.appointmentmanagementtest.common.security.JWTService;
import org.cnt.appointmentmanagementtest.helper.model.api.in.LoginDTO;
import org.cnt.appointmentmanagementtest.helper.model.api.in.RegisterDTO;
import org.cnt.appointmentmanagementtest.helper.model.api.out.TokenDTO;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Helper;
import org.cnt.appointmentmanagementtest.helper.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final JWTService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JWTService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerUserDto) {
        try {
            Helper registeredUser = authenticationService.signup(registerUserDto);
            return ResponseEntity.ok(registeredUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error al registrar el usuario"));
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticate(@RequestBody LoginDTO loginUserDto) throws Exception {
        try {
            Helper authenticatedUser = authenticationService.authenticate(loginUserDto);
            String jwtToken = jwtService.generateToken(authenticatedUser);
            TokenDTO loginResponse = new TokenDTO(jwtToken, jwtService.getExpirationTime());
            return ResponseEntity.ok(loginResponse);

        } catch (BadCredentialsException e) {
            // Contraseña incorrecta
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas."));

        } catch (IllegalArgumentException e) {
            // Algún error de validación (por ejemplo, datos vacíos)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));

        } catch (Exception e) {
            // Error general no controlado
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error inesperado al iniciar sesión."));
        }
    }
}
