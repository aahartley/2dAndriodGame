package com.example.a2dgame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private static final double MAX_UPS = 60;
    private static final double UPS_Period = 1E+3/MAX_UPS;
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    private double averageFPS;
    private double averageUPS;
    public static Canvas canvas;


    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;

    }

    public double getAverageFPS() {
        return averageFPS;
    }
    public double getAverageUPS(){
        return averageUPS;
    }


    public void setRunning(boolean isRunning) {
        running = isRunning;
    }

    @Override
    public void run() {
        int updateCount =0;
        int frameCount = 0;


        long startTime;
        long elapsedTime;
        long sleepTime;
        startTime= System.currentTimeMillis();
        while (running) {
            canvas = null;
            //update and render game
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.update();
                    updateCount++;
                    this.gameView.draw(canvas);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }
            // pause loop to not exceed ups
            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long) (updateCount*UPS_Period -elapsedTime);
            if(sleepTime >0) {
                try {
                    this.sleep(sleepTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //skip frames to keep ups
            while(sleepTime < 0 && updateCount < MAX_UPS){
                this.gameView.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount*UPS_Period -elapsedTime);
            }
            //calculate ups and fps
            elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime>= 1000) {
                averageUPS = updateCount/ (1E-3 * elapsedTime);
                averageFPS = updateCount/ (1E-3 * elapsedTime);
                frameCount = 0;
                updateCount =0;
                startTime= System.currentTimeMillis();
             //   System.out.println(averageFPS);
            }
        }
    }
}
