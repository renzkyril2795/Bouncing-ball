package com.renz.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.renz.game.bounce_now;

/**
 * Created by renzkyril on 01/07/2017.
 */

public class MenuState extends State {

    private Texture bg;
    private Texture playbtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("bg.jpg");
        playbtn = new Texture("play_button.png");
    }

    @Override
    public void handleInput() {

        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        //dispose();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bg,0,0,bounce_now.WIDTH,bounce_now.HEIGHT);
        sb.draw(playbtn,(bounce_now.WIDTH/2 - playbtn.getWidth()/2),bounce_now.HEIGHT/2);
        sb.end();
    }

    @Override
    protected void dispose() {
        bg.dispose();
        playbtn.dispose();
    }
}
