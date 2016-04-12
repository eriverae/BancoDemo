/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.entidades;

/**
 *
 * @author jmartinez
 */
public enum TipoIdentificacion {
    CC("Cédula"),
    TI("T. Identidad"),
    NIT("NIT");
    
    private final String label;
    
    private TipoIdentificacion(String label){
        this.label = label;
    }
    
    public String getLabel(){
        return this.label;
    }
    
}
