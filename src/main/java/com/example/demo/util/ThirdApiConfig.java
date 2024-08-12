package com.example.demo.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class ThirdApiConfig {

    private static Cloudinary cloudinary;
    private static JedisPool pool;

    static {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dsqhyfyud",
                "api_key", "892647361864212",
                "api_secret", "pKfod_tJFF0Pwd55mY8r8-FCtPw"));
    }

    public static Cloudinary getCloudinary() {
        return cloudinary;
    }


    static {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(60000);
        poolConfig.setTimeBetweenEvictionRunsMillis(30000);
        poolConfig.setNumTestsPerEvictionRun(-1);
//        pool = new JedisPool(poolConfig, "redis-14618.c292.ap-southeast-1-1.ec2.redns.redis-cloud.com",
//                14618, 3000, "default", "bq2UkSMCAxrdm1aYvJnpkXGokVZNqapK");

        pool = new JedisPool(poolConfig, "redis-18200.c85.us-east-1-2.ec2.redns.redis-cloud.com",
                18200, 3000, "default", "FYqYIMmLxrSNe1RXPGwXO3WtafuyCiJr");
    }

    public static Jedis getConnection() {
        return pool.getResource();
    }

}
