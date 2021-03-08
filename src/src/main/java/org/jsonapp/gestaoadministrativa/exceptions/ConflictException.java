package org.jsonapp.gestaoadministrativa.exceptions;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

public class ConflictException extends ClientErrorException{
    /**
     *
     */
    private static final long serialVersionUID = -4457105976342903298L;

    public ConflictException() {
        super(Response.Status.CONFLICT); // 409
    }
}
