package armstrong_number; public class main_min{ private static int powerof(int x, int y, int r){ return (y == 0) ? r : powerof(x, y-1, r*x);} public static int power(int x, int y){ return powerof(x, y, 1);} public static int char_to_int(char x){ char[] nn = {'0','1','2','3','4','5','6','7','8','9'};for(int n = 0; n < 10; n++) {if(x == nn[n]) {return n;}}return -1;} public static boolean isArmstrongNumber(int number, int base){ String number_ = number+""; int sum = 0; for(int i = 0; i < number_.length(); i++){ sum += power(char_to_int(number_.charAt(i)), base);} return sum==number;} public static void main(String[] Args){ System.out.println( isArmstrongNumber(153,3));} }