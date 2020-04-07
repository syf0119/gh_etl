package com.wondertek.bigdata.offline.mr.stats.newusers;

import com.wondertek.bigdata.offline.common.KpiEnum;
import com.wondertek.bigdata.offline.dimension.key.stats.StatsInstallUser;
import com.wondertek.bigdata.offline.dimension.value.MapWritableValue;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class StatsInstallUserReducer extends Reducer<StatsInstallUser,Text,StatsInstallUser,MapWritableValue>{
    private Set<String> set=new HashSet<>();

    @Override
    protected void reduce(StatsInstallUser key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text value : values) {
            set.add(value.toString());
        }
        MapWritable mapWritable = new MapWritable();

        mapWritable.put(new IntWritable(-1),new IntWritable(set.size()));

        set.clear();

        MapWritableValue mapWritableValue = new MapWritableValue();
        mapWritableValue.setKpi(KpiEnum.valueOfName(key.getKpiDimensionKey().getKpiName()));
        mapWritableValue.setValue(mapWritable);



            }
}
