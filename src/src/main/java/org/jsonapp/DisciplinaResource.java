package org.jsonapp;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jsonapp.gestaoadministrativa.DisciplinaDto;
import org.jsonapp.gestaoadministrativa.IDisciplinaApp;
import org.jsonapp.gestaoadministrativa.exceptions.ConflictException;

@Path("api/v1/disciplinas")
@Tag(name = "Gestão de disciplinas", description = "manutenção nos dados sobre disciplinas do professor")
public class DisciplinaResource {
    
    final IDisciplinaApp disciplinaApp;

    public DisciplinaResource(IDisciplinaApp disciplinaApp) {
        super();

        this.disciplinaApp = disciplinaApp;
    }

    @POST
    @APIResponse(responseCode = "201", description = "Cria nova disciplina")
    @APIResponse(responseCode = "404", description = "Não existe disciplina para atualizar")
    @APIResponse(responseCode = "409", description = "Disciplina já existe")
    @APIResponse(responseCode = "500", description = "Falha no servidor")
    @Operation(summary = "Cria uma nova disciplina")
    public Response post(DisciplinaDto disciplinaDto) {
        try{
            this.disciplinaApp.criar(disciplinaDto);
            return Response.status(Status.CREATED).build();
        }catch(NotFoundException ex) {
            return Response.status(Status.NOT_FOUND).build();
        }catch(ConflictException ex) {
            return Response.status(Status.CONFLICT).build();
        }catch(InternalServerErrorException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("{id}")
    @APIResponse(responseCode = "204", description = "Disciplina atualizada")
    @APIResponse(responseCode = "404", description = "Disciplina não existe")
    @APIResponse(responseCode = "500", description = "Falha no servidor")
    @Operation(summary = "Atualiza uma disciplina")
    public Response put(@PathParam("id")int id, DisciplinaDto disciplinaDto) {
        try{
            this.disciplinaApp.atualizar(id, disciplinaDto);
            return Response.status(Status.NO_CONTENT).build();
        }catch(NotFoundException ex) {
            return Response.status(Status.NOT_FOUND).build();
        }catch(ConflictException ex) {
            return Response.status(Status.CONFLICT).build();
        }catch(InternalServerErrorException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("{id}")
    @APIResponse(responseCode = "204", description = "Disciplina excluida")
    @APIResponse(responseCode = "404", description = "Disciplina não existe")
    @APIResponse(responseCode = "500", description = "Falha no servidor")
    @Operation(summary = "Desativa uma disciplina")
    public Response delete(@PathParam("id")int id) {
        try{
            this.disciplinaApp.deletar(id);
            return Response.status(Status.CREATED).build();
        }catch(NotFoundException ex) {
            return Response.status(Status.NOT_FOUND).build();
        }catch(ConflictException ex) {
            return Response.status(Status.CONFLICT).build();
        }catch(InternalServerErrorException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @APIResponse(responseCode = "200")
    @APIResponse(responseCode = "404", description = "nenhuma disciplina foi registrada")
    @APIResponse(responseCode = "500", description = "Falha no servidor")
    @Operation(summary = "Retorna todas as disciplinas")
    @Tag(name = "FUNCIONALIDADES FRONTEND (ANTENÇÃO)", description = "Endpoint para utilização dos clientes")
    public Response get() {
        try{
            return Response.status(Status.OK).entity(this.disciplinaApp.obterTodos()).build();
        }catch(NotFoundException ex) {
            return Response.status(Status.NOT_FOUND).build();
        }catch(ConflictException ex) {
            return Response.status(Status.CONFLICT).build();
        }catch(InternalServerErrorException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
