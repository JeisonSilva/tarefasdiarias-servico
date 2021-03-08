package org.jsonapp.gestaologin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jsonapp.gestaologin.entidades.Login;
import org.jsonapp.gestaologin.entidades.Perfil;
import org.jsonapp.gestaologin.exceptions.PerfilNaoAssociadoException;
import org.jsonapp.gestaologin.objetosvalor.Email;
import org.jsonapp.gestaologin.objetosvalor.Senha;
import org.jsonapp.gestaotarefas.entidades.Professor;
import org.jsonapp.gestaotarefas.objetosvalor.Nome;
import org.jsonapp.gestaotarefas.objetosvalor.ProfessorId;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class LoginTest {
    
    @Test
    public void devePossuirUmEmailESenha(){
        Email email = new Email("professor@professor.com");
        Senha senha = new Senha("123456789");

        Login login = new Login(email, senha);

        assertEquals(email, login.getEmail());
        assertEquals(senha, login.getSenha());
    }

    @Test
    public void deveAutorizarUmaSenhaValida() throws PerfilNaoAssociadoException {
       Login login = criarLogin();
       Senha senha = new Senha("123456789");

       login.autorizar(senha);
       
       assertTrue(login.getAutorizado());
    }

    @Test
    public void naoDeveAutorizarUmaSenhaInvalida() throws PerfilNaoAssociadoException {
        Login login = criarLogin();
        Senha senha = new Senha("TESTE");

        login.autorizar(senha);
       
        assertFalse(login.getAutorizado());
    }

    @Test
    public void deveAssociarPerfilProfessor(){
        Email email = new Email("professor@professor.com");
        Senha senha = new Senha("123456789");
        Perfil perfil = new Professor(new ProfessorId(email.toString()), new Nome("Teste"));
        Login login = new Login(email, senha);

        login.associarPerfil(perfil);

        assertEquals(perfil, login.getPerfil());
    }

    @Test
    public void loginSemPerfilNaoPodeAutorizarAcesso(){
        Email email = new Email("professor@professor.com");
        Senha senha = new Senha("123456789");
        Login login = new Login(email, senha);

        assertThrows(PerfilNaoAssociadoException.class, ()->login.autorizar(senha));

        
    }

    private Login criarLogin() {
        Email email = new Email("professor@professor.com");
        Senha senha = new Senha("123456789");
        Login login = new Login(email, senha);
        login.associarPerfil(new Professor(new ProfessorId(email.toString()), new Nome("Teste")));

        return login;
    }
}
