package org.jsonapp.gestaotarefas;

import java.util.List;

public interface IConsultaTarefaRepository {

	TarefaEdicaoDto obterTarefaParaEdicao(int id) throws Exception;

	List<TarefaConsultadaDto> obterTarefasNaoEnviadasParaAluno(String email) throws Exception;

	List<TarefaConsultadaDto> obterTarefasDisponibilizadas() throws Exception;
    
}
