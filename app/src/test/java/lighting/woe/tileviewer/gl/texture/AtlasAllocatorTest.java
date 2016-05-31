package lighting.woe.tileviewer.gl.texture;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import lighting.woe.BaseTest;

import static org.junit.Assert.*;

public class AtlasAllocatorTest extends BaseTest {

    private static final int SIZE = (int) Math.pow(2, sRandom.nextInt(10) + 1);
    private static final int POSITION = sRandom.nextInt(100);
    private static final int ATLAS_SIZE = SIZE * 10;

    private static final int TEXTURE_NUMBER = sRandom.nextInt(8);
    private static final int TEXTURE_HANDLE = sRandom.nextInt(32);

    @Mock
    private Texture mTexture;

    private AtlasAllocator mAllocator;

    @Before
    public void setUp() throws Exception {
        mAllocator = new AtlasAllocator(mTexture, SIZE);

        Mockito.doReturn(TEXTURE_NUMBER).when(mTexture).textureNumber();
        Mockito.doReturn(TEXTURE_HANDLE).when(mTexture).textureHandle();
        Mockito.doReturn(ATLAS_SIZE).when(mTexture).size();
    }

    @Test
    public void testObtainElement() throws Exception {
        AtlasTexture tex = mAllocator.obtainElement(SIZE, POSITION);

        Assert.assertNotNull(tex);
        Assert.assertEquals(POSITION, tex.slotNumber());
        Assert.assertEquals(TEXTURE_NUMBER, tex.textureNumber());
        Assert.assertEquals(TEXTURE_HANDLE, tex.textureHandle());
    }
}