/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vrptw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DR
 */
public class VRPTW {
    int T ;// number of vehicles
    int vehicleCapacity;
    ArrayList<Vertex> vertices;
    /**
     * @param args the command line arguments
     */
    void readInput(String filename){
        String text = null;
        int input,index,x,y,start,end;
        System.out.println("Reading Dataset...");
        
        File file = new File(filename);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            text = scanner.next();
            System.out.println("Dataset name: "+ text);
            
            //skip lines
            for(int i = 0;i<4;i++)text = scanner.nextLine();

            T = scanner.nextInt();
            System.out.println("Number of Vehicles: "+ T);
            
            vehicleCapacity = scanner.nextInt();
            System.out.println("Capacity of Vehicles: "+ vehicleCapacity);
            
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
                text = scanner.nextLine();
                System.out.println(v + " " + v.getTimeWindow()+ " Service time:"+ v.getServiceTime());
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VRPTW.class.getName()).log(Level.SEVERE, null, ex);
        }

//        BufferedReader reader = null;
//        
//        try {
//            reader = new BufferedReader(new FileReader(file));
//            text = reader.readLine();
//            System.out.println("Dataset name: "+ text);
//            text =  reader.readLine();//blank line
//            text =  reader.readLine();//VEHICLE
//            text =  reader.readLine();//NUMBER     CAPACITY
//            T = reader.read
//            
//            while ((text = reader.readLine()) != null) {
//                System.out.println(text);
////                list.add(Integer.parseInt(text));
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (reader != null) {
//                    reader.close();
//                }
//            } catch (IOException e) {
//            }
//        }
    }
    
    public static void main(String[] args) {
        
           VRPTW vrptw = new VRPTW();
           vrptw.readInput("R101.txt");
//print out the list
//        System.out.println(list);
    }

}
