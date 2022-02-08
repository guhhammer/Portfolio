package memory;

import exception.InvalidAddress;

public class Cache extends MemoryABS{

    private CacheLine[] memory;
    private int size = 0, Kpalavras = 64;
    private RAM ram;

    private void CheckAddress(int address) throws InvalidAddress {
        if (address < 0 || address >= this.size){ throw new InvalidAddress(address); }
    }

    private int getw(int address){
        String binaryString = Integer.toBinaryString(address), out = "";

        while(binaryString.length()< 24){ binaryString = "0"+binaryString; }

        for(int i = 18; i < 24; i++) { out += binaryString.charAt(i); }

        return Integer.parseInt(out, 2);

    }

    private int getr(int address){
        String binaryString = Integer.toBinaryString(address), out = "";

        while(binaryString.length()< 24){  binaryString = "0"+binaryString; }

        for(int i = 11; i < 18; i++) { out += binaryString.charAt(i); }

        return Integer.parseInt(out, 2);

    }

    private int gett(int address){
        String binaryString = Integer.toBinaryString(address), out = "";

        while(binaryString.length()< 24){  binaryString = "0"+binaryString; }

        for(int i = 0; i < 11; i++) {  out += binaryString.charAt(i); }

        return Integer.parseInt(out, 2);

    }

    private int gets(int address){
        String binaryString = Integer.toBinaryString(address), out = "";

        while(binaryString.length()< 24){  binaryString = "0"+binaryString; }

        for(int i = 0; i < 18; i++) {  out += binaryString.charAt(i); }

        return Integer.parseInt(out, 2);

    }

    public Cache(int size, RAM ram){
        this.size = size/this.Kpalavras; // Quantidade de Cachelines.
        this.memory = new CacheLine[size];
        this.ram = ram;

        for(int i = 0; i < this.memory.length; i++){
            this.memory[i] = new CacheLine(this.Kpalavras, i);
        } // Inicializa os Cachelines com o Kpalavras e "t".

        // "t" é inicializado como se a RAM não tivesse nenhum valor.

    }

    public int Size() { return this.size; }

    public int Get(int address) throws InvalidAddress {
        int r = getr(address), t = gett(address), w = getw(address), tlinha = this.memory[r].getTag();

        if(tlinha != t){ //Cache Miss

            // Se a cache line foi alterada, ela é copiada para a memória principal.
            if(this.memory[r].getFlag()){
                for(int i = 0; i < this.Kpalavras;i++){
                    int posicao = Integer.parseInt(Integer.toBinaryString(tlinha)+ Integer.toBinaryString(r)+ Integer.toBinaryString(i), 2);
                    this.ram.Set(posicao, this.memory[r].Get(i));
                } // Copia o CacheLine inteiro para a memória.
            }

            int s = gets(address); // Pega o valor da memória principal, salva e envia o valor certo.
            this.memory[r].setTag(gett(this.ram.Get(s))); // Atualiza "t".

            for(int i = 0; i < this.Kpalavras;i++){
                this.memory[r].Set(i,this.ram.Get(s+i));
            } // Copia da memória para o CacheLine.

            return this.memory[r].Get(w);

        }

        return this.memory[r].Get(w); // Cache Hit.

    }

    public void Set(int address, int word) throws InvalidAddress {
        int r = getr(address), t = gett(address), w = getw(address), tlinha = this.memory[r].getTag();

        if(tlinha == t){ // Cache Hit.
            this.memory[r].Set(w,word);
        }
        else{ // Cache Miss.

            // Se a cache line foi alterada, ela é copiada para a memória principal.
            if(this.memory[r].getFlag()){

                for(int i = 0; i < this.Kpalavras;i++){
                    int posicao = Integer.parseInt(Integer.toBinaryString(tlinha) + Integer.toBinaryString(r) + Integer.toBinaryString(i), 2);
                    this.ram.Set(posicao, this.memory[r].Get(i));
                } // Copia o CacheLine inteiro para a memória.

            }

            int s = gets(address); // Pega Valor da memória principal e salva no Cache.
            this.memory[r].setTag(gett(this.ram.Get(s))); // Atualiza "t".

            for(int i = 0; i < this.Kpalavras;i++){
                this.memory[r].Set(i,this.ram.Get(s+i));
            } // Copia da memória para o CacheLine.

            this.memory[r].Set(w,word);

        }

    }

}