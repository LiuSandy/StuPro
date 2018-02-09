package org.seckill.dto;

import org.seckill.entity.SucessKilled;
import org.seckill.enums.SeckillStatEnum;

/**
 * 封装秒杀执行后的结果
 */
public class SeckillExecution {

    private long seckillId;

    // 秒杀执行结果状态
    private int state;

    // 状态表示
    private String stateInfo;

    // 秒杀成功对象
    private SucessKilled sucessKilled;

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", sucessKilled=" + sucessKilled +
                '}';
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SucessKilled getSucessKilled() {
        return sucessKilled;
    }

    public void setSucessKilled(SucessKilled sucessKilled) {
        this.sucessKilled = sucessKilled;
    }

    public SeckillExecution(long seckillId, SeckillStatEnum statEnum, SucessKilled sucessKilled) {
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
        this.sucessKilled = sucessKilled;
    }

    public SeckillExecution(long seckillId, SeckillStatEnum statEnum) {
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
    }


}
