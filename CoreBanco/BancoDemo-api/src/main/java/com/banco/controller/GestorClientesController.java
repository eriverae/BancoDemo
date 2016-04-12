/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.controller;

import com.banco.ejb.IClienteFacadeLocal;

import javax.faces.bean.ManagedBean;

import com.banco.entidades.Cliente;
import com.banco.entidades.Genero;
import com.banco.entidades.TipoIdentificacion;
import com.banco.exceptions.BancoException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;

/**
 *
 * @author jmartinez
 */
@ManagedBean(name = "gestorClientes")
@ViewScoped
public class GestorClientesController implements Serializable {

   private List<Cliente> listaClientes;

   private Cliente clienteSelected;

   private Cliente cliente;

   //@EJB(lookup = "java:global/BancoDemo-ejb-1.0-SNAPSHOT/ClienteFacade!com.banco.ejb.IClienteFacadeLocal")
   @EJB
   IClienteFacadeLocal ejbCliente;

    Logger log = Logger.getLogger(GestorClientesController.class.getName());

   //En el constructor no se puede usar el EJB porque no se ha ejecutado el inject, se debe hacer en el init
   public GestorClientesController() {
      log.fine("Se ejecuta constructor en clase GestorClientesController");
      cliente = new Cliente();
   }

   @PostConstruct
   public void init() {
      log.fine("Se ejecuta init() en clase GestorClientesController");
      listaClientes = ejbCliente.findAll();
   }

   public List<Cliente> getListaClientes() {
      return listaClientes;
   }

   public void setListaClientes(List<Cliente> listaClientes) {
      this.listaClientes = listaClientes;
   }

   public String consultarClientes() {
      log.fine("Consultando clientes ...");
      listaClientes = ejbCliente.findAll();
      return "/banco/gestionarClientes.xhtml?faces-redirect=true";
   }

   public void setClienteSelected(Cliente cte) {
      this.clienteSelected = cte;
      this.cliente = cte;
      log.log(Level.FINE,"Cliente seleccionado {0}",  cliente.getIdentificacion());
      log.log(Level.FINE,"Nombre cliente seleccionado:{0} ", cliente.getNombre());
   }

   public Cliente getClienteSelected() {
      return clienteSelected;
   }

   /*
    * Para demostrar que es mejor tener un solo controller para acciones relacionadas
    * con una misma entidad, se requiere refrescar la lista en pantalla
    */
   public void eliminarCliente() {
      log.log(Level.FINE,"Se invoca acción elimar cliente {0}",  clienteSelected.getIdentificacion());
      String msg = "Cliente eliminado con exito !!!";
      try {
         ejbCliente.remove(clienteSelected);
         listaClientes.remove(clienteSelected);
      } catch (Exception be) {
         msg = be.getMessage();
      }
      FacesContext.getCurrentInstance().addMessage("mensajes", new FacesMessage(msg));
   }

   public void testAction() {
      log.fine("testAction ejecutado con exito");
   }

   public Cliente getCliente() {
      return cliente;
   }

   public void setCliente(Cliente cliente) {
      this.cliente = cliente;
   }

   public void validarIdentificacion() {
      log.log(Level.FINE,"Validando identificacion {0}-{1}",new Object[]{cliente.getTipoIdentificacion(),cliente.getIdentificacion()});
      Cliente cte = ejbCliente.findByIdentificacion(cliente.getTipoIdentificacion(), cliente.getIdentificacion());
      if (cte != null) {
         log.fine("Identificacion ya existe....");
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(cliente.getIdentificacion() + " ya existe!"));
      }
   }

   public SelectItem[] tipoIdentifiacionValues() {
      SelectItem[] items = new SelectItem[TipoIdentificacion.values().length];
      int i = 0;
      for (TipoIdentificacion ti : TipoIdentificacion.values()) {
         items[i++] = new SelectItem(ti, ti.getLabel());
      }
      return items;
   }

   public void modificarCliente() {
      log.log(Level.FINE,"Se invoca accion modificar cliente {0}", cliente.getId());
      String msg = "Cliente actualizado con exito !!!";
      //try {
         log.log(Level.FINE,"Nombre a modificar: {0} - Version:{1}", new Object[]{cliente.getNombre(),cliente.getVersion()} );
         cliente = ejbCliente.modificarCliente(cliente);
         //init();//Se actualiza lista de clientes para que no falle el bloqueo optimista
         log.log(Level.FINE,"Se actualizo cliente {0} - Nueva version:{1}", new Object[]{cliente.getIdentificacion(),cliente.getVersion()});
//      } catch (Exception be) {
//         be.getMessage();
//         msg = processException(be);
//         log.error("$$$Error actualizando cliente",be);
//      }
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
   }

   private String processException(Throwable e) {

      String message = e.getMessage();
      if (message != null) {
         return message;
      }
      return processException(e.getCause());

   }

   public SelectItem[] generoValues() {
      SelectItem[] items = new SelectItem[Genero.values().length];
      int k = 0;
      for (Genero g : Genero.values()) {
         items[k++] = new SelectItem(g, g.getLabel());
      }
      return items;
   }

   public void crearCliente() {
      String msg = "Cliente creado con exito !!!";
      try {
         ejbCliente.crearCliente(cliente);
      } catch (BancoException be) {
         msg = be.getMessage();
      }
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
   }
   
   public void mostrarDialogoMoficar(Cliente cte){
       log.log(Level.FINE,"Inicia mostrarDialogoModificar(...) para cliente {0}",cte.getIdentificacion());
       setClienteSelected(cte);
       Map<String,Object> options = new HashMap();
       options.put("resizable", false);
       options.put("draggable", false);
       options.put("modal", true);
       RequestContext.getCurrentInstance().openDialog("editarClienteDlg", options, null);
   }
   
   public void closeDialogoModificar(){
       RequestContext.getCurrentInstance().closeDialog(cliente);
   }
   

}
