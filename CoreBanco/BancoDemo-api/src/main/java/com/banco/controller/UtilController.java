/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.controller;

import com.banco.entidades.Genero;
import com.banco.entidades.TipoIdentificacion;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author jmartinez
 */
@ManagedBean
@RequestScoped
public class UtilController {

    /**
     * Creates a new instance of UtilController
     */
    public UtilController() {
    }
    
    public SelectItem[] tipoIdentifiacionValues() {
        SelectItem[] items = new SelectItem[TipoIdentificacion.values().length];
        int i = 0;
        for (TipoIdentificacion ti : TipoIdentificacion.values()) {
            items[i++] = new SelectItem(ti, ti.getLabel());
        }
        return items;
    }

    public SelectItem[] generoValues() {
        SelectItem[] items = new SelectItem[Genero.values().length];
        int k = 0;
        for (Genero g : Genero.values()) {
            items[k++] = new SelectItem(g, g.getLabel());
        }
        return items;
    }
}
