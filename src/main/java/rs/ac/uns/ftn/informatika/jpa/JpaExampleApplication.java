package rs.ac.uns.ftn.informatika.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class JpaExampleApplication {

	
	public static void main(String[] args) {

		SpringApplication.run(JpaExampleApplication.class, args);
	}

}
