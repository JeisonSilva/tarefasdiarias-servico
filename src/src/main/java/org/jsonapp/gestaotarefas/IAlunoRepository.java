package org.jsonapp.gestaotarefas;

import java.sql.SQLException;

import org.jsonapp.gestaologin.entidades.Aluno;
import org.jsonapp.gestaotarefas.objetosvalor.AlunoId;

public interface IAlunoRepository {

	AlunoId obterAlunoId(String emailAluno) throws Exception;

	void salvar(Aluno novoAluno) throws SQLException;
    
}
