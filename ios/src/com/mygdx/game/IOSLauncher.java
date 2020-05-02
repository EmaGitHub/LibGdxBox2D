package com.mygdx.game;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;

import org.robovm.apple.foundation.Foundation;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIEdgeInsets;
import org.robovm.apple.uikit.UIView;

public class IOSLauncher extends IOSApplication.Delegate {
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        return new IOSApplication(new AppGame(), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

    public static Vector2 getSafeAreaInsets() {                         // Support for iPhone X
        if (Foundation.getMajorSystemVersion() >= 11) {
            UIView view = UIApplication.getSharedApplication().getKeyWindow().getRootViewController().getView();
            UIEdgeInsets edgeInsets = view.getSafeAreaInsets();

            double top = edgeInsets.getTop() * view.getContentScaleFactor();
            double bottom = edgeInsets.getBottom() * view.getContentScaleFactor();
            return new Vector2((float) top, (float) bottom);
        }
        return new Vector2();
    }
}