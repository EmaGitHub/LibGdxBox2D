package com.mygdx.game.Screens;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Actors.EActor;
import com.mygdx.game.Actors.LineActor;
import com.mygdx.game.Actors.ObjContainer;
import com.mygdx.game.AppGame;
import com.mygdx.game.Utils.GlobalVar;

public class RootScreen extends AbstractScreen {

    private float PPM = GlobalVar.PPM;
    ObjContainer oc;
    Vector2 vector2=new Vector2();
    LineActor line;

    public RootScreen(AppGame game){
        super(game);
        super.freezeButtonVisible = true;
        super.moveButtonVisible = true;
    }

    @Override
    public void show(){                                             // Prima funzione chiamata
        super.show();
        this.objectFactory.createScreen2Boundaries();

        oc = new ObjContainer(this.game.camera);

        line = new LineActor(2*PPM, -5*UHM, 3*PPM);
        oc.addActor(line);

        stage.addActor(new EActor());
        stage.addActor(oc);

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
//      super.freezeScene();
        System.out.println("Actor Position Before moveBy on Group is : "+line.getX()+" And "+line.getY());

        oc.moveBy(50,50);

        System.out.println("After moveBy applied on Group, Actor Position is : "+line.getX()+"And"+line.getY());
        vector2.set(line.getX(),line.getY());
        Vector2 stageCord=oc.localToStageCoordinates(vector2);
        System.out.println("Position with Stage Cord. is : "+stageCord.x+" And "+stageCord.y);

    }

    @Override
    protected void resumeScene() {
        super.resumeScene();
    }
}