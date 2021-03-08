package org.jsonapp.gestaotarefas.apps;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;

import org.jsonapp.gestaotarefas.IConsultaTarefaRepository;
import org.jsonapp.gestaotarefas.IConsultasTarefaApp;
import org.jsonapp.gestaotarefas.TarefaConsultadaDto;
import org.jsonapp.gestaotarefas.TarefaEdicaoDto;

@ApplicationScoped
public class ConsultasTarefaApp implements IConsultasTarefaApp {
    final IConsultaTarefaRepository consultaTarefaRepository;

    public ConsultasTarefaApp(IConsultaTarefaRepository consultaTarefaRepository) {
        super();

        this.consultaTarefaRepository = consultaTarefaRepository;
        
    }

    @Override
    public TarefaEdicaoDto obterTarefaParaEdicao(int id) throws Exception {
        TarefaEdicaoDto tarefa = this.consultaTarefaRepository.obterTarefaParaEdicao(id);

        if(tarefa == null)
            throw new NotFoundException();
        
        return tarefa;
    }

    @Override
    public List<TarefaConsultadaDto> obterTarefasNaoEnviadasParaAluno(String email) throws Exception {
        List<TarefaConsultadaDto> tarefas = this.consultaTarefaRepository.obterTarefasNaoEnviadasParaAluno(email);
        
        if(tarefas == null)
            throw new NotFoundException();
        
            return tarefas;
    }

    @Override
    public Object obterTarefasDisponibilizadas() throws Exception {
        List<TarefaConsultadaDto> tarefas = this.consultaTarefaRepository.obterTarefasDisponibilizadas();
        
        if(tarefas == null)
            throw new NotFoundException();
        
            return tarefas;
    }
    
}
