package org.seckill.dao;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-dao.xml")
public class SeckillDaoTest extends TestCase {
    @Autowired
    private SeckillDao seckillDao;
    @Test
    public void testReduceNumber() {
        System.out.println(seckillDao.reduceNumber(1000, new Date()));

    }

    @Test
    public void testQueryById() {

        long seckillId=1000;
        Seckill seckill=seckillDao.queryById(seckillId);
        System.out.println(seckill.getName());
        System.out.println(seckill);

    }

    @Test
    public void testQueryAll() {
        List<Seckill> seckills = seckillDao.queryAll(01, 100);
        System.out.println(seckills.size());
        System.out.println(seckills);
    }
    @Test
    public void testupdatenum(){
        System.out.println(seckillDao.updatenum(1000, 100));
    }
}