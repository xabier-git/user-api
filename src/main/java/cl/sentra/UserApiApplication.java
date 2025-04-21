package cl.sentra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserApiApplication {
    public static void main(String[] args) {
        System.out.println("Iniciando UserApiApplication...");
        SpringApplication.run(UserApiApplication.class, args);
    }
}