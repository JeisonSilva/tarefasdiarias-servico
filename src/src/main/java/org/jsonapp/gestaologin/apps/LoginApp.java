package org.jsonapp.gestaologin.apps;

import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;

import org.jsonapp.gestaologin.AlteracaoSenhaDto;
import org.jsonapp.gestaologin.AutorizacaoDto;
import org.jsonapp.gestaologin.ILoginApp;
import org.jsonapp.gestaologin.ILoginRepository;
import org.jsonapp.gestaologin.LoginDto;
import org.jsonapp.gestaologin.entidades.Login;
import org.jsonapp.gestaologin.entidades.Perfil;
import org.jsonapp.gestaologin.exceptions.PerfilNaoAssociadoException;
import org.jsonapp.gestaologin.objetosvalor.Email;
import org.jsonapp.gestaologin.objetosvalor.Senha;

@ApplicationScoped
public class LoginApp implements ILoginApp {

    final ILoginRepository loginRepository;

    public LoginApp(ILoginRepository loginRepository) {
        super();

        this.loginRepository = loginRepository;
    }

    @Override
    public void criar(LoginDto loginDto) throws SQLException {
        Email email = new Email(loginDto.getEmail());
        Senha senha = new Senha(loginDto.getSenha());
        Login login = new Login(email, senha);

        Login loginExistente = this.loginRepository.obter(email.toString());

        if(loginExistente != null) {
           throw new BadRequestException("Login Existe"); 
        }
        
        this.loginRepository.salvar(login);
    }

    @Override
    public void alterar(String email, AlteracaoSenhaDto alteracaoSenhaDto) throws SQLException {
        Login login = this.loginRepository.obter(email);
        Senha senhaAtual = new Senha(alteracaoSenhaDto.getSenhaAtual());
        Senha novaSenha = new Senha(alteracaoSenhaDto.getNovaSenha());
        Senha confirmacaoSenha = new Senha(alteracaoSenhaDto.getConfirmacaoSenha());

        if (!login.confirmarSenhaAtual(senhaAtual))
            throw new BadRequestException();

        if (!novaSenha.equals(confirmacaoSenha))
            throw new BadRequestException();

        login.trocarSenha(novaSenha);

        this.loginRepository.atualizar(login);
    }

    @Override
    public void autorizar(String email, AutorizacaoDto autorizacaoDto) throws PerfilNaoAssociadoException,
            SQLException {
        Login login = this.loginRepository.obter(email);
        Perfil perfil = this.loginRepository.obterPerfil(email);

        login.associarPerfil(perfil);
        login.autorizar(new Senha(autorizacaoDto.getSenha()));

        if(!login.getAutorizado())
            throw new BadRequestException();
        
        this.loginRepository.atualizar(login);
    }

    @Override
    public void logout(String email) throws SQLException {
        Login login = this.loginRepository.obter(email);
        login.logout();

        this.loginRepository.atualizar(login);

    }

    @Override
    public void desabilitarConta(String email) throws SQLException {
        Login login = this.loginRepository.obter(email);
        login.desabilitarConta();

        this.loginRepository.atualizar(login);

    }
    
}
