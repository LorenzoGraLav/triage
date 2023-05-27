package it.prova.triage.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.prova.triage.model.Paziente;

public interface PazienteRepository extends PagingAndSortingRepository<Paziente, Long>, JpaSpecificationExecutor<Paziente> {

}