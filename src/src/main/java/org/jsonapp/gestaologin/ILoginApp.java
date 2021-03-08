package org.jsonapp.gestaologin;

import java.sql.SQLException;

import org.jsonapp.gestaologin.exceptions.PerfilNaoAssociadoException;

public interface ILoginApp {

	void criar(LoginDto loginDto) throws SQLException;

	void alterar(String email, AlteracaoSenhaDto alteracaoSenhaDto) throws SQLException;

	void autorizar(String email, AutorizacaoDto autorizacaoDto) throws PerfilNaoAssociadoException, SQLException;

	void logout(String email) throws SQLException;

	void desabilitarConta(String email) throws SQLException;
    
}
