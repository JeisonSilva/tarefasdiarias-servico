package org.jsonapp.gestaoadministrativa;

import java.sql.SQLException;

import org.jsonapp.gestaoadministrativa.entidades.Matricula;
import org.jsonapp.gestaoadministrativa.objetosvalor.AlunoId;

public interface IMatriculaRepository {

	Matricula obterMatriculaPorIdAluno(AlunoId alunoId) throws SQLException;

	void deletar(Matricula matricula) throws SQLException;

	void salvar(Matricula matricula) throws SQLException;
    
}
