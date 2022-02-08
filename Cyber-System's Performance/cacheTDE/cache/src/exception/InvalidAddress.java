package exception;

public class InvalidAddress extends Exception{

    private final int address;

    public InvalidAddress(){
        super();
        this.address = -1;
    }

    public InvalidAddress(int address){
        super("Invalid Address");
        this.address = address;
    }

    public int getAddress(){
        return this.address;
    }

}