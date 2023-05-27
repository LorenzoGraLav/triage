package it.prova.triage.service.paziente;

import java.util.List;

import it.prova.triage.dto.PazienteDTO;

public interface PazienteService {

	public List<PazienteDTO> listAllPazienti();

	public PazienteDTO visualizzaPaziente(Long id);

	public PazienteDTO inserisciPaziente(PazienteDTO pazienteInput);

	public PazienteDTO aggiornaPaziente(PazienteDTO pazienteInput);

	public void eliminaPaziente(Long id);

}