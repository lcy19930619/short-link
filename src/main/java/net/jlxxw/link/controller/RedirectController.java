package net.jlxxw.link.controller;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import net.jlxxw.link.domain.ShortLinkRecordDO;
import net.jlxxw.link.mapper.ClickRecordMapper;
import net.jlxxw.link.mapper.ShortLinkRecordMapper;
import net.jlxxw.link.properties.LinkProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chunyang.leng
 * @date 2022-09-15 9:38 AM
 */
@RestController
public class RedirectController {
    private static final Logger logger = LoggerFactory.getLogger(RedirectController.class);

    @Autowired
    private LinkProperties linkProperties;
    @Autowired
    private ThreadPoolTaskExecutor logThreadPool;
    @Autowired
    private ShortLinkRecordMapper shortLinkRecordMapper;
    @Autowired
    private ClickRecordMapper clickRecordMapper;
    /**
     * 重定向控制器
     *
     * @param code 短链码
     * @return 重定向地址
     */
    @GetMapping("/{code}")
    public void redirect(@PathVariable("code") String code, HttpServletResponse response) throws IOException {
        String defaultLink = linkProperties.getDefaultLink();
        if (StringUtils.isBlank(code)) {
            logger.info("短链接code为空,重定向至默认链接:{}", defaultLink);
            response.sendRedirect(defaultLink);
            return;
        }
        ShortLinkRecordDO shortLinkRecordDO = shortLinkRecordMapper.selectByKey(code);
        if (Objects.isNull(shortLinkRecordDO)) {
            logger.info("短链接code:{} 不存在,重定向:{}", code, defaultLink);
            response.sendRedirect(defaultLink);
            return;
        }

        if (!shortLinkRecordDO.getEffective()) {
            // 链接失效
            logger.info("短链接code:{} 失效, 重定向:{}", code, defaultLink);
            response.sendRedirect(defaultLink);
            return;
        }

        String originalUrl = shortLinkRecordDO.getOriginalUrl();

        if (originalUrl.contains("?")) {
            originalUrl = originalUrl.replace("?", "?shortLinkCode=" + code + "&");
        } else {
            originalUrl = originalUrl + "?shortLinkCode=" + code + "&";
        }
        boolean save = linkProperties.isSaveClickRecord();
        if (save){
            logThreadPool.execute(()->{
                try {
                    Long snowflakeId = shortLinkRecordDO.getSnowflakeId();
                    clickRecordMapper.insert(snowflakeId);
                }catch (Exception e){
                    logger.error("记录点击事件出现未知异常,key:" + code,e);
                }
            });
        }

        logger.info("短链接code:{} 正常解析, 重定向:{}", code, originalUrl);
        response.sendRedirect(originalUrl);
    }
}
