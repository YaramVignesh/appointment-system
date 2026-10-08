package com.vignesh01.appointment_system.service;

import com.vignesh01.appointment_system.repository.ClientRepository;
import com.vignesh01.appointment_system.repository.StaffRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AppUserDetailsService implements UserDetailsService {


    private ClientRepository clientRepository;
    private StaffRepository staffRepository;

    public AppUserDetailsService(ClientRepository clientRepository, StaffRepository staffRepository) {
        this.clientRepository = clientRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return clientRepository.findByEmail(email)
                .map(client -> createUserDetails(client.getEmail(), client.getPassword(), "ROLE_CLIENT"))
                .orElseGet(() -> staffRepository.findByEmail(email)
                        .map(staff -> createUserDetails(staff.getEmail(), staff.getPassword(), "ROLE_ADMIN"))
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email)));
    }

    private UserDetails createUserDetails(String email, String password, String role) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        return new User(email,password,true,true,true,true,authorities);
    }
}
