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
    
    public int[] getOrganizedData(){
        int[] ret = new int[width*height];
        byte[] tempPix = getPixelData();
        int j = 0;
        for(int i = 0; i < tempPix.length; i+=3){
            ret[j] = 0xff000000 + (tempPix[i] << 16) + (tempPix[i+1] << 8) + (tempPix[i+2]);
            j++;
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
