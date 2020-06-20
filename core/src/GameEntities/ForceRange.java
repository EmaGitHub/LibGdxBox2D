package GameEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

public class ForceRange extends Container<Slider> {

    private float force;

    public ForceRange() {

        super(new Slider(0, 100, 1, false,
                new Skin(Gdx.files.internal("Skins/skin/glassy-ui.json"))));

        this.setTransform(true);   // for enabling scaling and rotation
        this.size(100, 60);
        this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
        this.setPosition(0, 0);
        this.setScale(3);  //scale according to your requirement
    }
}
