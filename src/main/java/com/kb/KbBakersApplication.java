package com.kb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kb.Entity.User;
import com.kb.repository.UserRepository;

@SpringBootApplication
public class KbBakersApplication {

	public static void main(String[] args) {
		SpringApplication.run(KbBakersApplication.class, args);
	}
	
	@Bean
    CommandLineRunner init(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("admin@gmail.com")==null) {
                User admin = new User();
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRole("ROLE_ADMIN");
                userRepository.save(admin);
                System.out.println("Admin user created successfully");
            }
        };
	}

}
