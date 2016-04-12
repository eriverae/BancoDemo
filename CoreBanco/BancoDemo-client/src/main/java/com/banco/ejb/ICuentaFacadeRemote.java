/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.ejb;

import com.banco.entidades.Cliente;
import com.banco.entidades.Cuenta;
import com.banco.entidades.TipoIdentificacion;
import com.banco.exceptions.BancoException;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author jmartinez
 */
@Remote
public interface ICuentaFacadeRemote extends ICuentaFacadeLocal {

    @TransactionAttribute(value = TransactionAttributeType.NEVER)
    double consultarSaldo(TipoIdentificacion tipoID, long identificacion, Integer numeroCuenta) throws BancoException;

    int count();

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    Integer crearCuenta(Cuenta newCuenta, TipoIdentificacion tipoID, long identificacion) throws BancoException;

    Cuenta find(Object id);

    List<Cuenta> findAll();

    List<Cliente> findRange(int startPosition, int maxResults, String sortFields, String sortDirections);

    void remove(Cuenta entity);
    
}
