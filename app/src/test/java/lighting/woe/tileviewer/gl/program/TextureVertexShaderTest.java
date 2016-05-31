package lighting.woe.tileviewer.gl.program;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import lighting.woe.BaseTest;
import lighting.woe.tileviewer.BuildConfig;
import lighting.woe.tileviewer.R;

import static org.junit.Assert.*;


@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class TextureVertexShaderTest extends BaseTest {

    private TextureVertexShader mShader;
    private String mShaderSource;

    @Before
    public void setUp() throws Exception {
        mShader = new TextureVertexShader();

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                RuntimeEnvironment.application.getResources()
                        .openRawResource(R.raw.texture_vertex)));

        StringBuilder sb = new StringBuilder();
        while (reader.ready()) {
            sb.append(reader.readLine()).append("\n");
        }
        mShaderSource = sb.toString().trim();
    }

    @Test
    public void testSource() throws Exception {
        Assert.assertEquals(mShaderSource, mShader.source());
    }
}