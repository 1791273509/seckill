package org.seckill.dao;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest extends TestCase {
    @Autowired
    private SuccessKilledDao successKilledDao;
    @Test
    public void testInsertSuccessKilled() {
        System.out.println(successKilledDao.insertSuccessKilled(10100L, 10L));

    }

    @Test
    public void testQueryByIdWithSeckill() {
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(1000, 10);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}