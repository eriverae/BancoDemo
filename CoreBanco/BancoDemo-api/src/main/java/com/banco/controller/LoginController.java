/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jmartinez
 */
@ManagedBean
@RequestScoped
public class LoginController {

   public static final String USER_SESSION_KEY = "user";
   
   private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
   
   private String username;
   private String password;
   
   /**
    * Creates a new instance of LoginController
    */
   public LoginController() {
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }
   
   public String autenticar(){
      FacesContext context = FacesContext.getCurrentInstance();
      LOGGER.log(Level.FINE,"Autenticando usuario {0}",  username);
      if("admin".equals(username) && "admin".equals(password)){
         HttpSession session = 
                 (HttpSession)context.getExternalContext().getSession(true);
         session.setAttribute(USER_SESSION_KEY, username);   
         return "/banco/index.xhtml?faces-redirect=true";
      }else{
         FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                           "Login Falló !",
                                           "usuario/password incorrectos.");
         context.addMessage(null, message);
         return null;
      }
   }
   
   public String logout() {
        HttpSession session = (HttpSession)
             FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "/security/login.xhtml?faces-redirect=true";
        
    }
   
   public String getJSFVersion(){
      StringBuilder version = new StringBuilder();
      version.append(FacesContext.getCurrentInstance().getClass().getPackage().getImplementationTitle());
      version.append(",");
      version.append(FacesContext.getCurrentInstance().getClass().getPackage().getImplementationVendor());
      version.append("");
      version.append(FacesContext.getCurrentInstance().getClass().getPackage().getImplementationVersion());
      
      return version.toString();
         
   }
}
