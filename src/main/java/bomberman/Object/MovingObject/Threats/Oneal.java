package bomberman.Object.MovingObject.Threats;

import bomberman.GlobalVariable.FilesPath;
import bomberman.GlobalVariable.SoundVariable;
import bomberman.Map.PlayGround;

public class Oneal extends Enemy {
    /**
     * Constructor cho Oneal.
     *
     * @param belongTo tham chiếu tới PlayGround
     * @param x        tọa độ x
     * @param y        tọa độ y
     * @param width    chiều rộng
     * @param length   chiều dài
     */
    public Oneal(PlayGround belongTo, double x, double y, double width, double length) {
        super(belongTo, x, y, width, length);
    }

    /**
     * Constructor cho Oneal.
     *
     * @param belongTo tham chiếu tới PlayGround
     * @param x        tọa độ x
     * @param y        tọa độ y
     */
    public Oneal(PlayGround belongTo, double x, double y) {
        super(belongTo, x, y);
    }

    @Override
    public void setGraphicData() {
        setNumberOfFrame(8);
        setNumberOfGameFramePerFrame(3);

        setImageData(FilesPath.OnealUp, FilesPath.OnealDown, FilesPath.OnealLeft, FilesPath.OnealRight);
    }

    public void die() {
        SoundVariable.playSound(FilesPath.OnealDieAudio);
    }
}
