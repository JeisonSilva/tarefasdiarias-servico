package org.jsonapp.gestaologin.entidades;

import org.jsonapp.gestaologin.exceptions.PerfilNaoAssociadoException;
import org.jsonapp.gestaologin.objetosvalor.Email;
import org.jsonapp.gestaologin.objetosvalor.Senha;

public class Login {

	private Email email;
    private Senha senha;
    private boolean autorizado;
    private boolean habilitado;
    private Perfil perfil;

    public Login(Email email, Senha senha) {
        this.email = email;
        this.senha = senha;
    }
    
    public Login(Email email, Senha senha, boolean autorizado, boolean habilitado) {
        this.email = email;
        this.senha = senha;
        this.autorizado = autorizado;
        this.habilitado = habilitado;
	}

	public Email getEmail() {
        return email;
    }

    public Senha getSenha() {
        return senha;
    }
    
    public boolean getAutorizado() {
        return this.autorizado;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public boolean getHabilitado(){
        return this.habilitado;
    }

	public void autorizar(Senha senha) throws PerfilNaoAssociadoException {
        if(this.getPerfil() ==  null)
            throw new PerfilNaoAssociadoException();

        if(this.senha.equals(senha))
            this.autorizado = true;
        else
            this.autorizado = false;
	}

	public void associarPerfil(Perfil perfil) {
        this.perfil = perfil;
	}

	public boolean confirmarSenhaAtual(Senha senhaAtual) {
        return this.senha.equals(senhaAtual);
	}

	public void trocarSenha(Senha novaSenha) {
        this.senha = novaSenha;
	}

	public void logout() {
        this.autorizado = false;
	}

	public void desabilitarConta() {
        this.habilitado = false;
	}
    
}
