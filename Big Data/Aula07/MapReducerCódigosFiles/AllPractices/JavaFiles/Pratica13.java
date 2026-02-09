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

public class Pratica13 {

    public static class MapperImplementacao13 extends Mapper<Object, Text, Text, FloatWritable>{

        public void map(Object chave, Text valor, Context context) throws IOException, InterruptedException {

            String[] b = valor.toString().split(";");
            try{
                context.write(new Text(b[2]), new FloatWritable(Float.parseFloat(b[5])));

            }catch(Exception e){ e.getSuppressed(); }

        }

    }

    public static class ReducerImplementacao13 extends Reducer<Text, FloatWritable, Text, FloatWritable> {
        public void reduce(Text chave, Iterable<FloatWritable> valores, Context context) throws IOException, InterruptedException {

            float ff = 0.0f;
            for(FloatWritable v : valores){ ff = Math.max(Float.parseFloat(v.toString()), ff); }
            context.write(chave, new FloatWritable(ff));

        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        String arquivoEntrada = "/home/dados/operacoes_comerciais/base.csv";
        String arquivoSaida = System.getProperty("user.home") + "/Desktop/" + Pratica13.class.getSimpleName();
        if(args.length == 2){
            arquivoEntrada = args[0];
            arquivoSaida = args[1];
        }
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, String.valueOf(Pratica13.class.getSimpleName()));

        job.setJarByClass(Pratica13.class);
        job.setMapperClass(MapperImplementacao13.class);
        job.setReducerClass(ReducerImplementacao13.class);
        //Defina a classe da chave (Text, IntWritable, DoubleWritable, FloatWritable)
        job.setOutputKeyClass(Text.class);
        //Defina a classe do valor (Text, IntWritable, DoubleWritable, FloatWritable)
        job.setOutputValueClass(FloatWritable.class);

        FileInputFormat.addInputPath(job, new Path(arquivoEntrada));
        FileOutputFormat.setOutputPath(job, new Path(arquivoSaida));

        job.waitForCompletion(true);
    }

}
