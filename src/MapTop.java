
import java.awt.*;

/**
 * 顶层地图类
 * 绘制顶层组件与判断逻辑
 */
public class MapTop {

    // 格子位置
    int temp_x;
    int temp_y;

    // 重置游戏方法
    void reGame() {
        for (int i = 1; i <= GameUtil.MAP_W; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                GameUtil.DATA_TOP[i][j] = 0;
            }
        }
    }

    // 判断鼠标点击逻辑
    void logic() {

        temp_x = 0; // 初始化x位置
        temp_y = 0; // 初始化y位置

        if (GameUtil.MOUSE_X > GameUtil.OFFSET && GameUtil.MOUSE_Y > GameUtil.OFFSET * 3) { // 保证判断时鼠标坐标为正数
            temp_x = (GameUtil.MOUSE_X - GameUtil.OFFSET) / GameUtil.SQUARE_LENGTH + 1; // 判断鼠标点击了格子的x位置
            temp_y = (GameUtil.MOUSE_Y - GameUtil.OFFSET * 3) / GameUtil.SQUARE_LENGTH + 1; // 判断鼠标点击了格子的y位置
        }
        // 判断鼠标是否在雷区
        if (temp_x >= 1 && temp_x <= GameUtil.MAP_W
                && temp_y >= 1 && temp_y <= GameUtil.MAP_H) {

            // 如果在，判断鼠标点击位置
            if (GameUtil.LEFT == true) { // 如果鼠标左键被点击
                // 输出鼠标坐标
                System.out.println(GameUtil.MOUSE_X);
                System.out.println(GameUtil.MOUSE_Y);

                // 覆盖则翻开
                if (GameUtil.DATA_TOP[temp_x][temp_y] == 0) {
                    GameUtil.DATA_TOP[temp_x][temp_y] = -1; // 将该位置设置为-1（无覆盖）（即打开）
                }
                spaceOpen(temp_x, temp_y); // 调用打开格子方法
                GameUtil.LEFT = false; // 左键点击后，将标志位设为false
            }
            if (GameUtil.RIGHT == true) { // 如果鼠标右键被点击
                // 输出鼠标坐标
                System.out.println(GameUtil.MOUSE_X);
                System.out.println(GameUtil.MOUSE_Y);

                // 覆盖则插旗
                if (GameUtil.DATA_TOP[temp_x][temp_y] == 0) {
                    GameUtil.DATA_TOP[temp_x][temp_y] = 1; // 将该位置设置为1（插旗）
                    GameUtil.FLAG_NUM++; // 插旗数加1
                }
                // 插旗则取消
                else if (GameUtil.DATA_TOP[temp_x][temp_y] == 1) {
                    GameUtil.DATA_TOP[temp_x][temp_y] = 0; // 将该位置设置为0（取消插旗）
                    GameUtil.FLAG_NUM--; // 插旗数减1
                } else if (GameUtil.DATA_TOP[temp_x][temp_y] == -1) {
                    numOpen(temp_x, temp_y);
                }

                GameUtil.RIGHT = false; // 右键点击后，将标志位设为false
            }
        }
        boom(); // 失败判断
        victory(); // 胜利判断
    }

    // 失败显示
    void seeBoor() {
        for (int i = 1; i <= GameUtil.MAP_W; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                // 底层为雷，顶层未插旗，显示雷
                if (GameUtil.DATA_BOTTOM[i][j] == -1 && GameUtil.DATA_TOP[i][j] != 1) {
                    GameUtil.DATA_TOP[i][j] = -1;
                }
                // 底层不是雷，顶层插旗，显示插错旗
                if (GameUtil.DATA_BOTTOM[i][j] != -1 && GameUtil.DATA_TOP[i][j] == 1) {
                    GameUtil.DATA_TOP[i][j] = 2;
                }
            }
        }
    }

    // 数字翻开
    void numOpen(int x, int y) {
        // 记录插旗数
        int count = 0;
        if (GameUtil.DATA_BOTTOM[x][y] > 0) {
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (GameUtil.DATA_TOP[i][j] == 1) {
                        count++;
                    }
                }
            }
            if (count == GameUtil.DATA_BOTTOM[x][y]) {
                for (int i = x - 1; i <= x + 1; i++) {
                    for (int j = y - 1; j <= y + 1; j++) {
                        if (GameUtil.DATA_TOP[i][j] != 1) { // 如果格子没有被插旗
                            GameUtil.DATA_TOP[i][j] = -1;
                        }
                        // 必须在雷区中,打开空格
                        if (i >= 1 && j >= 1 && i <= GameUtil.MAP_W && j <= GameUtil.MAP_H) {
                            spaceOpen(i, j); // 调用自己
                        }
                    }
                }
            }

            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (GameUtil.DATA_TOP[i][j] != -1) { // 如果格子没有被打开
                        GameUtil.DATA_TOP[i][j] = -1; // 打开格子
                        // 必须在雷区中
                        if (i >= 1 && j >= 1 && i <= GameUtil.MAP_W && j <= GameUtil.MAP_H) {
                            spaceOpen(i, j); // 调用自己
                        }
                    }
                }
            }
        }
    }

    // 失败判断, true -> 失败, false -> 未失败
    boolean boom() {
        if (GameUtil.FLAG_NUM == GameUtil.RAY_MAX) {
            for (int i = 1; i <= GameUtil.MAP_W; i++) {
                for (int j = 1; j <= GameUtil.MAP_H; j++) {
                    if (GameUtil.DATA_TOP[i][j] == 0) {
                        GameUtil.DATA_TOP[i][j] = -1;
                    }
                }
            }
        }
        for (int i = 1; i <= GameUtil.MAP_W; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                if (GameUtil.DATA_BOTTOM[i][j] == -1 && GameUtil.DATA_TOP[i][j] == -1) {
                    System.out.println("You Faild."); // 失败
                    GameUtil.state = 2; // 失败状态
                    seeBoor(); // 显示剩余的雷与插错的旗
                    return true;

                }
            }
        }
        return false;
    }

    // 胜利判断 true -> 胜利, false -> 未胜利
    boolean victory() {
        // 统计未打开格子数
        int count = 0;
        for (int i = 1; i <= GameUtil.MAP_W; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                if (GameUtil.DATA_TOP[i][j] != -1) {
                    count++;
                }
            }
        }
        if (count == GameUtil.RAY_MAX) { // 如果被打开格子数等于雷的总数
            System.out.println("You Win."); // 胜利
            GameUtil.state = 1; // 胜利状态
            for (int i = 1; i <= GameUtil.MAP_W; i++) {
                for (int j = 1; j <= GameUtil.MAP_H; j++) {
                    // 未翻开变成旗
                    if (GameUtil.DATA_TOP[i][j] == 0) {
                        GameUtil.DATA_TOP[i][j] = 1; // 将该位置设置为1（变成旗）
                    }
                }
            }
            return true;
        }
        return false;
    }

    // 打开空格
    void spaceOpen(int x, int y) {
        if (GameUtil.DATA_BOTTOM[x][y] == 0) {
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    // 覆盖,才递归
                    if (GameUtil.DATA_TOP[i][j] != -1) { // 如果格子没有被打开
                        if (GameUtil.DATA_TOP[i][j] == 1) {
                            GameUtil.FLAG_NUM--; // 插旗数减1
                        }
                        GameUtil.DATA_TOP[i][j] = -1; // 打开格子
                        // 必须在雷区中
                        if (i >= 1 && j >= 1 && i <= GameUtil.MAP_W && j <= GameUtil.MAP_H) {
                            spaceOpen(i, j); // 调用自己
                        }

                    }

                }
            }
        }
    }

    // 绘制方法
    void paintSelf(Graphics g) {
        logic();
        // 生成地图
        for (int i = 1; i <= GameUtil.MAP_W; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                // 覆盖
                if (GameUtil.DATA_TOP[i][j] == 0) {
                    g.drawImage(GameUtil.top,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            // 定义图片的宽和高
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }

                // 插旗
                if (GameUtil.DATA_TOP[i][j] == 1) {
                    g.drawImage(GameUtil.flag,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            // 定义图片的宽和高
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }

                // 错插旗
                if (GameUtil.DATA_TOP[i][j] == 2) {
                    g.drawImage(GameUtil.noflag,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            // 定义图片的宽和高
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);

                }
            }
        }
    }
}
