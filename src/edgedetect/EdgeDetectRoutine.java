package edgedetect;

import java.util.ArrayList;

public class EdgeDetectRoutine {
    public EdgeDetectRoutine(){}
    
    public ArrayList<ShapeRectangle> runRoutine(int[] vals, int[] targ, int w, int h){
        ArrayList<ShapeRectangle> ret = new ArrayList<ShapeRectangle>();
        //System.out.println("Running edge detect routine...");
        System.out.println(targ[0]+","+targ[1]+","+targ[2]);
        int r = 0;
        int c = 0;
        while(r < h){
            if(vals[r*w+c] != -1){
                if(Functions.isInTolerance(vals[r*w+c], targ)){
                    int[] initEdge = new int[]{r,c};
                    int[] curPos = new int[]{r,c};
                    ShapeRectangle temp = new ShapeRectangle();
                    temp.newPoint(curPos);
                    boolean validShape = true;
                    int startPointer = 0;
                    while(true){
                        //System.out.println(curPos[0]+","+curPos[1]);
                        int[] newPos = Functions.nextPos(vals, curPos[0], curPos[1], targ, w, h, startPointer);
                        if(newPos[0] == initEdge[0] && newPos[1] == initEdge[1]){
                            //System.out.println("Success");
                            break;
                        }
                        if(newPos[0] == curPos[0] && newPos[1] == curPos[1]){
                            //System.out.println("Didn't Close");
                            break;
                        }
                        curPos = new int[]{newPos[0],newPos[1]};
                        if(vals[curPos[0]*w+curPos[1]] != -3)
                            vals[curPos[0]*w+curPos[1]] = -2;
                        temp.newPoint(curPos);
                        startPointer = newPos[2]+5;
                        while(startPointer > 7)
                            startPointer-=8;
                    }
                    int density = 0;
                    int timesChecked = 0;
                    for(int rr = temp.getMinY()+(temp.getHeight()/10); rr <= temp.getMaxY()-(temp.getHeight()/10); rr+=(temp.getHeight()/10)*Launcher.resolution+1){
                        for(int cc = temp.getMinX()+(temp.getWidth()/10); cc <= temp.getMaxX()-(temp.getHeight()/10); cc+=(temp.getWidth()/10)*Launcher.resolution+1){
                            if(vals[rr*w+cc] != -1 && (vals[rr*w+cc] == -2 || vals[rr*w+cc] == -3 || Functions.isInTolerance(vals[rr*w+cc], targ)))
                                density++;
                            timesChecked++;
                        }
                    }
                    for(int rr = temp.getMinY(); rr <= temp.getMaxY(); rr++){
                        for(int cc = temp.getMinX(); cc <= temp.getMaxX(); cc++){
                            vals[rr*w+cc] = -1;
                        }
                    }
                    temp.setDensity(((float)density)/timesChecked);
                    if(validShape && temp.isValid()){
                        //System.out.println("Added");
                        //System.out.println(density+","+timesChecked);
                        ret.add(temp);
                    }
                }
            }
            c+=Launcher.resolution;
            if(c >= w){
                r+=Launcher.resolution;
                c = 0;
            }
        }
        
        return ret;
    }

}
