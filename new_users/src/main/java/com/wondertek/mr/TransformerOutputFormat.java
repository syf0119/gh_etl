package com.wondertek.mr;

import com.wondertek.demension.NewUsersKpi;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.*;

import java.io.IOException;

public class TransformerOutputFormat extends OutputFormat<NewUsersKpi,NullWritable> {
    @Override
    public RecordWriter<NewUsersKpi, NullWritable> getRecordWriter(TaskAttemptContext context) throws IOException, InterruptedException {
        Configuration configuration = context.getConfiguration();
        return null;
    }

    @Override
    public void checkOutputSpecs(JobContext jobContext) throws IOException, InterruptedException {

    }

    @Override
    public OutputCommitter getOutputCommitter(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        return null;
    }
    class TransformRecord extends RecordWriter<NewUsersKpi,NullWritable>{

        @Override
        public void write(NewUsersKpi newUsersKpi, NullWritable nullWritable) throws IOException, InterruptedException {

        }

        @Override
        public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {

        }
    }
}
