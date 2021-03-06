package todos.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "todos")
public class Starter {

    public static void main(final String[] args) {

        SpringApplication.run(Starter.class, args);
    }
}