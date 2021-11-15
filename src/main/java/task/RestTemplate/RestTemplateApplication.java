package task.RestTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import task.RestTemplate.service.RestService;

@SpringBootApplication
public class RestTemplateApplication implements CommandLineRunner {

	private RestService restService;

	@Autowired
	public RestTemplateApplication(RestService restService) {
		this.restService = restService;
	}

	public static void main(String[] args) {
		SpringApplication.run(RestTemplateApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		restService.getUsersWithCookies();

		restService.createUser();

		restService.updateUser();

		restService.deleteUser();
	}
}
