import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import java.awt.Color;
import java.awt.Font;

/**
 * 工具类，存放静态参数
 */
public class GameUtil {
    // 地雷的个数
    static int RAY_MAX = 100;

    // 地图的宽
    static int MAP_W = 36;
    // 地图的高
    static int MAP_H = 17;
    // 雷区偏移量
    static int OFFSET = 45;
    // 格子的边长
    static int SQUARE_LENGTH = 50;

    // 插旗数量
    static int FLAG_NUM = 0;

    // 鼠标参数
    // 鼠标坐标
    static int MOUSE_X;
    static int MOUSE_Y;
    // 鼠标状态
    static boolean LEFT = false;
    static boolean RIGHT = false;

    // 游戏状态 0 游戏中， 1 胜利， 2 失败, 3 难度选择
    static int state = 3;

    // 游戏难度
    static int level;

    // 倒计时
    static long START_TIME; // 开始时间
    static long END_TIME; // 结束时间

    // 底层元素， -1 雷， 0 空， 1-8 数字
    static int[][] DATA_BOTTOM = new int[MAP_W + 2][MAP_H + 2];

    // 顶层元素， -1 无覆盖， 0 覆盖， 1 插旗， 2 插错旗
    static int[][] DATA_TOP = new int[MAP_W + 2][MAP_H + 2];

    // 载入图片
    static Image lei = Toolkit.getDefaultToolkit().getImage("images/lei.png");
    static Image top = Toolkit.getDefaultToolkit().getImage("images/top.png");
    static Image flag = Toolkit.getDefaultToolkit().getImage("images/flag.png");
    static Image noflag = Toolkit.getDefaultToolkit().getImage("images/noflag.png");

    // 载入 face over win
    static Image face = Toolkit.getDefaultToolkit().getImage("images/face.GIF");
    static Image over = Toolkit.getDefaultToolkit().getImage("images/over.png");
    static Image win = Toolkit.getDefaultToolkit().getImage("images/win.png");

    // 数字图片
    static Image[] images = new Image[9];
    static {
        for (int i = 1; i <= 8; i++) {
            images[i] = Toolkit.getDefaultToolkit().getImage("images/" + i + ".png");
        }
    }

    static void drawWord(Graphics g, String str, int x, int y, int size, Color color) {
        g.setColor(color); // 设置字体颜色
        g.setFont(new Font("仿宋", Font.BOLD, size)); // 设置字体
        g.drawString(str, x, y);
    }
}
