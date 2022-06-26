
package ra03jobpoo;

public class RA03JOBPOO {
    

    // Métodos:       6/7 
    public void testarContaCorrente(Banco x, String num){
        System.out.println("Saldo: "+x.consultarSaldoContaCorrente(num));  
    }
    
    public void testarContaCorrenteLimitada(Banco x, String num){
        System.out.println("Saldo: "+x.consultarSaldoContaLimitada(num));
    }
    
    public void testarContaPoupança(Banco x, String num){
        System.out.println("Saldo: "+x.consultarSaldoPoupança(num));
    }
    
    public void testarEmprestimo(Banco x, String num){
        System.out.println("Saldo: "+x.consultarSaldoEmprestimo(num));
    }
    
    public void testarContaEthereum(Banco x, String num){
        System.out.println("Saldo: "+x.consultarSaldoEthereum(num));
    }
    
    public void testarContaBitcoin(Banco x, String num){
        System.out.println("Saldo: "+x.consultarSaldoBitcoin(num));
    }
    
    public void testarSistema(){
        System.out.println("Tudo está certo!");
    }
    // Métodos
    
   
    
    public static void main(String[] args) {
     
        //////////////////// Cliente  
        //
        String nome = "Gustavo";                                         //Nome.
        String sobrenome = "Hammerschmidt";                         //Sobrenome.
        String cpf = "07226031930";                                       //CPF.
        String endereço = "curitiba, parana, água verde";            //Endereço.
        String email = "gustavocrazy@yahoo.com";                        //Email.
        String telefone = "041988252533";                            //Telefone.
        //
        /////////////////// Cliente
        
        
        /////////////////////////////  Conta,  Conta Corrente
        //
        String numCC = "1234";                                //Número da Conta.
        String numAgenCC = "2345";                          //Número da Agência.
        double valueCC = 12;                           //Valor Inicial da Conta.
        //
        /////////////////////////////  Conta,  Conta Corrente
         
        
        /////////////////////////////  Conta Corrente Limitada
        //
        String numCCL = "123456";                             //Número da Conta.
        String numAgenCCL = "5412";                         //Número da Agência.
        double valueCCL = 50000;                       //Valor Inicial da Conta.
        double limCCL = 5000;                             //Limite de transação.
        double tdJuroCCL = 0.2;                                 //Taxa de Juros. 
        //
        /////////////////////////////  Conta Corrente Limitada
        
        
        
        /////////////////////////////  Poupança
        //
        String numP = "903647";                            //Número da Poupança.
        String numAgenP = "3789";                           //Número da Agência.
        double valueP = 1000000;                            //Valor da Poupança.
        int ddIP = 20;                             //Dia de Criação da Poupança.
        int mmIP = 10;                             //Mês de Criação da Poupança.
        int aaaaIP = 2015;                         //Ano de Criação da Poupança.
        int ddP = 25;                             //Dia atual da Conta Poupança.
        int mmP = 12;                             //Mês atual da Conta Poupança.
        int aaaaP = 2016;                         //Ano atual da Conta Poupança.
        double tdCorrP = 2.2;             //Taxa de correção mensal da Poupança.
        //
        /////////////////////////////  Poupança
       
        
        /////////////////////////////  Papagaio
        //
        String numPP  = "23456";                           //Número do Papagaio.    
        String numAgenPP = "9304";                          //Número da Agência.
        double valuePP = 2500;                                 //Valor da Conta. 
        String nEmpPP = "43287";                         //Número do Empréstimo.
        double val2PP = 1000;                             //Valor do empréstimo.
        int tdJuroPP = 4;                                       //Taxa de Juros.
        String ddCriaPP = "10/12/2000";           //Data de Criação do Papagaio.
        String ddVencPP = "11/05/2001";        //Data de Vencimento do Papagaio.
        //
        /////////////////////////////  Papagaio
        
        
        /////////////////////////////  Bitcoin
        //
        String numB = "12785";                                //Número da Conta.
        String numAgenB = "8374";                           //Número da Agência.
        double valueB =  30000;                                //Valor Na conta.
        double Bitcoin = 60000;                              //Valor do Bitcoin.
        //
                                                    // Supor um valor para a moeda
        /////////////////////////////  Bitcoin
        
        
        /////////////////////////////  Ethereum
        //
        String numE = "22345";                                //Número da Conta.
        String numAgenE = "4562";                           //Número da Agência.
        double valueE = 50000;                                 //Valor Na Conta.
        float Ethereum = 20.50f;                            //Valor do Ethereum.
                                            // Supor um valor para a moeda
        //
        /////////////////////////////  Ethereum
        
        
        
        
        
        // x é um cliente do Banco.
        Banco x = new Banco(cpf, nome, sobrenome, endereço, email, telefone);
        x.criarCliente(cpf, nome, sobrenome, endereço, email, telefone);
        System.out.println(x.consultarCliente(cpf));
        
        
        //Conta Corrente        
        x.criarContaCorrente(cpf, numCC, numAgenCC, valueCC);
        System.out.println("Saldo: "+x.consultarSaldoContaCorrente(numCC));
        x.realizarDepositoContaCorrente(numCC, 2500);
        x.consultaContas(cpf);
        System.out.println("Saldo: "+x.consultarSaldoContaCorrente(numCC));  
        
        x.criarContaLimitada(cpf, numCCL, numAgenCCL, 
               valueCCL, limCCL, tdJuroCCL);
        
        x.criarContaPoupança(cpf, numP, numAgenP, 
              valueP, ddIP, mmIP, aaaaIP, tdCorrP);
         
        x.criarPapagaio(cpf, numPP, numAgenPP, valuePP,
                nEmpPP, val2PP, tdJuroPP, ddCriaPP, ddVencPP);
        
        x.criarBitcoin(cpf, numB, numAgenB, valueB, Bitcoin);
         
        
        x.criarEthereum(cpf, numE, numAgenE, valueE, Ethereum);
        
        
        
        
        x.ligarConta2ClienteContaCorrente(numCC);
        x.ligarConta2Bitcoin(numB);
        x.ligarConta2ClienteContaLimitada(numCCL);
        x.ligarConta2Ethereum(numE);
        x.ligarConta2Poupança(cpf, numP);
        x.ligarEmprestimo(nEmpPP);
        
        x.consultaContas(cpf);
        
//        //Conta Corrente Limitada        
//        x.criarContaLimitada(cpf, numCCL, numAgenCCL, 
//                valueCCL, limCCL, tdJuroCCL);
//        x.consultarSaldoContaLimitada(numCCL);
//        System.out.println(x.consultarSaldoContaLimitada(numCCL));
//        x.realizarDepositoContaLimitada(numCCL, 12000);
//        System.out.println(x.consultarSaldoContaLimitada(numCCL));
//        System.out.println(x.aumentarLimiteContaLimitada(numCCL, 1.08));
        
        
//        //Conta Poupança        
//        x.criarContaPoupança(cpf, numP, numAgenP, 
//                valueP, ddIP, mmIP, aaaaIP, tdCorrP);
//        System.out.println(x.consultarSaldoPoupança(numP));
//        x.realizarDepositoSaldoPoupança(numP, 2222);
//        System.out.println(x.consultarSaldoPoupança(numP));
//        x.corrigirSaldoPoupança(numP, ddP, mmP, aaaaP);
//        System.out.println(x.consultarSaldoPoupança(numP));
 
//        //Conta Papagaio        
//        x.criarPapagaio(cpf, numPP, numAgenPP, valuePP,
//                nEmpPP, val2PP, tdJuroPP, ddCriaPP, ddVencPP);
//        System.out.println(x.consultarSaldoEmprestimo(nEmpPP));
//        System.out.println(x.calcularValorAPagarEmprestimo(nEmpPP));
//        x.realizarQuitaçaoEmprestimo(nEmpPP);
//        System.out.println(x.consultarSaldoEmprestimo(nEmpPP));
//        
        
//        //Conta Bitcoin        
//        x.criarBitcoin(cpf, numB, numAgenB, valueB, Bitcoin);
//        System.out.println(x.consultarSaldoBitcoin(numB));
//        System.out.println(x.consultarSaldoEmReaisBitcoin(numB, Bitcoin));
//    
        
//        //Conta Ethereum        
//        x.criarEthereum(cpf, numE, numAgenE, valueE, Ethereum);
//        System.out.println(x.consultarSaldoEthereum(numE));
//        System.out.println(x.consultarSaldoEmReaisEthereum(numE, Ethereum));
//    
        
    } 
    
    
    
}
