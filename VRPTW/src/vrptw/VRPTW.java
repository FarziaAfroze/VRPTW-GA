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
        edges =  new ArrayList<Edge>();
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
                System.out.println(v + " " + v.getTimeWindow()+ " Service time:"+ v.getServiceTime());
            }
            N=index;
//            generateRandomIndividual();
            System.out.println("-------------------------------------------");
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VRPTW.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            scanner.close();
            
        }
        

    }
    
    void initVehicles(){
//        System.out.println("size "+vehicles.size());
        for(int i = 0;i<T;i++)
        {
           Vehicle v = new Vehicle();
           v.setIndex(i);
           v.setCapacity(vehicleCapacity);
           vehicles.add(v);
        }
//        System.out.println("size "+vehicles.size());
        System.out.println("Initialized "+vehicles.size()+" vehicles.");
        System.out.println("-------------------------------------------");
    }
    
    void initEdges(){
        for(int i  = 0; i< vertices.size() ;i++){
            for(int j= i+1;j<vertices.size();j++){
//                System.out.println("creating Edge:("+i+","+j+")");
                
                //calculate cost of vertex i and vertex j
                Vertex start = vertices.get(i);
                Vertex end = vertices.get(j);
                
                //distance = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
                double distance = Math.sqrt(
                        (start.getCoord_x()-end.getCoord_x())*(start.getCoord_x()-end.getCoord_x())
                      + (start.getCoord_y()-end.getCoord_y())*(start.getCoord_y()-end.getCoord_y()));
                
                Edge e = new Edge(start, end, distance);
                edges.add(e);
            }
        }
        System.out.println("created "+edges.size()+" Edges.");
        System.out.println("-------------------------------------------");
        
    }  
    
    Solution generateRandomIndividual(){
        
        Solution solution = new Solution(N); 
        solution.vehicles = vehicles;
//        System.out.println("solution.vehicles.size(): "+solution.vehicles.size());
        
        for(Vehicle vh : solution.vehicles){
            vh.getRoute().add(vertices.get(0)); //adding depot to all vehicles route as starting point
        }
        
        for(Vertex v : vertices ){ 
            if(v.getIndex() > 0){  // ignore vertex at 0 which is depot
               while(true){
                    int i= randInt(0, T-1);                
                    Vehicle vh = solution.vehicles.get(i);
                    vh.getRoute().add(v);
//                    solution.vehicles.add(vh);
                    if( (solution.capacityConstraint() && solution.timeConstraint())){
                       break;
                    } else{
                        solution.vehicles.get(i).getRoute().remove(v);
                    }
               }
            }
        }
        
        for(Vehicle vh : solution.vehicles){
            vh.getRoute().add(vertices.get(0)); //adding depot to all vehicles route
        }
//        System.out.println(solution.vehicles.size());
        if(solution.checkConstraints()){
            return solution ;
        }
        return null;        
        
    }
    
    Solution selectWithReplacement(ArrayList<Solution> solutions){
        int t = randInt(3,10) ;// random tournamnet size
        Solution best = getRandomIndividual(solutions);
        for(int i=2;i<t;i++){
            Solution next = getRandomIndividual(solutions);
            if(next.fitness() > best.fitness()){
                best = next;
            }
        }
        return best;
    }
    
    Solution getRandomIndividual(ArrayList<Solution> solutions){
        int t = randInt(0,solutions.size()-1) ;
        return solutions.get(t);
    }
    
    int randInt(int min, int max) {   
        Random rand =  new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    
    ArrayList<Solution> crossover(Solution Pa, Solution Pb)
    {
        return null;
    }
    
    Solution mutate(Solution s)
    {
        return null;
    }
    
    Solution geneticAlgorithm(){
        int populationSize = 100, totalIteration = 10;
        ArrayList<Solution> P = new ArrayList<Solution>();
        
        //generate the first random population
        for(int i =0; i< populationSize;i++){
            while(true){
                Solution s = generateRandomIndividual();
                if(s != null)
                {
                    P.add(s);
                    break;
                }
            } 
        }
        
        Solution best = null;//stores the best solution so far
        
        for(int i = 0; i<totalIteration ; i++){
           System.out.println("iteration "+i);
           for(Solution Pi : P){//assess fitness and populate best
               double fitness = Pi.fitness();
               if(best == null || fitness > best.fitness())
                   best = Pi;
           }
           ArrayList<Solution> Q = new ArrayList<Solution>();
           for(int j = 0;j< populationSize/2;j++ ){
               Solution Pa = selectWithReplacement(P);
               Solution Pb = selectWithReplacement(P);
               
               ArrayList<Solution> children = crossover(Pa, Pb);
               Solution Ca = mutate(children.get(0));
               Solution Cb = mutate(children.get(1));
               
               Q.add(Ca);
               Q.add(Cb); 
           }
           P = Q;
        }
        return best;
    }
    
    public static void main(String[] args) {
//        System.out.print("Hello");
        VRPTW vrptw = new VRPTW();
        vrptw.readInput("R101.txt");
        vrptw.initVehicles();
        vrptw.initEdges();
        System.out.println(vrptw.generateRandomIndividual());  
    }

}
