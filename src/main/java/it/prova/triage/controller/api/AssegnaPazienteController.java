package it.prova.triage.controller.api;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import it.prova.triage.dto.DottorePazienteRequestDTO;
import it.prova.triage.dto.DottorePazienteResponseDTO;
import it.prova.triage.service.paziente.PazienteService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/assegnaPazienteController")
public class AssegnaPazienteController {

	private static final Logger LOGGER = LogManager.getLogger(AssegnaPazienteController.class);

	@Autowired
	private WebClient webClient;
	@Autowired
	private PazienteService pazienteService;

	
	@PostMapping("/impostaVisita")
	public DottorePazienteResponseDTO impostaVisita(@RequestBody DottorePazienteRequestDTO dottore) {
		
		LOGGER.info(".........invocazione servizio esterno............");

		ResponseEntity<DottorePazienteResponseDTO> response = webClient.post().uri("/impostaVisita")
				.body(Mono.just(dottore), DottorePazienteRequestDTO.class)
				.retrieve().toEntity(DottorePazienteResponseDTO.class).block();

		
		if (response.getStatusCode() != HttpStatus.ACCEPTED) {
			throw new RuntimeException();
		}
		
		LOGGER.info(".........invocazione servizio esterno completata............");

		pazienteService.impostaCodiceDottore(dottore.getCodFiscalePazienteAttualmenteInVisita(),
				dottore.getCodiceDottore());
		
		return new DottorePazienteResponseDTO(response.getBody().getCodiceDottore(),
				response.getBody().getCodFiscalePazienteAttualmenteInVisita());
	}

	
}