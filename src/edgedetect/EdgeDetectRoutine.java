package edgedetect;

import java.util.ArrayList;

public class EdgeDetectRoutine {
    public EdgeDetectRoutine(){}
    
    public ArrayList<ShapeRectangle> runRoutine(byte[][][] vals, short[] targ){
        ArrayList<ShapeRectangle> ret = new ArrayList<ShapeRectangle>();
        //System.out.println("Running edge detect routine...");
        
        for(int r = 0; r < vals.length; r++){
            for(int c = 0; c < vals[0].length; c++){
                if(vals[r][c] != null){
                    if(Functions.isInTolerance(vals[r][c], targ)){
                        int[] initEdge = new int[]{r,c};
                        int[] curPos = new int[]{r,c};
                        ShapeRectangle temp = new ShapeRectangle();
                        temp.newPoint(curPos);
                        boolean validShape = true;
                        while(true){
                            int[] newPos = Functions.nextPos(vals, curPos[0], curPos[1], targ);
                            if(newPos[0] == initEdge[0] && newPos[1] == initEdge[1]){
                                //System.out.println("Success");
                                break;
                            }
                            if(newPos[0] == curPos[0] && newPos[1] == curPos[1]){
                                //System.out.println("Didn't Close");
                                break;
                            }
                            curPos = newPos;
                            vals[curPos[0]][curPos[1]] = null;
                            temp.newPoint(curPos);
                        }
                        int density = 0;
                        for(int rr = temp.getMinY(); rr <= temp.getMaxY(); rr++){
                            for(int cc = temp.getMinX(); cc <= temp.getMaxX(); cc++){
                                if(vals[rr][cc] != null && Functions.isInTolerance(vals[rr][cc], targ))
                                    density++;
                                vals[rr][cc] = null;
                            }
                        }
                        temp.setDensity(density);
                        if(validShape && temp.isValid()){
                            //System.out.println("Added");
                            ret.add(temp);
                        }
                    }
                }
            }
        }
        
        return ret;
    }

}
