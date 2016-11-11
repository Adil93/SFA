package sfa.view;

import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.security.auth.callback.CallbackHandler;
import javax.servlet.RequestDispatcher;
import javax.security.auth.Subject;

import javax.servlet.http.HttpSession;

import weblogic.security.SimpleCallbackHandler;
import weblogic.security.services.Authentication;

import weblogic.servlet.security.ServletAuthentication;

public class LoginBean {
    
    private String username;
    private String password;

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
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return  null; 
    }
}
