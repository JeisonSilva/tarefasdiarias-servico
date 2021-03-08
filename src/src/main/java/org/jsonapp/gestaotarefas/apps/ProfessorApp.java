package org.jsonapp.gestaotarefas.apps;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.jsonapp.gestaotarefas.IAlunoRepository;
import org.jsonapp.gestaotarefas.IDisciplinaRepository;
import org.jsonapp.gestaotarefas.IProfessorApp;
import org.jsonapp.gestaotarefas.IProfessorRepository;
import org.jsonapp.gestaotarefas.ITarefaRepository;
import org.jsonapp.gestaotarefas.TarefaDto;
import org.jsonapp.gestaotarefas.entidades.Professor;
import org.jsonapp.gestaotarefas.entidades.Tarefa;
import org.jsonapp.gestaotarefas.exceptions.*;
import org.jsonapp.gestaotarefas.objetosvalor.AlunoId;
import org.jsonapp.gestaotarefas.objetosvalor.DisciplinaId;
import org.jsonapp.gestaotarefas.objetosvalor.Titulo;

@ApplicationScoped
public class ProfessorApp implements IProfessorApp {

    final ITarefaRepository tarefaRepository;
    final IProfessorRepository professorRepository;
    final IAlunoRepository alunoRepository;
    final IDisciplinaRepository disciplinaRepository;

    public ProfessorApp(ITarefaRepository tarefaRepository, IProfessorRepository professorRepository,
            IAlunoRepository alunoRepository, IDisciplinaRepository disciplinaRepository) {
        super();

        this.tarefaRepository = tarefaRepository;
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    @Override
    public void criarNovatarefa(TarefaDto tarefa) throws Exception {

        try {
            criarNovaTarefa(tarefa);
        } catch (TituloNuloException e) {
            e.printStackTrace();
            throw new BadRequestException("Título inválido");
        } catch (AlunoNuloException e) {
            e.printStackTrace();
            throw new BadRequestException("Aluno inválido");
        } catch (DisciplinaNuloException e) {
            e.printStackTrace();
            throw new BadRequestException("Disciplina inválida");
        }
    }

    private void criarNovaTarefa(TarefaDto tarefa)
            throws TituloNuloException, AlunoNuloException, DisciplinaNuloException, Exception {
        Professor professor = this.professorRepository.obter(tarefa.getEmailProfessor());
        AlunoId aluno = this.alunoRepository.obterAlunoId(tarefa.getEmailAluno());
        DisciplinaId disciplina = this.disciplinaRepository.obterDisciplinaId(tarefa.getDisciplina());
        Titulo titulo = new Titulo(tarefa.getTitulo());
        double pontuacao = tarefa.getPontuacao();

        Tarefa novaTarefa = professor.criarNovaTarefa(titulo, aluno, disciplina, pontuacao);
        this.tarefaRepository.salvar(novaTarefa);
    }

    @Override
    public void atualizar(int id, TarefaDto tarefa) throws TarefaDisponibilizadaException, Exception {
        Professor professor = this.professorRepository.obter(tarefa.getEmailProfessor());
        Tarefa tarefaRegistrada = this.tarefaRepository.obter(id);
        AlunoId aluno = this.alunoRepository.obterAlunoId(tarefa.getEmailAluno());
        DisciplinaId disciplina = this.disciplinaRepository.obterDisciplinaId(tarefa.getDisciplina());
        double pontuacao = tarefa.getPontuacao();

        professor.alterarAluno(aluno, tarefaRegistrada);
        professor.alterarDisciplina(disciplina, tarefaRegistrada);
        professor.alterarPontuacao(pontuacao, tarefaRegistrada);
        professor.alterarTitulo(new Titulo(tarefa.getTitulo()),tarefaRegistrada);

        this.tarefaRepository.atualizar(tarefaRegistrada);
    }

    @Override
    public void excluir(int id) throws Exception {
        Tarefa tarefa = this.tarefaRepository.obter(id);
        
        if(tarefa == null)
            throw new NotFoundException();
        
        Professor professor = this.professorRepository.obter(tarefa.getProfessor());
        
        professor.excluir(tarefa);

        this.tarefaRepository.atualizar(tarefa);
    }

    @Override
    public void finalizarTarefa(int id) throws Exception {
        Tarefa tarefa = this.tarefaRepository.obter(id);

        if(tarefa == null)
            throw new NotFoundException();

        tarefa.disponibilizarTarefa();

        this.tarefaRepository.atualizar(tarefa);

    }

    @Override
    public void entregarTarefa(int id) throws Exception {
        Tarefa tarefa = this.tarefaRepository.obter(id);

        if(tarefa == null)
            throw new NotFoundException();

        tarefa.entregar();

        this.tarefaRepository.atualizar(tarefa);

    }
    
}
