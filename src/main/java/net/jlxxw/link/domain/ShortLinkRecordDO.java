package net.jlxxw.link.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 短链接记录实体数据
 * short_link_record
 * @author 
 */
public class ShortLinkRecordDO implements Serializable {
    /**
     * 数据id
     */
    private Long id;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 短链接key，长度15，非空，唯一
     */
    private String shortLinkKey;


    /**
     * 雪花id，唯一数据主键，用于join查询
     */
    private Long snowflakeId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getShortLinkKey() {
        return shortLinkKey;
    }

    public void setShortLinkKey(String shortLinkKey) {
        this.shortLinkKey = shortLinkKey;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getSnowflakeId() {
        return snowflakeId;
    }

    public void setSnowflakeId(Long snowflakeId) {
        this.snowflakeId = snowflakeId;
    }
}