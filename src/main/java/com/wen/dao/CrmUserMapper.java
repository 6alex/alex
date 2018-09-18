package com.wen.dao;

import com.wen.po.CrmUser;

public interface CrmUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CrmUser record);

    int insertSelective(CrmUser record);

    CrmUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CrmUser record);

    int updateByPrimaryKey(CrmUser record);
}