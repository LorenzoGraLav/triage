package it.prova.triage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.prova.triage.model.Paziente;

public interface PazienteRepository extends PagingAndSortingRepository<Paziente, Long>, JpaSpecificationExecutor<Paziente> {
	 Optional<Paziente> findByCodiceFiscale(String codiceFiscaleInput);

		@Query("select p from Paziente p where p.codiceDottore=?1")
		Paziente findPazienteByCodiceDottore(String codiceDottoreInput);
}