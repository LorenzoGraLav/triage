package it.prova.triage.dto;

import javax.validation.constraints.NotBlank;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Json include, se un dato è nullo non cerrà messo nell'output
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class DottoreDTO {

	private Long id;

	private String nome;

	private String cognome;

	@NotBlank(message = "{codiceDottore.notblank}")
	private String codiceDottore;

	private String codFiscalePazienteAttualmenteInVisita;

	private boolean inVisita;

	private boolean inServizio;
}