package com.wondertek.bigdata.offline.service.converter;

import com.wondertek.bigdata.offline.dimension.key.KeyBaseDimension;
import org.apache.hadoop.ipc.VersionedProtocol;

import java.io.IOException;

/**
 * 提供专门操作dimension表的接口
 * 或者是创建一个Operator接口
 * 必须继承自VersionedProtocol
 */
public interface IDimensionConverter extends VersionedProtocol {
    // 版本id
    long versionID = 1;
    // IP地址和端口号，保存在hdfs上
    String CONFIG_SAVE_PATH = "/liu/transformer/rpc/config";

    /**
     * 根据dimension的value值获取id<br/>
     * 如果数据库中有，那么直接返回。如果没有，那么进行插入后返回新的id值
     *
     * @param dimension
     * @return
     * @throws IOException
     */
    int getDimensionIdByValue(KeyBaseDimension dimension) throws IOException;
}
