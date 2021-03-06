/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vrptw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
        for(int i=0; i < T;i++)
        {
            Vehicle vehicle = new Vehicle();
            vehicle.setIndex(i);
            vehicle.setCapacity(vehicleCapacity);
            solution.vehicles.add(vehicle);
        }

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
                    if( (solution.capacityConstraint() && solution.timeConstraint())){
//                        System.out.println("added vertex"+v.getIndex() +" at vehicle "+vh.getIndex());
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
//        System.out.println("Max :  "+max+"   Min:"+ min);
        Random rand =  new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    
    ArrayList<Solution> crossover(Solution Pa, Solution Pb)
    {
        int threshold = 80;
        int p = randInt(0, 100);
        Vehicle vha=null,vhb=null;
        for(int i=0;i<Pa.vehicles.size();i++){
            if(p > threshold){
                vha = Pa.vehicles.get(i) ; 
                vhb = Pb.vehicles.get(i) ; 
                Pa.vehicles.set(i, vhb);                
                Pb.vehicles.set(i, vha); 
                Pa = handleError(Pa, vha);
                Pb = handleError(Pb, vhb);
            }
        }
//        Pa = handleDuplicates(Pa);
//        Pb = handleDuplicates(Pb);
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        solutions.add(Pa);
        solutions.add(Pb);
        return solutions;
    }
    
    Solution mutate(Solution s)
    {
        for(Vehicle vehicle : s.vehicles){
            for(Vertex vertex: vehicle.getRoute())
            {
                if(vertex.getIndex() > 0) // we wouldn't mutate depot
                {
                    //mutate this vetex with probabuility p
                    int p = randInt(1, 100);
                    if(p>80)
                    {
                        //mutate:
                        //save the vertex position
                        int vertexPosition = vehicle.getRoute().indexOf(vertex);
                        
                        // take a random vertex and place it at vertexPosition
                        int newVertexIndex = randInt(1,N);
                        Vertex newVertex = vertices.get(newVertexIndex);
                        
                        //if the new vertex is not already present in route
                        if(vehicle.getRoute().indexOf(newVertex)== -1 ){
                            
                            //replace it with new vertex
                            vehicle.getRoute().set(vertexPosition, 
                                vertices.get(newVertexIndex));
                        }
                            
                        
                    }
                }
            }
            
            s = handleError(s,vehicle);
            
        }
        
        //handle error here
//        s = handleDuplicates(s);
                
        return s;
    }
    Solution handleDuplicates(Solution solution, Vehicle vh){
        
//        int size = solution.vehicles.size();
        boolean arr[] = new boolean[N+1];
        for(Vertex v:solution.vehicles.get(vh.getIndex()).getRoute()){
           arr[v.getIndex()] = true;
        }

        for(Vehicle vehicle: solution.vehicles){
           if(vh.getIndex() != vehicle.getIndex()){
                ArrayList<Integer> toRemove = new ArrayList<Integer>();
                int pos = -1;
                for(Vertex v : vehicle.getRoute()){
                    pos++;
                    if(v.getIndex() != 0){
                        if(!arr[v.getIndex()]){
                            arr[v.getIndex()] = true;                           
                        }else{
                            toRemove.add(pos);
                        }    
                    }
                }
                
                //
                for(int index:toRemove){
                    vehicle.getRoute().remove(index);
                }
           }
       }
        return solution;
    }
    
    Solution handleError(Solution solution, Vehicle vh){ 
        solution= handleDuplicates(solution, vh);
        
//       missing
        int size = solution.vehicles.size();
       boolean flag = false;
       for(Vertex v: vh.getRoute()){
           
           flag = false;
           for(Vehicle vehicle:solution.vehicles){
                if(vehicle.getRoute().indexOf(v) != -1){                   
                   flag = true;
                   break;
                }
           }
           if(!flag){
               while(true){
//                   System.out.println(" Indexing Problem :  It is passed ");
                    int p =randInt(0, size-1);
                    Vehicle vha = solution.vehicles.get(p);
                    Vertex depot = vha.getRoute().get(vha.getRoute().size()-1);
//                    vha.getRoute().remove(depot);
                    vha.getRoute().remove(vha.getRoute().size()-1);
                    vha.getRoute().add(v);
                    vha.getRoute().add(depot);  

                    if(solution.checkConstraints()){
                        break;
                    }
               }
           }
       }
       return solution;        
    }
    
    Solution geneticAlgorithm(){
        int populationSize = 100, totalIteration = 1;
        ArrayList<Solution> P = new ArrayList<Solution>();

        //generate the first random population
        for(int i =0; i< populationSize;i++){
//            System.out.println("creating individual "+i);
            while(true){
                Solution s = generateRandomIndividual();
//                System.out.println(s);
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
//           System.out.println("best: " + best);
           ArrayList<Solution> Q = new ArrayList<Solution>();
           for(int j = 0;j< populationSize/2;j++ ){
               Solution Pa = selectWithReplacement(P);
               Solution Pb = selectWithReplacement(P);
               
               ArrayList<Solution> children = crossover(Pa, Pb);
               Solution Ca = mutate(children.get(0));
               Solution Cb = mutate(children.get(1));
               
               Q.add(Ca);
               Q.add(Cb); 
//                Q.add(children.get(0));
//                Q.add(children.get(1));
           }
           P = Q;
        }
        return best;
    }    

    
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
//        System.out.print("Hello");
        VRPTW vrptw = new VRPTW();
        String filename = "R101.txt";
        vrptw.readInput("solomon_25/"+filename);
        vrptw.initVehicles();
        vrptw.initEdges();
        vrptw.geneticAlgorithm();
        Solution optimalSolution = vrptw.geneticAlgorithm();
        System.out.println("Optimal Solution: "+optimalSolution);
        System.out.println("Cost: "+optimalSolution.cost());
        
        PrintWriter writer = new PrintWriter("Output/"+filename, "UTF-8");
        
        writer.println("Optimal Solution: "+optimalSolution);
        writer.println("Cost: "+optimalSolution.cost());
        writer.close();
    }

}
