package com.vignesh01.appointment_system.service;

import com.vignesh01.appointment_system.dto.LoginRequest;
import com.vignesh01.appointment_system.dto.RegisterRequest;
import com.vignesh01.appointment_system.entity.Client;
import com.vignesh01.appointment_system.repository.ClientRepository;
import com.vignesh01.appointment_system.repository.StaffRepository;
import com.vignesh01.appointment_system.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final StaffRepository staffRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public  AuthService(ClientRepository clientRepository, PasswordEncoder passwordEncoder, StaffRepository staffRepository ,
                        AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.staffRepository = staffRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    public Client registerClient(RegisterRequest registerRequest){
        if(clientRepository.findByEmail(registerRequest.getEmail()).isPresent()||
        staffRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new IllegalStateException("A user whith this email already exists");
        }

        Client newClient = new Client();
        newClient.setFirstName(registerRequest.getFirstName());
        newClient.setLastName(registerRequest.getLastName());
        newClient.setEmail(registerRequest.getEmail());

        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
        newClient.setPassword(hashedPassword);

        return clientRepository.save(newClient);
    }

    public String login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        return jwtUtil.generateToken(userDetails);

    }
}
