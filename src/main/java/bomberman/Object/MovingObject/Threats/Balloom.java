package bomberman.Object.MovingObject.Threats;

import bomberman.GlobalVariable.FilesPath;
import bomberman.GlobalVariable.RenderVariable;
import bomberman.GlobalVariable.SoundVariable;
import bomberman.Map.PlayGround;
import javafx.scene.image.Image;

import javax.sound.sampled.FloatControl;

public class Balloom extends Enemy {
    /**
     * Constructor cho Balloom.
     *
     * @param belongTo tham chiếu tới PlayGround
     * @param x        tọa độ x
     * @param y        tọa độ y
     * @param width    chiều rộng
     * @param length   chiều dài
     */
    public Balloom(PlayGround belongTo, double x, double y, double width, double length) {
        super(belongTo, x, y, width, length);
    }

    /**
     * Constructor cho Balloom.
     *
     * @param belongTo tham chiếu tới PlayGround
     * @param x        tọa độ x
     * @param y        tọa độ y
     */
    public Balloom(PlayGround belongTo, double x, double y) {
        super(belongTo, x, y);
    }

    @Override
    public Image getImage() {
        return FilesPath.Balloom;
    }

    @Override
    public void setGraphicSetting() {
        setNumberOfFramePerSprite(3);
    }

    public void die() {
        FloatControl volume = (FloatControl) FilesPath.BalloomDieAudio.getControl(FloatControl.Type.MASTER_GAIN);
        if (!RenderVariable.stateSound) {
            volume.setValue(volume.getMinimum());
        }
        else {
            volume.setValue(6);
        }
        SoundVariable.playSound(FilesPath.BalloomDieAudio);
    }
}
