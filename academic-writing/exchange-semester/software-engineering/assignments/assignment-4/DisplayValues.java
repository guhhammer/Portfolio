import java.io.*;
public class DisplayValues{
  public static void main(String[] args){
    try{
      BufferedReader inputReader = new
      BufferedReader(new InputStreamReader(System.in));
      String inputLine = null;
      do{
        inputLine = inputReader.readLine();
        if((inputLine != null) &&
           (inputLine.length() > 0)){
          if(inputLine.startsWith("#")){
            //This is a status line of text, and
            //should be passed down the pipeline with
            //the pound-sign stripped off
            System.out.println(inputLine.substring(1));
          }
          else if(inputLine.startsWith("%")){
            //This is a value to display
            if(inputLine.length() > 1){
              try{
                char valueType = inputLine.charAt(1);
                int value =
                Integer.parseInt(inputLine.substring(2));
                switch(valueType){
                  case 'a':
                    System.out.println("Altitude: " +
                                       value);
                    break;
                  case 'f':
                    System.out.println("Fuel remaining: " +
                                       value);
                    break;
                  case 'v':
                    System.out.println("Current Velocity: "
                                       + value);
                    break;
                  case 't':
                    System.out.println("Time elapsed: " +
                                       value);
                    break;
                }
              }
              catch(NumberFormatException nfe){
              }
            }
          }
        }
      }
      while(inputLine != null);
      inputReader.close();
    }
    catch(IOException ioe){
      ioe.printStackTrace();
    }
  }
}