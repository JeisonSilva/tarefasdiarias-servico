package org.jsonapp.gestaotarefas;

import java.sql.SQLException;

import org.jsonapp.gestaotarefas.entidades.Professor;
import org.jsonapp.gestaotarefas.objetosvalor.ProfessorId;

public interface IProfessorRepository {

	Professor obter(String emailProfessor) throws Exception;

	Professor obter(ProfessorId professor) throws Exception;

	void salvar(Professor professor) throws SQLException;
    
}
