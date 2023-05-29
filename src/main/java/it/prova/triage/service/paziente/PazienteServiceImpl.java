package it.prova.triage.service.paziente;

import java.time.LocalDate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.triage.model.Paziente;
import it.prova.triage.model.StatoPaziente;
import it.prova.triage.repository.PazienteRepository;

@Service
@Transactional
public class PazienteServiceImpl implements PazienteService {
	@Autowired
	private PazienteRepository repository;

	@Override
	public List<Paziente> listAllElements() {
		return (List<Paziente>) repository.findAll();
	}

	@Override
	public Paziente caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Paziente aggiorna(Paziente pazienteInstance) {
		
		Paziente paziente = repository.findById(pazienteInstance.getId()).orElse(null);
		
		if(paziente.getRegistrazione() != pazienteInstance.getRegistrazione())
		pazienteInstance.setRegistrazione(paziente.getRegistrazione());
		
		if (pazienteInstance.getNome() == null)
			pazienteInstance.setNome(paziente.getNome());

		if (pazienteInstance.getCognome() == null)
			pazienteInstance.setCognome(paziente.getCognome());
		if (pazienteInstance.getCodiceDottore() == null)
			pazienteInstance.setCodiceDottore(paziente.getCodiceDottore());
		if (pazienteInstance.getCodiceFiscale() == null)
			pazienteInstance.setCodiceFiscale(paziente.getCodiceFiscale());
		if(pazienteInstance.getStatoPaziente() == null)
		pazienteInstance.setStatoPaziente(StatoPaziente.IN_ATTESA_VISITA);

		return repository.save(pazienteInstance);
	}

	@Override
	public Paziente inserisciNuovo(Paziente pazienteInstance) {

		pazienteInstance.setRegistrazione(LocalDate.now());
		pazienteInstance.setStatoPaziente(StatoPaziente.IN_ATTESA_VISITA);

		return repository.save(pazienteInstance);
	}

	@Override
	public void rimuovi(Long idToRemove) {
		Paziente paziente = repository.findById(idToRemove).orElse(null);
		
		if(paziente.getStatoPaziente().equals(paziente.getStatoPaziente().DIMESSO))
		repository.deleteById(idToRemove);
		else
			throw new RuntimeException("non si può eliminare un paziente che non è stato dimesso");
	}

	@Override
	public Paziente findByCodiceFiscale(String codiceFiscaleInstance) {
		return repository.findByCodiceFiscale(codiceFiscaleInstance).orElse(null);
	}

	@Override
	public Paziente findByCodiceDottore(String codiceDottoreInstance) {
		return repository.findPazienteByCodiceDottore(codiceDottoreInstance);

	}

	@Override
	public void ricovera(Long id) {
		
		Paziente result = repository.findById(id).orElse(null);

		if (result == null)
			throw new RuntimeException("paziente non trovato");

		if (!result.getStatoPaziente().equals(StatoPaziente.IN_VISITA))
			throw new RuntimeException("Paziente non in visita");

		result.setStatoPaziente(StatoPaziente.RICOVERATO);
		result.setCodiceDottore(null);

		repository.save(result);
	}

	@Override
	public void impostaCodiceDottore(String cf, String cd) {
		Paziente result = repository.findByCodiceFiscale(cf).orElse(null);

		if (result == null)
			throw new RuntimeException("paziente non trovato");

		result.setCodiceDottore(cd);
		result.setStatoPaziente(StatoPaziente.IN_VISITA);
		repository.save(result);
	}

	@Override
	public void dimetti(Long id) {
		
		Paziente result = repository.findById(id).orElse(null);

		if (result == null)
			throw new RuntimeException("paziente non trovato");

		if (!result.getStatoPaziente().equals(StatoPaziente.IN_VISITA))
			throw new RuntimeException("Paziente non in visita");

		result.setStatoPaziente(StatoPaziente.DIMESSO);
		result.setCodiceDottore(null);

		repository.save(result);
	}
}