/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.ejb;

import com.banco.entidades.Cuenta;
import com.banco.entidades.Cliente;
import com.banco.entidades.Movimiento;
import com.banco.entidades.TipoIdentificacion;
import com.banco.entidades.TipoOperacion;
import com.banco.exceptions.BancoException;
import java.sql.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jmartinez
 */
@Stateless
public class CuentaFacade implements ICuentaFacadeRemote, ICuentaFacadeLocal  {
    @PersistenceContext(unitName = "BancoPU")
    private EntityManager em;
    
    @EJB
    IClienteFacadeLocal clienteEJB;

    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Integer crearCuenta(Cuenta newCuenta, TipoIdentificacion tipoID, long identificacion) throws BancoException{
        
        Cliente cte = clienteEJB.findByIdentificacion(tipoID, identificacion);
        if(cte == null){
            throw new BancoException("Cliente no existe ... ");
        }
        newCuenta.setCliente(cte);
        Date fechaActual = new Date(System.currentTimeMillis());
        newCuenta.setFechaCreacion(fechaActual);
        newCuenta = em.merge(newCuenta);
        
        Movimiento mvto = new Movimiento();
        mvto.setCuentaId(newCuenta);
        mvto.setFechaOperacion(fechaActual);
        mvto.setTipoOperacion(TipoOperacion.DEPOSITO);
        mvto.setValor(newCuenta.getSaldo());
        em.persist(mvto);
        em.flush();
        return newCuenta.getNumCuenta();
        
    }
    
    @TransactionAttribute(TransactionAttributeType.NEVER)
    @Override
    public double consultarSaldo(TipoIdentificacion tipoID, long identificacion, Integer numeroCuenta)
                throws BancoException{
        double saldo = 0;
        Query q = em.createNamedQuery("Cuenta.findByNumCuenta");
        q.setParameter("numCuenta", numeroCuenta);
        try{
            Cuenta cuenta = (Cuenta)q.getSingleResult();
            Cliente cliente = cuenta.getCliente();
            if(cliente.getTipoIdentificacion()== tipoID && cliente.getIdentificacion() == identificacion){
                saldo = cuenta.getSaldo();
            }else{
                throw new BancoException("Datos del dueño de la cuenta incorrectos");
            }
            
        }catch(NoResultException nre){
            throw new BancoException("Cuenta " + numeroCuenta + " no existe");
        }
        return saldo;
    }
    
    
    @Override
    public List<Cuenta> findAll() {
         Query  q = em.createNamedQuery("Cuenta.findAll");
        return q.getResultList();
    }
    
    @Override
    public void remove(Cuenta entity) {
        em.remove(entity);
    }
    
    @Override
    public Cuenta find(Object id) {
        return em.find(Cuenta.class, id);
    }
    
    @Override
    public List<Cliente> findRange(int startPosition, int maxResults, String sortFields, String sortDirections) {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Cuenta.class));
        javax.persistence.Query q = em.createQuery(cq);
        q.setFirstResult(startPosition);
        q.setMaxResults(maxResults);

        return q.getResultList();
    }
    
    @Override
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Cliente> rt = cq.from(Cuenta.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
