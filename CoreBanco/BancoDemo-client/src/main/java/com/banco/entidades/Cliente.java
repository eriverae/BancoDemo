/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jmartinez
 */
//Se agrega la anotacion @JsonIgnoreProperties para evitar error:
//com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field "$promise"
@JsonIgnoreProperties(ignoreUnknown = true) 
@Entity
@Table(name = "CLIENTE")
@NamedQueries({
   @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
   @NamedQuery(name = "Cliente.findById", query = "SELECT c FROM Cliente c WHERE c.id = :id"),
   @NamedQuery(name = "Cliente.findByIdentificacion",
      query = "SELECT c FROM Cliente c WHERE c.identificacion = :identificacion "
      + "and c.tipoIdentificacion = :tipoIdentificacion"),
   @NamedQuery(name = "Cliente.findByNombre", query = "SELECT c FROM Cliente c WHERE c.nombre = :nombre"),
   @NamedQuery(name = "Cliente.findByGenero", query = "SELECT c FROM Cliente c WHERE c.genero = :genero"),
   @NamedQuery(name = "Cliente.findByFechaNacimiento", query = "SELECT c FROM Cliente c WHERE c.fechaNacimiento = :fechaNacimiento")})
public class Cliente implements Serializable {

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Basic(optional = false)
   @Column(name = "ID")
   private Long id;

   //@Size(max = 10)
   @NotNull
   @Column(name = "TIPO_IDENTIFICACION")
   @Enumerated(EnumType.STRING)
   private TipoIdentificacion tipoIdentificacion;

   @Basic(optional = false)
   @NotNull
   @Column(name = "IDENTIFICACION")
   private long identificacion;

   @Size(max = 30)
   @Column(name = "NOMBRE")
   private String nombre;

   //@Size(max = 1)
   @Column(name = "GENERO")
   @Enumerated(EnumType.STRING)
   private Genero genero;

   @Basic(optional = false)
   @NotNull
   @Column(name = "FECHA_NACIMIENTO")
   @Temporal(TemporalType.DATE)
   private Date fechaNacimiento;

   /*
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteId", fetch = FetchType.LAZY)
   private Collection<Cuenta> cuentas;
  */

   @Version
   @Column(name = "VERSION")
   private Long version;

   public Cliente() {
   }

   public Cliente(TipoIdentificacion tipoId, Long identificacion, String name,
      Genero genero, Date fecNac) {
      //this.id = id;
      this.tipoIdentificacion = tipoId;
      this.identificacion = identificacion;
      this.nombre = name;
      this.genero = genero;
      this.fechaNacimiento = fecNac;
   }

   public Cliente(Long id, Date fechaNacimiento) {
      this.id = id;
      this.fechaNacimiento = fechaNacimiento;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public TipoIdentificacion getTipoIdentificacion() {
      return tipoIdentificacion;
   }

   public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
      this.tipoIdentificacion = tipoIdentificacion;
   }

   public String getNombre() {
      return nombre;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public Date getFechaNacimiento() {
      return fechaNacimiento;
   }

   public void setFechaNacimiento(Date fechaNacimiento) {
      this.fechaNacimiento = fechaNacimiento;
   }

   /*
   public Collection<Cuenta> getCuentas() {
      return cuentas;
   }

   public void setCuentas(Collection<Cuenta> cuentas) {
      this.cuentas = cuentas;
   }
   */

   @Override
   public int hashCode() {
      int hash = 0;
      hash += (id != null ? id.hashCode() : 0);
      return hash;
   }

   @Override
   public boolean equals(Object object) {
      // TODO: Warning - this method won't work in the case the id fields are not set
      if (!(object instanceof Cliente)) {
         return false;
      }
      Cliente other = (Cliente) object;
      if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
         return false;
      }
      return true;
   }

   @Override
   public String toString() {
      return "com.banco.entidades.Cliente[ id=" + id + " ]";
   }

   public long getIdentificacion() {
      return identificacion;
   }

   public void setIdentificacion(long identificacion) {
      this.identificacion = identificacion;
   }

   public Genero getGenero() {
      return genero;
   }

   public void setGenero(Genero genero) {
      this.genero = genero;
   }

   public Long getVersion() {
      return version;
   }

   public void setVersion(Long version) {
      this.version = version;
   }

}
