package com.soen487.t11.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.*;
import java.io.*;
import java.util.Date;

@Provider
public class ServerInterceptor implements WriterInterceptor {
    @Context
    private UriInfo uriInfo;
    @Override
    public void aroundWriteTo(WriterInterceptorContext context)
            throws IOException, WebApplicationException {
        System.out.println("-- in MyInterceptor#aroundWriteTo() --");
        if (!uriInfo.getPath().contains("login") && !uriInfo.getPath().contains("createCustomer")) {
            context.proceed();
            return;
        }
        Date timeNow = new Date();
        OutputStream originalStream = context.getOutputStream();
        // Setting up a temporary output stream for the chain to write into
        final ByteArrayOutputStream mem = new ByteArrayOutputStream();
        context.setOutputStream(mem);
        context.proceed();
        // The response is now in the memory stream.
        byte[] data = mem.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        // Parsing the generated output (from the chain
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tree = mapper.readTree(inputStream);
        // Adding the timestamp
        ((ObjectNode) tree).put("time", timeNow.toString());
        data = mapper.writeValueAsBytes(tree);
        // Setting the output stream back to the original and writing the modified response
        originalStream.write(data, 0, data.length);
        context.setOutputStream(originalStream);
    }

}


















