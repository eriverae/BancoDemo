/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.entidades;

/**
 *
 * @author jmartinez
 */
public enum Genero {
    M("Masculino"),
    F("Femenino");
    
    private final String label;
    
    private Genero(String label){
        this.label = label;
    }
    
    public String getLabel(){
        return label;
    }
    
}
