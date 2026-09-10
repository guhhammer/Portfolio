import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Practice 12: average price per kg (trade_usd / weight_kg) of Brazilian transactions per commodity and year.
 *
 * Input: the UN commodity-trade CSV, ';'-separated, with the columns
 *   0 country_or_area; 1 year; 2 comm_code; 3 commodity; 4 flow; 5 trade_usd; 6 weight_kg;
 *   7 quantity_name; 8 quantity; 9 category.
 */
public class BrazilAveragePricePerKgByCommodityAndYear {

    public static class TradeMapper extends Mapper<Object, Text, Text, DoubleWritable> {

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] cols = value.toString().split(";");
            if (cols[0].trim().contains("Brazil")) {
                try {
                    double pricePerKg = Double.parseDouble(cols[5]) / Double.parseDouble(cols[6]);
                    context.write(new Text("<" + cols[3] + ", " + cols[1] + ">"), new DoubleWritable(pricePerKg));
                } catch (Exception e) {
                    // malformed row (header or non-numeric field): skip it
                }
            }
        }
    }

    public static class TradeReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

        public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
            double sum = 0.0, count = 0.0;
            for (DoubleWritable v : values) {
                if (v.get() == Double.POSITIVE_INFINITY) { continue; }   // weight 0 -> skip
                sum += v.get();
                count++;
            }
            context.write(key, new DoubleWritable(sum / count));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        String inputPath = "/home/dados/operacoes_comerciais/base.csv";
        String outputPath = System.getProperty("user.home") + "/Desktop/" + BrazilAveragePricePerKgByCommodityAndYear.class.getSimpleName();
        if (args.length == 2) {
            inputPath = args[0];
            outputPath = args[1];
        }

        Job job = Job.getInstance(new Configuration(), BrazilAveragePricePerKgByCommodityAndYear.class.getSimpleName());
        job.setJarByClass(BrazilAveragePricePerKgByCommodityAndYear.class);
        job.setMapperClass(TradeMapper.class);
        job.setReducerClass(TradeReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));
        job.waitForCompletion(true);
    }
}
