
package ra03jobpoo;


public interface Cliente {
      
   
    public Cliente criar
        (String c, String n, String sn, String end, String em, String tel);  
    

    
    ///// Função de: Cliente          100%
    public void setTelefone(String tel);
    
    public void setEndereço(String end);
    
    public void setEmail(String em);
    
    public void setSobrenome(String sn);
    
    public String getCPF();
    
    public String getNome();
    
    public String getSobrenome();
    
    public String getEndereço();
    
    public String getEmail();
    
    public String getTelefone();
    
    public String consultar();
    
    public void atualizarTelefone(String tel);
    
    public void atualizarEndereço(String end);
    
    public void atualizarEmail(String em);
 
    public void atualizarSobrenome(String sn);
    
    public void atualizarEstado(double valor , String nome);
    
    
}
