package vrptw;


import java.util.ArrayList;
import vrptw.Vertex;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DR
 */
public class Vehicle {
    private int capacity;
    private ArrayList<Vertex> Route;

    public Vehicle(int capacity, ArrayList<Vertex> Route) {
        this.capacity = capacity;
        this.Route = Route;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Vertex> getRoute() {
        return Route;
    }

    public void setRoute(ArrayList<Vertex> Route) {
        this.Route = Route;
    }
    
    
}
