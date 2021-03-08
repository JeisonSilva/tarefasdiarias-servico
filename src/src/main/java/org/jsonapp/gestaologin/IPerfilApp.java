package org.jsonapp.gestaologin;

import java.sql.SQLException;

public interface IPerfilApp {

	PerfilDto obterPerfil(String email) throws SQLException;
    
}
