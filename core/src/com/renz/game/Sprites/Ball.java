package com.renz.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by renzkyril on 01/07/2017.
 */

public class Ball {

    private static final double FRICTION = -0.1;
    private static final int FORCE = 5;
    private static final double FACTOR = 0.01;

    private Vector3 position;
    private Vector3 prevPos;
    private Vector3 velocity;
    private boolean bouncingX,bouncingY;
    private boolean pushed;
    private boolean stopX,stopY;
    private Texture ball;
    private Sound flyM;

    public  Ball(int x,int y){
        position = new Vector3(x,y,0);
        prevPos = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        ball = new Texture("ball.png");
        bouncingX  = false;
        bouncingY  = false;
        pushed =false;
        stopX = false;
        stopY = false;
        this.flyM = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public void update(float dt){

        if (bouncingX) {

            if (position.x - prevPos.x >= FACTOR) {
                velocity.add((float) FRICTION, 0, 0);
                pushed = true;
            } else if (position.x - prevPos.x <= -FACTOR) {
                velocity.add(-(float)FRICTION, 0, 0);
                pushed = true;
            } else {
                if (pushed) {
                    bouncingX = false;
                    stopX = true;
                }
            }
        }

        if (bouncingY) {
            if (position.y - prevPos.y >= FACTOR) {
                velocity.add(0, (float) FRICTION, 0);
                pushed=true;
            } else if (position.y - prevPos.y <= -FACTOR) {
                velocity.add(0, -(float)FRICTION, 0);
                pushed=true;
            }else{
                if (pushed) {
                    bouncingY = false;
                    stopY = true;
                }
            }
        }
        velocity.scl(dt);
        prevPos.set(new Vector3(position.x,position.y,0));

        if (stopX && !stopY){
            position.add(0,velocity.y,0);
        } else if (stopY && !stopX){
            position.add(velocity.x,0,0);
        } else if (!stopY && !stopX){
            position.add(velocity.x,velocity.y,0);
        }
        velocity.scl(1/dt);
    }

    public void goBounce(float x,float y){
        velocity.add(x*FORCE,y*FORCE,0);
        bouncingX = true;
        bouncingY = true;
    }

    public void colidedY(){
        velocity.set(velocity.x,-velocity.y,0);
        this.flyM.play(0.5F);
    }

    public void colidedX(){
        velocity.set(-velocity.x,velocity.y,0);
        this.flyM.play(0.5F);
    }

    public void setY(float y){
        position.set(position.x,y,0);
    }

    public void setX(float x){
        position.set(x,position.y,0);
    }

    public boolean hasStop(){
        return stopX || stopY;
    }

    public Texture getBall() {
        return ball;
    }

    public Vector3 getPosition() {
        return position;
    }

}
