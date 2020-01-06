package br.com.itec.rifa;

import br.com.itec.rifa.config.FileStorageProperties;
import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = "br.com.itec.rifa")
@SpringBootApplication
public class RifaApplication {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(RifaApplication.class, args);
	}

}
