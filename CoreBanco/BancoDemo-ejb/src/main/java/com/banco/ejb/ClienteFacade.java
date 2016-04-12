/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.ejb;

import com.banco.entidades.Cliente;
import com.banco.entidades.TipoIdentificacion;
import com.banco.exceptions.BancoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jmartinez
 */
@Stateless
public class ClienteFacade implements IClienteFacadeRemote, IClienteFacadeLocal   {
    @PersistenceContext(unitName = "BancoPU")
    private EntityManager em;
    
    private static final Logger LOGGER = Logger.getLogger(ClienteFacade.class.getName());

    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
    }
    
    @Override
    public void crearCliente(Cliente cliente) throws BancoException{
        LOGGER.info("Inicia crearCliente(...)");
       //Se verifica si el cliente ya existe;
        Cliente cte = findByIdentificacion(cliente.getTipoIdentificacion(), cliente.getIdentificacion());
        if(cte!= null){
            em.lock(cte, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
            LOGGER.warning("Cliente "+ cliente.getIdentificacion() + " ya existe !!");
            throw new BancoException("El cliente " +cliente.getTipoIdentificacion()+ "-" + cliente.getIdentificacion() +" ya existe en el sistema");
        }
        em.persist(cliente);
        LOGGER.info("Finaliza crearCliente(...)");
        
    }
    
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public Cliente findByIdentificacion(TipoIdentificacion tId, long identificacion){
        LOGGER.log(Level.FINE,"Consulta cliente {0}",  identificacion);
        Query  q = em.createNamedQuery("Cliente.findByIdentificacion");
        q.setParameter("tipoIdentificacion", tId);
        q.setParameter("identificacion", identificacion);
        try{
           return ((Cliente)q.getSingleResult());
        }catch(NoResultException nre){
            LOGGER.log(Level.WARNING,"No se encontro cliente {0}", identificacion);
            return null;
        }
        
    }
    
    @Override
    public Cliente modificarCliente(Cliente cte){
        LOGGER.log(Level.FINE,"Modificando cliente con nombre : {0} - Version: {1}", new Object[]{cte.getNombre(),cte.getVersion()} );
        cte = em.merge(cte);
        return cte;
    }
    
    @Override
    public List<Cliente> findAll() {
        //Query  q = em.createNamedQuery("Cliente.findAll");
        Query q = em.createQuery("SELECT c FROM Cliente c");
        return q.getResultList();
    }
    
    @Override
    public void remove(Cliente entity) {
        em.remove(entity);
    }
    
    public void remove(Long id){
      LOGGER.log(Level.FINE,"Eliminar cliente con id {0}", id);
      Cliente cte = this.find(id);
      if (cte!=null){
        remove(cte);
        LOGGER.log(Level.INFO,"Cliente eliminado correctamente");
      }else{
        LOGGER.log(Level.INFO, "Cliente con id {} no existe", id);
      }
    }
    
    @Override
    public Cliente find(Object id) {
        return em.find(Cliente.class, id);
    }
    
    @Override
    public List<Cliente> findRange(int startPosition, int maxResults, String sortFields, String sortDirections) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Cliente.class));
        javax.persistence.Query q = em.createQuery(cq);
        q.setFirstResult(startPosition);
        q.setMaxResults(maxResults);

        return q.getResultList();
    }
    
    @Override
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Cliente> rt = cq.from(Cliente.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
