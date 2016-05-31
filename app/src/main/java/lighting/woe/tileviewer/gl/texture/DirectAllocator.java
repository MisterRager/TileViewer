package lighting.woe.tileviewer.gl.texture;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class DirectAllocator extends TaskArrayAllocator<DirectTexture> {

    public DirectAllocator(int capacity) {
        super(capacity);
    }

    @Override
    protected DirectTexture obtainElement(int size, int position) {
        return new DirectTexture(position, size);
    }

    @Override
    protected void upload(DirectTexture texture, Bitmap bmp) {
        int[] textureHandle = new int[1];
        GLES20.glGenTextures(1, textureHandle, 0);

        // Bind texture
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + texture.textureNumber());
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

        // Set filtering
        GLES20.glTexParameteri(
                GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(
                GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        // set wrap mode
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        uploadBitmap(bmp);
        texture.setTextureHandle(textureHandle[0]);
    }

    protected void uploadBitmap(Bitmap bmp){
        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);
    }

    @Override
    protected void cleanup(DirectTexture texture) {
        GLES20.glDeleteTextures(1, new int[]{texture.textureHandle()}, 0);
    }
}
