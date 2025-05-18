package ru.fridrock.jir_backend;

import org.springframework.boot.SpringApplication;

public class TestJirBackendApplication {

	public static void main(String[] args) {
		SpringApplication.from(JirBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
