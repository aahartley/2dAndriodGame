package com.example.a2dgame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.ContextCompat;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private CharacterSprite characterSprite;
    private Background background;
    private Obstacle obstacle;
    private  Ground ground;
    public static final int WIDTH =2500;
    public static final int Height =3000;



    public GameView(Context context){
        super(context);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                characterSprite.jump();
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        characterSprite = new CharacterSprite(BitmapFactory.decodeResource(getResources(),R.drawable.pink_monster_run));
        background = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.game_background_4));
        ground = new Ground(BitmapFactory.decodeResource(getResources(),R.drawable.owl_horizontal));
        obstacle = new Obstacle(BitmapFactory.decodeResource(getResources(),R.drawable.owl_horizontal));


        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(Exception e){
                e.printStackTrace();
            }
            retry = false;
        }

    }

    public void update(){
        background.update();
        characterSprite.update();
        obstacle.update();
        ground.update();
        if(characterSprite.isColliding()){
            System.out.println("u hit");
        surfaceDestroyed(getHolder());
            }



    //  else
        //  System.out.println("No hit");

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        final float scaleFactorX = ((float)getWidth()/WIDTH);
        final float scaleFactorY =(float) getHeight()/Height;
        if(canvas != null){
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX,scaleFactorY);
            background.draw(canvas);
            canvas.restoreToCount(savedState);
            ground.draw(canvas);
            obstacle.draw(canvas);
            characterSprite.draw(canvas);
            drawUPS(canvas);
            drawFPS(canvas);
            drawScore(canvas);

        }
    }
    public void drawUPS(Canvas canvas){
        String averageUPS = Double.toString(thread.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(),R.color.colorAccent);
        paint.setColor(color);
        paint.setTextSize(40);
        canvas.drawText("UPS: " + averageUPS, 90,40,paint);
    }
    public void drawFPS(Canvas canvas){
        String averageUPS = Double.toString(thread.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(),R.color.colorAccent);
        paint.setColor(color);
        paint.setTextSize(40);
        canvas.drawText("FPS: " + averageUPS, 100,100,paint);
    }
    public void drawScore(Canvas canvas){
        String score = Integer.toString(characterSprite.getScore());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(),R.color.colorAccent);
        paint.setColor(color);
        paint.setTextSize(40);
        canvas.drawText("Score:: " + score, 300,300,paint);
    }




}
