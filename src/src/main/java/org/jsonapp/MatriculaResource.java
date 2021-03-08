package org.jsonapp;

import java.sql.SQLException;

import javax.ws.rs.DELETE;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jsonapp.gestaoadministrativa.IMatriculaAlunoApp;
import org.jsonapp.gestaoadministrativa.exceptions.ConflictException;

@Path("api/v1/administrador/matriculas")
@Tag(name = "Administrador (Gestão do perfil)", description = "Gerenciamento do perfil")
public class MatriculaResource {

    final IMatriculaAlunoApp matriculaAlunoApp;

    public MatriculaResource(IMatriculaAlunoApp matriculaAlunoApp) {
        super();

        this.matriculaAlunoApp = matriculaAlunoApp;
    }

    @POST
    @Path("professor/{emailProfessor}/aluno/{emailAluno}")
    @APIResponse(responseCode = "204", description = "Matrícula realizada")
    @APIResponse(responseCode = "404", description = "Dados não existem")
    @APIResponse(responseCode = "409", description = "Matrícula existente")
    @Operation(summary = "Realiza uma matrícula do aluno na sala de aula")
    public Response putMatricula(@PathParam("emailProfessor") String emailProfessor, @PathParam("emailAluno") String emailAluno) {
        try {
            this.matriculaAlunoApp.matricular(emailProfessor, emailAluno);
            return Response.status(Status.CREATED).build();
        } catch (NotFoundException ex) {
            return Response.status(Status.NOT_FOUND).build();
        } catch (ConflictException ex) {
            return Response.status(Status.CONFLICT).build();
        } catch (InternalServerErrorException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("aluno/{email}/cancelamento")
    @APIResponse(responseCode = "204", description = "Matrícula realizada")
    @APIResponse(responseCode = "404", description = "Dados não existem")
    @APIResponse(responseCode = "409", description = "Matrícula existente")
    @Operation(summary = "Cancela uma matrícula")
    public Response putMatriculaCancelamento(@PathParam("email") String emailProfessor,
            @PathParam("email") String emailAluno) {
        try {
            this.matriculaAlunoApp.cancelarMatricula(emailProfessor, emailAluno);
            return Response.status(Status.CREATED).build();
        } catch (NotFoundException ex) {
            return Response.status(Status.NOT_FOUND).build();
        } catch (ConflictException ex) {
            return Response.status(Status.CONFLICT).build();
        } catch (InternalServerErrorException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (SQLException e) {            
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
