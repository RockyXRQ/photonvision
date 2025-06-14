package org.photonvision.vision.camera.USBCameras;

import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoException;
import edu.wpi.first.math.MathUtil;
import org.photonvision.common.configuration.CameraConfiguration;

public class Ks1a293CameraSettables extends GenericUSBCameraSettables {
    public Ks1a293CameraSettables(CameraConfiguration configuration, UsbCamera camera) {
        super(configuration, camera);
    }

    @Override
    public void setAllCamDefaults() {
        softSet("power_line_frequency", 1); // Assume 50Hz China
        softSet("white_balance_automatic", 0);
        autoExposureProp.set(1);
    }

    @Override
    protected void setUpExposureProperties() {
        super.setUpExposureProperties();

        this.minExposure = 1;
        this.maxExposure = 1024;
    }

    @Override
    public void setAutoExposure(boolean cameraAutoExposure) {
        super.setAutoExposure(false);
    }

    @Override
    public void setExposureRaw(double exposureRaw) {
        if (exposureRaw >= 0.0) {
            try {
                int propVal = (int) MathUtil.clamp(exposureRaw, minExposure, maxExposure);
                softSet("iris_absolute", propVal);
                this.lastExposureRaw = exposureRaw;
            } catch (VideoException e) {
                logger.error("Failed to set camera exposure!", e);
            }
        }
    }
}
