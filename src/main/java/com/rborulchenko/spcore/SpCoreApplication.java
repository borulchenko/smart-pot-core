package com.rborulchenko.spcore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.rborulchenko.spcore.service.PotStatusService;

@SpringBootApplication
@EnableScheduling
public class SpCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpCoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(PotStatusService potStatusService){
		return (args) -> {
			System.out.println(potStatusService.getAll().size());
			System.out.println(potStatusService.getLatest());
		};
	}
}
