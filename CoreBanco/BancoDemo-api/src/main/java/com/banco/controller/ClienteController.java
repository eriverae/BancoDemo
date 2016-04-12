/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.controller;

import com.banco.ejb.IClienteFacadeLocal;
import com.banco.entidades.Cliente;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author jmartinez
 */
@ManagedBean
@ViewScoped
public class ClienteController {

    private Cliente cliente;
    
    //@EJB(lookup = "java:global/BancoDemo-ejb-1.0-SNAPSHOT/ClienteFacade!com.banco.ejb.IClienteFacadeLocal")
    @EJB
    IClienteFacadeLocal clienteEJB;

    /**
     * Creates a new instance of ClienteController
     */
    public ClienteController() {
        cliente = new Cliente();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        System.out.println("Se selecciono cliente: " + cliente.getIdentificacion() + " para modificación");
        this.cliente = cliente;
    }

    public void eliminarCliente(){
       System.out.println("Se invoca acción eliminar cliente " + cliente.getIdentificacion());
       String msg = "Cliente eliminado con exito !!!";
        try {
            clienteEJB.remove(cliente);
        } catch (Exception be) {
            msg = be.getMessage();
        }
        FacesContext.getCurrentInstance().addMessage("mensajes", new FacesMessage(msg));
    }
}
