package com.mygdx.game.Screens;

import com.mygdx.game.Actors.EActor;
import com.mygdx.game.Actors.ObjContainer;
import com.mygdx.game.AppGame;

public class RootScreen extends AbstractScreen {

    public RootScreen(final AppGame game){
        super(game);
        super.freezeButtonVisible = false;
        super.moveButtonVisible = false;
    }

    @Override
    public void show(){                                             // Prima funzione chiamata
        super.show();
        this.objectFactory.createScreen2Boundaries();
        System.out.println("Root Screen");

        ObjContainer oc = new ObjContainer(this.game.camera);

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

    @Override
    protected void freezeScene() {
        super.freezeScene();
    }

    @Override
    protected void resumeScene() {
        super.resumeScene();
    }
}