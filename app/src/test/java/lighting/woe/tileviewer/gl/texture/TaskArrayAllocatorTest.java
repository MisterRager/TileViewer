package lighting.woe.tileviewer.gl.texture;

import android.graphics.Bitmap;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import lighting.woe.helper.ShadowSparseArray;
import lighting.woe.tileviewer.BuildConfig;

@Config(shadows = {ShadowSparseArray.class}, constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class TaskArrayAllocatorTest {
    private static final int HEIGHT, WIDTH, CAPACITY;

    static {
        Random rand = new Random();
        HEIGHT = rand.nextInt(50);
        WIDTH = rand.nextInt(50);
        CAPACITY = rand.nextInt(10) + 1;
    }

    @Spy
    StubTaskArrayAllocator mAllocator;

    @Mock
    private Bitmap mBitmap;

    @Mock
    private Texture mTexture;

    @Mock
    private FutureTask<Texture> mFutureTask;

    @Captor
    private ArgumentCaptor<FutureTask<Texture>> mTaskCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Mockito.doReturn(HEIGHT).when(mBitmap).getHeight();
        Mockito.doReturn(WIDTH).when(mBitmap).getWidth();

        Mockito.doReturn(mTexture).when(mAllocator).allocateTexture(Math.max(HEIGHT, WIDTH));
        Mockito.doReturn(mFutureTask).when(mAllocator).obtainUpload(mTexture, mBitmap);
        Mockito.doReturn(mFutureTask).when(mAllocator).obtainDummy();
        Mockito.doReturn(mFutureTask).when(mAllocator).obtainCleanup(mTexture);

        //noinspection unchecked
        Mockito.doAnswer(new Answer() {
            @Override
            public FutureTask<Texture> answer(InvocationOnMock invocation) throws Throwable {
                //noinspection unchecked
                return (FutureTask<Texture>) invocation.getArguments()[0];
            }
        }).when(mAllocator).putTask(Mockito.any(FutureTask.class));

        Mockito.doReturn(mTexture).when(mAllocator)
                .obtainElement(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void testUploadTexture() throws Exception {
        Assert.assertEquals(mFutureTask, mAllocator.uploadTexture(mBitmap));

        Mockito.verify(mAllocator).allocateTexture(Math.max(HEIGHT, WIDTH));
        Mockito.verify(mAllocator).obtainUpload(mTexture, mBitmap);
        Mockito.verify(mAllocator).putTask(mFutureTask);
    }

    @Test
    public void testUploadTextureNull() {
        Mockito.doReturn(null).when(mAllocator).allocateTexture(Mockito.anyInt());
        Assert.assertEquals(mFutureTask, mAllocator.uploadTexture(mBitmap));

        Mockito.verify(mAllocator, Mockito.never()).obtainUpload(mTexture, mBitmap);
        Mockito.verify(mAllocator).obtainDummy();
        Mockito.verify(mFutureTask).run();
    }

    @Test
    public void testRelease() throws Exception {
        Assert.assertEquals(mFutureTask, mAllocator.release(mTexture));
        Mockito.verify(mAllocator).obtainCleanup(mTexture);
        Mockito.verify(mAllocator).putTask(mFutureTask);
    }

    @Test
    public void testPerform() throws Exception {
        //noinspection unchecked
        Mockito.doCallRealMethod().when(mAllocator).putTask(Mockito.any(RunnableFuture.class));
        //noinspection unchecked
        RunnableFuture<Texture> task1 = Mockito.mock(RunnableFuture.class);
        //noinspection unchecked
        RunnableFuture<Texture> task2 = Mockito.mock(RunnableFuture.class);

        mAllocator.putTask(task1);
        mAllocator.putTask(task2);
        mAllocator.perform();

        Mockito.verify(task1).run();
        Mockito.verify(task2).run();
    }

    @Test
    public void testAllocate() {
        Mockito.doCallRealMethod().when(mAllocator).allocateTexture(HEIGHT);

        for (int k = 0; k < CAPACITY; k++) {
            Assert.assertSame(mTexture, mAllocator.allocateTexture(HEIGHT));
        }

        Assert.assertNull(mAllocator.allocateTexture(HEIGHT));
        Mockito.verify(mAllocator, Mockito.times(CAPACITY))
                .obtainElement(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void testObtainUpload() throws ExecutionException, InterruptedException {
        Mockito.doCallRealMethod().when(mAllocator).obtainUpload(mTexture, mBitmap);
        FutureTask<Texture> f = mAllocator.obtainUpload(mTexture, mBitmap);
        f.run();
        Assert.assertSame(mTexture, f.get());
    }

    @Test
    public void testObtainCleanup() throws ExecutionException, InterruptedException {
        Mockito.doCallRealMethod().when(mAllocator).obtainCleanup(mTexture);
        FutureTask<Texture> f = mAllocator.obtainCleanup(mTexture);
        f.run();
        Assert.assertSame(mTexture, f.get());
    }

    @Test
    public void testObtainDummy() throws ExecutionException, InterruptedException {
        Mockito.doCallRealMethod().when(mAllocator).obtainDummy();
        FutureTask<Texture> f = mAllocator.obtainDummy();
        f.run();
        Assert.assertNull(f.get());
    }

    private static class StubTaskArrayAllocator extends TaskArrayAllocator<Texture> {

        protected StubTaskArrayAllocator() {
            super(CAPACITY);
        }

        @Override
        protected Texture obtainElement(int size, int position) {
            return null;
        }

        @Override
        protected void upload(Texture texture, Bitmap bmp) {

        }

        @Override
        protected void cleanup(Texture texture) {

        }
    }
}