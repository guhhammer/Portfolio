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
 * Practice 11: average weight (kg) of Brazilian transactions per flow (import/export) and year.
 *
 * Input: the UN commodity-trade CSV, ';'-separated, with the columns
 *   0 country_or_area; 1 year; 2 comm_code; 3 commodity; 4 flow; 5 trade_usd; 6 weight_kg;
 *   7 quantity_name; 8 quantity; 9 category.
 */
public class BrazilAverageWeightPerFlowAndYear {

    public static class TradeMapper extends Mapper<Object, Text, Text, FloatWritable> {

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] cols = value.toString().split(";");
            if (cols[0].trim().contains("Brazil")) {
                try {
                    float weight = Float.parseFloat(cols[6]);
                    context.write(new Text("<" + cols[0] + ", " + cols[4] + ", " + cols[1] + ">"), new FloatWritable(weight));
                } catch (Exception e) {
                    // malformed row (header or non-numeric field): skip it
                }
            }
        }
    }

    public static class TradeReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {

        public void reduce(Text key, Iterable<FloatWritable> values, Context context) throws IOException, InterruptedException {
            float sum = 0.0f;
            int count = 0;
            for (FloatWritable v : values) { sum += v.get(); count++; }
            context.write(key, new FloatWritable(sum / count));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        String inputPath = "/home/dados/operacoes_comerciais/base.csv";
        String outputPath = System.getProperty("user.home") + "/Desktop/" + BrazilAverageWeightPerFlowAndYear.class.getSimpleName();
        if (args.length == 2) {
            inputPath = args[0];
            outputPath = args[1];
        }

        Job job = Job.getInstance(new Configuration(), BrazilAverageWeightPerFlowAndYear.class.getSimpleName());
        job.setJarByClass(BrazilAverageWeightPerFlowAndYear.class);
        job.setMapperClass(TradeMapper.class);
        job.setReducerClass(TradeReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FloatWritable.class);

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));
        job.waitForCompletion(true);
    }
}
