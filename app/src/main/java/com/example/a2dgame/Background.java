package com.example.a2dgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {
    private GameView gameView;
    private Bitmap bg;
    private boolean done= false;
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
            done = true;
            if (done)
                done=false;
                x = 0;
        }
        }

    public void setVector(int dx){
        this.dx=dx;
    }
    public float getWidth(){
        return bg.getWidth();
    }
    public float getHeight(){
        return bg.getHeight();
    }
}
