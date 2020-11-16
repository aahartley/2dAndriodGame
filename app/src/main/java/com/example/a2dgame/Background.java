package com.example.a2dgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {
    private Bitmap bg;
    private int x,y,dx;


    public Background(Bitmap bMap){
        bg = bMap;

    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(bg,x,y,null);
        if(x<0){
         canvas.drawBitmap(bg,x+getWidth(),y,null);
      }


    }
    public void update(){
        setVector(-5);
        x+=dx;
        if(x<-getWidth()) {
                x = 0;
        }
        }

    public void setVector(int dx){
        this.dx=dx;
    }
    public float getWidth(){
        return bg.getWidth();
    }
}
