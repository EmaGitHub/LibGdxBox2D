package com.mygdx.game.Screens;

import com.mygdx.game.Actors.EActor;
import com.mygdx.game.AppGame;
import com.mygdx.game.Utils.GlobalVar;

public class RootScreen extends AbstractScreen {

    private float PPM = GlobalVar.PPM;

    public RootScreen(AppGame game){
        super(game);
        super.freezeButtonVisible = false;
        super.moveButtonVisible = false;
    }

    @Override
    public void show(){                                             // Prima funzione chiamata
        super.show();
        this.objectFactory.createScreen2Boundaries();

        EActor e = new EActor();
        super.stage.addActor(new EActor());
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