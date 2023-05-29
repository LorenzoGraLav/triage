package it.prova.triage.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DottorePazienteResponseDTO {
	private String codiceDottore;
	private String codFiscalePazienteAttualmenteInVisita;
}