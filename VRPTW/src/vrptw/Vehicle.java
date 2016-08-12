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
    private int index;
    private int capacity;
    private ArrayList<Vertex> route;
    
    public Vehicle() {
        route = new ArrayList<Vertex>();
    }
    
    public Vehicle(int capacity, ArrayList<Vertex> Route) {
        this.capacity = capacity;
        this.route = Route;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Vertex> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<Vertex> Route) {
        this.route = Route;
    }
    
    boolean capacityConstraint(){
        if(route == null) return false;
        int totalQuantity = 0;
        for (Vertex v:route)
        {
            if(v.getIndex() != 0) totalQuantity += v.getQuantity();
        }
        return capacity >= totalQuantity;   
    }
    
    boolean timeConstraint(){
        if(route == null) return false;
<<<<<<< HEAD
        Vertex v = route.get(route.size()-1);
        if(v.getArrivalTime() > v.getTimeWindow().getEndTime())
                return false;
=======
//        for(Vertex v:route){
//            if(v.getArrivalTime() > v.getTimeWindow().getEndTime())
//                return false;
//        }
        Vertex v = route.get(route.size()-1);
        if(v.getArrivalTime() > v.getTimeWindow().getEndTime()){ // check if the vehicle returns to depot before the endTime of depot
            return false;
        }
>>>>>>> 4b3fe820a42d363693b28e4dcec38a4d6a1f6976
        return true;
    }
    
    boolean routesStartAndEndAtDepot(){
        if(route == null) return false;
        return (route.get(0).getIndex() == 0 ) && 
                (route.get(route.size()-1).getIndex() == 0 );
    }
}
