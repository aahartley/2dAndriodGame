package com.example.a2dgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Obstacle {
    private Bitmap ob;
    private static int x,y,dx;
    private int width =90;
    private int height=85;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int vY = 5;


    public Obstacle(Bitmap bMap){
        ob = bMap;
        x= screenWidth/2-500;
        y =screenHeight/2-700;

    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(ob,x,y,null);
        if(x<0){
            canvas.drawBitmap(ob,x+GameView.WIDTH,y,null);
         //   System.out.println("obs"+y);

        }

    }
    public void update(){
       /* setVector(-5);
        x+=dx;
        if(x<-GameView.WIDTH){
            x=0;*/
       setY(y+=vY);
       if(y>=600)
           y= 0;

        }

    public void setY(int y){
        Obstacle.y =y;
    }
    public static int getY(){
        return y;
    }
    public static int getX(){
        return x;
    }
    public int getHeight(){
        return ob.getHeight();
    }



    public void setVector(int dx){
        this.dx=dx;
    }
}
