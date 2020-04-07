package com.wondertek.mr;

import com.wondertek.demension.NewUsersKpi;
import com.wondertek.util.DateUtil;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class NewUsersReducer extends Reducer<NewUsersKpi,NullWritable,NewUsersKpi,NullWritable> {
    @Override
    protected void reduce(NewUsersKpi key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        int sum=0;

        for (NullWritable value : values) {
            sum++;
        }


        key.setKey_date(DateUtil.getYesterday());
        key.setNew_user_num(sum);


        System.out.println(key);
        context.write(key,null);
    }


}
