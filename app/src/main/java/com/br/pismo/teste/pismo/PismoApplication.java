package com.br.pismo.teste.pismo;

import com.br.pismo.teste.pismo.entity.OperationTypes;
import com.br.pismo.teste.pismo.repository.OperationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class PismoApplication {
	
	@Autowired
	private OperationTypeRepository operationTypeRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(PismoApplication.class, args);
	}
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
	
	@Bean
	public void insertTypes() {
		OperationTypes operationTypesAVista = new OperationTypes();
		operationTypesAVista.setId(1L);
		operationTypesAVista.setDescription("COMPRA A VISTA");
		operationTypesAVista.setCreditType(false);
		operationTypeRepository.save(operationTypesAVista);
		OperationTypes operationTypesParcelada = new OperationTypes();
		operationTypesParcelada.setId(2L);
		operationTypesParcelada.setDescription("COMPRA PARCELADA");
		operationTypesParcelada.setCreditType(false);
		operationTypeRepository.save(operationTypesParcelada);
		OperationTypes operationTypesSaque = new OperationTypes();
		operationTypesSaque.setId(3L);
		operationTypesSaque.setDescription("SAQUE");
		operationTypesSaque.setCreditType(false);
		operationTypeRepository.save(operationTypesSaque);
		OperationTypes operationTypesPagamento = new OperationTypes();
		operationTypesPagamento.setId(4L);
		operationTypesPagamento.setDescription("PAGAMENTO");
		operationTypesPagamento.setCreditType(true);
		operationTypeRepository.save(operationTypesPagamento);
	}
	
}
