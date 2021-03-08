package org.jsonapp.gestaologin.infra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;

import org.jsonapp.gestaologin.IPerfilRepository;
import org.jsonapp.gestaologin.PerfilDto;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.AgroalDataSource.FlushMode;

@ApplicationScoped
public class PerfilRepository implements IPerfilRepository {

    final AgroalDataSource agroalDataSource;

    public PerfilRepository(AgroalDataSource agroalDataSource) {
        super();

        this.agroalDataSource = agroalDataSource;
    }

    @Override
    public PerfilDto obter(String email) throws SQLException {
        PerfilDto perfil = null;
        try {
            PreparedStatement preparedStatement = criarQueryObterPerfilPorEmail(email,
                this.agroalDataSource.getConnection());

            perfil = selecionarPerfil(preparedStatement);
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
        stringBuilder.append("WHERE l.email = ? and l.habilitado = 1 and l.autorizado=1");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, email);
        return preparedStatement;
    }

    private PerfilDto selecionarPerfil(PreparedStatement preparedStatement) throws SQLException {
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

    private PerfilDto criarPerfilAluno(ResultSet dados) throws SQLException {
        String email = dados.getString("emailAluno");
        String nome = dados.getString("nomeAluno");
        return new PerfilDto(email, nome);
    }

    private PerfilDto criarPerfilProfessor(ResultSet dados) throws SQLException {
        String email = dados.getString("emailProfessor");
        String nome = dados.getString("nomeProfessor");
        return new PerfilDto(email, nome);
    }

    @Override
    protected void finalize() throws Throwable {
        this.agroalDataSource.flush(FlushMode.ALL);
    }
    
}
