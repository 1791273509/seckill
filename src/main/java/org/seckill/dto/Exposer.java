package org.seckill.dto;

import lombok.Data;

@Data
public class Exposer {
    public Exposer(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed, long seckillId,long now, long start, long end) {
        this.exposed = exposed;
        this.seckillId=seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }

    //    是否开启秒杀
    private boolean exposed;
    //    对秒杀地址加密
    private String md5;

    //    秒杀地址
    private long seckillId;

    //    系统当前时间
    private long now;

    //    秒杀开启时间
    private long start;

    //    秒杀开启时间
    private long end;

}
