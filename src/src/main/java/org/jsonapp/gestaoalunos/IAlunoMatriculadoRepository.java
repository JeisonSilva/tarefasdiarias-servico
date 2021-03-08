package org.jsonapp.gestaoalunos;

import java.sql.SQLException;
import java.util.List;

import org.jsonapp.gestaoalunos.objetosvalor.ProfessorId;

public interface IAlunoMatriculadoRepository {

	List<AlunoMatriculadoDto> obterAlunosMatriculadosPorEmailProfessor(ProfessorId id) throws SQLException;
    
}
