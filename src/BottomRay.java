/**
 * 初始化地雷
 */
public class BottomRay {
    // 存放坐标
    static int[] rays = new int[GameUtil.RAY_MAX * 2]; // 设置为静态类防止数组越界
    // 地雷坐标
    int x, y;
    // 是否放置, T 表示可以放置，F 表示不可以放置
    boolean isPlace = true;

    // 生成地雷
    void newRay() {
        for (int i = 0; i < GameUtil.RAY_MAX * 2; i += 2) {
            x = (int) (Math.random() * GameUtil.MAP_W + 1); // 1 - 12
            y = (int) (Math.random() * GameUtil.MAP_H + 1); // 1 - 12

            // 判断坐标是否存在，如果存在则重新生成坐标
            for (int j = 0; j < i; j += 2) {
                if (x == rays[j] && y == rays[j + 1]) {
                    i -= 2;
                    isPlace = false;
                    break;
                }
            }
            // 将坐标放入数组
            if (isPlace) {
                // 将生成的坐标复制到数组中
                rays[i] = x;
                rays[i + 1] = y;
            }
            isPlace = true; // 释放状态
        }

        for (int i = 0; i < GameUtil.RAY_MAX * 2; i += 2) {
            GameUtil.DATA_BOTTOM[rays[i]][rays[i + 1]] = -1; // 初始化地雷
        }
    }
}
