package org.jsonapp.gestaoadministrativa.apps;

import java.sql.SQLException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;

import org.jsonapp.gestaoadministrativa.DisciplinaDto;
import org.jsonapp.gestaoadministrativa.IDisciplinaApp;
import org.jsonapp.gestaoadministrativa.IDisciplinaRepository;
import org.jsonapp.gestaoadministrativa.ListaDeDisciplinasDisponiveisDto;
import org.jsonapp.gestaoadministrativa.entidades.Disciplina;
import org.jsonapp.gestaoadministrativa.objetosvalor.DisciplinaId;

@ApplicationScoped
public class DisciplinaApp implements IDisciplinaApp {

    final IDisciplinaRepository disciplinaRepository;

    public DisciplinaApp(IDisciplinaRepository disciplinaRepository) {
        super();

        this.disciplinaRepository = disciplinaRepository;
    }

    @Override
    public void criar(DisciplinaDto disciplinaDto) throws SQLException {
        Disciplina disciplina = new Disciplina(disciplinaDto.getDescricao());
        this.disciplinaRepository.salvar(disciplina);

    }

    @Override
    public void atualizar(int id, DisciplinaDto disciplinaDto) throws SQLException {
        Disciplina disciplina = obterDisciplinaPorId(id);

        disciplina.alterarDescricao(disciplinaDto.getDescricao());

        this.disciplinaRepository.atualizar(disciplina);

    }

    @Override
    public void deletar(int id) throws SQLException {
        Disciplina disciplina = obterDisciplinaPorId(id);

        disciplina.desabilitar();

        this.disciplinaRepository.atualizar(disciplina);
    }

    @Override
    public List<ListaDeDisciplinasDisponiveisDto> obterTodos() throws SQLException {
        List<ListaDeDisciplinasDisponiveisDto> disciplinas = this.disciplinaRepository.obter();
        return disciplinas;
    }

    private Disciplina obterDisciplinaPorId(int id) throws SQLException {
        Disciplina disciplina = this.disciplinaRepository.obter(new DisciplinaId(id));

        if(disciplina == null)
            throw new NotFoundException();
        return disciplina;
    }
    
}
