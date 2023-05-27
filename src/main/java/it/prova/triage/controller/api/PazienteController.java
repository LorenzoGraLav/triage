package it.prova.triage.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.triage.dto.PazienteDTO;
import it.prova.triage.service.paziente.PazienteService;

@RestController
@RequestMapping("/api/paziente")
public class PazienteController {

	@Autowired
	private PazienteService pazienteService;
	
	@GetMapping
	public List<PazienteDTO> visualizzaPazienti() {
		return pazienteService.listAllPazienti();
	}
	
	@GetMapping("/{id}")
	public PazienteDTO visualizza(@PathVariable(required = true) Long id) {
		return pazienteService.visualizzaPaziente(id);
	}
	
	@PostMapping
	public PazienteDTO createNew(@Valid @RequestBody PazienteDTO pazienteInput) {
		if (pazienteInput.getId() != null)
			throw new RuntimeException();
		return pazienteService.inserisciPaziente(pazienteInput);
	}
	@PutMapping("/{id}")
	public PazienteDTO update(@Valid @RequestBody PazienteDTO pazienteInput, @PathVariable(required = true) Long id) {
		if (pazienteInput.getId() != null)
			throw new RuntimeException();
		return pazienteService.aggiornaPaziente(pazienteInput);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(required = true) Long id) {
		pazienteService.eliminaPaziente(id);
	}
}