/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jmartinez
 */
@Entity
@Table(name = "CUENTA")
@NamedQueries({
    @NamedQuery(name = "Cuenta.findAll", query = "SELECT c FROM Cuenta c"),
    @NamedQuery(name = "Cuenta.findByNumCuenta", query = "SELECT c FROM Cuenta c WHERE c.numCuenta = :numCuenta"),
    @NamedQuery(name = "Cuenta.findBySaldo", query = "SELECT c FROM Cuenta c WHERE c.saldo = :saldo"),
    @NamedQuery(name = "Cuenta.findByFechaCreacion", query = "SELECT c FROM Cuenta c WHERE c.fechaCreacion = :fechaCreacion")})
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "NUM_CUENTA")
    private Integer numCuenta;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "SALDO")
    private double saldo;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuentaId", fetch = FetchType.LAZY)
    private Collection<Movimiento> movimientos;
    
    @JoinColumn(name = "CLIENTE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cliente clienteId;

    public Cuenta() {
    }

    public Cuenta(Integer numCuenta) {
        this.numCuenta = numCuenta;
    }

    public Cuenta(Integer numCuenta, double saldo, Date fechaCreacion) {
        this.numCuenta = numCuenta;
        this.saldo = saldo;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(Integer numCuenta) {
        this.numCuenta = numCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Collection<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Collection<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public Cliente getCliente() {
        return clienteId;
    }

    public void setCliente(Cliente cliente) {
        this.clienteId = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numCuenta != null ? numCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.numCuenta == null && other.numCuenta != null) || (this.numCuenta != null && !this.numCuenta.equals(other.numCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.banco.entidades.Cuenta[ numCuenta=" + numCuenta + " ]";
    }
}
