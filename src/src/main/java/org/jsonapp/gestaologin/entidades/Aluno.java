package org.jsonapp.gestaologin.entidades;

import org.jsonapp.gestaotarefas.objetosvalor.AlunoId;
import org.jsonapp.gestaotarefas.objetosvalor.Nome;

public class Aluno extends Perfil{
    final AlunoId alunoId;
	final Nome nome;

	public Aluno(AlunoId alunoId, Nome nome) {
		this.alunoId = alunoId;
		this.nome = nome;
	}

	public String getEmail(){
		return this.alunoId.toString();
	}

	public String getNome() {
		return nome.toString();
	}
}
