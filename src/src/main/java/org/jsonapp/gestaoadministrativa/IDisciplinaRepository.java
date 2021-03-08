package org.jsonapp.gestaoadministrativa;

import java.sql.SQLException;
import java.util.List;

import org.jsonapp.gestaoadministrativa.entidades.Disciplina;
import org.jsonapp.gestaoadministrativa.objetosvalor.DisciplinaId;

public interface IDisciplinaRepository {

	void salvar(Disciplina disciplina) throws SQLException;

	Disciplina obter(DisciplinaId disciplinaId) throws SQLException;

	void atualizar(Disciplina disciplina) throws SQLException;

	List<ListaDeDisciplinasDisponiveisDto> obter() throws SQLException;
    
}
