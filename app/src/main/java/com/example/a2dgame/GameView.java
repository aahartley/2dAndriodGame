package com.example.a2dgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Paint;
import android.graphics.Rect;
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
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    SharedPreferences sharedPref = getContext().getSharedPreferences("highscores",Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();
    public String highScore = sharedPref.getString("highscore","");





    public static boolean drawn = true;
    public boolean start = false;
    public boolean retry = false;
    Rect rec = new Rect(400,screenHeight-500,700,screenHeight-200);



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
                float x = event.getX();
                float y= event.getY();
                System.out.println("touch:"+x+" "+y);
                if(!start){
                    start = true;
                }
               if(x>400 && x <700 && y>screenHeight-500 && y<screenHeight-200){
                    retry=true;
                }

                if(!drawn && retry) {
                  drawn=true;
                  retry=false;
                }
                else {
                    characterSprite.jump();
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        characterSprite = new CharacterSprite(BitmapFactory.decodeResource(getResources(),R.drawable.pink_monster_run),highScore);
        background = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.game_background_4));
        ground = new Ground(BitmapFactory.decodeResource(getResources(),R.drawable.owl_horizontal));
        obstacle = new Obstacle(BitmapFactory.decodeResource(getResources(),R.drawable.owl_horizontal));

        if(thread.getState().equals(Thread.State.TERMINATED)){
            SurfaceHolder surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            thread = new MainThread(surfaceHolder, this);
        }

        thread.setRunning(true);
        thread.start();

    }



    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);
        while(retry){
            try{
                thread.join();
                retry = false;
                System.out.println(thread.getState());

            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }

    public void update(){

        if(start) {
            background.update();
            characterSprite.update();
            obstacle.update();
            ground.update();
        }

    }

    @Override
    public void draw(Canvas canvas) {
        try {
            super.draw(canvas);
            final float scaleFactorX = ((float) getWidth() / WIDTH);
            final float scaleFactorY = (float) getHeight() / Height;
            if (canvas != null) {
                final int savedState = canvas.save();
                canvas.scale(scaleFactorX, scaleFactorY);
                background.draw(canvas);
                canvas.restoreToCount(savedState);
                if(!start){
                    drawWelcome(canvas);
                }
                if (characterSprite.isColliding()) {
                    drawn = false;

                }
                if (!drawn) {
                    drawOver(canvas);
                      drawRetry(canvas);
                    drawHighScore(canvas);
                     drawLastScore(canvas);
                     drawInstr(canvas);
                    System.out.println("u hit");
                }
                if (drawn && start) {
                    ground.draw(canvas);
                    obstacle.draw(canvas);
                    characterSprite.draw(canvas);
                    drawUPS(canvas);
                    drawFPS(canvas);
                    drawScore(canvas);
                    drawHighScore(canvas);

                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("bug");
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
        paint.setTextSize(60);
        canvas.drawText("Score:: " + score, screenWidth/2-160,400,paint);
    }
    public void drawLastScore(Canvas canvas){
        String score = Integer.toString(characterSprite.getLastScore());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(),R.color.colorAccent);
        paint.setColor(color);
        paint.setTextSize(60);
        canvas.drawText("Score:: " + score, screenWidth/2-160,400,paint);
    }
    public void drawHighScore(Canvas canvas){
        if(characterSprite.getHighScore()==0) {
            editor.putString("highscore", String.valueOf(characterSprite.getHighScore()));
          //  editor.apply();
        }

        else {
            editor.putString("highscore", String.valueOf(characterSprite.getHighScore()));
            editor.putInt("score",characterSprite.getHighScore());
                editor.apply();

        }
        // highScore = sharedPref.getString("highscore",String.valueOf(characterSprite.getHighScore()));
        highScore = sharedPref.getString("highscore","");


            System.out.println("shared " + highScore);
            String score = highScore;
            Paint paint = new Paint();
            int color = ContextCompat.getColor(getContext(), R.color.colorAccent);
            paint.setColor(color);
            paint.setTextSize(60);
            canvas.drawText("HighScore: " + score, screenWidth/2-160, 300, paint);




    }
    public void drawOver(Canvas canvas){
        String over ="Game Over";
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(),R.color.red);
        paint.setColor(color);
        paint.setTextSize(200);
        canvas.drawText(over,25,1000,paint);
    }
    public void drawRetry(Canvas canvas){
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(),R.color.red);
        paint.setColor(color);
        canvas.drawRect(rec,paint);

    }

    public void drawWelcome(Canvas canvas){
        String over ="Tap to Start";
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(),R.color.red);
        paint.setColor(color);
        paint.setTextSize(200);
        canvas.drawText(over,24,1000,paint);
    }
    public void drawInstr(Canvas canvas){
        String score = "tap to restart";
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(),R.color.colorAccent);
        paint.setColor(color);
        paint.setTextSize(40);
        canvas.drawText(score, 435,screenHeight-350,paint);
    }




}
