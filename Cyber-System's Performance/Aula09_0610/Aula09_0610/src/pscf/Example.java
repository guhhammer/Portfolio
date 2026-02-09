package pscf;

public class Example {

    public static void main(String[] args) throws InterruptedException {

        if(args.length < 2){
            System.err.println("USO: Contador <N> <T>");
            System.exit(1);
        }

        long meu_pid = ProcessHandle.current().pid();

        int N = Integer.parseInt(args[0]); // java Contador.Main 10
        float T = Float.parseFloat(args[1]); // alterar Program arguments: 10 0.5
        for(int i = 1; i <=N; ++i){
            System.out.println(meu_pid+"> "+i);
            Thread.sleep((long)(T*1000));
        }

    }

}
