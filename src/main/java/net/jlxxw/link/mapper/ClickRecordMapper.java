package net.jlxxw.link.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ClickRecordMapper {

    @Insert("insert into click_record (snowflake_id) values #{snowflakeId}")
    int insert(@Param("snowflakeId") Long snowflakeId);
}