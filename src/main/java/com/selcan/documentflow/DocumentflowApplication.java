package com.selcan.documentflow;

import com.selcan.documentflow.entity.User;
import com.selcan.documentflow.enums.Role;
import com.selcan.documentflow.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DocumentflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentflowApplication.class, args);
	}
	@Bean
	CommandLineRunner init(UserRepository repo, PasswordEncoder encoder) {
		return args -> {

			if (repo.findByEmail("approver@test.com").isEmpty()) {

				User user = new User();
				user.setEmail("approver@test.com");
				user.setFullName("Approver");
				user.setPassword(encoder.encode("123457"));
				user.setRole(Role.APPROVER);
				repo.save(user);
			}
		};
	}

}
