package org.jsonapp;

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
import org.jsonapp.gestaoadministrativa.ClasseDto;
import org.jsonapp.gestaoadministrativa.IClasseProfessorApp;
import org.jsonapp.gestaoadministrativa.ProfessorSubstitutoDto;
import org.jsonapp.gestaoadministrativa.exceptions.ConflictException;

@Path("api/v1/administrador/classes")
@Tag(name = "Administrador (Gestão do perfil)", description = "Gerenciamento do perfil")
public class ClasseProfessorResource {
    
    final IClasseProfessorApp classeProfessorApp;

    public ClasseProfessorResource(IClasseProfessorApp classeProfessorApp) {
        super();

        this.classeProfessorApp = classeProfessorApp;
    }

    @POST
    @APIResponse(responseCode = "201", description = "Cria uma classe")
    @APIResponse(responseCode = "404", description = "Professor não existe")
    @APIResponse(responseCode = "409", description = "Classe cadastrada")
    @APIResponse(responseCode = "400", description = "Dados inválidos")
    @APIResponse(responseCode = "500", description = "Falha no servidor")
    @Operation(summary = "Operação responsável por criar classes para professores")
    public Response post(ClasseDto classe) {
        try{
            this.classeProfessorApp.criar(classe);
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
    @Path("professor/{email}/substituicao")
    @APIResponse(responseCode = "204", description = "Substituição realizada")
    @APIResponse(responseCode = "404", description = "Professor não existe")
    @APIResponse(responseCode = "409", description = "Professor da substituição não está apto")
    @Operation(summary = "Substitui o professor da sala de aula")
    public Response putMatricula(@PathParam("email")String email, ProfessorSubstitutoDto professorSubstitutoDto)
            {
        try{
            this.classeProfessorApp.substituirProfessor(email, professorSubstitutoDto);
            return Response.status(Status.CREATED).build();
        }catch(NotFoundException ex) {
            return Response.status(Status.NOT_FOUND).build();
        }catch(ConflictException ex) {
            return Response.status(Status.CONFLICT).build();
        }catch(Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
