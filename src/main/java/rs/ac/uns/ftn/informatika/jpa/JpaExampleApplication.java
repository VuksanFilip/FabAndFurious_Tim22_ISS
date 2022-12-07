package rs.ac.uns.ftn.informatika.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger1;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SpringBootApplication
public class JpaExampleApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(JpaExampleApplication.class, args);
	}

}
