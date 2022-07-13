package com.task.inventory_api.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Bean
    public RedissonClient createRedisClient() {
        return buildRedissionClient();
    }

    private RedissonClient buildRedissionClient() {
        Config config = new Config().setCodec(StringCodec.INSTANCE);
        SingleServerConfig singleServerConfig = config.useSingleServer();
        config.useSingleServer().setAddress("127.0.0.1:6379");
        return Redisson.create(config);
    }
}
