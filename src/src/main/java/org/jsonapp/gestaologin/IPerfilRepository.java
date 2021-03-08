package org.jsonapp.gestaologin;

import java.sql.SQLException;

public interface IPerfilRepository {

	PerfilDto obter(String email) throws SQLException;
    
}
