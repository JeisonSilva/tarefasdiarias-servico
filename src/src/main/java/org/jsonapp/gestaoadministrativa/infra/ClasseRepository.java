package org.jsonapp.gestaoadministrativa.infra;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;

import org.jsonapp.gestaoadministrativa.IClasseRepository;
import org.jsonapp.gestaoadministrativa.entidades.Classe;
import org.jsonapp.gestaoadministrativa.objetosvalor.ProfessorId;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.AgroalDataSource.FlushMode;

@ApplicationScoped
public class ClasseRepository implements IClasseRepository {

    final AgroalDataSource agroalDataSource;

    public ClasseRepository(AgroalDataSource agroalDataSource) {
        super();

        this.agroalDataSource = agroalDataSource;
    }

    @Override
    public Classe obterPorEmailProfessor(ProfessorId id) throws SQLException {
        PreparedStatement preparedStatement = criarQueryParaSelecionarClassePorProfessor(id,
                this.agroalDataSource.getConnection());
            
        return selecionarClasse(preparedStatement);
    }

    @Override
    public void salvar(Classe novaClasse) throws SQLException {
        PreparedStatement preparedStatement = insertNovaClasse(novaClasse, this.agroalDataSource.getConnection());
        preparedStatement.execute();
    }

    @Override
    public void atualizar(Classe classe) throws SQLException {
        PreparedStatement preparedStatement = atualizarClasse(classe, this.agroalDataSource.getConnection());
        preparedStatement.execute();
    }

    private PreparedStatement atualizarClasse(Classe classe, Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE classes SET professores_email=? WHERE codigo=?;");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, classe.getProfessorId());
        preparedStatement.setInt(2, classe.getCodigo());

        return preparedStatement;
    }

    private PreparedStatement criarQueryParaSelecionarClassePorProfessor(ProfessorId id, Connection connection)
            throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT c.codigo, c.professores_email FROM classes c WHERE c.professores_email = ?");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, id.toString());
        return preparedStatement;
    }

    private Classe selecionarClasse(PreparedStatement preparedStatement) throws SQLException {
        ResultSet dados = preparedStatement.executeQuery();
        while (dados.next()) {
            Classe classe = new Classe(dados.getInt("codigo"), new ProfessorId(dados.getString("professores_email")));
            return classe;
        }
        return null;
    }

    private PreparedStatement insertNovaClasse(Classe novaClasse, Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO classes(codigo, data, professores_email) VALUES(?, ?, ?);");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setInt(1, novaClasse.getCodigo());
        preparedStatement.setDate(2, Date.valueOf(novaClasse.getData()));
        preparedStatement.setString(3, novaClasse.getProfessorId());

        return preparedStatement;
    }

    @Override
    protected void finalize() throws Throwable {
        this.agroalDataSource.flush(FlushMode.ALL);
    }

}
