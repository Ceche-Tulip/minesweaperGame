import java.awt.*;

/**
 * 底层地图
 * 绘制游戏相关的组件
 */
public class MapButtom {

    BottomRay bottomRay = new BottomRay();
    BottomNum bottomNum = new BottomNum();

    // 第一次创建雷和数字
    {
        bottomRay.newRay(); // 调用生成雷
        bottomNum.newNum(); // 调用生成数字

    }

    // 重置游戏方法
    void reGame() {
        for (int i = 1; i <= GameUtil.MAP_W; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                GameUtil.DATA_BOTTOM[i][j] = 0;
            }
        }
        bottomRay.newRay(); // 调用生成雷
        bottomNum.newNum(); // 调用生成数字
    }

    // 绘制方法
    void paintSelf(Graphics g) {
        g.setColor(Color.red); // 颜色
        // 画竖线
        for (int i = 0; i <= GameUtil.MAP_W; i++) {
            g.drawLine(GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH, // 左下角
                    3 * GameUtil.OFFSET, // 左上角
                    GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH, // 右上角
                    GameUtil.MAP_H * GameUtil.SQUARE_LENGTH + 3 * GameUtil.OFFSET); // 右下角
        }

        // 画横线
        for (int i = 0; i <= GameUtil.MAP_H; i++) {
            g.drawLine(GameUtil.OFFSET, // 左下角
                    3 * GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH, // 左上角
                    GameUtil.OFFSET + GameUtil.MAP_W * GameUtil.SQUARE_LENGTH, // 右上角
                    3 * GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH); // 右下角
        }

        // 生成地图
        for (int i = 1; i <= GameUtil.MAP_W; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                // 生成地雷
                if (GameUtil.DATA_BOTTOM[i][j] == -1) {
                    g.drawImage(GameUtil.lei,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            // 定义图片的宽和高
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }
                // 生成数字
                if (GameUtil.DATA_BOTTOM[i][j] >= 0) {
                    g.drawImage(GameUtil.images[GameUtil.DATA_BOTTOM[i][j]],
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 15,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 5,
                            null);
                }
            }
        }

        // 绘制数字, 剩余雷数
        GameUtil.drawWord(g, "" + (GameUtil.RAY_MAX - GameUtil.FLAG_NUM),
                GameUtil.OFFSET,
                2 * GameUtil.OFFSET, 30, Color.red);
        // 倒计时
        GameUtil.drawWord(g, "" + (GameUtil.END_TIME - GameUtil.START_TIME) / 1000, // 计算剩余时间并换算成秒
                GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W - 1),
                2 * GameUtil.OFFSET, 30, Color.red);

        switch (GameUtil.state) {
            case 0:
                GameUtil.END_TIME = System.currentTimeMillis(); // 获取游戏结束时间
                g.drawImage(GameUtil.face,
                        GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W / 2),
                        GameUtil.OFFSET,
                        null);
                break;
            case 1:
                g.drawImage(GameUtil.win,
                        GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W / 2),
                        GameUtil.OFFSET,
                        null);
                break;
            case 2:
                g.drawImage(GameUtil.over,
                        GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W / 2),
                        GameUtil.OFFSET,
                        null);
                break;
        }
    }
}