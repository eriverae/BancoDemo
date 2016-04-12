/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.errorhandling;

import com.banco.exceptions.BancoException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author jmartinez
 */
public class BancoExceptionMapper implements ExceptionMapper<BancoException> {

    public Response toResponse(BancoException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        //setHttpStatus(ex, errorMessage);
        errorMessage.setCode(9999);
        errorMessage.setMessage(ex.getMessage());
        StringWriter errorStackTrace = new StringWriter();
        ex.printStackTrace(new PrintWriter(errorStackTrace));
        errorMessage.setDeveloperMessage(errorStackTrace.toString());
        errorMessage.setLink("www.banco.com");

        return Response.status(errorMessage.getStatus())
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();

        
    }

}
