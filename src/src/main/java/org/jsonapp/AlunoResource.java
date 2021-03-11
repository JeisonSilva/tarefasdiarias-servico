package org.jsonapp;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jsonapp.gestaoalunos.AlunoMatriculadoDto;
import org.jsonapp.gestaoalunos.IAlunoMatriculadoApp;

@Path("api/v1/alunos")
@Tag(name = "FUNCIONALIDADES FRONTEND (ANTENÇÃO)", description = "Endpoint para utilização dos clientes")
public class AlunoResource {
    
    final IAlunoMatriculadoApp alunoMatriculadoApp;

    public AlunoResource(IAlunoMatriculadoApp alunoMatriculadoApp) {
        super();

        this.alunoMatriculadoApp = alunoMatriculadoApp;
    }

    @GET
    @Path("/professor/{email}")
    @APIResponse(responseCode = "200")
    @APIResponse(responseCode = "404", description = "Não existe nenhum aluno matriculado")
    @APIResponse(responseCode = "500", description = "Falha no servidor")
    @Operation(summary = "Retorna todos os alunos matriculados por professor")
    public Response get(@PathParam("email")String email) {
        try {
            List<AlunoMatriculadoDto> alunosMatriculados =  this.alunoMatriculadoApp.obterAlunosMatriculadosPorEmailProfessor(email);
            return Response.status(Status.OK).entity(alunosMatriculados).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }catch(InternalServerErrorException | SQLException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/colegasclasse/{email}")
    @APIResponse(responseCode = "200")
    @APIResponse(responseCode = "404", description = "Não existe nenhum aluno matriculado")
    @APIResponse(responseCode = "500", description = "Falha no servidor")
    @Operation(summary = "Retorna todos os alunos matriculados por professor")
    public Response getColegasClasse(@PathParam("email")String email) {
        try {
            List<AlunoMatriculadoDto> alunosMatriculados =  this.alunoMatriculadoApp.obterAlunosMatriculadosPorEmailAluno(email);
            return Response.status(Status.OK).entity(alunosMatriculados).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }catch(InternalServerErrorException | SQLException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
