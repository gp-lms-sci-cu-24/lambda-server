package com.cu.sci.lambdaserver;

import com.cu.sci.lambdaserver.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LambdaServerApplication implements CommandLineRunner {
    private final UserRepository userRepository;

    public LambdaServerApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(LambdaServerApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception{
        userRepository.findAll().forEach(user->{
            System.out.println(user.toString() );
        });
    }
}
