/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jmartinez
 */
@Entity
@Table(name = "MOVIMIENTO")
@NamedQueries({
    @NamedQuery(name = "Movimiento.findAll", query = "SELECT m FROM Movimiento m"),
    @NamedQuery(name = "Movimiento.findById", query = "SELECT m FROM Movimiento m WHERE m.id = :id"),
    @NamedQuery(name = "Movimiento.findByTipoOperacion", query = "SELECT m FROM Movimiento m WHERE m.tipoOperacion = :tipoOperacion"),
    @NamedQuery(name = "Movimiento.findByValor", query = "SELECT m FROM Movimiento m WHERE m.valor = :valor"),
    @NamedQuery(name = "Movimiento.findByFechaOperacion", query = "SELECT m FROM Movimiento m WHERE m.fechaOperacion = :fechaOperacion")})
public class Movimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    //@Size(min = 1, max = 20)
    @Column(name = "TIPO_OPERACION")
    @Enumerated(EnumType.STRING)
    private TipoOperacion tipoOperacion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR")
    private double valor;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_OPERACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOperacion;
    
    @JoinColumn(name = "CUENTA_ID", referencedColumnName = "NUM_CUENTA")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cuenta cuentaId;

    public Movimiento() {
    }

    public Movimiento(Long id) {
        this.id = id;
    }

    public Movimiento(Long id, TipoOperacion tipoOperacion, double valor, Date fechaOperacion) {
        this.id = id;
        this.tipoOperacion = tipoOperacion;
        this.valor = valor;
        this.fechaOperacion = fechaOperacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public Cuenta getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Cuenta cuentaId) {
        this.cuentaId = cuentaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimiento)) {
            return false;
        }
        Movimiento other = (Movimiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.banco.entidades.Movimiento[ id=" + id + " ]";
    }
    
}
