package com.soen487.t11.client;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.net.URI;
@Provider
class CheckRequestFilter implements ClientRequestFilter, ClientResponseFilter {
    //This is static variable and it is shared among all the sessions in the client
    //Alternative solutions is such as Cookies,...
    public static String token;

    @Override
    public void filter(ClientRequestContext reqContext) throws IOException {
        System.out.println("-- Client request info --");
        log(reqContext.getUri(), reqContext.getHeaders());

        if (reqContext.getUri().getPath().contains("register") || reqContext.getUri().getPath().contains("login"))
        {return; }
        reqContext.getHeaders().add("Authorization-Token", token);
        System.out.println("Authorization-Token in Request Filter: "+ token);
    }

    @Override
    public void filter(ClientRequestContext reqContext,
                       ClientResponseContext resContext) throws IOException {
        System.out.println("-- Client response info --");
        token=resContext.getHeaderString("token");
        log(reqContext.getUri(), resContext.getHeaders());
    }

    private void log(URI uri, MultivaluedMap<String, ?> headers) {
        System.out.println("Request URI: " + uri.getPath());
        System.out.println("Headers:");
        headers.entrySet().forEach(h -> System.out.println(h.getKey() + ": " + h.getValue()));
    }
}



