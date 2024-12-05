import java.awt.Color;
import java.awt.Graphics;

/**
 * 难度选择类
 */
public class GameSelect {

    // 判断是否点击到选择难度按钮
    boolean hard() {
        if (GameUtil.MOUSE_X > 100 && GameUtil.MOUSE_X < 400) {
            if (GameUtil.MOUSE_Y > 50 && GameUtil.MOUSE_Y < 150) {
                GameUtil.level = 1; // 选择了简单模式
                GameUtil.state = 0; // 进入游戏状态
                return true;
            }
            if (GameUtil.MOUSE_Y > 200 && GameUtil.MOUSE_Y < 300) {
                GameUtil.level = 2; // 选择了普通模式
                GameUtil.state = 0; // 进入游戏状态
                return true;
            }
            if (GameUtil.MOUSE_Y > 350 && GameUtil.MOUSE_Y < 450) {
                GameUtil.level = 3; // 选择了困难模式
                GameUtil.state = 0; // 进入游戏状态
                return true;
            }
        }
        return false;
    }

    void paintSelf(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(100, 50, 300, 100);
        GameUtil.drawWord(g, "Easy", 220, 100, 30, Color.black);
        g.drawRect(100, 200, 300, 100);
        GameUtil.drawWord(g, "Comment", 220, 250, 30, Color.black);
        g.drawRect(100, 350, 300, 100);
        GameUtil.drawWord(g, "Hard", 220, 400, 30, Color.black);
        g.drawRect(100, 500, 300, 100);
        GameUtil.drawWord(g, "游戏制作人：Herwin", 100, 565, 30, Color.black);
    }

    // 重置参数的方法(方法重载)
    void hard(int level) {
        switch (level) {
            case 1:
                GameUtil.RAY_MAX = 10; // 设置地雷数为10个
                // 设置地图大小为9*9
                GameUtil.MAP_W = 9;
                GameUtil.MAP_H = 9;
                break;
            case 2:
                GameUtil.RAY_MAX = 40; // 设置地雷数为40个
                // 设置地图大小为16*16
                GameUtil.MAP_W = 16;
                GameUtil.MAP_H = 16;
                break;
            case 3:
                GameUtil.RAY_MAX = 99; // 设置地雷数为99个
                // 设置地图大小为30*16
                GameUtil.MAP_W = 30;
                GameUtil.MAP_H = 16;
                break;
            default:
                break;
        }
    }
}
