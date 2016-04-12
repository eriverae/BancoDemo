/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.controller;

import com.banco.ejb.IClienteFacadeLocal;
import com.banco.ejb.ICuentaFacadeLocal;
import com.banco.entidades.Cliente;
import com.banco.entidades.Cuenta;
import com.banco.entidades.TipoIdentificacion;
import com.banco.exceptions.BancoException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;


/**
 *
 * @author jmartinez
 */
@ManagedBean
@RequestScoped
public class CuentaController {

    private Cuenta cuenta;
    private String nombreCliente;
    private TipoIdentificacion tipoID;
    private long identificacion;
    
    //@EJB(lookup = "java:global/BancoDemo-ejb-1.0-SNAPSHOT/CuentaFacade!com.banco.ejb.ICuentaFacadeLocal")
    @EJB
    ICuentaFacadeLocal cuentaEJB;
    
    //@EJB(lookup = "java:global/BancoDemo-ejb-1.0-SNAPSHOT/ClienteFacade!com.banco.ejb.IClienteFacadeLocal")
    @EJB
    IClienteFacadeLocal clienteEJB;
    
    /**
     * Creates a new instance of CuentaController
     */
    public CuentaController() {
        cuenta = new Cuenta();
    }
    
    public void crearCuenta() {
        String msg;
        System.out.println(tipoID + "-" + identificacion );        
        try {
            Integer num = cuentaEJB.crearCuenta(cuenta, tipoID, identificacion);
            msg = "Se creó la cuenta No. " + num ;
        } catch (BancoException be) {
            msg = be.getMessage();
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));

    }
    
    public void verificarCliente(ValueChangeEvent e){
       System.out.println("Anteriro valor:" + e.getOldValue()); 
       System.out.println("Nuevo valor:" + e.getNewValue());
    }

    public void verificarCliente(){
       System.out.println("Verificando cliente.... : " + tipoID+"-"+identificacion);
       Cliente cliente = clienteEJB.findByIdentificacion(tipoID, identificacion);
       if (cliente != null){
           nombreCliente = cliente.getNombre();
           System.out.println("Cliente: " + cliente.getNombre());
       }else{
           nombreCliente="";
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No existe cliente para crear cuenta.."));   
//           FacesContext.getCurrentInstance().renderResponse();
       }
       
       
       //FacesContext.getCurrentInstance().renderResponse();
    }
    
    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public TipoIdentificacion getTipoID() {
        return tipoID;
    }

    public void setTipoID(TipoIdentificacion tipoID) {
        this.tipoID = tipoID;
    }

    public long getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(long identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    
    
}
