package Semana15;

    /*
    *
    *       CLASSE USADA PARA FACILITAR OPERRAÇÕES DE INSERÇÃO E VISUALIZAÇÃO DE PRODUTOS.
    *
    */


public class Product {

    private String name;
    private float price;

    public Product(String name, float price){
        this.name = name;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

}

