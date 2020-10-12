package org.seckill.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Slf4j
public class SeckillServiceimpl implements SeckillService {

    private SeckillDao seckillDao;

    private SuccessKilledDao successKilledDao;

//md5盐值字符串，用于混淆
    private final String salt = "reqwreqrqewrqe";

    private String getmd5(long seckillId){
        String base = seckillId + "/" + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
    @Override
    public List<Seckill> getSeckillList() {

        return seckillDao.queryAll(0,4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

//    输出秒杀地址
    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill byId = getById(seckillId);
        if(byId == null){
            return new Exposer(false,seckillId);
        }
        long starttime = byId.getStartTime().getTime();
        long endtime = byId.getEndTime().getTime();
        long now = System.currentTimeMillis();
        if(starttime > now || endtime < now){
            return new Exposer(false,seckillId,now,starttime,endtime);
        }
        String md5 = getmd5(seckillId);

        return new Exposer(true,md5,seckillId);
    }

    @Override
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        try {
            if (StringUtils.isEmpty(md5)  ||  !md5.equals(getmd5(seckillId))) {
                throw new SeckillException("seckill data rewrite");
            }
//        执行秒杀逻辑
            int number = seckillDao.reduceNumber(seckillId, new Date());
            if(number <= 0 ){
                throw new SeckillCloseException("seckill is closed");
            }else{
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if(insertCount <= 0){
                    throw new RepeatKillException("seckill repeate");

                }else{
//                秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId,1,"秒杀成功",successKilled);

                }

            }
        }
//        捕获我们自定义的子类异常
        catch (SeckillCloseException e){
            throw e;
        }catch (RepeatKillException e){
            throw e;
        }
        catch (Exception e){
            log.info("{}",e);
//            所有编译期异常转成运行异常，spring声明式事务
            throw new SeckillException("seckill inner error:"+ e.getMessage());
        }
    }
}
