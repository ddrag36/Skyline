/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skylines;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;


/**
 * 
 * @author Dimitrios Dragatas 
 * @aem 2676
 * @email ddragatas@csd.auth.gr
 */
public class SkyLines {

    /** 
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    
    public static void main(String[] args) throws IOException  {
        
        // TODO code application logic here
         String fileName=args[0];
         BufferedReader br = new BufferedReader(new FileReader(fileName));//reading the input files
         
         
         int counter =1;
         ArrayList<Point> coordsList= new ArrayList<>();
         String line = null;
         
         while ((line = br.readLine()) != null) {
               
               if (counter==1){
                   counter = counter + 1;
                   continue;    
                }
               
              
               String [] coord=line.split("\\s+");     // break the coordinates based on space or tab 
               
          
                int x = Integer.parseInt(coord[0]);
                int y = Integer.parseInt(coord[1]);
                
                Point p=new Point(x,y);    
              //add the coordinates into Arraylist                
                coordsList.add(p);                   
                
               
               }
         // sorting the arraylist with points by x.If two points have the same x then i put the point with the smallest y into arraylist cause rule of dominance for divide and conquer algorithm is : 
      // x1<=x2 and y1<=y2 and (x1!=x2 or y1!=y2). helped by stackoverflow for sorting!
      //complexity for sorting is O(n log n)

                   Collections.sort(coordsList,new Comparator<Point>() {

         @Override
         public int compare(Point o1, Point o2) {                    
             if (o1.getX()==o2.getX()){
                 return Integer.compare(o1.getY(),o2.getY());         
             }
         return Integer.compare(o1.getX(), o2.getX());
               }         
            });
              
              ArrayList<Point> returned = skyline(coordsList);
         for (int i=0;i<returned.size();i++) {         
              System.out.println(returned.get(i).x+" "+ returned.get(i).y);
                    }
        }
    /**
     * skyline function is using divide and conquer method.Works by breaking down a problem into two or more sub-problems until these become simple enough to be solved directly.
     * @param coordsList
     * @param 
     * @return lSkylineCoords returning the points that dominate
     * 
     */
    
  
   public static ArrayList<Point> skyline(ArrayList<Point>coordsList){
        if (coordsList.size()<=1){
            return coordsList;
        }
        ArrayList<Point>lSkylineCoords=new ArrayList<>();  
        ArrayList<Point>rSkylineCoords=new ArrayList<>();
        ArrayList<Point> lCoords=new ArrayList<>();
        ArrayList<Point> rCoords=new ArrayList<>();
        
        for (int i=0;i<coordsList.size()/2;i++){
        lCoords.add(coordsList.get(i));
            }
        for (int i=coordsList.size()/2;i<coordsList.size();i++){
            rCoords.add(coordsList.get(i));
            }
   
    lSkylineCoords=skyline(lCoords);   //using recursive function to break the problem into sub-problems 
    rSkylineCoords=skyline(rCoords);   //complexity for recursive is O(nlogn)
   
    int minimumy=2000;
     for (int i=0;i<lSkylineCoords.size();i++){      //This is the conquer method.Finding the minimum y of left half. 
                                                     //based on https://stackoverflow.com/questions/49603454/skyline-of-2d-points-divide-and-conquer-algorithm
                                                     //compexity for for loops O(n)
   if (lSkylineCoords.get(i).y<minimumy){
            minimumy=lSkylineCoords.get(i).y;       
        }
    }
     
   int j=0;
      
    while (j < rSkylineCoords.size()){         //for n points complexity is O(n)
          if (rSkylineCoords.get(j).y >= minimumy){  // removing every point from the right half with bigger y than left's half minimum y.When i remove a point, the rest is shifted left so i used while loop and omit incrementing index if removing occurs.
          rSkylineCoords.remove(j);     
        }
       else{
         j++;
      }  
          } 
    lSkylineCoords.addAll(rSkylineCoords);
    return lSkylineCoords;   
       
    }
    
     
  public static class Point{

        
    public int x, y;
    public Point (int x, int y){
      this.x = x;
      this.y = y;
    }
       public int getX() {
        return x;
    }

      
        public void setX(int x) {
        this.x = x;
    }

       
        public int getY() {
        return y;
    }

       
        public void setY(int y) {
        this.y = y;
    }
   
    }
}

