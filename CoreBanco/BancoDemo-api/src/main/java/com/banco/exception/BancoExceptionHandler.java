/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.exception;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.persistence.OptimisticLockException;

/**
 *
 * @author jmartinez
 */
public class BancoExceptionHandler extends ExceptionHandlerWrapper {

   private static final Logger LOG = Logger.getLogger(BancoExceptionHandler.class.getName());

   private final javax.faces.context.ExceptionHandler wrapped;

   public BancoExceptionHandler(final javax.faces.context.ExceptionHandler wrapped) {
      this.wrapped = wrapped;
   }

   @Override
   public javax.faces.context.ExceptionHandler getWrapped() {
      return this.wrapped;
   }

   @Override
   public void handle() throws FacesException {
      for (final Iterator<ExceptionQueuedEvent> it = getUnhandledExceptionQueuedEvents().iterator(); it.hasNext();) {
         Throwable t = it.next().getContext().getException();
         while ((t instanceof FacesException || t instanceof EJBException || t instanceof ELException)
            && t.getCause() != null) {
            t = t.getCause();
         }
         if (t instanceof FileNotFoundException || t instanceof ViewExpiredException || t instanceof OptimisticLockException) {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ExternalContext externalContext = facesContext.getExternalContext();
            final Map<String, Object> requestMap = externalContext.getRequestMap();
            try {
               LOG.info(t.getClass().getSimpleName() + "-" + t.getMessage());
               String message;
               if (t instanceof ViewExpiredException) {
                  final String viewId = ((ViewExpiredException) t).getViewId();
                  message = "View is expired. <a href='/ifos" + viewId + "'>Back</a>";
               }else if(t instanceof OptimisticLockException){
                  
                  message= "Otro usuario ha modificado los mismos datos, intente nuevamente";
                  
               }else {
                  message = t.getMessage(); // beware, don't leak internal info!
               }
               requestMap.put("errorMsg", message);
               try {
                  //externalContext.dispatch("/error.jsp?faces-redirect=false");
                  externalContext.dispatch("/error.jsp");
               } catch (final IOException e) {
                  LOG.log(Level.SEVERE,"Error view '/error.jsp' unknown!", e);
               }
               facesContext.responseComplete();
            } finally {
               it.remove();
            }
         }
      }
      getWrapped().handle();
   }

}
