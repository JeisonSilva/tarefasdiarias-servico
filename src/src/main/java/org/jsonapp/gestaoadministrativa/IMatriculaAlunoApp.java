package org.jsonapp.gestaoadministrativa;

import java.sql.SQLException;

public interface IMatriculaAlunoApp {
	void matricular(String emailProfessor, String emailAluno) throws Exception;

	void cancelarMatricula(String emailProfessor, String emailAluno) throws SQLException;
}
