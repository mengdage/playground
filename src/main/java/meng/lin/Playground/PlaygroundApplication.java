package meng.lin.Playground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PlaygroundApplication {

	public static void main(String[] args) {
		ApplicationContext run = SpringApplication.run(PlaygroundApplication.class, args);
		Object tester = run.getBean("tester");
		System.out.println(tester);

	}

}
