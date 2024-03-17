package com.cu.sci.lambdaserver;


import com.cu.sci.lambdaserver.admin.AdminRepository;
import com.cu.sci.lambdaserver.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LambdaServerApplication implements CommandLineRunner {
    private final UserRepository userRepository;

    public LambdaServerApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
//    private final AdminRepository adminRepository;
//
//    public LambdaServerApplication(AdminRepository adminRepository) {
//        this.adminRepository = adminRepository;
//    }
    public static void main(String[] args) {
        SpringApplication.run(LambdaServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Create a RestTemplate instance
        System.out.println("printing all users in db on start of application");
        userRepository.findAll().forEach(user -> {
            System.out.println(user.toString());
        });
    }
}