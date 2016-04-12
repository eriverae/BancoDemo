/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.ejb;

import com.banco.entidades.Cliente;
import com.banco.entidades.TipoIdentificacion;
import com.banco.exceptions.BancoException;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author jmartinez
 */
@Local
public interface IClienteFacadeLocal{

    int count();

    void crearCliente(Cliente cliente) throws BancoException;

    Cliente find(Object id);

    List<Cliente> findAll();

    @TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
    Cliente findByIdentificacion(TipoIdentificacion tId, long identificacion);

    List<Cliente> findRange(int startPosition, int maxResults, String sortFields, String sortDirections);

    Cliente modificarCliente(Cliente cte);

    void remove(Cliente entity);
    
    void remove(Long id);
    
}
