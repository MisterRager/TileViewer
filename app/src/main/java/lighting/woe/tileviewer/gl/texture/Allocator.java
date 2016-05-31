package lighting.woe.tileviewer.gl.texture;

import android.graphics.Bitmap;

import java.util.concurrent.Future;

import lighting.woe.tileviewer.gl.RenderWorker;

public interface Allocator<Tex extends Texture> {
    Future<Tex> uploadTexture(Bitmap bmp);

   Tex allocateTexture(int size);

    Future<Tex> release(Tex texture);
}
