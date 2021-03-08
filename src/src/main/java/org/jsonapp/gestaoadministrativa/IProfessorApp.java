package org.jsonapp.gestaoadministrativa;

import java.sql.SQLException;

public interface IProfessorApp {

	void criar(ProfessorDto professorDto) throws SQLException, Exception;
    
}
