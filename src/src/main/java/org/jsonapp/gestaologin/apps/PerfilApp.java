package org.jsonapp.gestaologin.apps;

import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;

import org.jsonapp.gestaologin.IPerfilApp;
import org.jsonapp.gestaologin.IPerfilRepository;
import org.jsonapp.gestaologin.PerfilDto;

@ApplicationScoped
public class PerfilApp implements IPerfilApp {

    final IPerfilRepository perfilRepository;

    public PerfilApp(IPerfilRepository perfilRepository) {
        super();

        this.perfilRepository = perfilRepository;
    }

    @Override
    public PerfilDto obterPerfil(String email) throws SQLException {
        PerfilDto perfil = this.perfilRepository.obter(email);

        if(perfil == null)
            throw new NotFoundException();
        
        return perfil;
    }
    
}
