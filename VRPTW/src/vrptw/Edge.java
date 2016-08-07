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
    int cost;

    public Edge(Vertex start, Vertex end, int cost) {
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    
    
}
