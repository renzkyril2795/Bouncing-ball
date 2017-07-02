package com.renz.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.renz.game.Sprites.Ball;
import com.renz.game.bounce_now;

/**
 * Created by renzkyril on 01/07/2017.
 */

public class PlayState extends State implements InputProcessor {

    private final int MAXWAIT=10;

    private Texture bg;
    private Ball ball;
    private boolean bouncing;
    private boolean dragged;
    private boolean dragging;
    private Vector3 acc;
    private int score;
    private BitmapFont scoreText;

    private float wait;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        ball = new Ball(bounce_now.WIDTH/2,bounce_now.HEIGHT/2-100);
        bg = new Texture("bg.jpg");
        bouncing =false;
        dragged = false;
        dragging = false;
        acc = new Vector3(0,0,0);
        wait=0;
        score = 0;
        scoreText = new BitmapFont();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    protected void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bg,0,0, bounce_now.WIDTH,bounce_now.HEIGHT);
        sb.draw(ball.getBall(),ball.getPosition().x,ball.getPosition().y);

        scoreText.setColor(Color.BLACK);
        scoreText.getData().setScale(2,2);
        scoreText.draw(sb,"score : "+score,0,bounce_now.HEIGHT-50);
        sb.end();
    }

    @Override
    protected void update(float dt) {
        handleInput();

        if(bouncing) {
            if(ball.getPosition().x <= 0 || (ball.getPosition().x+ball.getBall().getWidth())>=bounce_now.WIDTH) {
                if (ball.getPosition().x <=0){
                    ball.setX(0);
                }else{
                    ball.setX(bounce_now.WIDTH-ball.getBall().getWidth());
                }
                ball.colidedX();
                score++;
            }

            if ((ball.getPosition().y) <= 0 || ball.getPosition().y+2*ball.getBall().getHeight()>=bounce_now.HEIGHT){
                if (ball.getPosition().y <=0){
                    ball.setY(0);
                }else{
                    ball.setY(bounce_now.HEIGHT-2*ball.getBall().getHeight());
                }
                ball.colidedY();
                score++;
            }
            ball.update(dt);
        }

        if(ball.hasStop()){
            if(wait > MAXWAIT) {
                gsm.set(new MenuState(gsm));
            }
            wait += dt;
        }
    }

    @Override
    protected void dispose() {

    }


    /**
     * Called when a key was pressed
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    /**
     * Called when a key was released
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    /**
     * Called when a key was typed
     *
     * @param character The character
     * @return whether the input was processed
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link Buttons#LEFT} on iOS.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    /**
     * Called when a finger was lifted or a mouse button was released. The button parameter will be {@link Buttons#LEFT} on iOS.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @param button  the button   @return whether the input was processed
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if (dragged){
            ball.goBounce(ball.getPosition().x+ball.getBall().getWidth()/2-screenX,
                    screenY-ball.getPosition().y-ball.getBall().getHeight()/2);
            bouncing=true;
            dragged = false;
        }
        return true;
    }

    /**
     * Called when a finger or the mouse was dragged.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.  @return whether the input was processed
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        if (!dragging){
            Rectangle r = new Rectangle(ball.getPosition().x,ball.getPosition().y+ball.getBall().getHeight(),ball.getBall().getWidth(),ball.getBall().getHeight());
            dragging=r.contains(screenX,bounce_now.HEIGHT-screenY);
            Gdx.app.log("dragging",""+dragging);
        }


        if(!bouncing && dragging){
            dragged = true;
        }
        return  false;
    }

    /**
     * Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
     *
     * @param screenX
     * @param screenY
     * @return whether the input was processed
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        return false;
    }

    /**
     * Called when the mouse wheel was scrolled. Will not be called on iOS.
     *
     * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
     * @return whether the input was processed.
     */
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
