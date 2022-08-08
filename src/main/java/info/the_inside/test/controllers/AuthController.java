package info.the_inside.test.controllers;
/*
AuthController - контроллер авторизации пользователя
 */

import info.the_inside.test.configurations.JwtTokenUtil;
import info.the_inside.test.dto.JwtRequest;
import info.the_inside.test.dto.JwtResponse;
import info.the_inside.test.exceptions.RegistrationError;
import info.the_inside.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/auth")
    private ResponseEntity<?> createToken(@RequestBody JwtRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new RegistrationError("User is not found"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(request.getName());
        String token = jwtTokenUtil.generateToken(userDetails);
        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }



}
