package net.jlxxw.link.config;

import net.jlxxw.link.properties.LinkProperties;
import net.jlxxw.link.util.SnowflakeIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chunyang.leng
 * @date 2022-12-21 10:30 AM
 */
@Configuration
public class SnowflakeIdConfig {

    @Bean
    public SnowflakeIdWorker snowflakeIdWorker(LinkProperties linkProperties){
        int id = linkProperties.getSnowflakeWorkId();
        int dataCenterId = linkProperties.getSnowflakeDataCenterId();
        return new SnowflakeIdWorker(id,dataCenterId);
    }
}
