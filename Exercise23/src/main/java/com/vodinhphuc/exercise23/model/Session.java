/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vodinhphuc.exercise23.model;

/**
 *
 * @author vodinhphuc
 */
public class Session {
    private String SessionID;
    private String Username;
    private String Password;

    public Session(String SessionID, String Username, String Password) {
        this.SessionID = SessionID;
        this.Username = Username;
        this.Password = Password;
    }

    public String getSessionID() {
        return SessionID;
    }

    public void setSessionID(String SessionID) {
        this.SessionID = SessionID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
    
    
}
