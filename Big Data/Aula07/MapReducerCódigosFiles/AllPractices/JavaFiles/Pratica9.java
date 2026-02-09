import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
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

public class Pratica9 {

    public static class MapperImplementacao9 extends Mapper<Object, Text, Text, LongWritable>{

        public void map(Object chave, Text valor, Context context) throws IOException, InterruptedException {
            String[] b = valor.toString().split(";");
            long p = 0;
            try {
                p = Long.parseLong(b[6]);
                context.write(new Text("<"+b[3]+", "+b[1]+">"), new LongWritable(p));
            }catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }

    public static class ReducerImplementacao9 extends Reducer<Text, LongWritable, Text, LongWritable> {
        public void reduce(Text chave, Iterable<LongWritable> valores, Context context) throws IOException, InterruptedException {

            long avg = 0; int qntd = 0;
            for(LongWritable v: valores){ avg = avg+v.get(); qntd++; }
            context.write(chave, new LongWritable(avg/qntd));

        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        String arquivoEntrada = "/home/dados/operacoes_comerciais/base.csv";
        String arquivoSaida = System.getProperty("user.home") + "/Desktop/" + Pratica9.class.getSimpleName();
        if(args.length == 2){
            arquivoEntrada = args[0];
            arquivoSaida = args[1];
        }
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, String.valueOf(Pratica9.class.getSimpleName()));

        job.setJarByClass(Pratica9.class);
        job.setMapperClass(MapperImplementacao9.class);
        job.setReducerClass(ReducerImplementacao9.class);
        //Defina a classe da chave (Text, IntWritable, DoubleWritable, FloatWritable)
        job.setOutputKeyClass(Text.class);
        //Defina a classe do valor (Text, IntWritable, DoubleWritable, FloatWritable)
        job.setOutputValueClass(LongWritable.class);

        FileInputFormat.addInputPath(job, new Path(arquivoEntrada));
        FileOutputFormat.setOutputPath(job, new Path(arquivoSaida));

        job.waitForCompletion(true);
    }

}
