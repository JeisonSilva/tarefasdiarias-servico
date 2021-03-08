package org.jsonapp.gestaologin;

import java.sql.SQLException;

import org.jsonapp.gestaologin.entidades.Login;
import org.jsonapp.gestaologin.entidades.Perfil;

public interface ILoginRepository {

	void salvar(Login login) throws SQLException;

	Login obter(String email) throws SQLException;

	void atualizar(Login login) throws SQLException;

	Perfil obterPerfil(String email) throws SQLException;
    
}
