package net.jlxxw.link.controller;

import java.util.Objects;
import net.jlxxw.link.domain.ShortLinkRecordDO;
import net.jlxxw.link.dto.ShortLinkBuildDTO;
import net.jlxxw.link.mapper.ShortLinkRecordMapper;
import net.jlxxw.link.properties.LinkProperties;
import net.jlxxw.link.util.ShortLinkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chunyang.leng
 * @date 2022-12-21 10:53 AM
 */
@RestController
@RequestMapping("link")
public class LinkController {
    @Autowired
    private ShortLinkRecordMapper shortLinkRecordMapper;
    @Autowired
    private LinkProperties linkProperties;

    /**
     * 生成一个短链接
     * @param dto 基本参数
     * @return 生成好的短链接
     */
    @PostMapping("build")
    public String buildShortLink(@RequestBody ShortLinkBuildDTO dto){

        Long snowflakeId = dto.getSnowflakeId();
        String shortLinkKey = ShortLinkUtils.encode(snowflakeId);
        Boolean effective = dto.getEffective();
        String remark = dto.getRemark();
        Long uid = dto.getUid();
        String originalUrl = dto.getOriginalUrl();

        // 参数检查
        Objects.requireNonNull(snowflakeId);
        Objects.requireNonNull(effective);
        Objects.requireNonNull(uid);
        Objects.requireNonNull(originalUrl);


        ShortLinkRecordDO recordDO = new ShortLinkRecordDO();
        recordDO.setEffective(effective);
        recordDO.setShortLinkKey(shortLinkKey);
        recordDO.setSnowflakeId(snowflakeId);
        recordDO.setUid(uid);
        recordDO.setRemark(remark);
        recordDO.setOriginalUrl(originalUrl);
        shortLinkRecordMapper.insertSelective(recordDO);

        return linkProperties.getShortLinkDomain() + "/" + shortLinkKey;

    }
}
