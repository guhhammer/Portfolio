


import java.util.Scanner;

public class Stack {

    // team: Gustavo Hammerschmidt, João Capoani, Davi Leal.
    
    int topo;
    int[] data;
    int max;
    
    // constructor of Stack, sets the size. 
    public Stack(int m){
        this.topo = -1;
        this.max = m;
        this.data = new int[max];
    }
    
    
    // Begin: exercise 1.                                     -----------------
     
    // check whether it is empty.
    public String isEmpty(){
        if(topo == -1){ return "Stack isEmpty";}
        else{ return "Stack is not empty.";}
    }
    
    // check whether it is full.
    public String overflow(){
        if(topo == (max-1)){ return "Stack full!";}
        else{ return "Stack not full!";}
    }
    
    // pushes an element onto the stack.
    public void push(int n){
        if(topo != (max-1)){
            this.topo++;
            this.data[topo] = n;
        }
        else{
            System.out.println("Stack isFull! \nValue not pushed.\n");
        }
    }
    
    // pops the top element of the stack.
    public int pop(){
        if(topo != -1){
            int aux = topo;
            topo--;
            return data[aux];
        }
        else{ System.out.println("Stack is empty!");
        return 0;}
    }
    
    // peeks at the top element
    public int topo(){
        return data[topo];
    }
    
    // shows the examples and their solutions. 
    public static void examples1(){
        
        Stack x = new Stack(5);
        System.out.println("Examples, exercise 1:");
        x.push(10);
        System.out.println("a) "+x.topo()+" .emp");
        
        x.push(-2); 
        System.out.println("b) "+x.topo()+" .emp");
        
        x.push(16);
        System.out.println("c) "+x.topo()+" .emp");
        
        System.out.println("d) "+x.topo()+" .emp");
        
        System.out.println("e) "+x.pop()+" .des");
        
        x.push(40);
        System.out.println("f) "+x.topo()+" .emp");
        
        System.out.println("g) "+x.pop()+" .des");
        System.out.println("h) "+x.pop()+" .des");
        System.out.println("i) "+x.pop()+" .des");
        
        x.push(5);
        System.out.println("j) "+x.topo()+" .emp");
        
    }
    
    // End: exercise 1.                                       -----------------
    
    
    // Begin: exercise 2.                                     -----------------
    
    // check whether it is empty.
    public String isEmptyEx2(){
        if(topo == -1){ return "Stack isEmpty";}
        else{ return "Stack is not empty.";}
    }
    
    // check whether it is full.
    public String isFullEx2(){
        if(topo == (max-1)){ return "Stack full!";}
        else{ return "Stack not full!";}
    }
    
    // returns the matching opening bracket, used by validate.
    public char reverse(char aux){
        if(aux == ')'){return '(';}
        else if(aux == ']'){return '[';}
        else if(aux == '}'){return '{';}
        else{return 0;}
    }
    
    // validates the input string of brackets.
    public String validate(String n){
        System.out.println("\n\tExpression: \t"+n+"\n");
        char saver = '#'; 
        // For:  Percorre a string de [0] a [String.length()].
        for(int i = 0; i < max; i++){
            // if the top does not exceed the string length.
            if(topo != (max-1)){
               // aux  = auxiliar. 
               char aux = n.charAt(i);
               // Adiciona (, [ ou { à pilha. 
               if(aux == '(' || aux == '[' || aux == '{'){
                   this.topo++;
                   this.data[topo] = aux;
                   System.out.println("Stack ["+topo+"] <= adicionado <= "+aux);
               }
               // Se aux igual a ), ], ou }.
               else if(aux == ')' || aux == ']' || aux == '}'){
                   // Se o topo estiver vazio, 
                   //                 ou seja, ), ] ou } seja o first.
                   if(topo == -1){ 
                       return "\n\tMalformed formula. "
                               + "\tError: Stack["+topo+"]: "+aux
                            + "\n\t\t(First check). \n";
                   }
                   
                   // Se o oposto do aux for igual ao valor do topo.
                   else if(data[topo] == reverse(aux) && reverse(aux) != 0){
                       int aux2 = topo;
                       topo--;
                       System.out.println("Stack ["+aux2+"] <= removido <= "+aux);
                   }
  
                   //Se o topo diferir do atual auxiliar.
                   else {  return
                              "\n\tMalformed formula. "
                           + "\tError: Stack["+topo+"]: "+aux
                            + "\n\t\t(Second check). \n";}   
                    
               }
               // ignores every char that is not: (, [, {, ), ] or }.
               else{;}
               saver = aux;
            }
            else{System.out.println(isFullEx2());} 
            /* 
            Not needed, since the number of characters cannot exceed the
            length of the string, so it never overflows.
            */            
        }
        // if the loop ran to here and the top is -1, 
        // then the formula is well-formed. 
        if(topo == -1){  return "\n\t\tWell-formed formula\n"; }
        
        // if there is no closing bracket ), ], } for the opening ones (, [, {,
        // error flag. Not valid.
        else{ return "\n\tMalformed formula. \tError: Stack["+topo+"]: "+saver
                + "\n\t\t(Last check). \n";}
        
    }
    
    // shows the examples and their solutions. 
    public static void examples2(){
        
        //a
        System.out.println("Exemplo A:\n");
        String a = "(A+B})";
        Stack a_p = new Stack(a.length());
        System.out.println(a_p.validate(a));
        
        //b
        System.out.println("Exemplo B:\n");
        String b = "{[A + B] – [(C - D)]";
        Stack b_p = new Stack(b.length());
        System.out.println(b_p.validate(b));
        
        //c
        System.out.println("Exemplo C:\n");
        String c = "(A + B) – {C + D} – [F + G]";
        Stack c_p = new Stack(c.length());
        System.out.println(c_p.validate(c));
        
        //d
        System.out.println("Exemplo D:\n");
        String d = "((H) * {([J + K])}) ";
        Stack d_p = new Stack(d.length());
        System.out.println(d_p.validate(d));
        
        //e
        System.out.println("Exemplo E:\n");
        String e = "(((A)))";
        Stack e_p = new Stack(e.length());
        System.out.println(e_p.validate(e));
        
    }
    
    // Runs exercise 2.
    public static void execute(int modo, String n){
        Scanner lk = new Scanner(System.in);
        if(modo == 1){
            System.out.println("Enter an expression: ");
            String x = lk.next();
            Stack  x_p = new Stack(x.length());
            System.out.println(x_p.validate(x));
        }
        else{
            Stack  n_p = new Stack(n.length());
            System.out.println(n_p.validate(n));
        }
    }
    
    // End: exercise 2.                                        -----------------
    
    // explicativo.
    public static void help(){
        System.out.println(
        "Stack: array-backed stack of ints. "+
        "Exercise 1 - basic stack: isEmpty(), overflow(), push(), pop(), top(), examples1(). "+
        "Exercise 2 - bracket validator: isEmptyEx2(), isFullEx2(), reverse(), validate(), examples2(). "+
        "validate(String) checks that (), [] and {} brackets are balanced. "+
        "execute(mode, String): mode 1 reads from stdin, mode 0 uses the given string.");
    }

    public static void main(String[] args) {
        
        // team: Gustavo Hammerschmidt, João Capoani, Davi Leal.
        
        help();
        
        // exercise 1:
        //examples1();
        Stack x = new Stack(5);
        x.push(2);
        System.out.println(x.topo());
        x.push(3);
        x.push(2);
        x.push(2);
        x.push(21);
        x.push(234);  // will return the overflow flag.
        System.out.println(x.pop());
        
        // exercise 2:
        //examples2();
        //execute(modo,  String) modo ==1: Scanner; Ou: use string.
        execute(0, "{[(7*2)+4/7]*[(-2)/3]}+{[(A*5)/8]*3}");
      
    }
}