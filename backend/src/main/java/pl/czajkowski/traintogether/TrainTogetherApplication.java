package pl.czajkowski.traintogether;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.czajkowski.traintogether.security.jwt.RsaKeysProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeysProperties.class)
public class TrainTogetherApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainTogetherApplication.class, args);
	}

}
