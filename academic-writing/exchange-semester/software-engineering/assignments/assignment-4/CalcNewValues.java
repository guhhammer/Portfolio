import java.io.*;

public class CalcNewValues{

  public static void main(String[] args){
    //Initialize values
    final int GRAVITY = 2;
    int altitude = 1000;
    int fuel = 500;
    int velocity = 70;
    int time = 0;
    
    try{
      BufferedReader inputReader = new
      BufferedReader(new InputStreamReader(System.in));
      
      //Print initial values
      System.out.println("%a" + altitude);
      System.out.println("%f" + fuel);
      System.out.println("%v" + velocity);
      System.out.println("%t" + time);
      
      String inputLine = null;
      do{
        inputLine = inputReader.readLine();
        if((inputLine != null) &&
           (inputLine.length() > 0)){
          
          if(inputLine.startsWith("#")){
            //This is a status line of text, and
            //should be passed down the pipeline
            System.out.println(inputLine);
          }
          else if(inputLine.startsWith("%")){
            //This is an input burn rate
            try{
              int burnRate =
                Integer.parseInt(inputLine.substring(1));
              if(altitude <= 0){
                System.out.println("#The game is over.");
              }
              else if(burnRate > fuel){
                System.out.println("#Sorry, you don't" +
                                   "have that much fuel.");
              }
              else{
                //Calculate new application state
                time = time + 1;
                altitude = altitude - velocity;
                velocity = ((velocity + GRAVITY) * 10 -
                            burnRate * 2) / 10;
                fuel = fuel - burnRate;
                if(altitude <= 0){
                  altitude = 0;
                  if(velocity <= 5){
                    System.out.println("#You have" +
                                       "landed safely.");
                  }
                  else{
                    System.out.println("#You have" +
                                       "crashed.");
                  }
                }
              }
              //Print new values
              System.out.println("%a" + altitude);
              System.out.println("%f" + fuel);
              System.out.println("%v" + velocity);
              System.out.println("%t" + time);
            }
            catch(NumberFormatException nfe){
            }
          }
        }
      }
      while((inputLine != null) && (altitude > 0));
      inputReader.close();
    }
    catch(IOException ioe){
      ioe.printStackTrace();
    }
  }
}