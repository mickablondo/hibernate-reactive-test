package dev.mikablondo.hibernate_reactive_test;

import dev.mikablondo.hibernate_reactive_test.configuration.TestContainerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.testcontainers.DockerClientFactory;

@SpringBootApplication
public class HibernateReactiveTestApplication {

	public static void main(String[] args) {
		if (dockerIsAvailable()) {
			TestContainerConfig.init();
		}
		SpringApplication.run(HibernateReactiveTestApplication.class, args);
	}

	private static boolean dockerIsAvailable() {
		try {
			DockerClientFactory.instance().client();
			return true;
		} catch (Exception e) {
			System.err.println("Docker not available: " + e.getMessage());
			return false;
		}
	}
}
