package org.seckill.service;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/*.xml")
@Slf4j
public class SeckillServiceTest extends TestCase {


    @Autowired
    private SeckillService seckillService;


    @Test
    public void testGetSeckillList() {
        log.info("list{}",seckillService.getSeckillList());
    }

    @Test
    public void testGetById() {
        log.info("GetById{}",seckillService.getById(1000L));
    }

    @Test
    public void testExportSeckillUrl() {
        Exposer exposer = seckillService.exportSeckillUrl(1000l);
        log.info("{}",exposer);
    }
    @Test

    public void testExecuteSeckill() {

        System.out.println(seckillService.executeSeckill(1000, 121, ""));
    }
}