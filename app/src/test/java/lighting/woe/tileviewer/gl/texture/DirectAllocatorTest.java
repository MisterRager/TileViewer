package lighting.woe.tileviewer.gl.texture;

import android.graphics.Bitmap;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import lighting.woe.BaseTest;

public class DirectAllocatorTest extends BaseTest {

    private static final int CAPACITY = sRandom.nextInt(10) + 1;
    private static final int SIZE = (int) Math.pow(2, sRandom.nextInt(10) + 1);
    private static final int POSITION = (int) sRandom.nextInt(96);

    private DirectAllocator mAllocator;

    @Mock
    private DirectTexture mTexture;
    @Mock
    private Bitmap mBitmap;

    @Before
    public void setUp() throws Exception {
        mAllocator = Mockito.spy(new DirectAllocator(CAPACITY));
    }

    @Test
    public void testObtainElement() throws Exception {
        DirectTexture tex = mAllocator.obtainElement(SIZE, POSITION);

        Assert.assertThat(tex, CoreMatchers.any(DirectTexture.class));
        Assert.assertEquals(tex.slotNumber(), POSITION);
        Assert.assertEquals(tex.textureNumber(), POSITION);
        Assert.assertEquals(tex.size(), SIZE);
    }
}