package com.soen487.t11.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("/user")
public class AuthService {
    /**
     * Meant to add user that can utilize the API
     * @param username of user
     * @param password of user
     */
    @POST
    @Path("/register/{username}/{password}")
    @Produces("application/json")
    public String createUser(@PathParam("username")  String username, @PathParam("password") String password) {
       LoginManager.getInstance().addUser(username,password);
       return LoginManager.getInstance().getUser(username).toString();
    }

    @POST
    @Path("/login/{username}/{password}")
    @Produces("application/json")
    public Response login(@PathParam("username") String username, @PathParam("password") String password) {
        MyResponse authResponse;
        Response.Status status;
        String token=LoginManager.getInstance().generateToken(username,password);
        if(token != null){
                authResponse = new MyResponse(true, token);
                status = Response.Status.OK;
            }

        else{
            authResponse = new MyResponse(false, "");
            status = Response.Status.FORBIDDEN;
        }
        return Response.status(status).entity(authResponse).build();
    }

    /**
     * Used to logout from the API and delete the token
     * @param username of user
     * @return string containing the login token
     */
    @POST
    @Path("/logout/{username}")
    @Produces("application/json")
    public String logout(@PathParam("username") String username) {
        LoginManager.getInstance().getUser(username);
        if (LoginManager.getInstance().getUser(username)!= null){
            if (LoginManager.getInstance().getUser(username).getToken().equals(""))
            {
                return "Not logged in.";
            }
            else
            {
                LoginManager.getInstance().removeToken(username);
                return "Logged out. Token successfully destroyed.";
            }
        }
        else{
            return "User does not exist!";
        }
    }
}
