package org.jsonapp.gestaotarefas.objetosvalor;

public class AlunoId {

	final String email;

	public AlunoId(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return this.email;
	}
}
