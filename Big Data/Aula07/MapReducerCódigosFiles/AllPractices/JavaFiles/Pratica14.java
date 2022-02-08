import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/*
    Passos para implementação
        - Defina qual chave, valor voce irá gerar no Map e Reduce
        - Defina os tipos no mapper (Text, IntWritable, DoubleWritable, FloatWritable)
            - Formato do extends <Object, Text, TIPO_CHAVE, TIPO_VALOR>
        - Defina os tipos no reducer (Text, IntWritable, DoubleWritable, FloatWritable)
            - Formato do extends <TIPO_CHAVE, TIPO_VALOR, TIPO_CHAVE_SAIDA, TIPO_VALOR_SAIDA>
        - Defina os tipos no Main
        - Implemente as funções =)
 */

public class Pratica14 {

    public static class MapperImplementacao14 extends Mapper<Object, Text, Text, DoubleWritable>{

        public void map(Object chave, Text valor, Context context) throws IOException, InterruptedException {

            String[] b = valor.toString().split(";");
            try{

                double price = Double.valueOf(b[5])/Double.valueOf(b[6]);
                context.write(new Text(b[3]), new DoubleWritable(price));

            }catch (Exception e){ e.getSuppressed(); }

        }

    }

    public static class ReducerImplementacao14 extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
        public void reduce(Text chave, Iterable<DoubleWritable> valores, Context context) throws IOException, InterruptedException {

            double dd = 0.0;
            for(DoubleWritable v: valores){
                double m = Math.max(Float.parseFloat(v.toString()), dd);
                dd = (m == Double.POSITIVE_INFINITY) ? dd : m;
            }
            context.write(chave, new DoubleWritable(dd));

        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        String arquivoEntrada = "/home/dados/operacoes_comerciais/base.csv";
        String arquivoSaida = System.getProperty("user.home") + "/Desktop/" + Pratica14.class.getSimpleName();
        if(args.length == 2){
            arquivoEntrada = args[0];
            arquivoSaida = args[1];
        }
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, String.valueOf(Pratica14.class.getSimpleName()));

        job.setJarByClass(Pratica14.class);
        job.setMapperClass(MapperImplementacao14.class);
        job.setReducerClass(ReducerImplementacao14.class);
        //Defina a classe da chave (Text, IntWritable, DoubleWritable, FloatWritable)
        job.setOutputKeyClass(Text.class);
        //Defina a classe do valor (Text, IntWritable, DoubleWritable, FloatWritable)
        job.setOutputValueClass(DoubleWritable.class);

        FileInputFormat.addInputPath(job, new Path(arquivoEntrada));
        FileOutputFormat.setOutputPath(job, new Path(arquivoSaida));

        job.waitForCompletion(true);
    }

}
