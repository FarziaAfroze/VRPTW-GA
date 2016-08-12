/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vrptw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author DR
 */
public class VRPTW {
    int T ;// number of vehicles
    int N; // number of vertices
    int vehicleCapacity;
    ArrayList<Vertex> vertices;
    ArrayList<Vehicle> vehicles;
    ArrayList<Edge> edges;
    
    public VRPTW(){
        vertices = new ArrayList<Vertex>() ;
        vehicles =  new ArrayList<Vehicle>();
    }
    
    void readInput(String filename){
        String text = null;
        int input,index=0,x,y,start,end;
        System.out.println("Reading Dataset...");
        
        File file = new File(filename);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            text = scanner.next();
            System.out.println("Dataset name: "+ text);
            
            //skip lines
            for(int i = 0;i<4;i++)text = scanner.nextLine();

            T = scanner.nextInt();
//            System.out.println("Number of Vehicles: "+ T);
            
            vehicleCapacity = scanner.nextInt();
//            System.out.println("Capacity of Vehicles: "+ vehicleCapacity);
            
            //skip lines
            for(int i = 0;i<4;i++)text = scanner.nextLine();
            
            while(scanner.hasNext())
            {
                index = scanner.nextInt();
                x = scanner.nextInt();
                y = scanner.nextInt();
                Vertex v = new Vertex(index, x, y);
                v.setQuantity(scanner.nextInt());
                start = scanner.nextInt();
                end = scanner.nextInt();
                TimeWindow t = new TimeWindow(start, end);
                v.setTimeWindow(t);
             
                v.setServiceTime(scanner.nextInt());
                vertices.add(v);
                
                text = scanner.nextLine();
//                System.out.println(v + " " + v.getTimeWindow()+ " Service time:"+ v.getServiceTime());
            }
            N=index;
            generateRandomIndividual();
            System.out.println("-------------------------------------------");
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VRPTW.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            scanner.close();
            
        }
        

    }
    
    void initVehicles(){
        
        for(int i = 0;i<T;i++)
        {
           Vehicle v = new Vehicle();
           v.setIndex(i);
           v.setCapacity(vehicleCapacity);
           vehicles.add(v);
        }
    }
    
    void initEdges(){
        for(int i  = 0; i< vertices.size() ;i++){
            for(int j= i+1;j<vertices.size();j++){
                System.out.println("creating Edge:("+i+","+j+")");
                
                //calculate cost of vertex i and vertex j
                Vertex start = vertices.get(i);
                Vertex end = vertices.get(j);
                
                //distance = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
                double distance = Math.sqrt(
                        (start.getCoord_x()-end.getCoord_x())*(start.getCoord_x()-end.getCoord_x())
                      + (start.getCoord_y()-end.getCoord_y())*(start.getCoord_y()-end.getCoord_y()));
                
                Edge e = new Edge(start, end, distance);
                
            }
        }
    }  
    
    Solution generateRandomIndividual(){
        
        Solution solution = new Solution(N);  
        for(Vehicle vh : solution.vehicles){
            vh.getRoute().add(vertices.get(0)); //adding depot to all vehicles route as starting point
        }
        for(Vertex v : vertices ){ 
            if(v.getIndex() > 0){  // ignore vertex at 0 which is depot
               while(true){
                    int i= randInt(0, T-1);                
                    Vehicle vh = vehicles.get(i);
                    vh.getRoute().add(v);
                    solution.vehicles.add(vh);
                    if( (solution.capacityConstraint() && solution.timeConstraint())){
                       break;
                    } else{
                        solution.vehicles.remove(vh);
                    }
               }
            }
        }
        
        for(Vehicle vh : solution.vehicles){
            vh.getRoute().add(vertices.get(0)); //adding depot to all vehicles route
        }
        
        if(solution.checkConstraints()){
            return solution ;
        }
        return null;        
        
    }
    int randInt(int min, int max) {  
        Random rand = null;  
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    
    public static void main(String[] args) {
        System.out.print("Hello");
        VRPTW vrptw = new VRPTW();
        vrptw.readInput("R101.txt");
        vrptw.initVehicles();
        vrptw.initEdges();
        
        
//print out the list
//        System.out.println(list);
    }

}
