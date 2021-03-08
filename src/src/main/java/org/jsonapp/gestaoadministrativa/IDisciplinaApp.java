package org.jsonapp.gestaoadministrativa;

import java.sql.SQLException;
import java.util.List;

public interface IDisciplinaApp {

	void criar(DisciplinaDto disciplinaDto) throws SQLException;

	void atualizar(int id, DisciplinaDto disciplinaDto) throws SQLException;

	void deletar(int id) throws SQLException;

	List<ListaDeDisciplinasDisponiveisDto> obterTodos() throws SQLException;
    
}
