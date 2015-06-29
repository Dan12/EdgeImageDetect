package edgedetect;

import javax.swing.JFrame;

public class Launcher {
    
    public static final int resolution = 4;
    public static final short tolerance = 35;
    public static final int minShapeDim = 4;
    
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(false);
        JFrame fr = new JFrame("Application: ImagePixels");
        Panel im = new Panel(800, 450);
        fr.setContentPane(im);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setLocation(10, 10);
        fr.setResizable(false);
        fr.pack();
        fr.setVisible(true); 
    }
    
}
