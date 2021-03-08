package org.jsonapp.gestaotarefas;

import java.util.List;

public interface IConsultasTarefaApp {

	TarefaEdicaoDto obterTarefaParaEdicao(int id) throws Exception;

	List<TarefaConsultadaDto> obterTarefasNaoEnviadasParaAluno(String email) throws Exception;

	Object obterTarefasDisponibilizadas() throws Exception;
    
}
