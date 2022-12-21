package net.jlxxw.link.mapper;

import org.apache.ibatis.annotations.Mapper;
import net.jlxxw.link.domain.ShortLinkRecordDO;

@Mapper
public interface ShortLinkRecordMapper {

    int insertSelective(ShortLinkRecordDO record);

    ShortLinkRecordDO selectByKey(String key);
}