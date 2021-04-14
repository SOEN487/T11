package com.soen487.t11.rest;

import javax.ws.rs.*;
import java.util.ArrayList;

@Path("/customerform")
public class CustomerRestForm {
    /**
     * Class for holding the list of customers and handling the requests
     */

    private static ArrayList<Customer> customers = new ArrayList<>();

    /**
     * Meant for returning the list of customers
     *
     * @return A concatenation of the toString method for all customers
     * //
     */
    @GET
    @Produces("application/json")
    public ArrayList<Customer> getCustomers() {
        return customers;
    }



    @POST
    @Path("/createCustomer/{name}/{age}")
    @Produces("application/json")
    public Customer createCustomer(@PathParam("name") String name, @PathParam("age") int age) {
        Customer newCustomer = new Customer(name, age);
        customers.add(newCustomer);
        return newCustomer;
    }
}

