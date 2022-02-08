package Semana15;

import java.util.ArrayList;

    /*
    *
    *       ESTA CLASSE É UTILIZADA PARA FACILITAR O ARMAZENAMENTO DE BUSCAS FEITAS POR CLIENTES, BEM COMO
    *       A FÁCIL IDENTIFICAÇÃO DE RESULTADOS DA BUSCA.
    *
    */

public class History {

    public String data, UID, textoDaBusca, store, produtosRetornados;

    public History(String data, String UID, String textoDaBusca, String store, String produtosRetornados){

        this.data = data;
        this.UID = UID;
        this.textoDaBusca = textoDaBusca;
        this.store = store;
        this.produtosRetornados = produtosRetornados;

    }

    public String getData() {
        return data;
    }

    public String getProdutosRetornados() {
        return produtosRetornados;
    }

    public String getTextoDaBusca() {
        return textoDaBusca;
    }

    public String getUID() {
        return UID;
    }

    public String getStore() {
        return store;
    }

    public String toString(){

        return this.getData()+" "+this.getUID()+" "+this.getTextoDaBusca()+" "+this.getStore()+" "+this.getProdutosRetornados();

    }

    private String formatProdutos(){

        char[] s = this.getProdutosRetornados().toCharArray();
        String aux = "";
        ArrayList<String[]> strs = new ArrayList<>();
        for(int i = 2; i < s.length-2; i++){

            if(s[i] == '{'){

            }
            else if(s[i] == '}'){
                String[] auxx = aux.trim().split("__SEPARATOR__");
                strs.add(new String[]{auxx[0].trim(), auxx[1].trim()});
                aux = "";
            }
            else{
                aux += s[i];
            }


        }

        String ret = "";
        for(String[] sel : strs){

            ret += "\tTítulo: "+sel[0]+"\n\tPreço: "+sel[1]+"\n----------\n";

        }

        return ret;

    }

    public String formatBusca(){
        return  "\nLoja: "+getStore()+"\n"+formatProdutos()+"\n";
    }

    public String formatHistory(){

        return "\nData:  "+this.getData()+"\nUID:  "+this.getUID()+"\ntextoDaBusca:  "+this.getTextoDaBusca()
                +"\nLoja:  "+this.getStore()+"\nProdutos Retornados:  \n"+this.formatProdutos();

    }

}
