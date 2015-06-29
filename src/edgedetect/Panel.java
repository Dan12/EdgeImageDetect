package edgedetect;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Panel extends JPanel implements MouseListener{
    
    private ProcessedImage pimg;
    private short[] target = new short[]{151, 53, 42};
    private ArrayList<ShapeRectangle> shapes;
    byte[][][] vals;
    byte[][][] resVals;
    
    public Panel(int w, int h){
        super.setPreferredSize(new Dimension(w, h));
        super.setBackground(Color.WHITE);
        addMouseListener(this);
        BufferedImage tempImg = null;
        try {
            tempImg = ImageIO.read(Launcher.class.getResource("/res/redDot.jpg"));
        } catch (IOException ex) {System.out.println("Error Retrieving Image");}
        
        pimg = new ProcessedImage(tempImg);
        
        shapes = new ArrayList<ShapeRectangle>();
        
        vals = pimg.getOrganizedData();
        resVals = pimg.getOrganizedData();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.drawImage(pimg.getImage(), 0, 0,pimg.getWidth(), pimg.getHeight(), this);
        
        g.setColor(new Color(0,255,0,100));
        for(int r = 0; r < resVals.length; r++){
            for(int c = 0; c < resVals[0].length; c++){
                if(Functions.isInTolerance(resVals[r][c], target))
                   g.fillRect(c*Launcher.resolution, r*Launcher.resolution, Launcher.resolution, Launcher.resolution);
            }
        }   
        
        for(ShapeRectangle s : shapes)
            s.drawSquare(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getX() <= pimg.getWidth() && e.getY() <= pimg.getHeight()){
            int rgb = pimg.getImage().getRGB(e.getX(), e.getY());
            target = new short[]{(short)((rgb >> 16) & 0x000000FF),(short)((rgb >> 8) & 0x000000FF),(short)((rgb) & 0x000000FF)};
            //System.out.println(target[0]+","+target[1]+","+target[2]);
            //System.out.println(functions.isInTolerance(new byte[]{(byte)255,(byte)255,(byte)255}, target, tolerance));
            long st = System.nanoTime();
            vals = pimg.getOrganizedData();
            shapes = new EdgeDetectRoutine().runRoutine(vals, target);
            repaint();
            long et = System.nanoTime();
            System.out.println((et-st)+"us , "+((et-st)/1000000)+"ms");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}
