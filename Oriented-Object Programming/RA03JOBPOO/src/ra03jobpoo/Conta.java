
package ra03jobpoo;

 
public class Conta implements Emprestimo, ContaCorrenteLimitada,
        ContaCorrente, Poupança, Papagaio, Cliente, Criptomoeda,
        Bitcoin, Ethereum { 
    
    ////////////////////////////////////////////////////////////Conta
    private String numeroConta;
    private String numeroDaAgencia;
    private double valorConta;
    
    public Conta(String num, String numAgen, double value){
        this.numeroConta = num;
        this.numeroDaAgencia = numAgen;
        this.valorConta = value;
    } 
    // Construtor Conta, ContaCorrente, Criptomoeda
    
    
    ///Métodos get e set:
    
    public double getValorConta(){return valorConta;}
    
    public void setValorConta(double valor){this.valorConta = valor;}
   
    ///Métodos get e set.
   
    
    public double consultaSaldo(){return valorConta;}
    
    public void realizarDeposito(double x){
        double soma = valorConta + x;
        setValorConta(soma);
    }
    
    public void realizarSaque(double x){
        double soma = valorConta - x;
        setValorConta(soma);
    }
    
    
    
    public void ligarConta2Cliente(Cliente c){ /*no need*/ } 
    
    
    ///////////////////////////////////////////////////////////////Conta
    //////////////
    //////////////
    //////////////
    //////////////
    //////////////
    //////////////
    //////////////    
    ////////////////////////////////////////////////////////////Empréstimo
    
    private String numeroEmp; 
    private double valor;  
    private int taxaDeJuros;  //se for juros compostos, não é final
    private String dataDeCriaçao; 
    private String dataDeVencimento; 
    

    public Conta 
            (String num, String numAgen, double value, String nEmp, double val2, 
                                   int tdJuro, String ddCria, String ddVenc){
        this.numeroConta = num;
        this.numeroDaAgencia = numAgen;
        this.valorConta = value;
        this.numeroEmp = nEmp;
        this.valor = val2;
        this.taxaDeJuros = tdJuro;
        this.dataDeCriaçao = ddCria;
        this.dataDeVencimento = ddVenc;
    } 
    // Construtor Empréstimo, papagaio
    
    //Utilizar caso queira definir como juros compostos.
    public int setTaxaDeJuros(int tdj){return this.taxaDeJuros = tdj;}    
    
    ///// Função de: Emprestimo     75%
    public void ligar(Cliente c, Conta cc){ /* no need */ }
    
    public double calcularValorAPagar(){return valor*(1+taxaDeJuros);}
   
    public double realizarQuitaçao(){
        double block = getValorConta();
        block = block - (valor*(1+taxaDeJuros));
        return block;}
   
    public double consultarSaldo(){return getValorConta();}
    
    ////////////////////////////////////////////////////////////Empréstimo 
    //////////////
    //////////////
    //////////////
    //////////////
    /////////////////////////////////////////////////Conta Corrente Limitada
    
    private double limite;
    private double taxaDeJuro;
    
    public Conta
        (String num, String numAgen, double value,double lim, double tdJuro){
            this.numeroConta = num;
            this.numeroDaAgencia = numAgen;
            this.valorConta = value;
            this.limite = lim;
            this.taxaDeJuro = tdJuro;
    }
        // Construtor ContaCorrenteLimitada
    
        
    public ContaCorrenteLimitada criar
        (String num, String numAgen, double value,double lim, double tdJuro){
            return (ContaCorrenteLimitada) new Conta(num, numAgen, value, lim, tdJuro);
    }
    
    
    ///// Função de: Conta Corrente Limitada    100%
    public double setTaxaJuroCCL(double num){return this.taxaDeJuro = num;}
    
    public double getTaxaJuroCCl(){return taxaDeJuro;}
            
    public double getLimite(){return limite;}
    public double aumentarLimite(double auml){
        this.limite = limite * (  auml);
        return this.limite = limite * (  auml);}  
    
    public double reduzirLimite(double redl){
        this.limite = limite * ( 1 - redl);
        return this.limite = limite * ( 1 - redl);} 
    ///// Função de: Conta Corrente Limitada
    
    /////////////////////////////////////////////////Conta Corrente Limitada
    //////////////
    //////////////
    //////////////
    //////////////
    ///////////////////////////////////////////////Conta Corrente
    
    public ContaCorrente criar(String num, String numAgen, double value){
        return (ContaCorrente) new Conta(num, numAgen, value);
    }
   
    ///////////////////////////////////////////////Conta Corrente
    //////////////
    //////////////
    //////////////
    /////////////////////////////////////////////////Poupança
    
    private String dataDeAniversario;
    private int dia;
    private int mes;
    private int ano;
    private double taxaDeCorreçao;

    public Conta
        (String num, String numAgen, double value, 
                                    int dd, int mm,int aaaa, double tdCorr){
            this.numeroConta = num;
            this.numeroDaAgencia = numAgen;
            this.valorConta = value;
            this.dia = dd;
            this.mes = mm;
            this.ano = aaaa;
            this.taxaDeCorreçao = tdCorr;
            this.dataDeAniversario = dd+"/"+mm+"/"+aaaa;
    }  
        // Construtor Poupança
    
        
    public Poupança criar
        (String num, String numAgen, double value,
                                  int dd, int mm,int aaaa, double tdCorr){
            return (Poupança) new Conta(num, numAgen, value, dd, mm, aaaa, tdCorr);
    }

    /// Métodos get e set:
        
    public int getDIA(){return dia;}
    
    public int getMES(){return mes;}        
            
    public int getANO(){return ano;}

    public String getDataDeAniversario(){return dataDeAniversario;}
    
    
    //dia, mes, ano atual: assinatura
    public int timeCalculator(int ddA, int mmA, int aaaaA){
        //mI -> mes inicial e mA -> mes atual
        int ddI = dia;
        int mmI = mes;
        int aaaaI = ano;
        int somaDIA;
        int somaMES = 0;
        int somaANO;
        double somatorio;
        
        if(ddI == ddA){somaDIA = 0;}
        else if(ddI < ddA){somaDIA = (ddA-ddI);}
        else if(ddI > ddA){somaDIA = Math.abs(ddA-30)+(30-ddI);}
        else{somaDIA = 0;}
        
        if(mmI == mmA){somaMES = 0;}
        else if(mmI < mmA){somaMES = mmA - mmI;}
        else if(mmI > mmA){somaMES = Math.abs(mmA-12)+(12-mmI);}
        else{somaDIA = 0;}
        
        if(aaaaI == aaaaA){somaANO = 0;}
        else if(aaaaI < aaaaA){somaANO = aaaaA-aaaaI;}
        else{somaANO = 0;}//impossivel voltar no tempo
        
        somatorio = (somaDIA + (somaMES*30) + (somaANO*12))/30;
        
        return ((int)somatorio);
        
    }
    
    /// Métodos get e set.
    
    
    ///// Função de: Poupança    100%
    public double corrigirSaldo(int dd, int mm, int aaaa){
        return getValorConta()*(taxaDeCorreçao*timeCalculator(dd,mm,aaaa));  
    }
    ///// Função de: Poupança.
    /////////////////////////////////////////////////Poupança
    //////////////
    //////////////
    //////////////
    //////////////
    /////////////////////////////////////////Papagaio
    public Papagaio Criar
        (String num, String numAgen, double value, String nEmp,double val2, 
                                  int tdJuro, String ddCria, String ddVenc){
            return (Papagaio) new Conta(num, numAgen, value, nEmp, val2, 
                                tdJuro, ddCria, ddVenc);
    }
    /////////////////////////////////////////Papagaio
    //////////////
    //////////////
    //////////////
    //////////////
    /////////////////////////////////////////Cliente
    private String nome;
    private String sobrenome;
    private String CPF;
    private String endereço;
    private String email;
    private String telefone;
    
    private String correnteValor = "nenhum valor atribuido, conta não iniciada";
    private String limitadaValor = "nenhum valor atribuido, conta não iniciada";
    private String emprestimoValor = "nenhum valor atribuido, conta não iniciada";
    private String poupançaValor = "nenhum valor atribuido, conta não iniciada.";
    private String bitcoinValor = "nenhum valor atribuido, conta não iniciada.";
    private String ethereumValor = "nenhum valor atribuido, conta não iniciada.";
    // Cliente
    
    public Conta
        (String c, String n, String sn, String end, String em, String tel){
            this.CPF = c;
            this.nome = n;
            this.sobrenome = sn;
            this.endereço = end;
            this.email = em;
            this.telefone = tel;
    } 
    // Construtor Cliente
      
    public Cliente criar
        (String c, String n, String sn, String end, String em, String tel){
            return (Cliente) new Conta(c, n, sn, end, em, tel);
    } 
    // Construtor Cliente
    
        
    ///// Função de: Cliente          100%
    public void setTelefone(String tel){ this.telefone = tel;}
    
    public void setEndereço(String end){ this.endereço = end;}
    
    public void setEmail(String em){ this.email = em;}
    
    public void setSobrenome(String sn){ this.sobrenome = sn;}
    
    public void setEstadoCorrente(double valor){ this.correnteValor = " saldo: "+valor;}
    
    public void setEstadoLimitada(double valor){ this.limitadaValor = " saldo: "+valor;}
    
    public void setEstadoEmprestimo(double valor){ this.emprestimoValor = " saldo: "+valor;}
    
    public void setEstadoPoupança(double valor){ this.poupançaValor = " saldo: "+valor;}
    
    public void setEstadoBitcoin(double valor){ this.bitcoinValor = " saldo: "+valor;}
    
    public void setEstadoEthereum(double valor){ this.ethereumValor = " saldo: "+valor;}
    
    public String getCPF(){return CPF;}
    
    public String getNome(){return nome;}
    
    public String getSobrenome(){return sobrenome;}
    
    public String getEndereço(){return endereço;}
    
    public String getEmail(){return email;}
    
    public String getTelefone(){return telefone;}
    
    public String getEstCorrente(){return correnteValor;}
    
    public String getEstLimitada(){return limitadaValor;}
    
    public String getEstEmprestimo(){return emprestimoValor;}
    
    public String getEstPoupança(){return poupançaValor;}
    
    public String getEstBitcoin(){return bitcoinValor;}
    
    public String getEstEthereum(){return ethereumValor;}
    
    public String consultar(){
        return "CPF: "+getCPF()+
                "\nNome: "+getNome()+
                "\nSobrenome: "+getSobrenome()+
                "\nEndereço: "+getEndereço()+
                "\nEmail: "+getEmail()+
                "\nTelefone: "+getTelefone()+
                "\n";
    }
    
    public void atualizarTelefone(String tel){
        this.setTelefone(tel);
    }
    
    public void atualizarEndereço(String end){
        this.setEndereço(end);
    }
    
    public void atualizarEmail(String em){
        this.setEmail(em);
    }
 
    public void atualizarSobrenome(String sn){
        this.setSobrenome(sn);
    }
    
    public void atualizarEstado(double valor , String nome){
        if(nome == "corrente"){
            this.setEstadoCorrente(valor);
        }
        else if(nome == "limitada"){
            this.setEstadoLimitada(valor);
        }
        else if(nome == "emprestimo"){
            this.setEstadoEmprestimo(valor);
        }
        else if(nome == "poupança"){
            this.setEstadoPoupança(valor);
        }
        else if(nome == "bitcoin"){
            this.setEstadoBitcoin(valor);
        }
        else if(nome == "ethereum"){
            this.setEstadoEthereum(valor);
        }
        
    }
    ///// Função de: Cliente
    
   
    //////////////////////////////////////////////////////Criptomoeda
    
    private double Bitvalue;
    private float Ethvalue;
    
     
    public Conta(String cpf, String num, String numAgen, double value, double Bitv){
        this.CPF = cpf;
        this.numeroConta = num;
        this.numeroDaAgencia = numAgen;
        this.valorConta = value;
        this.Bitvalue = Bitv;
    } 
    
    public Bitcoin Criar
        (String cpf, String num, String numAgen, double value, double Bitv){
            return (Bitcoin) new Conta(cpf, num, numAgen, value, Bitv);
    }
        // Construtor Bitcoin
    
    public Conta(String cpf, String num, String numAgen, double value, float Ethv){
        this.CPF = cpf;
        this.numeroConta = num;
        this.numeroDaAgencia = numAgen;
        this.valorConta = value;
        this.Ethvalue = Ethv;
    } 
    
    public Ethereum Criar
        (String cpf, String num, String numAgen, double value, float Ethv){
            return (Ethereum) new Conta(cpf, num, numAgen, value, Ethv);
    } 
        // Construtor Ethereum
    
    ////////////////////////////////////Bitcoin, Ethereum
    
    public double converterSaldo(double coin, double x){
        return x*coin;    // coin: valor de um bit 
    }
   
    public double getValue(){return valorConta;}
    
    
    public double consultaSaldoEmReais(double coin){
        return converterSaldo(coin, getValue());
    }

    
    
    //////////////////////////////////////////////////////Criptomoeda   
    
    
   
}
