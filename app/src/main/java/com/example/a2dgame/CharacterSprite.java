package com.example.a2dgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;


public class CharacterSprite {
    private Bitmap image;
    private Bitmap[] images = new Bitmap[6];
    private int width =90;
    private int height=85;
    private int currentFrame=0;
    private double x,y;
    private int yVelocity = 28;
    private int xx,yy;



    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public CharacterSprite(Bitmap bmap){
        image= bmap;
        x = (screenWidth/2)-200;
        y = screenHeight/2+100;
        for(int i=0; i<images.length-1;i++){
            images[i] = Bitmap.createBitmap(image,i*width,0,width,height);
        }
       // System.out.println(screenHeight);




    }

   public boolean isColliding(){
        if((y-200)>= 1550 || (y-200)<= 600) //getY - whatever = 1550 then make them move
            return true;
        else{
            System.out.println(Obstacle.getX()+" "+Obstacle.getY());
           System.out.println("char " +x+" "+y);

            return false;
    }}



    public void draw(Canvas canvas){
        if(currentFrame==0)
            currentFrame=1;
        else if(currentFrame==1)
            currentFrame=2;
        else if(currentFrame==2)
            currentFrame=3;
        else if (currentFrame==3)
            currentFrame=4;
        else
            currentFrame=0;
        canvas.drawBitmap(images[currentFrame], (float) x, (float) y, null);
       //System.out.println(images[currentFrame].getWidth());




    }
    public void jump(){
        y-=450;
    }

    public void update(){
        y += yVelocity;
        if(y>screenHeight) y=0;
        else if(y<0) y=screenHeight-100;
        if(isColliding()) System.out.println("DEAD");





    }



}
