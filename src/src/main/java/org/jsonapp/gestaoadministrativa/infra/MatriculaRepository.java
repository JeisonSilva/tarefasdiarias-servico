package org.jsonapp.gestaoadministrativa.infra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;

import org.jsonapp.gestaoadministrativa.IMatriculaRepository;
import org.jsonapp.gestaoadministrativa.entidades.Matricula;
import org.jsonapp.gestaoadministrativa.objetosvalor.AlunoId;
import org.jsonapp.gestaoadministrativa.objetosvalor.MatriculaId;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.AgroalDataSource.FlushMode;

@ApplicationScoped
public class MatriculaRepository implements IMatriculaRepository {

    final AgroalDataSource agroalDataSource;

    public MatriculaRepository(AgroalDataSource agroalDataSource) {
        super();

        this.agroalDataSource = agroalDataSource;
    }

    @Override
    public Matricula obterMatriculaPorIdAluno(AlunoId alunoId) throws SQLException {
        Matricula matricula = null;

        try {
            PreparedStatement preparedStatement = criarQueryObterMatriculaPorAluno(alunoId,
                    this.agroalDataSource.getConnection());
            matricula = selectionarAluno(preparedStatement);
            finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return matricula;

    }

    @Override
    public void salvar(Matricula matricula) throws SQLException {

        try {
            PreparedStatement preparedStatement = salvarMatricula(matricula, this.agroalDataSource.getConnection());
            preparedStatement.execute();
            finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(Matricula matricula) throws SQLException {
        
        try {
            PreparedStatement preparedStatement = deletarMatricula(matricula, this.agroalDataSource.getConnection());
            preparedStatement.execute();
            finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    private PreparedStatement deletarMatricula(Matricula matricula, Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM matriculas WHERE id=?");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setInt(1, matricula.getId());
        
        return preparedStatement;
    }

    private PreparedStatement salvarMatricula(Matricula matricula, Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO matriculas(classes_codigo, alunos_email) VALUES(?, ?)");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setInt(1, matricula.getClasse());
        preparedStatement.setString(2, matricula.getAluno());

        return preparedStatement;
    }

    private PreparedStatement criarQueryObterMatriculaPorAluno(AlunoId alunoId, Connection connection)
            throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT m.id, m.alunos_email ");
        stringBuilder.append("FROM alunos a ");
        stringBuilder.append("INNER JOIN matriculas m ON a.email = m.alunos_email ");
        stringBuilder.append("WHERE a.email = ?");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, alunoId.toString());

        return preparedStatement;
    }

    private Matricula selectionarAluno(PreparedStatement preparedStatement) throws SQLException {
        ResultSet dados = preparedStatement.executeQuery();
        while (dados.next()) {
            Matricula matricula = new Matricula(
                new MatriculaId(dados.getInt("id")), 
                new AlunoId(dados.getString("alunos_email")));

            return matricula;
        }
        return null;
    }

    @Override
    protected void finalize() throws Throwable {
        this.agroalDataSource.flush(FlushMode.ALL);
    }
    
}
