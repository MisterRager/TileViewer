package lighting.woe.tileviewer.gl.texture;

import android.graphics.RectF;

public class AtlasTexture implements Texture {
    private final Texture mParent;
    private final RectF mUV;
    private final int mSize;
    private final int mSlotNumber;
    private final int mOffsetX;
    private final int mOffsetY;

    public AtlasTexture(Texture atlas, int size, int atlasPosition) {
        mParent = atlas;
        float atlasSize = atlas.size();
        int slotsPerRow = atlas.size() / size;

        int atlasX = atlasPosition / slotsPerRow;
        int atlasY = atlasPosition % slotsPerRow;

        mOffsetX = atlasX * size;
        mOffsetY = atlasY * size;

        mUV = new RectF(
                mOffsetX / atlasSize, mOffsetY / atlasSize,
                (mOffsetX + size) / atlasSize, (mOffsetY + size) / atlasSize);
        mSize = size;
        mSlotNumber = atlasPosition;
    }

    @Override
    public int textureNumber() {
        return mParent.textureNumber();
    }

    @Override
    public int slotNumber() {
        return mSlotNumber;
    }

    @Override
    public int textureHandle() {
        return mParent.textureHandle();
    }

    @Override
    public RectF uv() {
        return mUV;
    }

    @Override
    public int size() {
        return mSize;
    }

    public int getOffsetY() {
        return mOffsetY;
    }

    public int getOffsetX() {
        return mOffsetX;
    }
}
