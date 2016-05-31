package lighting.woe.tileviewer.gl.texture;

import android.graphics.Bitmap;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import lighting.woe.tileviewer.gl.RenderWorker;

abstract public class TaskArrayAllocator<Tex extends Texture>
        implements Allocator<Tex>, RenderWorker {

    protected final List<RunnableFuture<Tex>> mPendingTasks = new ArrayList<>();
    private final SparseArray<Tex> mTextures = new SparseArray<>();

    private final AtomicInteger mCapacity;

    protected TaskArrayAllocator(int capacity) {
        this.mCapacity = new AtomicInteger(capacity);
    }

    @Override
    public Future<Tex> uploadTexture(Bitmap bmp) {
        FutureTask<Tex> future;
        Tex texture = allocateTexture(Math.max(bmp.getHeight(), bmp.getWidth()));

        if (null != texture) {
            return putTask(obtainUpload(texture, bmp));
        }

        future = obtainDummy();
        future.run();

        return future;
    }

    @Override
    public Future<Tex> release(Tex texture) {
        return putTask(obtainCleanup(texture));
    }

    @Override
    public void perform() {
        synchronized (mPendingTasks) {
            while (!mPendingTasks.isEmpty()) {
                mPendingTasks.remove(mPendingTasks.size() - 1).run();
            }
        }
    }

    @Override
    public Tex allocateTexture(int size) {
        Tex texture = null;
        synchronized (mTextures) {
            for (int k = 0, capacity = mCapacity.get(); k < capacity; k++) {
                if (null == mTextures.get(k)) {
                    texture = obtainElement(size, k);
                    mTextures.put(k, texture);
                    break;
                }
            }
        }
        return texture;
    }

    abstract protected Tex obtainElement(int size, int position);

    abstract protected void upload(Tex texture, Bitmap bmp);

    abstract protected void cleanup(Tex texture);

    protected RunnableFuture<Tex> putTask(RunnableFuture<Tex> task) {
        synchronized (mPendingTasks) {
            mPendingTasks.add(task);
        }
        return task;
    }

    protected FutureTask<Tex> obtainUpload(Tex texture, Bitmap bmp) {
        return new FutureTask<>(new Upload(texture, bmp));
    }

    protected FutureTask<Tex> obtainCleanup(Tex texture) {
        return new FutureTask<>(new Cleanup(texture));
    }

    protected FutureTask<Tex> obtainDummy() {
        return new FutureTask<>(new DummyTask<Tex>());
    }

    private class Upload implements Callable<Tex> {
        private final Tex texture;
        final Bitmap bitmap;

        private Upload(Tex texture, Bitmap bitmap) {
            this.texture = texture;
            this.bitmap = bitmap;
        }

        @Override
        public Tex call() throws Exception {
            upload(texture, bitmap);
            return texture;
        }
    }

    private class Cleanup implements Callable<Tex> {
        private final Tex texture;

        private Cleanup(Tex texture) {
            this.texture = texture;
        }

        @Override
        public Tex call() throws Exception {
            cleanup(texture);
            mTextures.remove(texture.slotNumber());
            return texture;
        }
    }

    private static class DummyTask<Tex extends Texture> implements Callable<Tex> {
        @Override
        public Tex call() throws Exception {
            return null;
        }
    }

}
