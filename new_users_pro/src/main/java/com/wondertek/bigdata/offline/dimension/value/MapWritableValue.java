package com.wondertek.bigdata.offline.dimension.value;

import com.wondertek.bigdata.offline.common.KpiEnum;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.WritableUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 填充Map对象的输出对象
 */
public class MapWritableValue extends BaseStatsValueWritable {
    /**
     * Map Writable对象
     */
    private MapWritable value = new MapWritable();
    /**
     * kpi 类型
     */
    private KpiEnum kpi;

    /**
     * 无参默认构造方法，必须给定
     */
    public MapWritableValue() {
        super();
    }

    /**
     * 给定全部参数的构造方法
     *
     * @param value
     * @param kpi
     */
    public MapWritableValue(MapWritable value, KpiEnum kpi) {
        super();
        this.value = value;
        this.kpi = kpi;
    }

    public MapWritable getValue() {
        return value;
    }

    public void setValue(MapWritable value) {
        this.value = value;
    }

    public void setKpi(KpiEnum kpi) {
        this.kpi = kpi;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        this.value.write(out);
        // 枚举类型序列化
        WritableUtils.writeEnum(out, this.kpi);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.value.readFields(in);
        // 枚举类型反序列化
        this.kpi = WritableUtils.readEnum(in, KpiEnum.class);
    }

    @Override
    public KpiEnum getKpi() {
        return this.kpi;
    }

}
