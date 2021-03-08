package org.jsonapp.gestaoadministrativa.infra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.jsonapp.gestaoadministrativa.IDisciplinaRepository;
import org.jsonapp.gestaoadministrativa.ListaDeDisciplinasDisponiveisDto;
import org.jsonapp.gestaoadministrativa.entidades.Disciplina;
import org.jsonapp.gestaoadministrativa.objetosvalor.DisciplinaId;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.AgroalDataSource.FlushMode;

@ApplicationScoped
public class DisciplinaRepository implements IDisciplinaRepository {

    final AgroalDataSource agroalDataSource;

    public DisciplinaRepository(AgroalDataSource agroalDataSource) {
        super();

        this.agroalDataSource = agroalDataSource;
    }

    @Override
    public void salvar(Disciplina disciplina) throws SQLException {
        PreparedStatement preparedStatement = salvarDisciplina(disciplina, this.agroalDataSource.getConnection());
        preparedStatement.execute();

    }

    @Override
    public Disciplina obter(DisciplinaId disciplinaId) throws SQLException {
        PreparedStatement preparedStatement = criarQueryParaRetornarDisciplinaPorId(disciplinaId, this.agroalDataSource.getConnection());
        return obterDisciplina(preparedStatement);
    }

    
    @Override
    public void atualizar(Disciplina disciplina) throws SQLException {
        PreparedStatement preparedStatement = atualizarDisciplina(disciplina, this.agroalDataSource.getConnection());
        preparedStatement.execute();

    }

    @Override
    public List<ListaDeDisciplinasDisponiveisDto> obter() throws SQLException {
        PreparedStatement preparedStatement = criarQueryParaRetornarTodasDisciplinas(this.agroalDataSource.getConnection());
        
        return estanciarTodasDisciplinas(preparedStatement);
    }

    private Disciplina obterDisciplina(PreparedStatement preparedStatement) throws SQLException {
        ResultSet dados = preparedStatement.executeQuery();
        while (dados.next()){
            boolean desabilitado = dados.getBoolean("desabilitado");
            Disciplina disciplina = new Disciplina(dados.getInt("id"), dados.getString("descricao"));
        
            if(desabilitado)
                disciplina.desabilitar();

            return disciplina;
        }

        return null;
    }

    private PreparedStatement atualizarDisciplina(Disciplina disciplina, Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE disciplinas SET descricao=?, desabilitado=? WHERE id=?;");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, disciplina.getDescricao());
        preparedStatement.setBoolean(2, disciplina.getDesabilitado());
        preparedStatement.setInt(3, disciplina.getId());

        return preparedStatement;
    }


    private PreparedStatement criarQueryParaRetornarTodasDisciplinas(Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select id, descricao, desabilitado from disciplinas WHERE desabilitado=0");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        return preparedStatement;
    }

    private List<ListaDeDisciplinasDisponiveisDto> estanciarTodasDisciplinas(PreparedStatement preparedStatement)
            throws SQLException {
        ResultSet dados = preparedStatement.executeQuery();
        List<ListaDeDisciplinasDisponiveisDto> disciplinasDisponiveisDto = new ArrayList<>(dados.getRow());

        while (dados.next()) {
            int id = dados.getInt("id");
            String descricao = dados.getString("descricao");
            ListaDeDisciplinasDisponiveisDto disciplinaDisponivel = new ListaDeDisciplinasDisponiveisDto(id, descricao);
            disciplinasDisponiveisDto.add(disciplinaDisponivel);
        }
        return disciplinasDisponiveisDto;
    }

    private PreparedStatement salvarDisciplina(Disciplina disciplina, Connection connection) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO disciplinas(descricao) VALUES(?);");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, disciplina.getDescricao());

        return preparedStatement;
    }

    private PreparedStatement criarQueryParaRetornarDisciplinaPorId(DisciplinaId disciplinaId, Connection connection)
            throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select id, descricao, desabilitado from disciplinas WHERE id=?");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setInt(1, Integer.valueOf(disciplinaId.toString()));
        
        return preparedStatement;
    }

    @Override
    protected void finalize() throws Throwable {
        this.agroalDataSource.flush(FlushMode.ALL);
    }
    
}
