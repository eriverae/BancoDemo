/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.banco.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 *
 * @author jmartinez
 */
public class BancoExceptionHandlerFactory extends ExceptionHandlerFactory {
   private ExceptionHandlerFactory parent;
 
   // this injection handles jsf
   public BancoExceptionHandlerFactory(ExceptionHandlerFactory parent) {
    this.parent = parent;
   }
 
    @Override
    public ExceptionHandler getExceptionHandler() {
 
        ExceptionHandler handler = new BancoExceptionHandler(parent.getExceptionHandler());
 
        return handler;
    }
 
   
}
