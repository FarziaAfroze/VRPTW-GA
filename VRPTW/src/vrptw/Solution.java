/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vrptw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author DR
 */
public class Solution {
    public ArrayList<Vehicle> vehicles;
    int totalVertex;

    public Solution(int totalVertex) {
        this.vehicles = new ArrayList<Vehicle>() ; 
        this.totalVertex = totalVertex;
    }
    
    boolean checkConstraints(){
        /*
        every route starts and ends at depot vertex
        each customer served exactly once and by exactly one vehicle
        time and capacity constraints are maintained
        */
        return routesStartAndEndAtDepot() && uniquenessConstraint() 
                && timeConstraint() 
                && capacityConstraint();
    }
    boolean routesStartAndEndAtDepot(){
//        for(Vehicle vehicle : vehicles)
//        {
//            if(!vehicle.routesStartAndEndAtDepot()) return false;
//        }
        return true;
    }
    boolean uniquenessConstraint(){
        
//        List<Boolean> isCovered= new ArrayList<Boolean>(Arrays.asList(new Boolean[totalVertex]));
//        Collections.fill(isCovered, Boolean.FALSE);
//        
//        for(Vehicle vehicle : vehicles)
//        {
//            if(vehicle.getIndex() == 0) isCovered.set(0, Boolean.TRUE);
//            else{
//                if(isCovered.get(vehicle.getIndex())== true) 
//                    return false;
//                isCovered.set(vehicle.getIndex(), Boolean.TRUE);
//            }
//                    
//        }
//        
//        for(boolean b :isCovered)
//        {
//            if(!b) return false;
//        }
        
         return true;
    }
    boolean timeConstraint(){
//        for(Vehicle vehicle : vehicles)
//        {
//            if(!vehicle.timeConstraint()) return false;
//        }
        return true;
    }
    boolean capacityConstraint(){
//        for(Vehicle vehicle : vehicles)
//        {
//            if(!vehicle.capacityConstraint()) return false;
//        }
        return true;
    } 
        
    double cost(){ 
        double totalTime=0,time=0,penalty,waitingTime;
        Vertex u= null;
        for(Vehicle vh : vehicles){            
            time =0;
            for(Vertex v: vh.getRoute()){               
                if(v.getIndex() == 0){
                    u = v;
                }else{                    
                    time += calculateDistance(u, v);
                    v.setArrivalTime(time);
                    u = v;                    
                    if(time > v.getTimeWindow().getEndTime()){
                        penalty = time - v.getTimeWindow().getEndTime();
                        time += penalty;
                    }else if(time < v.getTimeWindow().getStartTime()){
                        waitingTime = v.getTimeWindow().getStartTime() - time;
                        time += waitingTime;
                    }
                    time += v.getServiceTime();
                }
            }
            totalTime += time;            
        }
        return totalTime;
    }
    
    double calculateDistance(Vertex start,Vertex end){
        double distance = Math.sqrt(
                               (start.getCoord_x()-end.getCoord_x())*(start.getCoord_x()-end.getCoord_x())
                             + (start.getCoord_y()-end.getCoord_y())*(start.getCoord_y()-end.getCoord_y()));
        return distance;    
    }
    
    double fitness(){
        return 1.0 / cost();
    }
    
    @Override
    public String toString() {
        String solution="";
        for(Vehicle vh : vehicles){
            solution += vh.getIndex() + "  : "; 
            for(Vertex v : vh.getRoute()){
               solution += v + "->";   
            }
            solution += "\n";
        }
        return solution;
    }
}

