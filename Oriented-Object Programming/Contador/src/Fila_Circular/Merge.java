package Fila_Circular;

public class Merge {

    // Uso: Pega todos os elementos da fila.
    static FilaC enxuto(FilaC n) {

        int k = 0;
        FilaC vAux = new FilaC(n.gettamanho());
        while (n.primeiro + k <= n.getTamanho() - 1){
            vAux.inserir(n.dado[n.primeiro + k]); k++;
        }

        k = 0;
        while (k <= n.ultimo){ vAux.inserir(n.dado[k]); k++; }
        
        return vAux;
    }   // gemacht.

    //  Uso: Coloca em ordem crescente os elementos da fila.
    static FilaC ordem(FilaC n) {

        //Pode-se fazer tanto no main quanto aqui, o uso do enxuto().
        int tam = enxuto(n).getTamanho();
        int aux;

        // Ordena os elementos.
        for (int i = 0; i < tam; i++) {
            for (int j = i + 1; j < tam; j++) {
                if (n.dado[i] > n.dado[j]) {
                    aux = n.dado[i];
                    n.dado[i] = n.dado[j];
                    n.dado[j] = aux;
                }
            }
        }
        return n;
    }   // gemacht.

    //  Uso:  Concatena em ordem crescente duas filas.
    public static FilaC merge(FilaC A, FilaC B) {

        FilaC C = new FilaC(A.getTamanho() + B.getTamanho());

        while (!A.vazia() && !B.vazia()) {
            if (A.primeiro() < B.primeiro()) {
                C.inserir(A.primeiro()); A.remover();
            } 
            else{ C.inserir(B.primeiro()); B.remover();}
        }

        while (!A.vazia()) {C.inserir(A.primeiro()); A.remover(); }

        while (!B.vazia()) {C.inserir(B.primeiro()); B.remover(); }

        return C;
    }   // gemacht.

    //  Uso: informações sobre o algoritmo.
    public static void ajuda() {
        System.out.println(
                "\n"
                + "Begin:  Método ajuda ----------------------------------------------"
                + "---------------\n\n"
                + "\tNomes: Gustavo Hammerschmidt."
                + "\n\n"
                + "  Funções: \n\n"
                + "\t Classe FilaC:"
                + "\t\t vazia(), cheia(), inserir(), remover(), primeiro(),"
                + "\n\t\t\t\t\t ultimo(), getTamanho(), gettamanho()."
                + "\n\n\t Classe Merge:"
                + "\t\t enxuto(), ordem(), merge(), ajuda(), print_ordem(), "
                + "\n\t\t\t\t\t print_merge(), print_exemplo_de_fila()."
                + "\n\n\t Observação:"
                + "\n\t   -Método getTamanho() retorna "
                + "o tamanho Máximo da fila."
                + "\n\t   -Método gettamanho() retorna o tamanho da fila."
                + "\n\t   -Método print_exemplo_de_fila() mostra um exemplo de fila "
                + "com indicadores\n\t    dos ponteiros: primeiro e último."
                + "\n\t   -Método enxuto() pega apenas do primeiro ao ultimo, "
                + "\n\t    desconsiderando os outros espaços da fila."
                + "\n\t   -Método print_ordem() imprime a fila normal e"
                + "\n\t    depois sua versão ordenada."
                + "\n\t   -Método print_merge() imprime a fila "
                + "\n\t    resultante da operação merge."
                + "\n\n"
                + "End: Método ajuda ----------------------------------------------"
                + "-------------------\n\n");

    }   // gemacht.

    //  Uso: exemplo de uma Fila.
    public static void print_exemplo_de_fila() {

        FilaC A = new FilaC(3);
        System.out.println(" -exemplo_de_fila(): \n");
        A.inserir(1);
        System.out.println("Primeiro elemento: " + A.primeiro()
                + "\nÚltimo elemento: " + A.ultimo() + "\n");
        A.inserir(2);
        System.out.println("Primeiro elemento: " + A.primeiro()
                + "\nÚltimo elemento: " + A.ultimo() + "\n");
        A.inserir(3);
        System.out.println("Primeiro elemento: " + A.primeiro()
                + "\nÚltimo elemento: " + A.ultimo() + "\n");

        System.out.println("A.vazia(): " + A.vazia()
                + "\nA.cheia(): " + A.cheia());

        A.remover();
        System.out.println("\nPrimeiro elemento: " + A.primeiro()
                + "\nÚltimo elemento: " + A.ultimo() + "\n");
        A.remover();
        System.out.println("Primeiro elemento: " + A.primeiro()
                + "\nÚltimo elemento: " + A.ultimo() + "\n");
        A.remover();
        System.out.println("Primeiro elemento: " + A.primeiro()
                + "\nÚltimo elemento: " + A.ultimo() + "\n");

        System.out.println("A.vazia(): " + A.vazia()
                + "\nA.cheia(): " + A.cheia() + "\n\n");
    }   // gemacht.

    //  Uso: imprimir vetor ordenado.
    public static void print_ordem(FilaC n) {

        System.out.println(" -print_ordem():\n");
        System.out.print("A[" + (n.getTamanho()) + "] = { ");
        if (n.getTamanho() != 0) {
            for (int i = 0; i < n.getTamanho(); i++) {
                if (i == n.getTamanho() - 1) {
                    System.out.println(n.dado[i] + " }. ");
                } else {
                    System.out.print(n.dado[i] + ", ");
                }
            }
        } else {
            System.out.println(" }. ");
        }

        System.out.print("\nA[" + (n.getTamanho()) + "] (Fila Ordenada) = { ");
        if (n.getTamanho() != 0) {
            for (int i = 0; i < n.getTamanho(); i++) {
                if (i == n.getTamanho() - 1) {
                    System.out.println(ordem(n).dado[i] + " }. \n");
                } else {
                    System.out.print(ordem(n).dado[i] + ", ");
                }
            }
        } else {
            System.out.println(" }. \n");
        }
    }   // gemacht.

    //  Uso: imprimir vetor resultante do Merge.
    public static void print_merge(FilaC c) {
        System.out.println(" -print_merge(): \n");
        int tam = c.getTamanho();
        System.out.print("C[" + tam + "] (Fila resultante do Merge) = { ");
        if (tam != 0) {
            for (int i = 0; i < tam; i++) {
                if (i == tam - 1) {
                    System.out.println(c.dado[i] + " }. \n");
                } else {
                    System.out.print(c.dado[i] + ", ");
                }
            }
        } else {
            System.out.println(" }. \n");
        }
    }   // gemacht.

    //  Uso: exemplo de fila.
    public static FilaC f() {
        FilaC a = new FilaC(10);
        a.inserir(5);
        a.inserir(3);
        a.inserir(10);
        a.inserir(4);
        a.inserir(9);
        a.inserir(05);
        a.inserir(45);
        a.remover();
        a.remover();
        a.remover();
        a.inserir(54543);
        a.inserir(8);
        a.remover();
        a.remover();
        return a;
    }   // gemacht.

    //  Uso: exemplo de fila.
    public static FilaC g() {
        FilaC a = new FilaC(5);
        a.inserir(9);
        a.inserir(6);
        a.inserir(3);
        a.inserir(04);
        a.inserir(21);
        return a;
    }   // gemacht.

    public static void main(String[] args) {

        // Nomes: Gustavo Hammerschmidt.
        //ajuda();

        //print_exemplo_de_fila();
        FilaC a = enxuto(f());
        FilaC b = enxuto(g());

        print_ordem(a);
        print_ordem(b);

        print_merge(merge(ordem(a), ordem(b)));

    }

}
