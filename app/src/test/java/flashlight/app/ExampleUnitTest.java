package flashlight.app;

import android.hardware.camera2.CameraAccessException;

import org.junit.Test;

import static org.junit.Assert.*;


public class ExampleUnitTest {
    MainActivity mainActivity;

    @Test
    public void testInitialization() {
        try {
            mainActivity.initialize();
        } catch (CameraAccessException e) {
        }
        assertFalse(mainActivity.getFlashState());
    }


}