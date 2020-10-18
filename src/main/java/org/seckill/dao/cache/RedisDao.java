package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import lombok.extern.slf4j.Slf4j;
import org.seckill.entity.Seckill;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Slf4j
public class RedisDao {
    private JedisPool jedispool;
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);


    public RedisDao(String ip, int port) {
        jedispool = new JedisPool(ip, port);
    }

    public Seckill getSeckill(long seckillId) {
        Jedis jedis = jedispool.getResource();
        try {
            String key = "seckill:" + seckillId;
            byte[] bytes = jedis.get(key.getBytes());
            if (bytes != null) {
                Seckill res = schema.newMessage();
                ProtostuffIOUtil.mergeFrom(bytes, res, schema);
                return res;
            }
        } catch (Exception e) {
            log.info("exception:" + e.getMessage());
        } finally {

            jedis.close();
        }
        return null;

    }

    public String putSeckill(Seckill seckill) {
        Jedis jedis = jedispool.getResource();
        try {
            String key = "seckill:" + seckill.getSeckillId();
            byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                    LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            int time = 60 * 60;
            jedis.setex(key.getBytes(),time,bytes);

        }catch (Exception e){

        }finally {
            jedis.close();

        }
        return "";

    }

}