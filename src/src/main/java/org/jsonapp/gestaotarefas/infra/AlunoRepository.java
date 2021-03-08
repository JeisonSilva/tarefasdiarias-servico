package org.jsonapp.gestaotarefas.infra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;

import org.jsonapp.gestaologin.entidades.Aluno;
import org.jsonapp.gestaotarefas.IAlunoRepository;
import org.jsonapp.gestaotarefas.objetosvalor.AlunoId;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.AgroalDataSource.FlushMode;

@ApplicationScoped
public class AlunoRepository implements IAlunoRepository {

    private AgroalDataSource agroalDataSource;

    public AlunoRepository(AgroalDataSource agroalDataSource) {
        super();

        this.agroalDataSource = agroalDataSource;

    }

    @Override
    public AlunoId obterAlunoId(String emailAluno) throws Exception {
        try {
            PreparedStatement preparedStatement = criarQueryAlunoPorEmail(emailAluno, obterConexao());
            AlunoId alunoId = selectionarAluno(preparedStatement);
            finalize();
            return alunoId;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    private Connection obterConexao() throws SQLException {
        if (agroalDataSource.getConnection().isClosed()) {
            return agroalDataSource.createConnectionBuilder().build();
        } else {
            return agroalDataSource.getConnection();
        }
    }

    @Override
    public void salvar(Aluno novoAluno) throws SQLException {
        
        try {
            PreparedStatement preparedStatement = criarInsertAluno(novoAluno, obterConexao());
            preparedStatement.execute();
            finalize();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    
    @Override
    protected void finalize() throws Throwable {
        this.agroalDataSource.flush(FlushMode.ALL);
    }

    private PreparedStatement criarQueryAlunoPorEmail(String emailAluno, Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT a.email as email ");
        stringBuilder.append("FROM alunos a ");
        stringBuilder.append("WHERE a.email = ?");

        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, emailAluno);
        return preparedStatement;
    }

    private AlunoId selectionarAluno(PreparedStatement preparedStatement) throws SQLException {
        ResultSet dados = preparedStatement.executeQuery();
        AlunoId id = null;
        
        while (dados.next()) {
            id = new AlunoId(dados.getString("email"));
            return id;
        }

        dados.close();
        return id;
    }

    private PreparedStatement criarInsertAluno(Aluno novoAluno, Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO alunos(email, nome) VALUES(?, ?)");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, novoAluno.getEmail());
        preparedStatement.setString(2, novoAluno.getNome());
        
        return preparedStatement;
    }

    
}
