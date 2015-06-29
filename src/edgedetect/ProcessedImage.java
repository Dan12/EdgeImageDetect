package edgedetect;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ProcessedImage {
    private final BufferedImage image;
    private final int width;
    private final int height;
    private final boolean alpha;
    
    public ProcessedImage(BufferedImage bf){
        image = bf;
        width = image.getWidth();
        height = image.getHeight();
        alpha = image.getAlphaRaster() != null;
    }
    
    public byte[][][] getOrganizedData(){
        byte[] tempPix = getPixelData();
        int retWidth = width/Launcher.resolution;
        int retHeight = height/Launcher.resolution;
        byte[][][] ret = new byte[retHeight][retWidth][3];
        int widthCutoff = (width%Launcher.resolution)*3;
        int row = 0;
        int col = 0;
        int tempPixInc = 3*Launcher.resolution;
        int rowInc = (Launcher.resolution-1)*width*3;
        for(int i = 0; i < tempPix.length; i+=tempPixInc){
            ret[row][col] = new byte[]{tempPix[i+2],tempPix[i+1],tempPix[i]};
            col++;
            if(col >= retWidth){
                col = 0;
                row++;
                i+=widthCutoff;
                i+=rowInc;
            }
            if(row >= retHeight)
                break;
        }
        return ret;
    }
    
    private byte[] getPixelData(){
        return ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    }
    
    public BufferedImage getImage(){
        return image;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public boolean hasAlpha(){
        return alpha;
    }
}
