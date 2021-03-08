package org.jsonapp.gestaoadministrativa;

import java.sql.SQLException;

public interface IAlunoApp {

	void criar(AlunoDto alunoDto) throws SQLException, Exception;
    
}
