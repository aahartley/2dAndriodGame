package com.example.a2dgame;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;


public class CharacterSprite {
    private Bitmap image;
    private Bitmap[] images = new Bitmap[6];
    private GameView gV;
    private int width =90;
    private int height=85;
    private int currentFrame=0;
    private double x,y;
    private int yVelocity = 28;
    private int xx,yy;
    private int score =0;
    private int highScore;
    private String saved;
    private int lastScore;



    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public CharacterSprite(Bitmap bmap, String highs){
        image= bmap;
        x = (screenWidth/2)-75;
        y = screenHeight/2+100;
        for(int i=0; i<images.length-1;i++){
            if(height > image.getHeight() ) {
                height = image.getHeight();
                width-=5;
            }

            images[i] = Bitmap.createBitmap(image,i*width,0,width,height);
            System.out.println("HEIGHT IS: "+image.getHeight());
        }
        if(highs.equals(""))
            saved ="0";
        else
        saved = highs;
       // System.out.println(screenHeight);




    }
    /*(y-200)>= 1550 ||*/
   public boolean isColliding(){
        if((y-200)>=Ground.getY()-250 || (y-200)<= Obstacle.getY()+150) {
            //getY - whatever = 1550 then make them move y-200 <= getY+195
            setScore(score);
            lastScore = score;
            score =0;
            return true;
        }
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
        y-=350;   //450
        score++;
    }
    public void setScore(int score){
       if(highScore <score && score > Integer.parseInt(saved) /*&& Integer.parseInt(saved)!=0*/) {
           highScore = score;
       }
       else
           highScore=highScore;
    }
    public int getScore(){
       return score;
    }
    public int getLastScore(){
       return lastScore;
    }
    public int getHighScore(){
       return highScore;
    }

    public void update(){
        if(!GameView.drawn) {
            System.out.println("DEAD");
            y = screenHeight/2+100;
            return;
        }
            y += yVelocity;
            if (y > screenHeight) y = 0;
            else if (y < 0) y = screenHeight - 100;
    }



}
