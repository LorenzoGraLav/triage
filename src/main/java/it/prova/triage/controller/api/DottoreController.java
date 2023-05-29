package it.prova.triage.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import it.prova.triage.dto.DottoreDTO;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/dottore")
public class DottoreController {

	private static final Logger LOGGER = LogManager.getLogger(DottoreController.class);

	@Autowired
	private WebClient webClient;

	@GetMapping
	public List<DottoreDTO> listAll() {

		LOGGER.info(".........invocazione servizio esterno............");

		Mono<List<DottoreDTO>> response = webClient.get().retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<DottoreDTO>>() {
				});

		LOGGER.info(".........invocazione servizio esterno completata............");

		List<DottoreDTO> lista = response.block();
		return lista;

	}

	@PostMapping("/private")
	public ResponseEntity<DottoreDTO> inserisciDottore(@Valid @RequestBody DottoreDTO dottore) {
		LOGGER.info(".........invocazione servizio esterno............");

		ResponseEntity<DottoreDTO> response = webClient.post().body(Mono.just(dottore), DottoreDTO.class)
				.retrieve().toEntity(DottoreDTO.class).block();

		if (response.getStatusCode() != HttpStatus.CREATED) {
			throw new RuntimeException();
		}

		LOGGER.info(".........invocazione servizio esterno completata............");

//		return DottoreDTO.builder().cognome(response.getBody().getCognome())
//				.nome(response.getBody().getNome()).codiceDottore(response.getBody().getCodiceDottore()).build();
		
		return response;
	}
	
	@PutMapping("/private")
	public ResponseEntity<DottoreDTO> aggiornaDottore(@Valid @RequestBody DottoreDTO dottore) {
		LOGGER.info(".........invocazione servizio esterno............");
		
		ResponseEntity<DottoreDTO> response = webClient.put().body(Mono.just(dottore), DottoreDTO.class)
				.retrieve().toEntity(DottoreDTO.class).block();
		
		if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
			throw new RuntimeException();
		}
		
		LOGGER.info(".........invocazione servizio esterno completata............");
		
//		return DottoreDTO.builder().cognome(response.getBody().getCognome())
//				.nome(response.getBody().getNome()).codiceDottore(response.getBody().getCodiceDottore()).build();
		
		return response;
	}
	
}