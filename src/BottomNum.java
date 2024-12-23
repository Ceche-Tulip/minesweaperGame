/**
 * 底层数字类
 */
public class BottomNum {
    void newNum() {
        for (int i = 1; i <= GameUtil.MAP_W; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                // 判断这个位置是否是雷
                if (GameUtil.DATA_BOTTOM[i][j] == -1) { // 如果是雷
                    for (int k = i - 1; k <= i + 1; k++) {
                        for (int l = j - 1; l <= j + 1; l++) {
                            // 判断区域是否为数字
                            if (GameUtil.DATA_BOTTOM[k][l] >= 0) {
                                // 计算这个数字的个数
                                GameUtil.DATA_BOTTOM[k][l] += 1;
                            }
                        }
                    }
                }
            }
        }
    }
}
