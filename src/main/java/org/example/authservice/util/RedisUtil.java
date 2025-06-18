package org.example.authservice.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authservice.config.AppConfig;
import org.example.authservice.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
@NoArgsConstructor
@Slf4j
public class RedisUtil {
    @Autowired
    private AppConfig appConfig;

    private JedisPool jedisPool;
    private String redisHost;
    private int redisPort;
    private String redisAuth;
    private long expirationTime;
    private long RefreshtokenTime;

    @PostConstruct
    private void init() {
        try {
            this.redisHost = appConfig.getRedisHost();
            this.redisPort = appConfig.getRedisPort();
            this.redisAuth = appConfig.getRedisAuth();
            this.expirationTime = appConfig.getExpirationTime();
            this.RefreshtokenTime = Long.parseLong(appConfig.getExpirationRefreshToken());

            // Initialize JedisPool
            this.jedisPool = createJedisPool();
            log.info("Redis connection initialized successfully");
        } catch (Exception e) {
            log.error("Failed to initialize Redis connection: {}", e.getMessage());
            throw new BadRequestException("Failed to initialize Redis connection");
        }
    }

    private JedisPool createJedisPool() {
        try {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(50);
            poolConfig.setMaxIdle(20);
            poolConfig.setMinIdle(5);
            poolConfig.setTestOnBorrow(true);
            poolConfig.setTestOnReturn(true);
            poolConfig.setTestWhileIdle(true);
            poolConfig.setBlockWhenExhausted(true);
            poolConfig.setMaxWaitMillis(3000);

            return new JedisPool(poolConfig, redisHost, redisPort, 5000, redisAuth);
        } catch (Exception e) {
            log.error("Failed to create Jedis pool: {}", e.getMessage());
            throw new BadRequestException("Failed to create Redis connection pool");
        }
    }

    @PreDestroy
    public void cleanup() {
        try {
            if (jedisPool != null && !jedisPool.isClosed()) {
                jedisPool.close();
                log.info("Redis connection pool closed successfully");
            }
        } catch (Exception e) {
            log.error("Error during Redis cleanup: {}", e.getMessage());
        }
    }

    public boolean saveToRedis(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            // Save with expiration time
            jedis.setex(key, (int)expirationTime, value);
            return true;
        } catch (Exception e) {
            log.error("Error saving data to Redis with key {}: {}", key, e.getMessage());
            throw new BadRequestException("There was an error while saving data to redis");
        }
    }

    public boolean saveRefreshtoken(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(key, (int)RefreshtokenTime, value);
            return true;
        }
        catch (Exception e) {
            log.error("Error saving refresh token to Redis with key {}: {}", key, e.getMessage());
            throw new BadRequestException("There was an error while saving refresh token to redis");
        }
    }

    public String getFromRedis(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        } catch (Exception e) {
            log.error("Error getting data from Redis with key {}: {}", key, e.getMessage());
            throw new BadRequestException("There was an error while getting data from redis");
        }
    }

    public void deleteFromRedis(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
            log.debug("Successfully deleted key {} from Redis", key);
        } catch (Exception e) {
            log.error("Error deleting from Redis with key {}: {}", key, e.getMessage());
            throw new BadRequestException("There was an error while deleting data from redis");
        }
    }
}