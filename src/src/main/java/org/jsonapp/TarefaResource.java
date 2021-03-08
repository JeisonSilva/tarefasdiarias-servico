package org.jsonapp;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jsonapp.gestaotarefas.IConsultasTarefaApp;
import org.jsonapp.gestaotarefas.IProfessorApp;
import org.jsonapp.gestaotarefas.TarefaDto;

@Path("api/v1/tarefas")
@Tag(name = "Gerenciamento de tarefas", description = "Utilizado para manter as tarefas dos professores")
@Tag(name = "FUNCIONALIDADES FRONTEND (ANTENÇÃO)", description = "Endpoint para utilização dos clientes")
public class TarefaResource {

    final IProfessorApp professorApp;
    final IConsultasTarefaApp consultasTarefaApp;

    public TarefaResource(
        IProfessorApp professorApp
        ,IConsultasTarefaApp consultasTarefaApp) {
        super();

        this.professorApp = professorApp;
        this.consultasTarefaApp = consultasTarefaApp;
    }

    @POST
    @APIResponse(responseCode = "201", description = "Cria uma tarefa")
    @APIResponse(responseCode = "404", description = "Não existe professor para executar esta operação")
    @APIResponse(responseCode = "400", description = "Dados inválidos")
    @APIResponse(responseCode = "500", description = "Falha no processamento do servidor")
    @Operation(summary = "Cria uma nova tarefa")
    public Response post(TarefaDto tarefa) {
        try {
            this.professorApp.criarNovatarefa(tarefa);
            return Response.status(Status.CREATED).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();

        } catch (BadRequestException e) {            
            return Response.status(Status.BAD_REQUEST).build();
        } catch(Exception ex) {            
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("{id}")
    @APIResponse(responseCode = "204", description = "tarefa atualizada")
    @APIResponse(responseCode = "404", description = "Não existe a tarefa")
    @Operation(summary = "Atualiza os dados de uma tarefa")
    @APIResponse(responseCode = "500", description = "Falha no processamento do servidor")
    public Response put(@PathParam("id")int id, TarefaDto tarefa) {
        try {
            this.professorApp.atualizar(id, tarefa);
            return Response.status(Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();

        } catch (BadRequestException e) {            
            return Response.status(Status.BAD_REQUEST).build();
        } catch(Exception ex) {            
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("{id}")
    @APIResponse(responseCode = "204", description = "Tarefa deletada")
    @APIResponse(responseCode = "404", description = "Tarefa não existe mais")
    @APIResponse(responseCode = "500", description = "Falha no processamento do servidor")
    @Operation(summary = "Exclui uma tarefa")
    public Response delete(@PathParam("id")int id) {
        try {
            this.professorApp.excluir(id);
            return Response.status(Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();

        } catch (BadRequestException e) {            
            return Response.status(Status.BAD_REQUEST).build();
        } catch(Exception ex) {            
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("{id}")
    @APIResponse(responseCode = "200")
    @APIResponse(responseCode = "404", description = "Não existe esta tarefa")
    @APIResponse(responseCode = "500", description = "Falha no processamento do servidor")
    @Operation(summary = "Retorna uma tarefa para edição")
    public Response getPorId(@PathParam("id")int id) {
        try {            
            return Response.status(Status.OK).entity(this.consultasTarefaApp.obterTarefaParaEdicao(id)).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();

        }  catch(Exception ex) {            
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("professor/{email}")
    @APIResponse(responseCode = "200")
    @APIResponse(responseCode = "404", description = "Não existe tarefas em digitação")
    @APIResponse(responseCode = "500", description = "Falha no processamento do servidor")
    @Operation(summary = "Retorna todas as tarefas em digitação")
    public Response getPorEmailProfessor(@PathParam("email")String email) {
        try {            
            return Response.status(Status.OK).entity(this.consultasTarefaApp.obterTarefasNaoEnviadasParaAluno(email)).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();

        }  catch(Exception ex) {            
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("{id}/finalizado")
    @APIResponse(responseCode = "204", description = "Tarefa foi enviada para aluno")
    @APIResponse(responseCode = "404", description = "Não existe esta tarefa no servidor")
    @Operation(summary = "Finaliza uma tarefa e deixa disponibilizado para o aluno")
    public Response putFinalizaTarefa(@PathParam("id")int id) {
        try {
            this.professorApp.finalizarTarefa(id);
            return Response.status(Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();

        } catch (BadRequestException e) {            
            return Response.status(Status.BAD_REQUEST).build();
        } catch(Exception ex) {            
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("{id}/entrega")
    @APIResponse(responseCode = "204", description = "Tarefa foi entregue pelo aluno")
    @APIResponse(responseCode = "404", description = "Não existe tarefa disponível para entrega")
    @Operation(summary = "Entrega uma tarefa para o professor")
    public Response putEntregaTarefa(@PathParam("id")int id) {
        try {
            this.professorApp.entregarTarefa(id);
            return Response.status(Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();

        } catch (BadRequestException e) {            
            return Response.status(Status.BAD_REQUEST).build();
        } catch(Exception ex) {            
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/disponibilizadas")
    @APIResponse(responseCode = "200", description = "Retorna as tarefas disponibilizadas para os alunos")
    @APIResponse(responseCode = "404", description = "Não existe tarefas")
    @Operation(summary = "Lista as tarefas disponibilizadas para o aluno")
    public Response getDisponibilizadas() {
        try {
            ;
            return Response.status(Status.OK).entity(this.consultasTarefaApp.obterTarefasDisponibilizadas()).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();

        } catch (BadRequestException e) {            
            return Response.status(Status.BAD_REQUEST).build();
        } catch(Exception ex) {            
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
