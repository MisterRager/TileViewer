package lighting.woe.tileviewer.gl.texture;

import android.graphics.RectF;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import lighting.woe.BaseTest;
import lighting.woe.helper.ShadowRectF;
import lighting.woe.tileviewer.BuildConfig;

@Config(shadows = {ShadowRectF.class}, constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class DirectTextureTest extends BaseTest {

    private static final int SLOT = sRandom.nextInt(16);
    private static final int SIZE = sRandom.nextInt(64) + 1;

    @Test
    public void testNew() {
        DirectTexture tex = new DirectTexture(SLOT, SIZE);

        Assert.assertEquals(SLOT, tex.textureNumber());
        Assert.assertEquals(SLOT, tex.slotNumber());

        Assert.assertEquals(SIZE, tex.size());

        Assert.assertThat(tex.uv(), Matchers.isA(RectF.class));
        Assert.assertEquals(0, tex.uv().left, FLOAT_EPSILON);
        Assert.assertEquals(0, tex.uv().top, FLOAT_EPSILON);
        Assert.assertEquals(1, tex.uv().right, FLOAT_EPSILON);
        Assert.assertEquals(1, tex.uv().bottom, FLOAT_EPSILON);
    }
}