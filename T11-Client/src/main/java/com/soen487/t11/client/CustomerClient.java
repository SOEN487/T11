package com.soen487.t11.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;


public class CustomerClient {

    public static void main(String[] args) throws InterruptedException {
        createUser("Hamed","1234");
        login("Hamed","1234");
        createCustomer("Ali", 30);
        getCustomers();
        Thread.sleep(5000);
        createCustomer("Nick", 24);
        createCustomer("Hamed", 28);
        getCustomers();
        logout("Hamed");
    }

    /**
     * Gets the list of customers by calling the API
     * @return string representation of all the customers currently in the restaurant
     */

    private static void createUser(String username, String password) {
        Client client = ClientBuilder.newBuilder().register(new CheckRequestFilter()).build();
        String Uri="http://localhost:8080/restaurant/user/register/"+username+"/"+password;
        WebTarget allOrderTarget = client.target(Uri);
        String responseCreateUser = allOrderTarget.request().post(Entity.text("Create User from Client"),String.class);
        System.out.println("Response for registering user: " + responseCreateUser);
    }

    private static void login(String username, String password) {
        Client client = ClientBuilder.newBuilder().register(new CheckRequestFilter()).build();
        String Uri="http://localhost:8080/restaurant/user/login/"+username+"/"+password;
        WebTarget allOrderTarget = client.target(Uri);
        String responseLogin = allOrderTarget.request().post(Entity.text("login user"),String.class);
        System.out.println("Response for login: " + responseLogin);
    }
    private static void createCustomer(String name, int age) {
        Client client = ClientBuilder.newBuilder().register(new CheckRequestFilter()).build();
        String Uri="http://localhost:8080/restaurant/customerform/createCustomer/"+name+"/"+age;
        WebTarget allOrderTarget = client.target(Uri);
        String responseCreateCustomer = allOrderTarget.request().post(Entity.text("create Customer"),String.class);
        System.out.println("Response for creating Customer: " + responseCreateCustomer);
    }
    private static String getCustomers() {
        Client client = ClientBuilder.newBuilder().register(new CheckRequestFilter()).build();
        WebTarget allOrderTarget = client.target("http://localhost:8080/restaurant/customerform");
        String responseGet = allOrderTarget.request().get(String.class);;
        System.out.println("Response for Getting Customer: " + responseGet);
        return responseGet;
    }

    private static void logout(String username) {
        Client client = ClientBuilder.newBuilder().register(new CheckRequestFilter()).build();
        String Uri="http://localhost:8080/restaurant/user/logout/"+username;
        WebTarget allOrderTarget = client.target(Uri);
        String responseLogout = allOrderTarget.request().post(Entity.text("logout user"),String.class);
        System.out.println("Response for logout: " + responseLogout);
    }

}
