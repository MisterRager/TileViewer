package lighting.woe;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

import java.util.Random;

public class BaseTest {

    protected static final float FLOAT_EPSILON = 0.000001f;
    protected static final Random sRandom = new Random(System.currentTimeMillis());

    @Before
    public void initMockitoAnnotations() {
        MockitoAnnotations.initMocks(this);
    }
}
