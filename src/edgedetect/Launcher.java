package edgedetect;

import javax.swing.JFrame;

public class Launcher {
    
    public static final int resolution = 4;
    public static final short tolerance = 35;
    public static final int minShapeDim = 15;
    
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(false);
        JFrame fr = new JFrame("Application: Launcher");
        Panel im = new Panel(800, 450);
        fr.setContentPane(im);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setLocation(10, 10);
        fr.setResizable(false);
        fr.pack();
        fr.setVisible(true);
        
        //bitwise operators test
        int a = 0xff000000 + (10 << 16) + (160 << 8) + (190);
        int a1 = (a >> 16) & 0xff;
        int a2 = (a >> 8) & 0xff;
        int a3 = (a) & 0xff;
        System.out.println(a1+","+a2+","+a3);
    }
    
}
