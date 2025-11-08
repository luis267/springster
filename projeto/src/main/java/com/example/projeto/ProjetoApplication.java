package com.example.projeto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Apenas @SpringBootApplication, sem atributos extras.
// Isso garantirá que o Spring escaneie todos os sub-pacotes
// (como .config, .controller, .service, .repository)
@SpringBootApplication
public class ProjetoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoApplication.class, args);
	}

}