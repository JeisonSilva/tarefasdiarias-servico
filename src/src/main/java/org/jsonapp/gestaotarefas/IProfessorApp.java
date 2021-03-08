package org.jsonapp.gestaotarefas;

import org.jsonapp.gestaotarefas.exceptions.TarefaDisponibilizadaException;

public interface IProfessorApp {

	void criarNovatarefa(TarefaDto tarefa) throws Exception;

	void atualizar(int id, TarefaDto tarefa) throws TarefaDisponibilizadaException, Exception;

	void excluir(int id) throws Exception;

	void finalizarTarefa(int id) throws Exception;

	void entregarTarefa(int id) throws Exception;
    
}
