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
    private int[] target = new int[]{151, 53, 42};
    private ArrayList<ShapeRectangle> shapes;
    int[] vals;
    
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
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.drawImage(pimg.getImage(), 0, 0,pimg.getWidth(), pimg.getHeight(), this);
        
//        drawResult(g);
        
        for(ShapeRectangle s : shapes)
            s.drawSquare(g);
    }
    
    public void drawResult(Graphics g){
        int r = 0;
        int c = 0;
        while(r < pimg.getHeight()){
            if(vals[r*pimg.getWidth()+c] != -1){
                int[] col = Functions.getRGB(vals, r, c, pimg.getWidth());
                g.setColor(new Color(col[0],col[1],col[2]));
            }
            else
                g.setColor(Color.YELLOW);
            g.fillRect(c, r, Launcher.resolution, Launcher.resolution);
            c+=Launcher.resolution;
            if(c > pimg.getWidth()){
                c = 0;
                r+=Launcher.resolution;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getX() <= pimg.getWidth() && e.getY() <= pimg.getHeight()){
            long st = System.nanoTime();
            int rgb = pimg.getImage().getRGB(e.getX(), e.getY());
            vals = pimg.getOrganizedData();
            //target = new int[]{(rgb >> 16) & 0x000000ff,(rgb >> 8) & 0x000000ff,(rgb) & 0x000000ff};
            target = Functions.getRGB(vals, e.getY(), e.getX(), pimg.getWidth());
            //System.out.println(target[0]+","+target[1]+","+target[2]);
            //System.out.println(functions.isInTolerance(new byte[]{(byte)255,(byte)255,(byte)255}, target, tolerance));
            shapes = new EdgeDetectRoutine().runRoutine(vals, target,pimg.getWidth(),pimg.getHeight());
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
