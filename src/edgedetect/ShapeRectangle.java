package edgedetect;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

public class ShapeRectangle {
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    private Random rnd;
    private float density;
    
    public ShapeRectangle(){
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;
        rnd = new Random();
        density = 0;
    }
    
    public void drawSquare(Graphics g){
        //g.setColor(new Color(rnd.nextInt(200),rnd.nextInt(200),rnd.nextInt(200),100));
        g.setColor(new Color(0,255,0,100));
        g.fillRect(minX, minY, (maxX-minX+Launcher.resolution), (maxY-minY+Launcher.resolution));
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.BLACK);
        //g.drawString(String.format("%.2f", ((float)density)/getArea()), minX+4, maxY-4);
        g.drawString(String.format("%.2f", density), minX+4, maxY-4);
    }
    
    public void newPoint(int[] p){
        setMinX(p[1]);
        setMinY(p[0]);
        setMaxX(p[1]);
        setMaxY(p[0]);
    }
    
    private int getArea(){
        return (maxX-minX)*(maxY-minY);
    }
    
    public boolean isValid(){
        return (maxX-minX > Launcher.minShapeDim && maxY-minY > Launcher.minShapeDim);
    }
    
    private void setMinX(int m){
        if(m < minX)
            minX = m;
    }
    
    private void setMinY(int m){
        if(m < minY)
            minY = m;
    }
    
    private void setMaxX(int m){
        if(m > maxX)
            maxX = m;
    }
    
    private void setMaxY(int m){
        if(m > maxY)
            maxY = m;
    }
    
    public void setDensity(float d){
        density = d;
    }
    
    public int getMinX(){
        return minX;
    }
    
    public int getMinY(){
        return minY;
    }
    
    public int getMaxX(){
        return maxX;
    }
    
    public int getMaxY(){
        return maxY;
    }
    
    public int getWidth(){
        return maxX-minX;
    }
    
    public int getHeight(){
        return maxY-minY;
    }
    
    public int getCenterX(){
        return (minX+(maxX-minX)/2)*Launcher.resolution;
    }
    
    public int getCenterY(){
        return (minX+(maxX-minX)/2)*Launcher.resolution;
    }
}

