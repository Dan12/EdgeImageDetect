package edgedetect;

public class Functions {    
    public Functions(){}
    
    public static boolean isInTolerance(byte[] test, short[] targ){
        return Math.abs((short)(test[0] & 0xff)-targ[0]) <= Launcher.tolerance && Math.abs((short)(test[1] & 0xff)-targ[1]) <= Launcher.tolerance && Math.abs((short)(test[2] & 0xff)-targ[2]) <= Launcher.tolerance;
    }
    
    public static int[] nextPos(byte[][][] vals, int r, int c, short[] targ){
        boolean seenOpenPixel = false;
        int pointerNum = 0;
        int[] ret = new int[]{r,c};
        for(int i = 0; i < 8; i++){
            int[] pointer = mapPointerNum(i,0);
            if(inBounds(vals,r+pointer[1],c+pointer[0]) && vals[r+pointer[1]][c+pointer[0]] != null && !isInTolerance(vals[r+pointer[1]][c+pointer[0]], targ)){
                pointerNum = i;
                seenOpenPixel = true;
                break;
            }     
        }
        if(seenOpenPixel){
            for(int i = 0; i < 8; i++){
                int[] pointer = mapPointerNum(i,pointerNum);
                if(inBounds(vals,r+pointer[1],c+pointer[0]) && vals[r+pointer[1]][c+pointer[0]] != null && isInTolerance(vals[r+pointer[1]][c+pointer[0]], targ)){
                    ret = new int[]{r+pointer[1],c+pointer[0]};
                    break;
                }     
            }
        }
        return ret;
    }
    
    private static boolean inBounds(byte[][][] vals, int r, int c){
        return (r >= 0 && r < vals.length && c >= 0 && c < vals[0].length);
    }
    
    //x,y
    private static int[] mapPointerNum(int i, int s){
        int[] ret = new int[]{1,0};
        int test = s+i;
        if(test > 7)
            test -= 8;
        switch (test){
            case 0:
                ret = new int[]{1,-1};
                break;
            case 1:
                ret = new int[]{1,0};
                break;
            case 2:
                ret = new int[]{1,1};
                break;
            case 3:
                ret = new int[]{0,1};
                break;
            case 4:
                ret = new int[]{-1,1};
                break;
            case 5:
                ret = new int[]{-1,0};
                break;
            case 6:
                ret = new int[]{-1,-1};
                break;
            case 7:
                ret = new int[]{0,-1};
                break;
        }
        return ret;
    }
}
