package com.mygdx.game.Screens;

import com.mygdx.game.Actors.EActor;
import com.mygdx.game.Actors.LineContainer;
import com.mygdx.game.AppGame;

import GameEntities.Controllers;

public class RootScreen extends AbstractGameScreen {

    public RootScreen(final AppGame game){
        super(game);
        controllers = new Controllers(0, 0,false, false, game);
    }

    @Override
    public void show(){                                             // Prima funzione chiamata
        super.show();
        this.objectFactory.createScreen2Boundaries();
        System.out.println("Root Screen");

        LineContainer oc = new LineContainer(0*PPM, -5*UHM, 5*PPM);
        stage.addActor(oc);
        stage.addActor(new EActor());
    }

    @Override
    public void touched(){
        //System.out.println("Touched "+touchPoint.x+", "+touchPoint.y);
    }

    @Override
    public void hide(){
        super.hide();
    }
}