package com.se.dal;

import com.se.entity.UCTF;
import com.se.entity.UCase;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;


import java.util.List;

@Mapper
public interface UCRepository {
    boolean insertUC(@Param("pid")long pid, @Param("content")String content, @Param("ucName")String name);

    UCase findUCByPidAndUCName(@Param("pid")long pid,@Param("ucName")String ucName);

    boolean insertUCTF(@Param("ucId")long ucId,@Param("corpusIndex")int corpusIndex,@Param("tf")int tf);

    List<UCTF> findTFByUCID(@Param("ucId")long ucId);

    int countUCByPid(@Param("pid")long pid);

    public List<UCTF> findUCTFBYPidAndUcname(@Param("pid")long pid, @Param("ucname")String ucname);
}
