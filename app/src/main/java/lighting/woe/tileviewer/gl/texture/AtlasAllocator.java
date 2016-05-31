package lighting.woe.tileviewer.gl.texture;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class AtlasAllocator extends TaskArrayAllocator<AtlasTexture> {

    private final Texture mTexture;
    private final int mAtlasTextureSize;

    public AtlasAllocator(Texture parent, int textureSize) {
        super((int) Math.pow(parent.size() / textureSize, 2));
        this.mTexture = parent;
        this.mAtlasTextureSize = textureSize;
    }

    @Override
    protected AtlasTexture obtainElement(int size, int position) {
        return new AtlasTexture(mTexture, mAtlasTextureSize, position);
    }

    @Override
    protected void upload(AtlasTexture texture, Bitmap bmp) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + texture.textureNumber());
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.textureHandle());

        GLUtils.texSubImage2D(
                GLES20.GL_TEXTURE0 + texture.textureNumber(),
                GLES20.GL_RGBA,
                texture.getOffsetX(), texture.getOffsetY(),
                bmp);
    }

    @Override
    protected void cleanup(AtlasTexture texture) {
    }

}
