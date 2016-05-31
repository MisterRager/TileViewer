package lighting.woe.tileviewer.gl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import lighting.woe.tileviewer.gl.texture.DirectAllocator;

public class Renderer implements GLSurfaceView.Renderer{
    private final Collection<RenderWorker> mRenderTaskWorkers = new CopyOnWriteArrayList<>();
    private DirectAllocator mTextureAllocator;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the clear color to PERPS
        GLES20.glClearColor(1.0f, 0.0f, 1.0f, 1);

        // get the max number of textures
        int[] textures = new int[1];
        GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_IMAGE_UNITS, textures, 0);

        mTextureAllocator = new DirectAllocator(textures[0]);
        mRenderTaskWorkers.add(mTextureAllocator);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        for(RenderWorker worker : mRenderTaskWorkers){
            worker.perform();
        }
    }
}
