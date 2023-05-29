package it.prova.triage.controller.api.exception;

public class AssegnazioneErrataPazienteException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AssegnazioneErrataPazienteException(String message) {
		super(message);
	}

}
