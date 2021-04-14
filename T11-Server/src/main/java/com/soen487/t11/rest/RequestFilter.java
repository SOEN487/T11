package com.soen487.t11.rest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Provider
public class RequestFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext)
            throws IOException {
        System.out.println("Client Powered By Received from client: "+requestContext.getHeaderString("Client-Powered-By"));
        System.out.println("Authorization-Token Received from client: "+requestContext.getHeaderString("Authorization-Token"));
        UriInfo uriInfo=requestContext.getUriInfo();
        if (uriInfo.getPath().contains("user/register") || uriInfo.getPath().contains("user/login") || uriInfo.getPath().contains("user/logout") ) {
            return ;
        }
        String tokenHeader=  requestContext.getHeaderString("Authorization-Token");
        System.out.println("token Header in filter: "+ tokenHeader);
         Date generatedTime=LoginManager.getInstance().generateDate(tokenHeader);
        //if (generatedTime != null) //Means the token is the same
        if (generatedTime != null) {
            Date timeNow = new Date();
            long diff = timeNow.getTime() - generatedTime.getTime() ;
            long tokenDuration = TimeUnit.MILLISECONDS.toMinutes(diff);
            System.out.println("Duration: " + tokenDuration);
            if (tokenDuration > 30){
                LoginManager.getInstance().removeTokenByToken(tokenHeader);
                System.out.println("Session timed out");
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("User not authenticated by Filter!").build());
                return;
            }
            else{
                System.out.println("Token in Header is VALID");
                return;
            }
        }
        else // If the token is changed
        {
            System.out.println("Token in Header is NOT VALID");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("User not authenticated by Filter!").build());
            return ;
        }

    }
}
