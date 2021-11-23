package bomberman.Object.NonMovingObject;

import bomberman.GlobalVariable.ImagesPath;

public class Item extends Block {
    /**
     * List type of item.
     */
    public enum typeOfItems {
        BOMB_ITEM_,
        FLAME_ITEM_,
        SPEED_ITEM_,
    }

    /**
     * This item's type.
     */
    typeOfItems type;

    /**
     * Status if this item has been eaten.
     */
    private boolean ateStatus = false;

    public typeOfItems getType() {
        return type;
    }

    public void setAteStatus(boolean ateStatus) {
        this.ateStatus = ateStatus;
    }

    public boolean getAteStatus() {
        return ateStatus;
    }

    /**
     * Constructor cho Item.
     *
     * @param x      tọa độ x
     * @param y      tọa độ y
     * @param width  chiều rộng
     * @param length chiều dài
     */
    public Item(double x, double y, double width, double length) {
        super(x, y, width, length);
    }

    /**
     * Constructor cho Item.
     *
     * @param x      tọa độ x
     * @param y      tọa độ y
     * @param width  chiều rộng
     * @param length chiều dài
     * @param type   loại item
     */
    public Item(double x, double y, double width, double length, typeOfItems type) {
        super(x, y, width, length);

        this.type = type;
    }

    @Override
    public void setFinalStateImageInfo() {
        if (ateStatus) {
            FINAL_STATE_IMAGE = ImagesPath.Grass;
        } else if (type == typeOfItems.BOMB_ITEM_) {
            FINAL_STATE_IMAGE = ImagesPath.PowerUpBomb;
        } else if (type == typeOfItems.FLAME_ITEM_) {
            FINAL_STATE_IMAGE = ImagesPath.PowerUpFlame;
        } else {
            FINAL_STATE_IMAGE = ImagesPath.PowerUpSpeed;
        }
    }
}
