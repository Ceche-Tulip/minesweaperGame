import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWin extends JFrame {

    int width = 2 * GameUtil.OFFSET + GameUtil.MAP_W * GameUtil.SQUARE_LENGTH; // 窗口宽度
    int height = 4 * GameUtil.OFFSET + GameUtil.MAP_H * GameUtil.SQUARE_LENGTH; // 窗口高度

    Image offScreenImage = null; // 离屏图像

    MapButtom mapButton = new MapButtom(); // 地图按钮
    MapTop mapTop = new MapTop(); // 地图顶部
    GameSelect gameSelect = new GameSelect(); // 游戏选择

    // 是否开始， false 未开始， true 开始
    boolean begin = false;

    void launch() {
        GameUtil.START_TIME = System.currentTimeMillis(); // 获取当前时间

        this.setVisible(true); // 设置窗口是否可见
        if (GameUtil.state == 3) { // 在游戏选择的时候
            this.setSize(500, 650); // 设置选择游戏难度窗口大小
        } else {
            this.setSize(width, height); // 设置游戏中窗口大小
        }
        this.setLocationRelativeTo(null); // 设置窗口位置
        this.setTitle("Peking's_Game"); // 设置窗口标题
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // 设置关闭窗口操作

        // 鼠标事件
        this.addMouseListener(new MouseAdapter() { // 鼠标点击
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                switch (GameUtil.state) {
                    case 0:
                        if (e.getButton() == 1) { // 鼠标 -> 左键 -> 被点击
                            System.out.println("1 : 鼠标 - 左键 - 被点击");
                            GameUtil.MOUSE_X = e.getX(); // 获取鼠标的X坐标
                            GameUtil.MOUSE_Y = e.getY(); // 获取鼠标的Y坐标
                            // 修改鼠标左键的状态为true
                            GameUtil.LEFT = true; // 鼠标左键被点击
                        }
                        if (e.getButton() == 3) { // 鼠标 -> 右键 -> 被点击
                            System.out.println("3 : 鼠标 - 右键 - 被点击");
                            GameUtil.MOUSE_X = e.getX(); // 获取鼠标的X坐标
                            GameUtil.MOUSE_Y = e.getY(); // 获取鼠标的Y坐标
                            // 修改鼠标右键的状态为true
                            GameUtil.RIGHT = true; // 鼠标右键被点击
                        }

                    case 1:

                    case 2:
                        if (e.getButton() == 1) {
                            if (e.getX() > GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W / 2) &&
                                    e.getX() < GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W / 2) +
                                            GameUtil.SQUARE_LENGTH
                                    &&
                                    e.getY() > GameUtil.OFFSET &&
                                    e.getY() < GameUtil.OFFSET + GameUtil.SQUARE_LENGTH) {
                                // 重置游戏
                                mapButton.reGame();
                                mapTop.reGame();
                                GameUtil.FLAG_NUM = 0; // 释放旗帜数量
                                GameUtil.START_TIME = System.currentTimeMillis(); // 获取当前时间
                                GameUtil.state = 0;
                            }

                        }
                        if (e.getButton() == 2) {
                            GameUtil.state = 3; // 进入难度选择界面
                            begin = true;
                        }
                        break;
                    case 3:
                        if (e.getButton() == 1) { // 鼠标 -> 左键 -> 被点击
                            GameUtil.MOUSE_X = e.getX(); // 获取鼠标的X坐标
                            GameUtil.MOUSE_Y = e.getY(); // 获取鼠标的Y坐标
                            begin = gameSelect.hard(); // 判断是否点击到了难度选择框
                        }
                        break;
                    default:
                        break;
                }

            }
        }); // 鼠标监听器

        // 添加死循环绘制图像
        while (true) {
            repaint(); // 绘制
            begin(); // 判断是否开始
            try {
                Thread.sleep(40); // 休眠40毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void begin() {
        if (begin) {
            begin = false; // 释放begin状态
            gameSelect.hard(GameUtil.level); // 载入对应的难度参数
            dispose(); // 关闭窗口
            GameWin gameWin = new GameWin(); // 重新加载GameWin类用来新建一个窗口
            GameUtil.START_TIME = System.currentTimeMillis(); // 重置开始时间为当前时间
            GameUtil.FLAG_NUM = 0; // 重置旗帜数
            // 重新开始游戏
            mapButton.reGame();
            mapTop.reGame();
            gameWin.launch(); // 启动游戏
        }
    }

    // @Override
    // public void paint(Graphics g) {
    // if (GameUtil.state == 3) { // 如果选择难度
    // g.setColor(Color.white); // 设置窗口为白色
    // g.fillRect(0, 0, 500, 500); // 填充
    // gameSelect.paintSelf(g); // 绘制游戏选择
    // } else {

    // offScreenImage = this.createImage(width, height); // 创建离屏图像
    // Graphics gImage = offScreenImage.getGraphics(); // 获取离屏图像的画笔

    // // 设置背景颜色
    // gImage.setColor(Color.white);
    // gImage.fillRect(0, 0, width, height);

    // mapButton.paintSelf(gImage); // 绘制地图按钮
    // mapTop.paintSelf(gImage); // 绘制地图顶部
    // g.drawImage(offScreenImage, 0, 0, null);
    // }
    // }
    @Override
    public void paint(Graphics g) {
        // 创建离屏图像
        offScreenImage = this.createImage(width, height);
        Graphics gImage = offScreenImage.getGraphics(); // 获取离屏图像的画笔

        // 设置背景颜色
        gImage.setColor(Color.white);
        gImage.fillRect(0, 0, width, height);

        if (GameUtil.state == 3) { // 如果选择难度
            gImage.setColor(Color.white); // 设置窗口为白色
            gImage.fillRect(0, 0, 500, 500); // 填充
            gameSelect.paintSelf(gImage); // 绘制游戏选择
        } else {
            mapButton.paintSelf(gImage); // 绘制地图按钮
            mapTop.paintSelf(gImage); // 绘制地图顶部
        }

        // 将离屏图像绘制到屏幕上
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public static void main(String[] args) {
        GameWin game = new GameWin();
        game.launch();
    }
}