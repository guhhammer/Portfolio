import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Practice 13: highest transaction value (USD) per commodity code.
 *
 * Input: the UN commodity-trade CSV, ';'-separated, with the columns
 *   0 country_or_area; 1 year; 2 comm_code; 3 commodity; 4 flow; 5 trade_usd; 6 weight_kg;
 *   7 quantity_name; 8 quantity; 9 category.
 */
public class MaxTradeValuePerCommodityCode {

    public static class TradeMapper extends Mapper<Object, Text, Text, FloatWritable> {

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] cols = value.toString().split(";");
            try {
                float tradeUsd = Float.parseFloat(cols[5]);
                context.write(new Text(cols[2]), new FloatWritable(tradeUsd));
            } catch (Exception e) {
                // malformed row (header or non-numeric field): skip it
            }
        }
    }

    public static class TradeReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {

        public void reduce(Text key, Iterable<FloatWritable> values, Context context) throws IOException, InterruptedException {
            float max = 0.0f;
            for (FloatWritable v : values) { max = Math.max(v.get(), max); }
            context.write(key, new FloatWritable(max));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        String inputPath = "/home/dados/operacoes_comerciais/base.csv";
        String outputPath = System.getProperty("user.home") + "/Desktop/" + MaxTradeValuePerCommodityCode.class.getSimpleName();
        if (args.length == 2) {
            inputPath = args[0];
            outputPath = args[1];
        }

        Job job = Job.getInstance(new Configuration(), MaxTradeValuePerCommodityCode.class.getSimpleName());
        job.setJarByClass(MaxTradeValuePerCommodityCode.class);
        job.setMapperClass(TradeMapper.class);
        job.setReducerClass(TradeReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FloatWritable.class);

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));
        job.waitForCompletion(true);
    }
}
