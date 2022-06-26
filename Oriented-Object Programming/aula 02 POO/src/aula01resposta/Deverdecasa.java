
package aula01resposta;

import java.util.Scanner;
/**
 *
 * @author Gustavo
 */



/* Para executar um exrcício vá até o construtor na classe main
    e o retire do modo argumento (/*-> *'/)
*/


public class Deverdecasa {
    
    
    static Scanner lk = new Scanner(System.in);
   
    public static void Deverdecasa(){
        System.out.println("Escolha um Exercício.");
        System.out.println("Função e Vetores ->  Exercicios de 1 a 11.");
        System.out.print("Função e matrizes -> Matriz: Exercicios de 1 a 15");
        System.out.print("\n");
        
        
    }
    
    public static void exercicio1(double x, double y){
       Scanner s = new Scanner(System.in);
       //System.out.print("Digite x: ");
       //x = s.nextDouble();
       
       //System.out.print("Digite y: ");
       //y = s.nextDouble();
       
       double z;
       z = y * x;
       System.out.println("Essa será a área do retÂngulo: " + z+"\n");             
    
    }
    
    public static void exercicio2(float a, float b,float abs){       
        float s;
        
        
        s = 0;
        
        for(a = 1;a <= b;a +=abs){
            s = (float) (s + (-0.0051*((Math.pow((a+abs),6))+(Math.pow((a),6))))
                    +(0.2115*((Math.pow((a+abs),5))-(Math.pow((a),5))))
                    +(-2.7844 *((Math.pow((a+abs),4))+(Math.pow((a),4))))
                    +(14.938*((Math.pow((a+abs),3))-(Math.pow((a),3))))
                    +(-30.395 *((Math.pow((a+abs),2))+(Math.pow((a),2))))
                    +(26*((a+abs)+(a)))); 
            
            System.out.println(a+ "<- atual valor de a.");
        
        }
        
        System.out.println("\n"+"Essa será a área da função: " + s 
                + " quando a precisão for: "+abs+"\n");
        
        
    
     
    }
    //acho que é isso!!!, ver se essa é area mesmo!!
    
    public static void exercicio3( int[] vet3){ 
        int menorvalor = vet3[0];
        int position = 0;
        
        
        for(int i = 0; i < vet3.length; i++){
            if(menorvalor > vet3[i]){
                menorvalor = vet3[i];
                
            }
        }    
        for(int j = 0; j < vet3.length; j++){
            if(vet3[j] == menorvalor){
                
                position = j;
            
            }
        }
        
        
        
        
        System.out.print("vet3[] = {");
        for(int j = 0; j < vet3.length; j++){
                    
            System.out.print(vet3[j]+", ");
        }

        System.out.print(" } "+"\n");
        
        System.out.print("Esse é o menor elemento do vet3: "+menorvalor+"\n");
        System.out.print("E essa é a posição dele: "+position+" ou vet3["
                +position+"]."+"\n");
    }
    
    public static void exercicio4(int []A, int[]B){
        int []C = new int[A.length];
        
        for(int i = 0; i < A.length;i++){
            C[i] = A[i] * B[i];
        }
        
        System.out.print("C[] = { ");
        for(int i =0; i<A.length;i++ ) {
            System.out.print(C[i]+", ");
        
        }
        System.out.print("}"+"\n");
        
       
    }
                                            
    public static void exercicio5(int []IntVetorS, int K){
        int []OutVetorS = new int[IntVetorS.length];
        
        for(int i = 0;i < OutVetorS.length; i++){
            OutVetorS[i] = (IntVetorS[i])*K;
            }
        System.out.print("A[] = { ");
        for(int i = 0; i< OutVetorS.length; i++){
            System.out.print(OutVetorS[i]+", ");
        }
        System.out.print(" }"+"\n");
        
        
    }
    
    public static void exercicio6(int []f, int []g){
    int []h = new int[f.length];
    
    for (int i = 0; i < h.length; i++){
        h[i] = (f[i])*(g[i]);
    }
    
    System.out.print("h[] = { ");
    for(int i = 0; i<h.length;i++){
        System.out.print(h[i]+", ");
        
    
    
    }
    System.out.print(" }"+"\n");
    
    }

    public static void exercicio7(int []vect){
        int []vectres = new int[vect.length];
        
        for(int i = 0;i < vectres.length; i++){
        if(vect[i]%2 == 0){
            vectres[i] = vect[i];  
        }
        }
        
        System.out.print("Vect[] = { ");
        for(int i = 0; i < vectres.length;i++){
            System.out.print(vectres[i]+", ");
        }
        System.out.print(" }"+"\n");
    }

    public static void exercicio8(int []vect20){
        int []vect20res = new int[vect20.length];
        int k = 19;
        for(int i = 0; i < k+1; i++){
            vect20res[(k-i)] = vect20[i];
        }
        
       System.out.print("Esse é o vetor 'vect20' invertido: { ");
       for(int i = 0; i < vect20.length;i++){
           System.out.print(vect20res[i]+", ");     
       }
       System.out.print(" }"+"\n");
    }
    
    public static void exercicio9(int []x, int []y, int size){
        
        int []z= new int[size];
        
        for(int i = 0;i <x.length;i++){
            z[i] = (x[i])+(y[i]);
        }
        
        System.out.print("SOMA:    z[] = { ");
         for(int i = 0;i <z.length;i++){
             System.out.print(z[i]+", ");
         }
        System.out.print(" } "+"\n");
        
        
        
        
        System.out.println("");
        for(int i = 0;i <x.length;i++){
            z[i] = (x[i])-(y[i]);
        }
        
        System.out.print("Diferença:  z[] = { ");
        for(int i = 0;i <z.length;i++){
            System.out.print(z[i]+", ");
        }   
        System.out.print(" } "+"\n");
        
        
        
        System.out.println("");
        for(int i = 0;i <x.length;i++){
            z[i] = (x[i])*(y[i]);
        }
        
        System.out.print("Produto:  z[] = { ");
        for(int i = 0;i <z.length;i++){
           System.out.print(z[i]+", ");
        }       
        System.out.println(" } ");
         
        System.out.println("");
        
        
    
    } 
   
    public static void exercicio10(int[] VetorRead){
        int[] contador = new int[VetorRead.length];
        
        for(int a = 0; a < VetorRead.length; a++){
            for(int b = 0; b < VetorRead.length; b++){
                if(VetorRead[a] == VetorRead[b]){
                    contador[a] = contador[a] + 1;
                 
                }
   
            }
        
        }    
        
        
        System.out.println("\n");
        System.out.println("Esse é o vetor VetorRead: ");
        System.out.print("VetorRead[] = { ");
        for(int c =0; c < VetorRead.length; c++){
            System.out.print(VetorRead[c]+", ");
        }
        System.out.print("} "+"\n");
        System.out.println("");
        
        System.out.println("Esse é o vetor de ocorrências (repetições): ");
        System.out.print("Ocorrências[] = { ");
        for(int d = 0; d < VetorRead.length; d++){
            System.out.print(contador[d]+", ");
        }
        System.out.print("} "+"\n");
        System.out.println(" ");
        
        System.out.println("Agora, a lista de ocorrências: ");
        System.out.println("VetorRead[]          ||         Ocorrências: ");
        for(int e = 0; e < VetorRead.length; e++){
            System.out.println("Elemento VetorRead["+e+"] = "+VetorRead[e]+
                    " repete-se "+(contador[e]-1)+" vezes. ");
        }
        System.out.print("\n"+"\n"+"    Fim"+"\n");
        
        
        
        
        /*
        System.out.print("");
        for(int c = 0; c < VetorRead.length; c++){
            System.out.print(contador[c]+" ");
        }
        */
               
        
        
    }   //fazer usando while
           
    public static void exercicio11(float[] vet500){
        
        float maiorvalor;
        maiorvalor = vet500[0];
        
        float[] vet500res = new float[vet500.length];
       
        
        for(int i = 0; i < vet500.length; i++){
            
            if(vet500[i] > maiorvalor){
                
                maiorvalor = vet500[i];
            
            }  // maiorvalor agora será divisor
        
        }
        
        
        
        for(int j = 0; j < vet500.length; j++){
    
            vet500res[j]= (vet500[j])/maiorvalor;
                      
        }
        
        
        
        
        System.out.println("Esse é o vetor dividido pelo meu maior valor:"+"\n"
                + "vet500res[] = [");
        for(int k = 0; k < vet500.length; k++){
            System.out.println(vet500res[k]+" <- elemento na posição "+(k)+" ou vet500res["+k+"].");
        
        }
        System.out.println("             ] <- fim do vet500res.");
   
    
    
    
    
    
    }
    
    
    // Acima exercícios de  vetores;  abaixo  exercicios de matrizes!!!
    
  
    public static void MatrizExercicio1(){
        
        int[][] bots = {{3,1,3},
            {6,5,5}};
        
        int[][] month = {{100,50},
            {50,100},
            {50,50}};
        
        int x = 0, y = 0, w = 0, z = 0;
        
        // x = msio e pequeno
        for(int i = 0; i < 3; i++){
            x = x + bots[0][i] * month[i][0];        
        }
        System.out.print("Abril e pequenos: "+x+"\n");
        
        // w = maio  e grande
        for(int i = 0; i < 3;i++){
            w = w + bots[1][i] * month[i][0];
        }
        System.out.print("Abril e grandes: "+w+"\n");
        
       //y = junho e pequeno
        for(int i = 0; i < 3; i++){
            y = y + bots[0][i] * month[i][1];
        }
        System.out.print("Junho e pequenos: "+y+"\n");
        
        //z = junho e grande
        for(int i = 0; i < 3; i++){
            z = z + bots[1][i] * month[i][1];
        }
        System.out.print("Junho e grandes: "+z+"\n");
    
    
    
    
    }   
    
    public static void MatrizExercicio2(){
        
        // TAMANHO PARA SOMA E DIFERENÇA
        int rowM = 4;
        int columnM = 6 ;
        int[][] M = new int[rowM][columnM];
        
        int rowN = 4;
        int columnN = 6;
        int[][] N = new int[rowN][columnN];           
        
        int rowRES = rowM;
        int columnRES = columnN;
        int[][] RES = new int[rowRES][columnRES];
               
        
        
        
        
        
        // Part 1 start: 
        
        // START : DEFINA VALORES PARA M E N
        for(int Ma = 0; Ma < rowM; Ma++){
            for(int Mb = 0; Mb < columnM; Mb++){
                M[Ma][Mb] = Ma + Mb;
            }    

        }     //valores matrix M
        
        
        for(int Na = 0; Na < rowN; Na++){
            for(int Nb = 0; Nb < columnN; Nb++){
                   N[Na][Nb] = Na + Nb;
                   
            }
        }   // Valores matrix N
   
           
        for(int RESa = 0; RESa < rowRES; RESa++){
            for(int RESb = 0; RESb < columnRES; RESb++){
                RES[RESa][RESb] = 0;
            }
        }        //LIMPANDO MATRIZ RESPOSTA (RES)
        
        // FIM : DEFINA VALORES.    
        
      
        //soma de M e n
        for (int g = 0; g < rowRES; g++){
            for(int h = 0; h < columnRES; h++){
                RES[g][h] = M[g][h] + N[g][h];
                
            }
        
        }    // FOR PARA OPERAÇÃO ENTRE MATRIZES
        
      
        System.out.println("Matriz RES["+rowM+"]["+columnM+"] ="+"\n");
        for(int w = 0; w < rowRES; w++){
            for(int t = 0; t < columnRES; t++){
                System.out.print(RES[w][t]+" ");
                
            }
            System.out.println();   // PRINT DA MATRIZ RESPOSTA
        }     
        System.out.print("\n"+"Fim matriz resposta."+"\n");
        
        // Part 1 end.
        
        
        
        
        

        //Part 2 start:
        

        // START : DEFINA VALORES PARA M E N
        for(int Ma = 0; Ma < rowM; Ma++){
            for(int Mb = 0; Mb < columnM; Mb++){
                M[Ma][Mb] = Ma + Mb;
            }    

        }     //valores matrix M
        
        
        for(int Na = 0; Na < rowN; Na++){
            for(int Nb = 0; Nb < columnN; Nb++){
                   N[Na][Nb] = Na + Nb;
                   
            }
        }   // Valores matrix N
   
           
        for(int RESa = 0; RESa < rowRES; RESa++){
            for(int RESb = 0; RESb < columnRES; RESb++){
                RES[RESa][RESb] = 0;
            }
        }        //LIMPANDO MATRIZ RESPOSTA (RES)
        
        // FIM : DEFINA VALORES.    
        
        
        
        
        //subtração m - n
        for(int q = 0; q < rowRES; q++){
            for(int p = 0; p < columnRES; p++){
                RES[q][p] = M[q][p] - N[q][p];
            
            }           //FOR PARA OPERAÇAO DE DIFERENÇA
        
        }
            
            
        System.out.println("Matriz RES["+rowM+"]["+columnM+"] ="+"\n");
        for(int D = 0; D < rowRES; D++){
            for(int B = 0; B < columnRES; B++){
                System.out.print(RES[D][B]+" ");
                
            }
            System.out.println();   // PRINT DA MATRIZ RESPOSTA
        }     
        System.out.print("\n"+"Fim matriz resposta."+"\n");
        
            
            
            
         
        
        //Part 2 end.
        
        
        
        
        
        //Part 3 start:
        
        
        // TAMANHO PARA SOMA E DIFERENÇA
        int rowU = 4;
        int columnU = 6 ;
        int[][] U = new int[rowU][columnU];
        
        int rowS = columnU;
        int columnS = rowU;
        int[][] S = new int[rowS][columnS];           
        
        int rowRESXX = rowU;
        int columnRESXX = columnS;
        int[][] RESXX = new int[rowRESXX][columnRESXX];
        
        
        
        
        // START : DEFINA VALORES PARA U E S
        
        for(int Ua = 0; Ua < rowM; Ua++){
            for(int Ub = 0; Ub < columnU; Ub++){
                M[Ua][Ub] = Ua + Ub;
            }    

        }     //valores matrix U
        
        
        for(int Sa = 0; Sa < rowN; Sa++){
            for(int Sb = 0; Sb < columnN; Sb++){
                   N[Sa][Sb] = Sa + Sb;
                   
            }
        }   // Valores matrix S
           
        for(int RESXXa = 0; RESXXa < rowRESXX; RESXXa++){
            for(int RESXXb = 0; RESXXb < columnRESXX; RESXXb++){
                RESXX[RESXXa][RESXXb] = 0;
            }
        }        //LIMPANDO MATRIZ RESPOSTA (RESXX)
        
        
        // FIM : DEFINA VALORES.
        
        
        

        // multiplicação U x S
        for(int k = 0; k < rowU; k++){
            for(int l = 0; l < columnS; l++){
                for(int p = 0; p < columnU; p++){
                    RESXX[k][l] = (U[k][p])*(S[p][k]);
                
                }
                
                
            }
        
        }      // FOR PARA OPERAÇÃO MUJLTIPLICAÇÃO
        
        
        System.out.println("Matriz RESXX["+rowU+"]["+columnS+"] ="+"\n");
        for(int D = 0; D < rowRESXX; D++){
            for(int B = 0; B < columnRESXX; B++){
                System.out.print(RESXX[D][B]+" ");
                
            }
            System.out.println();   // PRINT DA MATRIZ RESPOSTA
        }     
        System.out.print("\n"+"Fim matriz resposta."+"\n");
        
       
        //Part 3 end.
        
        
    
    }       
    
    public static void MatrizExercicio3(int rowM, int columnM, int[][] M, int A, int[] V){
        
        for(int f = 0; f < rowM; f++){
            for(int g = 0; g <columnM; g++){
                M[f][g] = (M[f][g]) * A;         
            }
        
        } // for para operação
        
        
        System.out.print("Essa é a matriz M multiplicada por A"+"\n");
        System.out.println("A * M[][] ="+"\n");
        for(int h = 0; h < rowM; h++){
            for(int k = 0; k < rowM; k++){
                System.out.print(M[h][k]+" ");
            }
            System.out.print("\n");
        }
        System.out.println("Fim matriz M. "+"\n");  //for para print: matriz
        
        //erro aqui
        
        
        int z = 0;
        for(int r = 0; r < rowM; r++){
            for(int s = 0; s < columnM;s++){
                V[z++] = M[r][s];
            }
        }       //for de transferência vetor
        
        
        System.out.print("Esse é o vetor V[] = {");
        for(int t = 0; t <V.length; t++ ){
            System.out.print(V[t]+", ");
        }
        System.out.print(" }");  // for print: vetor
        
    } 
    
    public static void MatrizExercicio4(int rowM, int columnM, int[][] M){
        int sumline4 = 0;
        int sumcolumn2 = 0;
        int sumDiagPrin = 0;
        int sumDiagSecun = 0;
        int sumMatriz = 0;
        
        System.out.print("Início da matriz M[][] = "+"\n");
        for(int a = 0; a < rowM;a++){
            for(int b = 0; b < columnM; b++){
                System.out.print(M[a][b]+" ");
            
            }
            System.out.print("\n");
        }
        System.out.print("\n"+"Fim matriz M."+"\n");
        
        
        
        for(int c = 3;c < 4; c++){
            for(int d = 0; d < columnM;d++){
                sumline4 = sumline4 + M[c][d];
            }
        }
        System.out.print("Essa é a soma da 4 linha: "+ sumline4+"\n");
        
        
        
        for(int e = 0; e < rowM; e++){
            for(int f = 1; f < 2; f++){
                sumcolumn2 = sumcolumn2 +M[e][f];
            }
        }
        System.out.print("Essa será a soma da coluna 2: "+sumcolumn2+"\n");
        
        
        for(int g = 0; g < rowM; g++){
            for(int h = 0; h < columnM; h++){
                if( g == h){
                    sumDiagPrin = sumDiagPrin + M[g][h];
                }
            }
        }
        System.out.print("Essa é a soma da diagonal principal: "+
                sumDiagPrin+"\n");
        
        
        for(int k = 0; k < rowM; k++){
            for(int l = 0; l < columnM; l++){
                if( k == (columnM-1- l)){
                    sumDiagSecun = sumDiagSecun + M[k][l];
                }
            }
        }
        System.out.print("Essa será a soma da diagonal secundária: "+
                sumDiagSecun+"\n");
        
        
        for(int p = 0; p < rowM; p++){
            for(int q = 0; q < columnM; q++){
                sumMatriz = sumMatriz + M[p][q];                
            }
        }
        System.out.print("Essa é a soma dos elementos da matriz: "+sumMatriz+"\n");
    
    
    } 
    
    public static void MatrizExercicio5(int rowA, int columnA, int[][] A){
        int[][] contador = new int[rowA][columnA]; 
        
             
                
        
        for(int c = 0; c < rowA; c++){
            for(int d = 0; d < columnA; d++){
                for(int e = 0; e <rowA; e++){
                    for(int f = 0; f < columnA; f++){
                        if(A[c][d] == A[e][f]){
                            contador[c][d] = contador[c][d] + 1;
                        }
                    }
                }
            }
        }
        
        System.out.println("\n"+"Essa é a matriz A[][]: "+"\n");
        for(int g = 0; g < rowA; g++){
            for(int h = 0; h < columnA; h++){
                System.out.print(A[g][h]+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
        
        for(int i = 0; i < rowA; i++){
            for(int j = 0; j < columnA; j++){
                System.out.println("Elemento A["+i+"]["+j+"] = "+A[i][j]+
                        " repete-se "+(contador[i][j]-1)+" vezes.");
                
            }
        System.out.print("\n"+"\n"+"      Fim     "+"\n");
    
        }
            
    
    }    
    
    public static void MatrizExercicio6(int rowM, int columnM, double[][] M){
        
        double divisor = M[0][0];
        double[] listaDivisor = new double[rowM];
        double[][] mod = new double[rowM][columnM];
        
        System.out.print("\n \n"+"Matriz M conforme digitada: "+"\n \n");
        for(int c = 0; c < rowM; c++){
            for(int d = 0; d < columnM; d++){
                System.out.print(M[c][d]+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n"+"     Fim matriz M.  \n");
        // print matriz original
        
        
        for(int e = 0; e < rowM; e++){
            for(int f = 0; f < columnM; f++){
                mod[e][f] = 0;
            } 
        }      // criação matriz mod
        
        for(int i = 0; i < rowM; i++){
            int z = i ;
            for(int j = 0; j < columnM; j++){
                if(divisor < M[i][j]){
                    divisor = M[i][j];
                }
            }
            listaDivisor[z] = divisor;
        }
        
             
         
        
        
        for(int k = 0; k < listaDivisor.length; k++){
            if(listaDivisor[k] < 0){
                listaDivisor[k] = -1*listaDivisor[k];
            }
        }       // for modulo
        
        
        for(int k = 0; k < listaDivisor.length; k++){
            if(listaDivisor[k] == 0){
                listaDivisor[k] = 1;
            }
        }    //divisores prontos; até aqui tudo certo
        
        System.out.print("\n");
        for(int a = 0; a < listaDivisor.length;a++){
            System.out.print(listaDivisor[a]+" ");
        }
        
        
        
        
        
        /// ERRO DIVISÃO
        
        for(int m = 0; m < rowM; m++){    
            int y = 0;
            for(int n = 0; n < columnM; n++){
                mod[n][m] = (  (double) M[n][m])/( (double) listaDivisor[(y++)]);
            }
        }
        
        
        System.out.print("\n \n Essa será a matriz modificada: \n \n");
        for(int j = 0; j < rowM; j++){
            for(int k = 0; k < columnM; k++){
                System.out.print(mod[j][k]+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n \n");
     
    }      
   
    public static void MatrizExercicio7(int rowM, int columnM, int[][] M){
        
        
        
        System.out.print("\n Matriz M digitada: \n \n");
        for(int a = 0; a < rowM; a++){
            for(int b = 0; b < columnM; b++){
                System.out.print(M[a][b]+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n  \n  Fim matriz M. ");
        
       int[][] Mres = new int[rowM][columnM];
       for(int e = 0; e < rowM; e++){
            for(int f = 0; f < columnM; f++){
                Mres[e][f] = M[e][f];
           }
       }          // segunda matriz
       
        for(int x = 0; x < columnM; x++){
            Mres[7][x] = M[1][x];
            Mres[1][x] = M[7][x];
        } // troca linha 2 com 8
        
        for(int y = 0; y < rowM; y++){
            Mres[y][9] = M[y][3];
            Mres[y][3] = M[y][9];
            
        } // troca coluna 10 com 4 
        
        
        System.out.print("\n \n Matriz M com mudanças: \n \n");
        for(int g = 0; g < rowM; g++){
            for(int h = 0; h < columnM; h++){
                System.out.print(Mres[g][h]+"  ");
            }
            System.out.print("\n");
        }                                                  // print matriz
        System.out.print("\n \n Fim Matriz M alterada. \n \n");
        
        
        
        
        
        
    
    }    
    
    public static void MatrizExercicio8(int rowM, int columnM, int[][] M){
        
        
        System.out.print("\n Matriz M digitada: \n \n");
        for(int a = 0; a < rowM; a++){
            for(int b = 0; b < columnM; b++){
                System.out.print(M[a][b]+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n  \n  Fim matriz M. ");
        
       int[][] Mres = new int[rowM][columnM];
       for(int e = 0; e < rowM; e++){
            for(int f = 0; f < columnM; f++){
                Mres[e][f] = M[e][f];
           }
        }          // segunda matriz
       
       int[] conv = new int[columnM];
       int z =0;
       for(int p = 0; p < rowM; p++){
           for(int q = 0; q < columnM; q++){
               if( p ==(columnM -1- q)){   // secun to prim
                   conv[z++] = M[p][q];
               }
           }
        }
       
       int sd = 0;
       for(int w = 0; w < rowM; w++){
           for(int u = 0; u < columnM; u++){
               if( w == u ){
                   Mres[w][u] = conv[sd++];
               }
           }
       }
       
       
       for(int s = 0; s < rowM; s++){
           for(int t = 0; t < columnM; t++){
               if( s == t){
                   Mres[columnM-1-t][s] = M[s][t];
                   System.out.print(Mres[s][t]);
               }
           }
        }
       

        for( int r = 0; r < rowM; r++){
            Mres[4][r] = M[r][9];
            Mres[r][9] = M[4][r];
        }
        
        System.out.print("\n \n Matriz M com mudanças: \n \n");
        for(int g = 0; g < rowM; g++){
            for(int h = 0; h < columnM; h++){
                System.out.print(Mres[g][h]+"  ");
            }
            System.out.print("\n");
        }                                                  // print matriz
        System.out.print("\n \n Fim Matriz M alterada. \n \n");
       
    
    
    }  
   
    public static void MatrizExercicio9(int rowM, int columnM, int[][] M){
        
        int[][] Mres = new int[rowM][columnM];
        
        System.out.print("\n Matriz M digitada: \n \n");
        for(int a = 0; a < rowM; a++){
            for(int b = 0; b < columnM; b++){
                System.out.print(M[a][b]+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n  \n  Fim matriz M. ");
        
        
        
        
        
        
        
        for(int a = 0; a < rowM; a++){
            Mres[a][1] = M[a][0];
            Mres[a][0] = M[a][1];
            
            Mres[a][3] = M[a][2];
            Mres[a][2] = M[a][3];
            
            Mres[a][5] = M[a][4];
            Mres[a][4] = M[a][5];
            
        }
        
        
        System.out.print("\n \n Matriz M com mudanças: \n \n");
        for(int g = 0; g < rowM; g++){
            for(int h = 0; h < columnM; h++){
                System.out.print(Mres[g][h]+"  ");
            }
            System.out.print("\n");
        }                                                  // print matriz
        System.out.print("\n \n Fim Matriz M alterada. \n \n");
        
               
        
        
        
        
    
    }   
   
    public static void MatrizExercicio10(int rowM, int columnM, int[][] M){
        
        System.out.print("\n Matriz M digitada: \n \n");
        for(int a = 0; a < rowM; a++){
            for(int b = 0; b < columnM; b++){
                System.out.print(M[a][b]+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n  \n  Fim matriz M. ");
    
        int SomaDiag = 0;
        for(int f = 0; f < rowM; f++){
            for(int g = 0; g < columnM; g++){
                if( f == g ){
                    SomaDiag = SomaDiag + M[f][g];
                }
            }
        }
        
        int media;
        media = SomaDiag/rowM;
        
        System.out.print("\n \n Essa será a média da "
                +" diagonal principal da Matriz M: "+media+"."+ "\n \n");
        
    }   
    
    public static void MatrizExercicio11(int rowM, int columnM, int[][] M){
        
        int big = M[0][0];
        int[][] Mres = new int[rowM][columnM];
        
        
        
        System.out.print("\n Matriz M digitada: \n \n");
        for(int a = 0; a < rowM; a++){
            for(int b = 0; b < columnM; b++){
                System.out.print(M[a][b]+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n  \n  Fim matriz M. ");
        
        
        for(int i = 0; i < rowM; i++){
            for(int j = 0; j < columnM; j++){
                Mres[i][j] = M[i][j];
            }
        }
        
        
        
        
        
        
        for(int r = 0; r < rowM; r++){
            for(int s = 0; s < columnM; s++){
                if(big < M[r][s]){
                    big = M[r][s];
                }
            }
        }
        
        for(int t = 0; t < rowM; t++){
            for(int u = 0; u < columnM; u++){
                if( t  == u ){
                    Mres[t][u] = (M[t][u])*big;
                }
            }
        }
        
      
    System.out.print("\n \n Matriz M com mudanças: \n \n");
        for(int g = 0; g < rowM; g++){
            for(int h = 0; h < columnM; h++){
                System.out.print(Mres[g][h]+"  ");
            }
            System.out.print("\n");
        }                                                  // print matriz
        System.out.print("\n \n Fim Matriz M alterada. \n \n");
    
      
        
    }   
  
    public static void MatrizExercicio12(int rowM, int columnM, int[][] M){
        
        int Maior = M[0][0];
        int pos1m = 0;
        int pos1n = 0;
        
        System.out.print("\n Matriz M digitada: \n \n");
        for(int a = 0; a < rowM; a++){
            for(int b = 0; b < columnM; b++){
                System.out.print(M[a][b]+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n  \n  Fim matriz M. ");
        
        for(int a = 0; a < rowM; a++){
            for(int b = 0; b < columnM; b++){
                if( Maior < M[a][b]){
                    Maior = M[a][b];
                }
                
            }
        }        // maior elemento
        
        for(int c = 0; c < rowM; c++){
            for(int d = 0; d < columnM; d++){
                if( Maior == M[c][d]){
                   pos1m = c;
                   pos1n = d;
                }
                
            }
        }         // posição maior elemento
        
        int Menor = M[0][0];
        int pos2m = 0;
        int pos2n = 0;
        
        for(int e = 0; e < columnM; e++){
            if(Menor > M[pos1m][e]){
                Menor = M[pos1m][e];
            }
        }        // menor elemento linha
        
        
        for(int f = 0; f < rowM; f++){
            for(int g = 0; g <columnM; g++){
                if(Menor == M[f][g]){
                    pos2m = f;
                    pos2n = g;
                }
            }
        }                   // posição menor1
        
        System.out.print("\n \n O maior elemento é M["+pos1m+"]["+pos1n+"] = "+Maior+"\n");
        System.out.print("\n \n O menor elemento da linha em que se encontra o \n "
                +"maior elemento (MINIMAX) é M["+pos2m+"]["+pos2n+"] = "+Menor+"\n \n");
        
        
        
        
        
        
    
    }  
      
    public static void MatrizExercicio13(){
        
        int rowMICH = 6;
        int columnMICH = 6;
        int[][] DICH = new int[rowMICH][columnMICH];
        
        for(int a = 0; a < rowMICH; a++){
            for(int b = 0; b < columnMICH; b++){
                DICH[a][b] = 1;
            }
        }
        
        for(int c = 1; c < rowMICH; c += 3){
            for(int d = 1; d < columnMICH-1; d++){
                DICH[c][d] = 2;
            }
        }
        
        for(int c = 1; c < rowMICH-1; c ++){
            for(int d = 1; d < columnMICH; d += 3){
                DICH[c][d] = 2;
            }
        }
        
        for(int e = 2; e  < 4; e++){
            for(int f = 2; f < 4; f++){
                DICH[e][f] = 3;
            }
        }
        
        System.out.print("\n"+"\n"+"Essa será a matriz DICH[][] = "+"\n"+"\n");
        for(int g = 0; g < rowMICH; g++ ){
            for(int h = 0;  h < columnMICH; h++){
                System.out.print(DICH[g][h]+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n"+"\n"+
                "           Fim da matriz DICH."+"\n");
        
        
        
        
    
    }   
    
    public static void MatrizExercicio14(){
        
        int rowMAT = 6;
        int columnMAT = 6;
        
        int[][] MAT = new int[rowMAT][columnMAT];
        
        
        
        for(int i = 0; i < rowMAT; i++){
            for(int j = 0; j < columnMAT; j++){
                MAT[i][j] = 3;
            }
        }   //for para preencher matriz 6x6 só de 3!!!
        
        
        for(int k = 0; k < rowMAT; k++){
            for(int l = 0; l < columnMAT; l++){
                if(k == l){
                    MAT[k][l] = 1;
                }
            }
        }   // for para preencher Diag. Princ. só com 1;
        
        
        for(int m = 0; m < rowMAT; m++){
            for(int n = 0; n < columnMAT; n++){
                if(m ==(columnMAT-1 - n)){
                    MAT[m][n] = 2;
                }
            }
        }   // for para preencher Diag. Secun. só com 2;
        
        
        System.out.print("\n");
        System.out.println("Essa será a matriz MAT[][]: "+"\n");
        for(int p = 0; p < rowMAT; p++){
            for(int q = 0; q < columnMAT; q++){
                System.out.print(MAT[p][q]+" ");
            }
            System.out.print("\n");
        }   // for para imprimir matriz final
        System.out.print("\n"+"\n"+" Fim da matriz MAT."+"\n");
        
        
        
        
        
        
        
        
        
        
        
        
        
    
    }   
    
    public static void MatrizExercicio15(int rowM, int columnM, int[][] M){
        
        //COMEÇO CRIAÇÃO:PRINT MATRIZ DIGITADA E CRIAÇAO DE MATRIZES AM E BM.
        
        System.out.print("\n \n Essa é a Matriz M digitada: \n \n");
        for(int a = 0; a < rowM; a++){
            for(int b = 0; b < columnM; b++){
                System.out.print(M[a][b]+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n \n Fim matriz M.\n \n");  // print matriz m
        
        
        int[][] AM = new int[rowM][columnM];
        for(int a = 0; a < rowM; a++){
            for(int b = 0; b < columnM; b++){
                
                AM[a][b] = M[a][b];
            }
        }         //Matriz exercicio A
        
        int[][] BM = new int[rowM][columnM];
        for(int a = 0; a < rowM; a++){
            for(int b = 0; b < columnM; b++){
                BM[a][b] = M[a][b];
            }
        }         //Matriz exercicio B
        
        // FIM CRIAÇÃO.
        
        
        
        
        
        
        ////// COMEÇO LETRA A !!
        
        System.out.print("\n Letra a:\n \n");
       
        for(int i = 0; i < rowM; i++){
            for(int j = 0; j < columnM; j++){
                if( i == (columnM-1-j)){
                   AM[i][j] = 0;
                }
            }
        }                 //Diag. secun. só com 0.
        
        for(int i = 0; i < rowM; i++){
            for(int j = i; j < columnM; j++){
                if(j >= (columnM-1-i)){
                    AM[i][j] = 0;
                }
            }
        }           // METADE DIREITA DA PARTE DE BAIXO
        
        
        for(int i = 0; i < rowM; i++){
            for(int j = i; j < columnM; j++){
                if( (i) >= (columnM-1-j)){
                    AM[j][i] = 0;
                }
            }
        }        // METADE ESQUERDA DA PARTE DE BAIXO
        
        System.out.print("\n \n Matriz da letra a: \n \n");
        for(int i = 0; i < rowM; i++){
            for(int j = 0; j < columnM; j++){
                System.out.print(AM[i][j]+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n Fim matriz letra a. \n ");
                 // PRINT MATRIZ AM 
        
        
        
        int somaA = 0;
        for(int i = 0; i < rowM; i++){
            for(int j = 0; j < columnM; j++){
                somaA = somaA + AM[i][j];
            }
        }
        System.out.print("\n O resultado da soma da matriz triangular do "+
                " exercício A é: "+somaA+"!! \n \n");
                         //SOMA MATRIZ AM
        
        
        //////////////////// FIM LETRA A !!
        
        
        
        
        
        
        //////////////////COMEÇO LETRA B !!
        
        
        for(int i = 0; i < rowM; i++){
            for(int j = 0; j < columnM; j++){
                if( i == j){
                    BM[i][j] = 0;
                }
                else if(i == (columnM-1-j)){
                    BM[i][j] = 0;
                }
            }
        }
        
        for(int i = 0; i < rowM; i++){
            for(int j = i; j < columnM; j++){
                if(j >= (columnM-1-i)){
                    BM[i][j] = 0;
                }
            }
        }           // Do meio à direita entre as diagonais.    
        
        
        for(int i = 0; i < rowM; i++){
            for(int j = i; j < columnM; j++){
                if( (i) <= (columnM-1-j)){
                    BM[j][i] = 0;
                }
            }
        }        // Do meio à esquerda entre as diagonais
        
        
        System.out.print("\n \n Matriz da letra b: \n \n");
        for(int i = 0; i < rowM; i++){
            for(int j = 0; j < columnM; j++){
                System.out.print(BM[i][j]+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n Fim matriz letra b. \n ");
                 // PRINT MATRIZ BM 
        
        
        int somaB = 0;
        for(int i = 0; i < rowM; i++){
            for(int j = 0; j < columnM; j++){
                somaB = somaB + BM[i][j];
            }
        }
        System.out.print("\n O resultado da soma da matriz triangular do "+
                " exercício A é: "+somaB+"!! \n \n");
                         //SOMA MATRIZ BM
        
        
        //////// FIM LETRA B !!
        
        
        
        
        
    }  
    
    public static void exercicio10comWhile(int[] VetorRead){
        
        int[] contador = new int[VetorRead.length];
        int i = 0;
        while(i < VetorRead.length){
            int j = 0;
            while(j < VetorRead.length){
                if(VetorRead[i] == VetorRead[j]){
                    contador[i] = contador[i] + 1;                
                }
                j++;
            }
        i++;
        }
           
        
        
        System.out.println("\n");
        System.out.println("Esse é o vetor VetorRead: ");
        System.out.print("VetorRead[] = { ");
        for(int c =0; c < VetorRead.length; c++){
            System.out.print(VetorRead[c]+", ");
        }
        System.out.print("} "+"\n");
        System.out.println("");
        
        System.out.println("Esse é o vetor de ocorrências (repetições): ");
        System.out.print("Ocorrências[] = { ");
        for(int d = 0; d < VetorRead.length; d++){
            System.out.print(contador[d]+", ");
        }
        System.out.print("} "+"\n");
        System.out.println(" ");
        
        System.out.println("Agora, a lista de ocorrências: ");
        System.out.println("VetorRead[]          ||         Ocorrências: ");
        for(int e = 0; e < VetorRead.length; e++){
            System.out.println("Elemento VetorRead["+e+"] = "+VetorRead[e]+
                    " repete-se "+(contador[e]-1)+" vezes. ");
        }
        System.out.print("\n"+"\n"+"    Fim"+"\n");
    }
    
    public static void exercicio10comDoWhile(int[] VetorRead){
        
        int[] contador = new int[VetorRead.length];
        int i = 0;
        do{
            int j = 0;
            do{
                if(VetorRead[i] == VetorRead[j]){
                    contador[i] = contador[i] + 1;
                }
                j++;
            }while(j < VetorRead.length);
        i++;
        }while(i < VetorRead.length);
        
        
        System.out.println("\n");
        System.out.println("Esse é o vetor VetorRead: ");
        System.out.print("VetorRead[] = { ");
        for(int c =0; c < VetorRead.length; c++){
            System.out.print(VetorRead[c]+", ");
        }
        System.out.print("} "+"\n");
        System.out.println("");
        
        System.out.println("Esse é o vetor de ocorrências (repetições): ");
        System.out.print("Ocorrências[] = { ");
        for(int d = 0; d < VetorRead.length; d++){
            System.out.print(contador[d]+", ");
        }
        System.out.print("} "+"\n");
        System.out.println(" ");
        
        System.out.println("Agora, a lista de ocorrências: ");
        System.out.println("VetorRead[]          ||         Ocorrências: ");
        for(int e = 0; e < VetorRead.length; e++){
            System.out.println("Elemento VetorRead["+e+"] = "+VetorRead[e]+
                    " repete-se "+(contador[e]-1)+" vezes. ");
        }
        System.out.print("\n"+"\n"+"    Fim"+"\n");
        
    }
    
    public static void main(String []args){
        
        
        
        /*
        //CHAMADA IDENTIFICADOR
        Deverdecasa();
        */
        
        
        
        
        
        
        /*
        //CHAMADA EXERCÍCIO 1
        double x, y;
        System.out.println("Digite x: ");
        x = lk.nextInt();
        
        System.out.println("Digite y: ");
        y = lk.nextInt();
        
        exercicio1(x,y);
        */
        
        
        
        
        
        
        /*
        //CHAMADA EXERCICIO 2         
        int a = 1, b = 9;
        float abs;
        abs =(float) 0.0001;
        
        exercicio2(a,b,abs);
        */
        

        
        
        
        
        
        
        /*
        //CHAMADA EXERCICIO 3                 
        int n;
        System.out.println("Defina o número de elementos: ");
        n = lk.nextInt();
        
        int[] vet3 = new int[n];
        
        for(int i =0; i < n; i++){
            System.out.println("Elemento "+i+" do array vet3: ");
            vet3[i] = lk.nextInt();
        
        }
        
        exercicio3( vet3);
        */
        

        
        
        
        
        
      
        
        
        /*
        //CHAMADA EXERCICIO 4
        int[] a = new int[20], b = new int[20];
        for(int i = 0; i < a.length; i++){
            System.out.println("Elemento "+i+" do array a:");
            a[i] = lk.nextInt();
            
            
            System.out.println("Elemento "+i+" do array b:");
            b[i] = lk.nextInt();               
        
        }
        exercicio4(a,b);
        */
        
        
        
        
        
        
        
        /*
        //CHAMADA EXERCICIO 5
        int[] x = new int[20];
        int k;
        for(int i = 0;i <x.length; i++){
            System.out.println("Elemento "+i+" do array x:");
            x[i] = lk.nextInt();
                      
        } 
        System.out.println("Digite k: ");
        k = lk.nextInt();
        
        exercicio5(x, k);
        */
        
        
        
        
        
        
        
        
        /*
        //CHAMADA EXERCICIO 6
        int[] f = new int[20], g = new int[20];
        
        for(int i =0; i <f.length;i++){
            System.out.println("Elemento "+i+" do array f: ");
            f[i] = lk.nextInt();
            
            System.out.println("Elemento "+i+" do array g: ");
            g[i] = lk.nextInt();       
        }
        
        exercicio6(f, g);
        */
        
        
        
        
        
        
        
        
        
        /*
        //CHAMADA EXERCICIO 7
        int[] vect = new int[20];
        
        for(int i = 0; i < vect.length; i++){
            System.out.println("Elemento "+i+" do array vect: ");
            vect[i] = lk.nextInt();
            
        }
        
        exercicio7(vect);
        */
        
        
        
        
        
        
        
        
 
        
        
        /*
        //CHAMADA EXERCICIO 8
        int[] vect20 = new int[20];
        
        for(int i = 0; i < vect20.length;i++){
            System.out.println("Elemento "+i+" do array vect20: ");
            vect20[i] = lk.nextInt();
                    
        }
        
        exercicio8(vect20);
        */
        
        
        
        
        
        
        
        
        
        /*
        //CHAMADA EXERCÍCIO 9
        int size;
        System.out.println("Número de elementos do array x e y:");
        size = lk.nextInt();
        int[] x = new int[size], y = new int[size];
        for (int i = 0; i < size; i++){
            System.out.println("Elemento "+i+" do array x: ");
            x[i] = lk.nextInt();
            
            System.out.println("Elemento "+i+" do array y: ");
            y[i] = lk.nextInt();
                       
            
        }
         exercicio9(x, y, size);
        */
        
        
        
     
        
        /*
        //CHAMADA EXERCÍCIO10   
        int tamanho = 20;
        int[] VetorRead = new int[tamanho];
        for(int i = 0; i < VetorRead.length; i++){
            System.out.print("Defina o elemento "+i+" do VetorRead: ");
            VetorRead[i] = lk.nextInt();
        }
        
        exercicio10(VetorRead);
       */
        
             
       
        
        
        
        /*
        //CHAMADA EXERCICIO11
        int tamanho = 500;
        float[] vet500 = new float[tamanho];
        
        for(int i = 0; i < vet500.length; i++){
            vet500[i] = i;
            //primeiro elemento 1, ultimo 500.
        }
        
        
        exercicio11(vet500);
        */
        
        
        
        
 
        
        /*
        //MATRIZ EXERCICIO 1
        MatrizExercicio1();
        */
        
        
        
        
        
        
        
        
        /*
        //CHAMADA MATRIZ EXERCICIO 2
        MatrizExercicio2();
        */
        
        
        
        
        
        
        
        
        /*
        //CHAMADA MATRIZ EXERCICIO 3
        int rowM = 6;
        int columnM = 6;
        int[][] M = new int [rowM][columnM];
        int A;
        int conta = rowM*columnM;
        int[] V = new int [conta];
        
        for( int i = 0; i < rowM; i++){
            for(int j = 0; j < columnM; j++){
                System.out.print("Defina o elemento M["+i+"]["+j+"]: ");
                M[i][j] = lk.nextInt();
                System.out.print("\n");
            }
        }
        System.out.print("Defina A (valor do multiplicador): ");
        A = lk.nextInt();
        System.out.print("\n");
        
        MatrizExercicio3(rowM, columnM, M, A, V);
        */
        
        
        
        
        /*    
        //CHAMADA MATRIZ EXERCICIO 4    
        int rowM = 5;
        int columnM = 5;
        int[][] M = new int[rowM][columnM];
        
        for(int i = 0; i < rowM; i++){
            for(int j = 0; j < columnM; j++){
                System.out.print("Defina o elemento M["+i+"]["+j+"] :");
                M[i][j] = lk.nextInt();
                System.out.print("\n");
            }
        }
        MatrizExercicio4(rowM, columnM, M);
        */
        
        
      
        
        /*
        //CHAMADA MATRIZ EXERCICIO 5
        int rowA = 15;
        int columnA = 5;
        int[][] A = new int[rowA][columnA];
       
        for(int a = 0; a < rowA; a++ ){
            for(int b = 0; b < columnA; b++){
                System.out.print("Defina elemento A["+a+"]["+b+"]: ");
                A[a][b] = lk.nextInt();
                System.out.print("\n");
            }
        }
       
        MatrizExercicio5(rowA,columnA, A);
        */
        
        
        
        
        
        /*
        //CHAMADA MATRIZ EXERCICIO 6
        int rowM = 5;
        int columnM = 5;
        double [][] M =  new double[rowM][columnM];
        
        for(int a = 0; a < rowM; a++){
            for(int b = 0; b < columnM; b++){
                System.out.print("Defina o elemento M["+a+"]["+b+"]: ");
                M[a][b] = b+a;
                //M[a][b] = lk.nextInt();
                System.out.print("\n");
            }
        }
        
        MatrizExercicio6(rowM, columnM, M);
        */
        
        
        
        
        
        /*
        //CHAMADA MATRIZ EXERCICIO 7
        int rowM = 10;
        int columnM = 10;
        int[][] M = new int[rowM][columnM];
        
        for(int i = 0; i < rowM; i++){
            for(int j = 0; j <columnM; j++){
                System.out.print("Digite o elemento M["+i+"]["+j+"]: ");
                M[i][j] = lk.nextInt();
                System.out.print("\n");
            }
        }
        
        MatrizExercicio7(rowM, columnM, M);
        */  
               
        
        
        
        
        
        
        /*
        //CHAMADA MATRIZ EXERCICIO 8
        int rowM = 10;
        int columnM = 10;
        int[][] M = new int[rowM][columnM];
        
        for(int i = 0; i < rowM; i++){
            for(int j = 0; j <columnM; j++){
                System.out.print("Digite o elemento M["+i+"]["+j+"]: ");
                M[i][j] = lk.nextInt();
                System.out.print("\n");
            }
        }
        
        MatrizExercicio8(rowM, columnM, M);
        */
        
        
        
        
        /*
        //CHAMADA MATRIZ EXERCICIO 9
        int rowM = 6;
        int columnM = 6;
        int[][] M = new int[rowM][columnM];
        
        for(int i = 0; i < rowM; i++){
            for(int j = 0; j <columnM; j++){
                System.out.print("Digite o elemento M["+i+"]["+j+"]: ");
                M[i][j] = lk.nextInt();
                System.out.print("\n");
            }
        }
        
        MatrizExercicio9(rowM, columnM, M);
        */
        
        
        
        
      
        /*
        //CHAMADA MATRIZ EXERCICIO 10
        int rowM = 10;
        int columnM = 10;
        int[][] M = new int[rowM][columnM];
        
        for(int i = 0; i < rowM; i++){
            for(int j = 0; j <columnM; j++){
                System.out.print("Digite o elemento M["+i+"]["+j+"]: ");
                M[i][j] = lk.nextInt();
                System.out.print("\n");
            }
        }
        
        MatrizExercicio10(rowM, columnM, M);
        */






        /*
        //CHAMADA MATRIZ EXERCICIO 11
        int rowM = 50;
        int columnM = 50;
        int[][] M = new int[rowM][columnM];
        int y = 2;
        
        for(int i = 0; i < rowM; i++){
            for(int j = 0; j <columnM; j++){
                M[i][j] =  y;
            }
        }
        
        MatrizExercicio11(rowM, columnM, M);
        */
        
        
        
        
        
        
        
        /*
        //CHAMADA MATRIZ EXERCICIO 12
        int rowM = 10;
        int columnM = 10;
        int[][] M = new int[rowM][columnM];
        
        for(int i = 0; i < rowM; i++){
            for(int j = 0; j <columnM; j++){
                System.out.print("Digite o elemento M["+i+"]["+j+"]: ");
                M[i][j] = lk.nextInt();
                System.out.print("\n");
            }
        }
        
        MatrizExercicio12(rowM, columnM, M);
        */
        
        
        
        
        
        
        
        /*
        //CHAMADA MATRIZ EXERCICIO 13
        MatrizExercicio13();
        */
        
   
        
        /*
        //CHAMADA MATRIZ EXERCICIO 14
        MatrizExercicio14();
        */
        
        
        
        
        /*
        //CHAMADA MATRIZ EXERCICIO 15
        int rowM = 12;
        int columnM = 12;
        int[][] M = new int[rowM][columnM];
        
        for(int i = 0; i < rowM; i++){
            for(int j = 0; j <columnM; j++){
                
                // ESCOLHA O MODO
                
                //USA O INPUT DO USUÁRIO
                //System.out.print("Digite o elemento M["+i+"]["+j+"]: ");
                //M[i][j] = lk.nextInt();
                //System.out.print("\n");
                
                //GERA MATRIZ DE NÚMEROS PREDEFINIDOS
                M[i][j] = 5;
                
            }
        }
        MatrizExercicio15(rowM, columnM, M);
       */
        
        
        /*
        //CHAMADA EXERCÍCIO10comWhile   
        int tamanho = 20;
        int[] VetorRead = new int[tamanho];
        for(int i = 0; i < VetorRead.length; i++){
            System.out.print("Defina o elemento "+i+" do VetorRead: ");
            VetorRead[i] = lk.nextInt();
        }
        
        exercicio10comWhile(VetorRead);
        */
        
        /*
        //CHAMADA EXERCÍCIO10comWhile   
        int tamanho = 20;
        int[] VetorRead = new int[tamanho];
        for(int i = 0; i < VetorRead.length; i++){
            System.out.print("Defina o elemento "+i+" do VetorRead: ");
            VetorRead[i] = lk.nextInt();
        }
        
        exercicio10comDoWhile(VetorRead);
        */
        
        /*
        * Conclusão: a resposta do exercício 10 foi mais rápida utilizando 
        * o método do while. 
        */
        
        
        
    }  

    
    
}