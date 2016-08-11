/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vrptw;

import java.util.ArrayList;

/**
 *
 * @author DR
 */
public class Solution {
    public ArrayList<Vehicle> vehicles;

    public Solution() {
        vehicles = new  ArrayList<Vehicle>() ;
                
    }
    
    boolean checkConstraints(){
        /*
        every route starts and ends at depot vertex
        each customer served exactly once and by exactly one vehicle
        time and capacity constraints are maintained
        */
        return routesStartAndEndAtDepot() && uniquenessConstraint() 
                && timeConstraint() && capacityConstraint();
    }
    boolean routesStartAndEndAtDepot(){
        for(Vehicle vehicle : vehicles)
        {
            if(!vehicle.routesStartAndEndAtDepot()) return false;
        }
        return true;
    }
    boolean uniquenessConstraint(){
        return false;
    }
    boolean timeConstraint(){
        for(Vehicle vehicle : vehicles)
        {
            if(!vehicle.capacityConstraint()) return false;
        }
        return true;
    }
    boolean capacityConstraint(){
        for(Vehicle vehicle : vehicles)
        {
            if(!vehicle.capacityConstraint()) return false;
        }
        return true;
    }
}

