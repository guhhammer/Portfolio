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


// País com a maior quantidade de transações comerciais efetuadas.
public class Pratica1 {

    public static class MapperImplementacao1
            extends Mapper<Object, Text, Text, IntWritable>{

        public void map(Object chave, Text valor, Context context)
                throws IOException, InterruptedException {

            context.write(new Text(valor.toString().split(";")[0]), new IntWritable(1));

        }

    }

    public static class ReducerImplementacao1
            extends Reducer<Text, IntWritable, Text, IntWritable> {

        public void reduce(Text chave, Iterable<IntWritable> valores, Context context)
                throws IOException, InterruptedException {

            int qt =0;
            for(IntWritable valor: valores){ qt = qt + valor.get(); }
            context.write(chave, new IntWritable(qt));

        }

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        String arquivoEntrada = "/home/dados/operacoes_comerciais/base.csv";
        String arquivoSaida = System.getProperty("user.home") + "/Desktop/" + Pratica1.class.getSimpleName();
        if(args.length == 2){
            arquivoEntrada = args[0];
            arquivoSaida = args[1];
        }
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, String.valueOf(Pratica1.class.getSimpleName()));

        job.setJarByClass(Pratica1.class);
        job.setMapperClass(Pratica1.MapperImplementacao1.class);
        job.setReducerClass(Pratica1.ReducerImplementacao1.class);

        //Defina a classe da chave (Text, IntWritable, DoubleWritable, FloatWritable)
        job.setOutputKeyClass(Text.class);
        //Defina a classe do valor (Text, IntWritable, DoubleWritable, FloatWritable)
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(arquivoEntrada));
        FileOutputFormat.setOutputPath(job, new Path(arquivoSaida));

        job.waitForCompletion(true);
    }

}
