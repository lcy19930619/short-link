package net.jlxxw.link.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author chunyang.leng
 * @date 2022-12-21 10:26 AM
 */
@Configuration
@ConfigurationProperties("link")
public class LinkProperties {

    /**
     * 雪花id 数据中心id
     */
    private int snowflakeDataCenterId = 0;

    /**
     * 雪花id 工作中心id
     */
    private int snowflakeWorkId = 0;

    /**
     * 链接失效时的默认跳转地址
     */
    private String defaultLink;

    /**
     * 短链接域名
     */
    private String shortLinkDomain;

    /**
     * 保存点击记录
     */
    private boolean saveClickRecord = true;

    public int getSnowflakeDataCenterId() {
        return snowflakeDataCenterId;
    }

    public void setSnowflakeDataCenterId(int snowflakeDataCenterId) {
        this.snowflakeDataCenterId = snowflakeDataCenterId;
    }

    public int getSnowflakeWorkId() {
        return snowflakeWorkId;
    }

    public void setSnowflakeWorkId(int snowflakeWorkId) {
        this.snowflakeWorkId = snowflakeWorkId;
    }

    public String getDefaultLink() {
        return defaultLink;
    }

    public void setDefaultLink(String defaultLink) {
        this.defaultLink = defaultLink;
    }

    public String getShortLinkDomain() {
        return shortLinkDomain;
    }

    public void setShortLinkDomain(String shortLinkDomain) {
        this.shortLinkDomain = shortLinkDomain;
    }

    public boolean isSaveClickRecord() {
        return saveClickRecord;
    }

    public void setSaveClickRecord(boolean saveClickRecord) {
        this.saveClickRecord = saveClickRecord;
    }
}
