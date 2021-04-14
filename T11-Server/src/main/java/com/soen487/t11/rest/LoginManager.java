package com.soen487.t11.rest;


import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class LoginManager {
    private Map<String,User> users = new ConcurrentHashMap<>();
    private Map<String, String> tokenUsername = new ConcurrentHashMap<>();
    private  Map<String, Date> tokenExpiration = new ConcurrentHashMap<>();
    private String tokenHeader;
    private LoginManager() {
    }
    public static LoginManager getInstance() {
        return SingletonHolder.INSTANCE;
    }
    private static class SingletonHolder {
        private static final LoginManager INSTANCE = new LoginManager();
    }
    public void addUser(String username, String password){
        User user = new User(username, password);
        users.put(username, user);
    }
    public User getUser(String username){
        //NOT BEST PRACTICE SINCE PASSWORD IS EXPOSED
        return users.get(username);
    }
    public String generateToken(String username, String password){
        User user= users.get(username);
        if (user== null || !user.getPassword().equals(password)){
            return null;
        }
        user.generateToken();
        String token=user.getToken();
        tokenUsername.put(token, username);
        tokenExpiration.put(token, new Date());
        setTokenHeader(token);
        return token;
    }
    public Date generateDate(String token){
        return this.tokenExpiration.get(token);
    }

    public void removeToken(String username){
        User user= users.get(username);
        tokenUsername.remove(user.getToken());
        tokenExpiration.remove(user.getToken());
    }
    public void removeTokenByToken(String token){
        tokenUsername.remove(token);
        tokenExpiration.remove(token);
    }
    public void setTokenHeader(String token){
        this.tokenHeader=token;
    }
    public String getTokenHeader(){
        return this.tokenHeader;
    }

}