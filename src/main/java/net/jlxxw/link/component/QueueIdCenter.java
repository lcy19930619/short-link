package net.jlxxw.link.component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.annotation.PostConstruct;
import net.jlxxw.link.util.SnowflakeIdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 队列id 生成器，解决雪花id存在的时钟回拨问题
 * @author chunyang.leng
 * @date 2022-11-28 1:12 PM
 */
@Primary
@Component
public class QueueIdCenter implements IdCenter {
    private static final Logger logger = LoggerFactory.getLogger(QueueIdCenter.class);
    /**
     * 长度 20w 的 雪花id
     */
    private static final BlockingQueue<Long> QUEUE = new LinkedBlockingQueue<>(200000);

    @Autowired
    private  SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @PostConstruct
    private void init(){
        threadPoolTaskExecutor.execute(()->{
            while (true) {
                try {
                    initQueue();
                }catch ( Exception e ){
                    logger.error("队列id生成器出现未知异常",e);
                }finally {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {

                    }
                }
            }
        });

    }



    /**
     * 获取id
     *
     * @return
     */
    @Override
    public Long getId() {
        try {
            return QUEUE.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private void initQueue(){
        int size = 100000;
        if (QUEUE.size() < size){
            logger.info("初始化队列开始");
            Set<Long> set = new HashSet<>(size);
            while (set.size() < size){
                try {
                    long id = snowflakeIdWorker.nextId();
                    set.add(id);
                }catch (Exception ignored) {

                }
            }
            QUEUE.addAll(set);
            logger.info("初始化队列结束");
        }
    }
}
