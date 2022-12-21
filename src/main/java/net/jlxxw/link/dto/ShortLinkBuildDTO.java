package net.jlxxw.link.dto;

/**
 * @author chunyang.leng
 * @date 2022-12-21 10:58 AM
 */
public class ShortLinkBuildDTO {

    /**
     * 所属用户id
     */
    private Long uid;

    /**
     * 原始链接，非空
     */
    private String originalUrl;

    /**
     * 是否有效，防止链接泄露导致出现问题
     * true有效，false失效
     */
    private Boolean effective;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 雪花id
     */
    private Long snowflakeId;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Boolean getEffective() {
        return effective;
    }

    public void setEffective(Boolean effective) {
        this.effective = effective;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getSnowflakeId() {
        return snowflakeId;
    }

    public void setSnowflakeId(Long snowflakeId) {
        this.snowflakeId = snowflakeId;
    }
}
