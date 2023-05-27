package it.prova.triage.service.paziente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.triage.dto.PazienteDTO;
import it.prova.triage.model.Paziente;
import it.prova.triage.repository.PazienteRepository;

@Service
@Transactional
public class PazienteServiceImpl implements PazienteService {
	

	@Autowired
	private PazienteRepository repository;
	

	@Override
	@Transactional(readOnly = true)
	public List<PazienteDTO> listAllPazienti() {
		return PazienteDTO.createPazienteDTOListFromModelList((List<Paziente>) repository.findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public PazienteDTO visualizzaPaziente(Long id) {
		return PazienteDTO.buildPazienteDTOFromModel(repository.findById(id).orElse(null));
	}

	@Override
	@Transactional
	public PazienteDTO inserisciPaziente(PazienteDTO pazienteInput) {
		return PazienteDTO.buildPazienteDTOFromModel(repository.save(pazienteInput.buildPazienteModel()));
	}

	@Override
	@Transactional
	public PazienteDTO aggiornaPaziente(PazienteDTO pazienteInput) {
		return PazienteDTO.buildPazienteDTOFromModel(repository.save(pazienteInput.buildPazienteModel()));
	}

	@Override
	@Transactional
	public void eliminaPaziente(Long id) {
		repository.deleteById(id);		
	}

}
