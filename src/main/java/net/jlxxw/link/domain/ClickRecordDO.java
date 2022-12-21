package net.jlxxw.link.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 点击记录实体类
 * click_record
 * @author 
 */
public class ClickRecordDO implements Serializable {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 雪花id，唯一数据主键，用于join查询
     */
    private Long snowflakeId;


    /**
     * 数据创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSnowflakeId() {
        return snowflakeId;
    }

    public void setSnowflakeId(Long snowflakeId) {
        this.snowflakeId = snowflakeId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}