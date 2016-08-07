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
public class Vertex {
    private int index;
    private int coord_x;
    private int coord_y;

    public Vertex(int index, int coord_x, int coord_y) {
        this.index = index;
        this.coord_x = coord_x;
        this.coord_y = coord_y;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCoord_x() {
        return coord_x;
    }

    public void setCoord_x(int coord_x) {
        this.coord_x = coord_x;
    }

    public int getCoord_y() {
        return coord_y;
    }

    public void setCoord_y(int coord_y) {
        this.coord_y = coord_y;
    }

    @Override
    public String toString() {
        return ""+index + "(" + coord_x + "," + coord_y + '}';
    }
    
    
    
}
