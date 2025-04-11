package es.bifacia.ytmp3;

import es.bifacia.ytmp3.service.MainService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class Ytmp3Application {

	@Autowired
	private MainService mainService;

	public static void main(String[] args) {
		SpringApplication.run(Ytmp3Application.class, args);
	}

	@PostConstruct
	public void start() throws Exception {
		mainService.runApplication();
	}

}
