package flashlight.app;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Boolean hasFlash;
    private Boolean flashState;
    private CameraManager cameraManager;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            getSupportActionBar().hide(); //removes the top title bar
        } catch (NullPointerException e) {
            // ignore as title bar was already removed
        }

        imageButton = findViewById(R.id.imageButton4);
        imageButton.setOnClickListener(new SwitchListener());

        try {
            initialize();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void initialize() throws CameraAccessException {
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            hasFlash = cameraManager.getCameraCharacteristics("0").get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
        } catch (NullPointerException e) {
            hasFlash = false;
        }
        flashState = false;
        if (!hasFlash) {
            displayFlashNotAvailableMessage();
        }
    }

    public Boolean getFlashState() {
        return flashState;
    }

    public void displayFlashNotAvailableMessage() {
        Toast.makeText(this, "Flash is not available on this device", Toast.LENGTH_SHORT).show();
    }


    private class SwitchListener implements ImageButton.OnClickListener {
        @Override
        public void onClick(View view) {
            if (hasFlash) {
                try {
                    String cameraId = cameraManager.getCameraIdList()[0];
                    if (!flashState) {
                        cameraManager.setTorchMode(cameraId, true);
                        imageButton.setImageResource(R.drawable.on_button);
                        flashState = true;
                    } else {
                        cameraManager.setTorchMode(cameraId, false);
                        imageButton.setImageResource(R.drawable.off_button);
                        flashState = false;
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            } else {
                displayFlashNotAvailableMessage();
            }
        }
    }
}
