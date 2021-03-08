package org.jsonapp.gestaotarefas.objetosvalor;

public class Titulo {

	final String descricao;

	public Titulo(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}
    
}
