package lighting.woe.tileviewer.gl.texture;

import android.graphics.RectF;

public interface Texture {

    int textureNumber();

    int textureHandle();

    int slotNumber();

    int size();

    RectF uv();
}
