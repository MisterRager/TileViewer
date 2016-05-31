package lighting.woe.tileviewer.gl.texture;

import android.graphics.RectF;

public class DirectTexture implements Texture {
    private final int mSlot;
    private final RectF mUV = new RectF(0, 0, 1f, 1f);
    private final int mSize;
    private int mTextureHandle = -1;

    public DirectTexture(int mSlot, int size) {
        this.mSlot = mSlot;
        this.mSize = size;
    }

    @Override
    public int textureNumber() {
        return mSlot;
    }

    @Override
    public int slotNumber() {
        return mSlot;
    }

    @Override
    public int size() {
        return mSize;
    }

    @Override
    public RectF uv() {
        return mUV;
    }

    public void setTextureHandle(int handle) {
        this.mTextureHandle = handle;
    }

    @Override
    public int textureHandle() {
        return mTextureHandle;
    }
}
