package com.generation.deliverymandapramim.configuration;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI springDeliveryMandaPraMim() {
		return new OpenAPI()	
				.info(new Info()
						.title("Projeto Delivery Manda Pra Mim")
						.description("Projeto Delivery Manda Pra Mim - Generation Brasil")
						.version("v0.0.1")
						.license(new License()
								.name("Grupo 7")
								.url("https://github.com/Projeto-Integrador-Grupo7"))
						.contact(new Contact()
								.name("Grupo 7")
								.url("https://github.com/Projeto-Integrador-Grupo7")
								.email("generation.g777@gmail.com")))
						.externalDocs(new ExternalDocumentation()
								.description("Generation")
								.url("https://brazil.generation.org/"));
	}

	@Bean
	OpenApiCustomizer customerGlobalHeaderOpenApiCustomiser() {
		
		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations()
					.forEach(operation -> {
				
				ApiResponses apiResponses = operation.getResponses();
				
				apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
				apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!"));
				apiResponses.addApiResponse("204", createApiResponse("Objeto Excluído!"));
				apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição!"));
				apiResponses.addApiResponse("401", createApiResponse("Acesso Não Autorizado!"));
				apiResponses.addApiResponse("403", createApiResponse("Acesso Proibido!"));
				apiResponses.addApiResponse("404", createApiResponse("Objeto Não Encontrado!"));
				apiResponses.addApiResponse("500", createApiResponse("Erro Na Aplicação!"));
				
				
			}));
		};
	}

	private ApiResponse createApiResponse(String message) {

		return new ApiResponse().description(message);
	}

}
