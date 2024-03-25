package hello.newsallimi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NewsAllimiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsAllimiApplication.class, args);
	}

}
