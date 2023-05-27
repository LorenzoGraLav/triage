package it.prova.triage.controller.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.triage.dto.UtenteDTO;
import it.prova.triage.model.Utente;
import it.prova.triage.security.dto.UtenteInfoJWTResponseDTO;
import it.prova.triage.service.utente.UtenteService;


@RestController
@RequestMapping("/api/utente")
public class UtenteController {


	@Autowired
	private UtenteService utenteService;

	@GetMapping(value = "/userInfo")
	public ResponseEntity<UtenteInfoJWTResponseDTO> getUserInfo() {

		// se sono qui significa che sono autenticato quindi devo estrarre le info dal
		// contesto
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// estraggo le info dal principal
		Utente utenteLoggato = utenteService.findByUsername(username);
		List<String> ruoli = utenteLoggato.getRuoli().stream().map(item -> item.getCodice())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new UtenteInfoJWTResponseDTO(utenteLoggato.getNome(), utenteLoggato.getCognome(),
				utenteLoggato.getUsername(), ruoli));
	}

	@GetMapping
	public List<UtenteDTO> getAll() {
		return UtenteDTO.createUtenteDTOListFromModelList(utenteService.listAllUtenti());
	}

	
	@GetMapping("/{id}")
	public UtenteDTO findById(@PathVariable(value = "id", required = true) long id) {

		Utente utente = utenteService.caricaSingoloUtenteConRuoli(id);

		if (utente == null)
			throw new RuntimeException("");

		return UtenteDTO.buildUtenteDTOFromModel(utente);

	}

	
	@PutMapping("/{id}")
	public UtenteDTO update(@Valid @RequestBody UtenteDTO utenteInput,
			@PathVariable(required = true) Long id) {

		Utente utente = utenteService.caricaSingoloUtente(id);

		if (utente == null)
			throw new RuntimeException("");

		utenteInput.setId(id);
		Utente utenteAggiornato = utenteService.aggiorna(utenteInput.buildUtenteModel(false));
		return UtenteDTO.buildUtenteDTOFromModel(utenteAggiornato);

	}

	
	@DeleteMapping("/private/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(required = true) Long id) {

		Utente utente = utenteService.caricaSingoloUtente(id);

		if (utente == null)
			throw new RuntimeException("non esiste nessun id");
		
		utenteService.rimuovi(id);

	}
	

	
	@PostMapping("/create")
	public UtenteDTO creaUtente(@Valid @RequestBody UtenteDTO utenteInput) {
		return  UtenteDTO.buildUtenteDTOFromModel(utenteService.inserisciNuovo(utenteInput.buildUtenteModel(true)));
	}
	
	
}