package com.example.a2dgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Ground {
    private Bitmap gr;
    private static int x,y,dx;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int vY =5;

    public Ground(Bitmap bMap){
        gr = bMap;
        x=screenWidth/2-500;
       // this.y= screenHeight/2+700;
        y=2000;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(gr,x,y,null);

        }



    public void update(){
        setY(y-=vY);
        System.out.println(getY());
        if(y== 1400)
            setY(2000);
        }
        public void setY(int y){
        Ground.y =y;
        }
        public static int getX(){
        return x;
        }
        public static int getY(){
        return y;
        }

    }

