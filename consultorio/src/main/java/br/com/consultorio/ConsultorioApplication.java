package br.com.consultorio;

import br.com.consultorio.repository.MedicoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsultorioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsultorioApplication.class, args);
		MedicoRepository medicoRepository;

	}

}
