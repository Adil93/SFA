package sfa.view;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.security.auth.callback.CallbackHandler;
import javax.servlet.RequestDispatcher;
import javax.security.auth.Subject;

import javax.servlet.http.HttpSession;

import oracle.adf.share.ADFContext;

import oracle.adf.share.security.SecurityContext;

import oracle.adf.share.security.identitymanagement.UserProfile;

import oracle.security.idm.IMException;
import oracle.security.jps.JpsException;
import oracle.security.jps.service.idstore.IdentityStoreException;

import weblogic.security.SimpleCallbackHandler;
import weblogic.security.services.Authentication;

import weblogic.servlet.security.ServletAuthentication;

public class LoginBean {
    
    private String username;
    private String password;
    private String errorMsg="";
    private boolean isVerified=false;
    private String displayName="Adil";

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    } 

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    
    public void  setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    public String  getErrorMsg() {
        return errorMsg;
    }
    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public boolean isIsVerified() {
        return isVerified;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    
    public String doLogin() {
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        CallbackHandler handler = new SimpleCallbackHandler(this.username, this.password.getBytes());
        Subject subject;
        
        try {
            subject = Authentication.login(handler);
            ServletAuthentication.runAs(subject, request);
            ServletAuthentication.generateNewSessionID(request);
            String successUrl = "/adfAuthentication?success_url=/faces/Home.jspx";
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            RequestDispatcher dispatcher = request.getRequestDispatcher(successUrl);
            HttpSession session = request.getSession();
            dispatcher.forward(request, response);
            facesContext.responseComplete();
            if(getDisplayname()!=null)
            this.setDisplayName(getDisplayname());
            else
            this.setDisplayName(this.getUsername());
            
          
        } catch(Exception e) {
            e.printStackTrace();
            this.setErrorMsg("!!Incorrect username/password. Please try again.");
            this.setIsVerified(true);
           // request.setAttribute("errorMsg", this.errorMsg);
           // facesContext.addMessage("ss3", new FacesMessage(errorMsg));
                    
            try {
                externalContext.redirect("login.jspx");
               //facesContext.addMessage("ss3", new FacesMessage("Username or password is incorrect"));
            } catch (IOException f) {
                e.printStackTrace();
            } 
        }
        
            return null;
        }
        
       
    
    
    public String doLogout(){
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
        try {
            externalContext.redirect("adfAuthentication?logout=true&end_url=/faces/login.jspx");
            //facesContext.responseComplete();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
        }
    public String getDisplayname() throws IdentityStoreException, JpsException,  IMException {
          ADFContext adfCtx = ADFContext.getCurrent();
            SecurityContext secCntx = adfCtx.getSecurityContext();
             UserProfile up = secCntx.getUserProfile();
             String user = up.getDisplayName();        
             return user;
             }


  
}
