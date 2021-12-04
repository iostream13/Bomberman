package bomberman.Object.MovingObject.Threats;

import bomberman.GlobalVariable.FilesPath;
import bomberman.GlobalVariable.SoundVariable;
import bomberman.Map.PlayGround;

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
    public void setGraphicData() {
        setNumberOfFrame(8);
        setNumberOfGameFramePerFrame(3);

        setImageData(FilesPath.BalloomUp, FilesPath.BalloomDown, FilesPath.BalloomLeft, FilesPath.BalloomRight);
    }

    public void die() {
        SoundVariable.playSound(FilesPath.BalloomDieAudio);
    }
}
