import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Practice 4: number of transactions per commodity.
 *
 * Input: the UN commodity-trade CSV, ';'-separated, with the columns
 *   0 country_or_area; 1 year; 2 comm_code; 3 commodity; 4 flow; 5 trade_usd; 6 weight_kg;
 *   7 quantity_name; 8 quantity; 9 category.
 */
public class TransactionsPerCommodity {

    public static class TradeMapper extends Mapper<Object, Text, Text, IntWritable> {

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] cols = value.toString().split(";");
            context.write(new Text(cols[3]), new IntWritable(1));
        }
    }

    public static class TradeReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int total = 0;
            for (IntWritable v : values) { total += v.get(); }
            context.write(key, new IntWritable(total));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        String inputPath = "/home/dados/operacoes_comerciais/base.csv";
        String outputPath = System.getProperty("user.home") + "/Desktop/" + TransactionsPerCommodity.class.getSimpleName();
        if (args.length == 2) {
            inputPath = args[0];
            outputPath = args[1];
        }

        Job job = Job.getInstance(new Configuration(), TransactionsPerCommodity.class.getSimpleName());
        job.setJarByClass(TransactionsPerCommodity.class);
        job.setMapperClass(TradeMapper.class);
        job.setReducerClass(TradeReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));
        job.waitForCompletion(true);
    }
}
