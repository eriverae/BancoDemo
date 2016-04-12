/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.banco.ws;

import com.banco.ejb.ICuentaFacadeLocal;
import com.banco.entidades.Cuenta;
import com.banco.entidades.TipoIdentificacion;
import com.banco.exceptions.BancoException;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author jmartinez
 */
@WebService(serviceName = "BancoService")
public class BancoService {
    
    //@EJB(lookup = "java:global/BancoDemo-ejb-1.0-SNAPSHOT/CuentaFacade!com.banco.ejb.ICuentaFacadeLocal")
    @EJB
    private ICuentaFacadeLocal ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "crearCuenta")
    public Integer crearCuenta(@WebParam(name = "newCuenta") Cuenta newCuenta, @WebParam(name = "tipoID") TipoIdentificacion tipoID, @WebParam(name = "identificacion") long identificacion) throws BancoException {
        return ejbRef.crearCuenta(newCuenta, tipoID, identificacion);
    }

    @WebMethod(operationName = "consultarSaldo")
    public double consultarSaldo(@WebParam(name = "tipoID") TipoIdentificacion tipoID, @WebParam(name = "identificacion") long identificacion, @WebParam(name = "numeroCuenta") Integer numeroCuenta) throws BancoException {
        return ejbRef.consultarSaldo(tipoID, identificacion, numeroCuenta);
    }
    
}
