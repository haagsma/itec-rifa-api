package br.com.itec.rifa;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = "br.com.itec.rifa")
@SpringBootApplication
public class RifaApplication {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(RifaApplication.class, args);
	}

}
