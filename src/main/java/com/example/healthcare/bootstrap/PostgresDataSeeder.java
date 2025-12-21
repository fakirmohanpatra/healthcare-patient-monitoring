package com.example.healthcare.bootstrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.healthcare.domain.security.Role;
import com.example.healthcare.domain.security.User;
import com.example.healthcare.repository.jpa.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Profile("!production")
public class PostgresDataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        
        if (userRepository.count() >= 50) {
            seedAdmin();
            seedDoctorsAndNurses();
        }
    }

    private void seedAdmin() {
        if (userRepository.existsByUsername("admin")) {
            return; // Admin already exists
        }   
        User admin = createUser(
            "admin", 
            "adminpass", 
            Set.of(Role.ROLE_ADMIN
        ));
        userRepository.save(admin); 
        System.out.println("Seeded admin user into PostgreSQL database.");
    }

    private void seedDoctorsAndNurses() {
        if (userRepository.count() >= 50) {
            return; // Doctors and Nurses already exist
        }
        List<User> users = new ArrayList<>();

        // 🔐 Single ADMIN
        users.add(createUser(
            "admin", 
            "adminpass", 
            Set.of(Role.ROLE_ADMIN
        )));

        // 👩‍⚕️ Multiple DOCTORS
        for (int i = 1; i <= 20; i++) {
            users.add(createUser(
                "doctor" + i, 
                "doctorpass" + i, 
                Set.of(Role.ROLE_DOCTOR
            )));
        }

        // 👩‍⚕️ Multiple NURSES
        for (int i = 1; i <= 20; i++) {
            users.add(createUser(
                "nurse" + i, 
                "nursepass" + i, 
                Set.of(Role.ROLE_NURSE
            )));
        }

        userRepository.saveAll(users);
        System.out.println("Seeded " + users.size() + " users into PostgreSQL database.");
    }

    private User createUser(String username, String rawPassword, Set<Role> roles) {
        return new User(
            null,
            username,
            passwordEncoder.encode(rawPassword),
            roles
        );
    }
}
