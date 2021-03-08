package org.jsonapp.gestaotarefas;

import static org.junit.jupiter.api.Assertions.*;

import org.jsonapp.gestaotarefas.entidades.*;
import org.jsonapp.gestaotarefas.exceptions.*;
import org.jsonapp.gestaotarefas.objetosvalor.*;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ProfessorTest {

    public ProfessorTest() {
        super();

    }

    @Test
    public void professorCriarUmaTarefa() throws TituloNuloException, AlunoNuloException, DisciplinaNuloException {
        Professor professor = new Professor(new ProfessorId("professor@professor.com"), new Nome("Professor"));
        Tarefa tarefa = professor.criarNovaTarefa(new Titulo("Ola mundo"), new AlunoId("aluno@aluno.com"), new DisciplinaId(1), 9.8d);
        assertNotNull(tarefa);
    }

    @Test
    public void professorAlterarTituloDaTarefa()
            throws TituloNuloException, AlunoNuloException, DisciplinaNuloException, TarefaDisponibilizadaException {
        Professor professor = new Professor(new ProfessorId("professor@professor.com"), new Nome("Professor"));
        Tarefa tarefa = new Tarefa(new Titulo("Ola mundo"), new AlunoId("aluno@aluno.com"), new DisciplinaId(1), 9.8d);
        Titulo novoTitulo = new Titulo("Novo titulo");

        professor.alterarTitulo(novoTitulo, tarefa);

        assertEquals(novoTitulo, tarefa.getTitulo());
    }

    @Test
    public void professorAlteraDisciplinaDaTarefa()
            throws TituloNuloException, AlunoNuloException, DisciplinaNuloException, TarefaDisponibilizadaException {
        Professor professor = new Professor(new ProfessorId("professor@professor.com"), new Nome("Professor"));
        Tarefa tarefa = new Tarefa(new Titulo("Ola mundo"), new AlunoId("aluno@aluno.com"), new DisciplinaId(1), 9.8d);
        DisciplinaId novaDisciplina = new DisciplinaId(2);

        professor.alterarDisciplina(novaDisciplina, tarefa);

        assertEquals(novaDisciplina, tarefa.getDisciplina());
    }

    @Test
    public void professorAlteraAlunoDaTarefa() throws TarefaDisponibilizadaException, TituloNuloException,
            AlunoNuloException, DisciplinaNuloException {
        Professor professor = new Professor(new ProfessorId("professor@professor.com"), new Nome("Professor"));
        Tarefa tarefa = new Tarefa(new Titulo("Ola mundo"), new AlunoId("aluno@aluno.com"), new DisciplinaId(1), 9.8d);
        AlunoId novoAluno = new AlunoId("novoAluno@aluno.com");

        professor.alterarAluno(novoAluno, tarefa);

        assertEquals(novoAluno, tarefa.getAluno());
    }

    @Test
    public void professorAlteraPontuacaoDaTarefa()
            throws TituloNuloException, AlunoNuloException, DisciplinaNuloException, TarefaDisponibilizadaException {
        Professor professor = new Professor(new ProfessorId("professor@professor.com"), new Nome("Professor"));
        Tarefa tarefa = new Tarefa(new Titulo("Ola mundo"), new AlunoId("aluno@aluno.com"), new DisciplinaId(1), 9.8d);
        double pontuacao = 6.0d;

        professor.alterarPontuacao(pontuacao, tarefa);

        assertEquals(pontuacao, tarefa.getPontuacao());
    }

    @Test
    public void naoDeveAceitarTarefaSemTitulo(){
        Professor professor = new Professor(new ProfessorId("professor@professor.com"), new Nome("Professor"));
        assertThrows(TituloNuloException.class, ()-> professor.criarNovaTarefa(null, new AlunoId("aluno@aluno.com"), new DisciplinaId(1), 9.8d));    
    }

    @Test
    public void naoDeveAceitarTarefaSemAluno(){
        Professor professor = new Professor(new ProfessorId("professor@professor.com"), new Nome("Professor"));
        assertThrows(AlunoNuloException.class, ()-> professor.criarNovaTarefa(new Titulo("Ola mundo"), null, new DisciplinaId(1), 9.8d));    
    }

    @Test
    public void naoDeveAceitarTarefaSemDisciplina(){
        Professor professor = new Professor(new ProfessorId("professor@professor.com"), new Nome("Professor"));
        assertThrows(DisciplinaNuloException.class, ()-> professor.criarNovaTarefa(new Titulo("Ola mundo"), new AlunoId("aluno@aluno.com"), null, 9.8d));    
    }

    @Test
    public void professorDisponibilizaTarefaParaAluno()
            throws TituloNuloException, AlunoNuloException, DisciplinaNuloException {
        Professor professor = new Professor(new ProfessorId("professor@professor.com"), new Nome("Professor"));
        Tarefa tarefa = new Tarefa(new Titulo("Ola mundo"), new AlunoId("aluno@aluno.com"), new DisciplinaId(1), 9.8d);

        professor.disponibilizarTarefaParaAluno(tarefa);

        assertTrue(tarefa.getDisponibilizadoParaAluno());
    }

    @Test
    public void professorNaoAlteraTituloDaTarefaDisponibilizadaParaAluno()
            throws TituloNuloException, AlunoNuloException, DisciplinaNuloException {
        Professor professor = new Professor(new ProfessorId("professor@professor.com"), new Nome("Professor"));
        Tarefa tarefa = new Tarefa(new Titulo("Ola mundo"), new AlunoId("aluno@aluno.com"), new DisciplinaId(1), 9.8d);
        Titulo novoTitulo = new Titulo("Novo titulo");

        tarefa.disponibilizarTarefa();
        assertThrows(TarefaDisponibilizadaException.class, ()-> professor.alterarTitulo(novoTitulo, tarefa));

    }

    @Test
    public void professorNaoAlteraDisciplinaDaTarefaDisponibilizadaParaAluno()
            throws TituloNuloException, AlunoNuloException, DisciplinaNuloException {
        Professor professor = new Professor(new ProfessorId("professor@professor.com"), new Nome("Professor"));
        Tarefa tarefa = new Tarefa(new Titulo("Ola mundo"), new AlunoId("aluno@aluno.com"), new DisciplinaId(1), 9.8d);
        DisciplinaId novaDisciplina = new DisciplinaId(2);

        tarefa.disponibilizarTarefa();
        assertThrows(TarefaDisponibilizadaException.class, ()->professor.alterarDisciplina(novaDisciplina, tarefa));
        
    }

    @Test
    public void professorNaoAlteraAlunoComTarefaDisponibilizada()
            throws TituloNuloException, AlunoNuloException, DisciplinaNuloException {
        Professor professor = new Professor(new ProfessorId("professor@professor.com"), new Nome("Professor"));
        Tarefa tarefa = new Tarefa(new Titulo("Ola mundo"), new AlunoId("aluno@aluno.com"), new DisciplinaId(1), 9.8d);
        AlunoId novoAluno = new AlunoId("novoAluno@aluno.com");

        tarefa.disponibilizarTarefa();
        assertThrows(TarefaDisponibilizadaException.class, ()-> professor.alterarAluno(novoAluno, tarefa));
        
    }

    @Test
    public void professorNaoAlteraPontuacaoDaTarefaDisponibilizadaParaAluno()
            throws TituloNuloException, AlunoNuloException, DisciplinaNuloException {
        Professor professor = new Professor(new ProfessorId("professor@professor.com"), new Nome("Professor"));
        Tarefa tarefa = new Tarefa(new Titulo("Ola mundo"), new AlunoId("aluno@aluno.com"), new DisciplinaId(1), 9.8d);
        double pontuacao = 6.0d;

        tarefa.disponibilizarTarefa();
        assertThrows(TarefaDisponibilizadaException.class, ()-> professor.alterarPontuacao(pontuacao, tarefa));

    }

    
}
