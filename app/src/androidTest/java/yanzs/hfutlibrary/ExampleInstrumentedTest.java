package yanzs.hfutlibrary;

import android.content.Context;

import static junit.framework.Assert.assertEquals;

/**
 * Instrumented use_default_head, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under use_default_head.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("yanzs.hfutlibrary", appContext.getPackageName());
    }
}
