package meng.lin.Playground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PlaygroundApplication {

	public static void main(String[] args) {
		ApplicationContext run = SpringApplication.run(PlaygroundApplication.class, args);
	}

}
