package lighting.woe.tileviewer.gl.texture;

import android.graphics.RectF;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import lighting.woe.BaseTest;
import lighting.woe.helper.ShadowRectF;
import lighting.woe.tileviewer.BuildConfig;

@Config(shadows = {ShadowRectF.class}, constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class AtlasTextureTest extends BaseTest {

    private static final int SIZE = (int) Math.pow(sRandom.nextInt(10) + 1, 2);
    private static final int PER_ROW = 4;
    private static final float PARENT_SIZE = SIZE * PER_ROW;

    private static final int POSITION = sRandom.nextInt(16);
    private static final int TEXTURE_NUMBER = sRandom.nextInt(8);
    private static final int TEXTURE_HANDLE = sRandom.nextInt(32) + 1;

    @Mock
    private Texture mParentTexture;

    @Before
    public void setUp() throws Exception {
        Mockito.doReturn((int) PARENT_SIZE).when(mParentTexture).size();
        Mockito.doReturn(TEXTURE_NUMBER).when(mParentTexture).textureNumber();
        Mockito.doReturn(TEXTURE_HANDLE).when(mParentTexture).textureHandle();
    }

    @Test
    public void testNew(){
        AtlasTexture tex = new AtlasTexture(mParentTexture, SIZE, POSITION);

        Assert.assertEquals(SIZE, tex.size());
        Assert.assertEquals(POSITION, tex.slotNumber());
        Assert.assertEquals(TEXTURE_HANDLE, tex.textureHandle());
        Assert.assertEquals(TEXTURE_NUMBER, tex.textureNumber());

        Assert.assertEquals((POSITION / PER_ROW) * SIZE, tex.getOffsetX());
        Assert.assertEquals((POSITION % PER_ROW) * SIZE, tex.getOffsetY());

        Assert.assertNotNull(tex.uv());
        Assert.assertThat(tex.uv(), Matchers.isA(RectF.class));

        Assert.assertEquals(tex.getOffsetX() / PARENT_SIZE, tex.uv().left, FLOAT_EPSILON);
        Assert.assertEquals(tex.getOffsetY() / PARENT_SIZE, tex.uv().top, FLOAT_EPSILON);

        Assert.assertEquals(
                (tex.getOffsetX() + tex.size()) / PARENT_SIZE, tex.uv().right, FLOAT_EPSILON);
        Assert.assertEquals(
                (tex.getOffsetY() + tex.size()) / PARENT_SIZE, tex.uv().bottom, FLOAT_EPSILON);
    }
}