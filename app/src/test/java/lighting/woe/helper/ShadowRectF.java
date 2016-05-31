package lighting.woe.helper;

import android.graphics.RectF;

import org.robolectric.annotation.Implements;

@SuppressWarnings("unused")
@Implements(RectF.class)
public class ShadowRectF {
    public float left;
    public float top;
    public float right;
    public float bottom;

    public ShadowRectF(){
    }

    public ShadowRectF(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
}
