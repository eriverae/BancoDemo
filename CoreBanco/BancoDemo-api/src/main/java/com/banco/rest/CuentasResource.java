/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.rest;

import com.banco.entidades.Cuenta;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author jmartinez
 */
@Path("cuentas")
@RequestScoped
public class CuentasResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CuentasResource
     */
    public CuentasResource() {
    }

    /**
     * Retrieves representation of an instance of com.banco.rest.CuentasResource
     * @return an instance of com.banco.entidades.Cuenta
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Cuenta getJson() {
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        return new Cuenta();
    }

    /**
     * PUT method for updating or creating an instance of CuentasResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(Cuenta content) {
    }
}
