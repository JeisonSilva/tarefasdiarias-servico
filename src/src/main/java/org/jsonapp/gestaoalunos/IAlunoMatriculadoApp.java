package org.jsonapp.gestaoalunos;

import java.sql.SQLException;
import java.util.List;

public interface IAlunoMatriculadoApp {

	List<AlunoMatriculadoDto> obterAlunosMatriculadosPorEmailProfessor(String email) throws SQLException;

    List<AlunoMatriculadoDto> obterAlunosMatriculadosPorEmailAluno(String email) throws SQLException;
    
}
