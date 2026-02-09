package Main;

import java.math.*;

public class Main {
	
	public static int getSBlockIndex(int ender, int s) {
	
		String str = Integer.toBinaryString(ender), ret = "";
		for(int i = 0; i < str.length(); i++) {
			ret += (i < s) ? str.charAt(i) : "0";
		}
	
		return Integer.parseInt(ret, 2);
		
	}
	
	public static int ender_size(int ender) {
		int cont = 0;
		while(ender > 0) { ender >>= 1; cont++; }
		return cont;	
	}
	
	public static int word_ender(int ender, int word_size) {
		return (ender & ((1 << word_size)-1));
	}
	
	public static void main(String[] args) {
		
		int words_in_cache_line = 64,
		    cache_size = (int)(8 * Math.pow(2, 10)), // 8K
		    memory_size = (int)(16 * Math.pow(2,  20)); // 16M
		
		int ender = 10560325;
		
		int word_size = (int) (Math.log10(words_in_cache_line)/Math.log10(2));
		int r = (int)( Math.log10((int) (cache_size/Math.pow(2, word_size)))/Math.log10(2)); 
		int t  = Integer.toBinaryString(ender).length() - r - word_size;
		int s = t+r;
		
		
		int op_1 = (int) Math.floor( Math.sqrt(ender));
		
		
		int block_starts_at = getSBlockIndex(ender, s);
		int	block_ends_at = block_starts_at + words_in_cache_line;
		
		System.out.println(word_ender(ender, word_size));
		
		

	}
	
}
