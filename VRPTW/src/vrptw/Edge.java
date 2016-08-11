/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vrptw;

/**
 *
 * @author DR
 */
public class Edge {
    private Vertex start;
    private Vertex end;
    double cost;

    public Edge(Vertex start, Vertex end, double cost) {
        this.start = start;
        this.end = end;
        this.cost = cost;
    }

    public Vertex getStart() {
        return start;
    }

    public void setStart(Vertex start) {
        this.start = start;
    }

    public Vertex getEnd() {
        return end;
    }

    public void setEnd(Vertex end) {
        this.end = end;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    
}
