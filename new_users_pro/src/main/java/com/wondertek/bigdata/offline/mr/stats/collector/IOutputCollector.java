package com.wondertek.bigdata.offline.mr.stats.collector;

import com.wondertek.bigdata.offline.dimension.key.KeyBaseDimension;
import com.wondertek.bigdata.offline.dimension.value.BaseStatsValueWritable;
import com.wondertek.bigdata.offline.service.converter.IDimensionConverter;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;
import java.sql.PreparedStatement;

/**
 * 定义具体mapreduce对于的输出操作代码
 * 
 */
public interface IOutputCollector {

    /**
     * 定义具体执行sql数据插入的方法
     * 
     * @param conf
     * @param key
     * @param value
     * @param pstm
     * @param converter
     * @throws IOException
     */
    public void collect(Configuration conf, KeyBaseDimension key, BaseStatsValueWritable value,
                        PreparedStatement pstm, IDimensionConverter converter) throws IOException;
}
