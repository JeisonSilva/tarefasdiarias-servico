package org.jsonapp.gestaotarefas.objetosvalor;

public class ProfessorId {

	final String email;

	public ProfessorId(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return this.email;
	}
}
