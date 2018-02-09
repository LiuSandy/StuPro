package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SucessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/spring-dao.xml"})
public class SucessKilledDaoTest {


    @Autowired
    private SucessKilledDao sucessKilledDao;
    @Test
    public void insertSucessKilled() {
        long id = 1001L;
        long phone = 18624048680L;
        int insertCount = sucessKilledDao.insertSucessKilled(id,phone);
        System.out.println("insertCount="+insertCount);
    }

    @Test
    public void queryByIdWithSeckill() {
        long id = 1001L;
        long phone = 18624048680L;
        SucessKilled sucessKilled = sucessKilledDao.queryByIdWithSeckill(id,phone);
        System.out.println(sucessKilled);
        System.out.println(sucessKilled.getSeckill());
    }
}