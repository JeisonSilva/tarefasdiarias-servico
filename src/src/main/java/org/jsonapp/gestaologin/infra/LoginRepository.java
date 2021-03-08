package org.jsonapp.gestaologin.infra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;

import org.jsonapp.gestaologin.ILoginRepository;
import org.jsonapp.gestaologin.entidades.Aluno;
import org.jsonapp.gestaologin.entidades.Login;
import org.jsonapp.gestaologin.entidades.Perfil;
import org.jsonapp.gestaologin.objetosvalor.Email;
import org.jsonapp.gestaologin.objetosvalor.Senha;
import org.jsonapp.gestaotarefas.entidades.Professor;
import org.jsonapp.gestaotarefas.objetosvalor.AlunoId;
import org.jsonapp.gestaotarefas.objetosvalor.Nome;
import org.jsonapp.gestaotarefas.objetosvalor.ProfessorId;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.AgroalDataSource.FlushMode;

@ApplicationScoped
public class LoginRepository implements ILoginRepository {

    final AgroalDataSource agroalDataSource;

    public LoginRepository(AgroalDataSource agroalDataSource) {
        super();

        this.agroalDataSource = agroalDataSource;
    }

    @Override
    public void salvar(Login login) throws SQLException {

        try {
            PreparedStatement preparedStatement = criarInsertLogin(login, this.agroalDataSource.getConnection());
            preparedStatement.execute();
            finalize();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Login obter(String email) throws SQLException {
        Login login = null;

        try {
            PreparedStatement preparedStatement = criarQueryObterLoginPorEmail(email,
                    this.agroalDataSource.getConnection());
            login = gerarLogin(preparedStatement);
            finalize();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return login;
    }

    @Override
    public void atualizar(Login login) throws SQLException {
        try {
            PreparedStatement preparedStatement = atualizarLogin(login, this.agroalDataSource.getConnection());
            preparedStatement.execute();
            finalize();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public Perfil obterPerfil(String email) throws SQLException {
        Perfil perfil = null;
        try {
            PreparedStatement preparedStatement = criarQueryObterPerfilPorEmail(email,
                this.agroalDataSource.getConnection());
            perfil = criarPerfil(preparedStatement);
            finalize();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return perfil;
    }

    private PreparedStatement criarQueryObterPerfilPorEmail(String email, Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT a.email as emailAluno, a.nome as nomeAluno, p.email as emailProfessor, p.nome as nomeProfessor ");
        stringBuilder.append("FROM logins l ");
        stringBuilder.append("LEFT JOIN alunos a ON l.email = a.email ");
        stringBuilder.append("LEFT JOIN professores p ON l.email = p.email ");        
        stringBuilder.append("WHERE l.email = ?");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, email);
        return preparedStatement;
    }

    private Perfil criarPerfil(PreparedStatement preparedStatement) throws SQLException {
        ResultSet dados = preparedStatement.executeQuery();
        while (dados.next()) {
            String emailProfessor = dados.getString("emailProfessor");
            String emailAluno = dados.getString("emailAluno");

            if(emailAluno != null && !emailAluno.isEmpty())
                return criarPerfilAluno(dados);

            if(emailProfessor != null && !emailProfessor.isEmpty())
                return criarPerfilProfessor(dados);

        }
        return null;
    }

    private Perfil criarPerfilAluno(ResultSet dados) throws SQLException {
        String email = dados.getString("emailAluno");
        String nome = dados.getString("nomeAluno");
        return new Aluno(new AlunoId(email), new Nome(nome));
    }

    private Perfil criarPerfilProfessor(ResultSet dados) throws SQLException {
        String email = dados.getString("emailProfessor");
        String nome = dados.getString("nomeProfessor");
        return new Professor(new ProfessorId(email), new Nome(nome));
    }

    private PreparedStatement atualizarLogin(Login login, Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE logins SET senha=?, autorizado=?, habilitado=? WHERE email=?;");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, login.getSenha().toString());
        preparedStatement.setBoolean(2, login.getAutorizado());
        preparedStatement.setBoolean(3, login.getHabilitado());
        preparedStatement.setString(4, login.getEmail().toString());

        return preparedStatement;
    }

    private PreparedStatement criarInsertLogin(Login login, Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO logins(email, senha) VALUES(?, ?);");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, login.getEmail().toString());
        preparedStatement.setString(2, login.getSenha().toString());

        return preparedStatement;
    }

    private PreparedStatement criarQueryObterLoginPorEmail(String email, Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT email, senha, autorizado, habilitado FROM logins WHERE email= ?");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, email);
        return preparedStatement;
    }

    private Login gerarLogin(PreparedStatement preparedStatement) throws SQLException {
        ResultSet dados = preparedStatement.executeQuery();
        while (dados.next()) {
            Email email = new Email(dados.getString("email"));
            Senha senha = new Senha(dados.getString("senha"));
            boolean autorizado = dados.getBoolean("autorizado");
            boolean habilitado = dados.getBoolean("habilitado");

            Login login = new Login(email, senha, autorizado, habilitado);
            return login;
        }

        return null;
    }

    @Override
    protected void finalize() throws Throwable {
        this.agroalDataSource.flush(FlushMode.ALL);
    }

}
