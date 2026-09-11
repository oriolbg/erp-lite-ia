package erplite.ia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:gemini.properties")
public class IaApplication {

	public static void main(String[] args) {
		SpringApplication.run(IaApplication.class, args);
	}

}
