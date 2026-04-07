package br.com.fiap.projeto_musica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfiguration {
	
	@Bean
	OpenAPI configurarSwagger() {
		return new OpenAPI().info(new Info()
				.title("Projeto Múscia - FIAP 2026")
				.description("Este projeto tem como objetivo realizar a gestão de músicas,"
						+ " artistas e integrantes via uma aplicação Springboot")
				.summary("Projeto de gestão musical")
				.termsOfService("Texto de Termos de Serviço")
				.version("1.0.0")
				.license(new License().url("/licenses").name("Tela de Aquisição de Planos de Uso"))
				);
	}
}
