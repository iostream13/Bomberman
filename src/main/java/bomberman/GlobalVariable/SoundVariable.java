package bomberman.GlobalVariable;

import javax.sound.sampled.Clip;

public class SoundVariable {
    public static void playSound(Clip clip) {
        clip.start();
        resetSound(clip);
    }

    public static void loopSound(Clip clip, int time) {
        clip.loop(time);
        resetSound(clip);
    }

    public static void endSound(Clip clip) {
        clip.stop();
        resetSound(clip);
    }

    public static void resetSound(Clip clip) {
        clip.setMicrosecondPosition(0);
    }

    public static void endAllSounds() {
        endSound(FilesPath.BalloomDieAudio);
        endSound(FilesPath.BomberDieAudio);
        endSound(FilesPath.YouLoseAudio);
        endSound(FilesPath.ExplosionAudio);
        endSound(FilesPath.OnealDieAudio);
        endSound(FilesPath.YouWonAudio);
        endSound(FilesPath.PlayGroundAudio);
        endSound(FilesPath.PowerUpAudio);
        endSound(FilesPath.ExplosionAudio);
        endSound(FilesPath.PlaceBombAudio);
        endSound(FilesPath.LevelUpAudio);
    }
}
