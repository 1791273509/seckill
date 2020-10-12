package org.seckill.enums;

/**
 * @author 17912
 */

public enum SeckillStateEnum {
    DATE_REWRITE(-3, "数据篡改"),
    END(0, "秒杀结束"),
    INNER_ERROR(-2, "系统异常"),
    REPEAT_KILL(-1, "重复秒杀"),
    SUCCESS(1, "秒杀成功");
    private int state;

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    private String stateInfo;

    SeckillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }


    public static SeckillStateEnum stateOf(int index) {
        for (SeckillStateEnum value : values()) {
            if (value.getState() == index) {
                return value;
            }
        }
        return null;
    }
}
