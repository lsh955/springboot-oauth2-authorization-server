package com.oauth.mapper;

import com.oauth.dto.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author 이승환
 * @since 2020-04-20
 */
@Mapper
@Component
public interface MemberMapper {
    
    Member findAll(@Param("findAll") String findAll);
    
}
