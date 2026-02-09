
package ra03jobpoo;

import java.util.HashMap;

public class Banco extends Conta {
   
 
    HashMap<String, Cliente> cliente_hash = new HashMap<>();
    
    HashMap<String, ContaCorrente> corrente_hash = new HashMap<>();
    HashMap<String, Double> ligCorrente_hash = new HashMap<>();
    
    HashMap<String, ContaCorrenteLimitada> limitada_hash = new HashMap<>();
    HashMap<String, Double> ligLimitada_hash = new HashMap<>();
    
    HashMap<String, Poupança> poupança_hash = new HashMap<>();
    HashMap<String, Double> ligPoupança_hash = new HashMap<>();
    
    HashMap<String, Papagaio> papagaio_hash = new HashMap<>();
    HashMap<String, Double> ligPapagaio_hash = new HashMap<>(); 
    
    HashMap<String, Bitcoin> bitcoin_hash = new HashMap<>();
    HashMap<String, Double> ligBitcoin_hash = new HashMap<>(); 
    
    HashMap<String, Ethereum> ethereum_hash  = new HashMap<>();
    HashMap<String, Double> ligEthereum_hash = new HashMap<>(); 
    
    
    //////////////////////////////////////////////////////////// Construtores
    public Banco(String num, String numAgen, double value) {
        super(num, numAgen, value);
    }
     // Construtor ContaCorrente
    
    public Banco
            (String num, String numAgen, double value, String nEmp, double val2, 
                                   int tdJuro, String ddCria, String ddVenc){
       super( num, numAgen, value, nEmp, val2, 
                      tdJuro, ddCria, ddVenc);
    } 
    // Construtor Empréstimo, papagaio
             
    public Banco
        (String num, String numAgen, double value,double lim, double tdJuro){
            super(num, numAgen, value, lim, tdJuro);
        } 
    // Construtor ContaCorrenteLimitada        
        
    public Banco
        (String num, String numAgen, double value, 
                                    int dd, int mm,int aaaa, double tdCorr){
            super(num, numAgen, value, dd, mm, aaaa, tdCorr);
        } 
    // Construtor Poupança
       
    public Banco
        (String c, String n, String sn, String end, String em, String tel){
            super(c, n, sn, end, em, tel);
    } 
    // Construtor Cliente
        
    public Banco(String cpf, String num, String numAgen, double value, double Bitv){
        super(cpf, num, numAgen, value, Bitv);
    }
    // Construtor Bitcoin    
    
    public Banco(String cpf, String num, String numAgen, double value, float Ethv){
        super(cpf, num, numAgen, value, Ethv);
    } 
    // Construtor Ethereum    
    
    //////////////////////////////////////////////////////////// Construtores    
    
    
    //100%
    ///////////////////////////////////////////////CLIENTE
    public Cliente criarCliente
        (String c, String n, String sn, String end, String em, String tel){ 
            
            Banco x = new Banco(c, n, sn, end, em, tel);
            Cliente y = new Conta(c, n, sn, end, em, tel);
            y.criar(c, n, sn, end, em, tel);
            x.criar(c, n, sn, end, em, tel);
            cliente_hash.put(c, y);
            return x;
    }
    
    public String consultarCliente(String cpf){
        return cliente_hash.get(cpf).consultar();
    }
    
    public void atualizarClienteEndereço(String cpf, String end){
        cliente_hash.get(cpf).atualizarEndereço(end);   
    }
    
    public void atualizarClienteTelefone(String cpf, String tel){
        cliente_hash.get(cpf).atualizarTelefone(tel);
    }
    
    public void atualizarClienteEmail(String cpf, String em){
        cliente_hash.get(cpf).atualizarEmail(em);
    }
    
    public void atualizarClienteSobrenome(String cpf, String sn){
        cliente_hash.get(cpf).atualizarSobrenome(sn);
    }
    ///////////////////////////////////////////////CLIENTE
    
    
    //100%
    ///////////////////////////////////////////////CONTACORRENTE
    public ContaCorrente criarContaCorrente
        (String cpf, String num, String numAgen, double value){
            Banco x = new Banco(num, numAgen, value);
            ContaCorrente y = new Conta(num, numAgen, value);
            y.criar(num, numAgen, value);
            corrente_hash.put(num, y);
            return x;
    }      // mudar os tipos de banco para os respectivos
    
    public double consultarSaldoContaCorrente(String num){
        return corrente_hash.get(num).consultaSaldo();
    }
    
    public void realizarDepositoContaCorrente(String num, double x){
        corrente_hash.get(num).realizarDeposito(x);
    }
    
    public void realizarSaqueContaCorrente(String num, double x){
        corrente_hash.get(num).realizarSaque(x);
    }
    
    public void ligarConta2ClienteContaCorrente(String num){ 
        double x = corrente_hash.get(num).consultaSaldo();
        ligCorrente_hash.put(num, x);
    }
    
    ///////////////////////////////////////////////CONTACORRENTE
    
    
    //100% 
    ///////////////////////////////////////////////CONTALIMITADA
    public ContaCorrenteLimitada criarContaLimitada
        (String cpf, String num, String numAgen,double value,double lim, double tdJuro){
            Banco x = new Banco(num, numAgen, value, lim, tdJuro);
            x.criar(num, numAgen, value, lim, tdJuro);
            limitada_hash.put(num, x);
            return x;
    }
    
    public double consultarSaldoContaLimitada(String num){
        return limitada_hash.get(num).consultaSaldo();
    }
    
    public double juros(String num){
        return limitada_hash.get(num).getTaxaJuroCCl();
    }
    
    public double LIM(String num){
        return limitada_hash.get(num).getLimite();
    }
    
    public void realizarDepositoContaLimitada(String num, double x){
        if(limitada_hash.get(num).consultaSaldo() > 0){
            limitada_hash.get(num).realizarDeposito(x*juros(num));
        }
        
        else {
            limitada_hash.get(num).realizarDeposito(x);
        }
    }
    
    public void realizarSaqueContaLimitada(String num, double x){
        if(limitada_hash.get(num).getLimite() < x){
        limitada_hash.get(num).realizarSaque(getLimite());
        }
        else{
            limitada_hash.get(num).realizarSaque(x);
        }
    }
    
    public void ligarConta2ClienteContaLimitada(String num){ 
        double x = limitada_hash.get(num).consultaSaldo();
        ligLimitada_hash.put(num, x);
    }
    
    public double aumentarLimiteContaLimitada(String num, double aum){ 
        return limitada_hash.get(num).aumentarLimite(aum);
    }
    
    public double reduzirLimiteContaLimitada(String num, double red){
        return limitada_hash.get(num).reduzirLimite(red);
    }
    ///////////////////////////////////////////////CONTALIMITADA
    
    
    //100%
    ///////////////////////////////////////////////POUPANÇA
    public Poupança criarContaPoupança
        (String cpf, String num, String numAgen, double value,int dd, int mm,int aaaa, 
                                                            double tdCorr){
            Banco x = new Banco(num, numAgen, value, dd, mm, aaaa, tdCorr);
            x.criar(num, numAgen, value, dd, mm, aaaa, tdCorr);
            poupança_hash.put(num, x);
            return x;
    }
    
    public double consultarSaldoPoupança(String num){
        return poupança_hash.get(num).consultaSaldo();
    }
    
    public void realizarDepositoSaldoPoupança(String num, double x){
        poupança_hash.get(num).realizarDeposito(x);
    }
    
    public void realizarSaldoPoupança(String num, double x){
        poupança_hash.get(num).realizarSaque(x);
    }
    
    //public void ligarConta2ClientePoupança(){ /*no need*/ }
    
    public void corrigirSaldoPoupança(String num, int dd, int mm, int aaaa){
        poupança_hash.get(num).corrigirSaldo(dd, mm, aaaa);
    }
    
    public void ligarConta2Poupança(String cpf, String num){ 
        double x = poupança_hash.get(num).consultaSaldo();
        ligPoupança_hash.put(num, x);
        
    }
    ///////////////////////////////////////////////POUPANÇA
    
    
    
    
    
    //100% 
    ///////////////////////////////////////////////PAPAGAIO
    public Papagaio criarPapagaio
        (String cpf, String num, String numAgen, double value, String nEmp,
                     double val2, int tdJuro, String ddCria, String ddVenc){
            Banco x = new Banco(num, numAgen, value, nEmp, val2, tdJuro, 
                                                                ddCria, ddVenc);
            x.Criar(num, numAgen, value, nEmp, val2, tdJuro, ddCria, ddVenc);
            papagaio_hash.put(nEmp, x);
            return x;
    }
    
    public void ligarEmprestimo(String nEmp){ 
        double x = papagaio_hash.get(nEmp).consultarSaldo();
        ligPapagaio_hash.put(nEmp, x);
        
    }
    
    public double calcularValorAPagarEmprestimo(String nEmp){
        return papagaio_hash.get(nEmp).calcularValorAPagar();
    }
    
    public double realizarQuitaçaoEmprestimo(String nEmp){
        return papagaio_hash.get(nEmp).realizarQuitaçao();
    }
    
    public double consultarSaldoEmprestimo(String nEmp){
        return papagaio_hash.get(nEmp).consultarSaldo();
    }
    ///////////////////////////////////////////////PAPAGAIO



    ///////////////////////////////////////////////   Bitcoin
    public Bitcoin criarBitcoin
        (String cpf, String num, String numAgen, double value, double Bitv){
            Banco x = new Banco(cpf, num, numAgen, value, Bitv);
            x.Criar(cpf, num, numAgen, value, Bitv);
            bitcoin_hash.put(num, x);   ///ver se usa num ou cpf
            return x;
    }
    
    public double consultarSaldoBitcoin(String num){
        return bitcoin_hash.get(num).consultaSaldo();
    }
    
    public double consultarSaldoEmReaisBitcoin(String num, double coin){
        return bitcoin_hash.get(num).consultaSaldoEmReais(coin);
    }
    
    public void realizarDepositoBitcoin(String num, double x){
        bitcoin_hash.get(num).realizarDeposito(x);
    }
    
    public void realizarSaqueBitcoin(String num, double x){
        bitcoin_hash.get(num).realizarSaque(x);
    }
    
    public void ligarConta2Bitcoin(String num){ 
        double x = bitcoin_hash.get(num).consultaSaldo();
        ligBitcoin_hash.put(num, x); 
    }
    ///////////////////////////////////////////////   Bitcoin
    
    
    
    
    
    ///////////////////////////////////////////////   Ethereum
    public Bitcoin criarEthereum
        (String cpf, String num, String numAgen, double value, float Ethv){
            Banco x = new Banco(cpf, num, numAgen, value, Ethv);
            x.Criar(cpf, num, numAgen, value, Ethv);
            ethereum_hash.put(num, x);   ///ver se usa num ou cpf
            return x;
    }
    
    public double consultarSaldoEthereum(String num){
        return ethereum_hash.get(num).consultaSaldo();
    }
    
    public double consultarSaldoEmReaisEthereum(String num, double coin){
        return ethereum_hash.get(num).consultaSaldoEmReais(coin);
    }
    
    public void realizarDepositoEthereum(String num, double x){
        ethereum_hash.get(num).realizarDeposito(x);
    }
    
    public void realizarSaqueContaEthereum(String num, double x){
        ethereum_hash.get(num).realizarSaque(x);
    }
    
    public void ligarConta2Ethereum(String num){ 
        double x = ethereum_hash.get(num).consultaSaldo();
        ligEthereum_hash.put(num, x);  
    }
    ///////////////////////////////////////////////   Bitcoin
 
    
    public void consultaContas(String cpf){
        
        int a = 0;
        
        if(a == 0){
            System.out.println("\n  Cliente: "+cliente_hash.get(cpf).getNome());
            
            for (String name: this.ligCorrente_hash.keySet()){
                System.out.println("    Contas Corrente: \n"+
                                   "      Número da conta: "+name+", valor: "+ligCorrente_hash.values()+"; \n");
                a += 1;
            }
            
            for (String name: this.ligLimitada_hash.keySet()){
                System.out.println("    Contas Limitada: \n"+
                                   "      Número da conta: "+name+", valor: "+ligLimitada_hash.values()+"; \n");
                a += 1;
            }
            
            for (String name: this.ligPoupança_hash.keySet()){
                System.out.println("    Contas Poupança: \n"+
                                   "      Número da conta: "+name+", valor: "+ligPoupança_hash.values()+"; \n");
                a += 1;
            }
            
            for (String name: this.ligPapagaio_hash.keySet()){
                System.out.println("    Contas Papagaio: \n"+
                                   "      Número da conta: "+name+", valor: "+ligPapagaio_hash.values()+"; \n");
                a += 1;
            }
            for (String name: this.ligBitcoin_hash.keySet()){
                System.out.println("    Contas Bitcoin: \n"+
                                   "      Número da conta: "+name+", valor: "+ligBitcoin_hash.values()+"; \n");
                a += 1;
            }
            for (String name: this.ligEthereum_hash.keySet()){
                System.out.println("    Contas Ethereum: \n"+
                                   "      Número da conta: "+name+", valor: "+ligEthereum_hash.values()+"; \n");
                a += 1;
            }
            if(a != 0){
            System.out.println("Fim das contas");
            }
            else{System.out.println("  Nenhuma conta foi ligada ao cliente!\n");}
       }
   }
   
    
    
    
    
    
    
}