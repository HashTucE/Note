package com.mediscreen.note;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class NoteApplication implements CommandLineRunner {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(NoteApplication.class, args);
	}

	@Override
	public void run(String... args) {

		System.out.println(" ");
		System.out.println("  _   _               ");
		System.out.println(" | \\ | |      _        ");
		System.out.println(" |  \\| | ___ | |_ ___ ");
		System.out.println(" | . ` |/ _ \\| __/ _ \\");
		System.out.println(" | |\\  | (_) | ||  __/");
		System.out.println(" |_| \\_|\\___/ \\__\\___|");
		System.out.println(" =====================");
		System.out.println(" :: API ::    (v1.0.0)");
		System.out.println(" ");
	}
}
