
package ParalelismoRecursivo;

import static ParalelismoRecursivo.Main.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class fazerArquivoPython{
    
    public static String info(String nome, ArrayList<double[]> Aux){
        String stringAuxiliar = "scores"+nome+" = (";
        
        for(int i =0; i < Aux.size(); i++){
            if(i == Aux.size()-1){
                stringAuxiliar += "["+Aux.get(i)[0]+", "+
                                     Aux.get(i)[1]+", "+
                                     Aux.get(i)[2]+"])";
            }
            else{
                stringAuxiliar += "["+Aux.get(i)[0]+", "+
                                     Aux.get(i)[1]+", "+
                                     Aux.get(i)[2]+"],";
            }
        }
        
        return stringAuxiliar;
  
    }
    
    
    public static void escreverPythonCode(ArrayList<double[]> nums) 
            throws FileNotFoundException, UnsupportedEncodingException{
        
        String path = caminhoMain+"src\\ParalelismoRecursivo\\"+nomeArqPython;
        PrintWriter writer = new PrintWriter(path, "UTF-8");
        
        writer.println("");
        writer.println("");
        
        writer.println("import xlsxwriter");        
        
        writer.println("");
        writer.println("");
        writer.println("");
        
        String caminho = "\""+caminhoMain+"src\\ParalelismoRecursivo\\"+nomeArqExcel+"\"";
        
        writer.println(
                "workbook = xlsxwriter.Workbook("+caminho.replace("\\", "\\\\")+")");
        
        writer.println("");
        writer.println("");
        
        String[] nomes = new String[1];
        nomes[0] = "Nums"; 
        
        writer.println(info(nomes[0],nums));
        writer.println("");
        writer.println("");
        
        writer.println("worksheet"+nomes[0]+" = "
                + "workbook.add_worksheet(\"P_"+nomes[0]+"\")");
        
        writer.println("");
        writer.println("");
        
        writer.println("worksheet"+nomes[0]+".write(0,0, \"2^N\")");
        writer.println("worksheet"+nomes[0]+".write(0,1, \"Tempo Sequencial\")");
        writer.println("worksheet"+nomes[0]+".write(0,2, \"Tempo Paralelo\")");
        
        writer.println("");
        writer.println("");
        
        writer.println("row"+nomes[0]+" = 1");
        writer.println("col"+nomes[0]+" = 0");

        writer.println("for x, y, z  in (scores"+nomes[0]+"):");
        writer.println("\tcol"+nomes[0]+" = 0");
        writer.println("\tworksheet"+nomes[0]+".write(row"+nomes[0]+
                       ", col"+nomes[0]+", x) ");
        writer.println("\tworksheet"+nomes[0]+".write(row"+nomes[0]+
                       ", col"+nomes[0]+" + 1, y) ");
        writer.println("\tworksheet"+nomes[0]+".write(row"+nomes[0]+
                       ", col"+nomes[0]+" + 2, z) ");

        writer.println("\trow"+nomes[0]+" += 1");
        writer.println("");
        writer.println("");

        
        writer.println("chart"+nomes[0]+" = workbook.add_chart({\'type\': \'line\'})");

        writer.println("");

        writer.println("chart"+nomes[0]+".add_series({");
        writer.println("\t\'name\': \'Tempo Sequencial\',");
        writer.println("\t\'categories\': \'P_"+nomes[0]+"!$A$2:$A$"
                                            +(2+(rightLimit-leftLimit))+"\',");
        writer.println("\t\'values\': \'P_"+nomes[0]+"!$B$2:$B$"
                                            +(2+(rightLimit-leftLimit))+"\',");
        writer.println("\t\'data_labels\': {\'value\': True, \'num_format\': \'#,##0.00\'},");
        writer.println("\t\'line\': {\'color\': \'orange\', \'width\': 2.25},");
        writer.println("\t\'marker\': {");
        writer.println("\t\t\'type\': \'circle\',");
        writer.println("\t\t\'size\': 6,");
        writer.println("\t\t\'border\': {\'color\': \'black\'},");
        writer.println("\t\t\'fill\': {\'color\': \'blue\'},");
        writer.println("\t},");
        writer.println("})");
        
        
        writer.println("chart"+nomes[0]+".add_series({");
        writer.println("\t\'name\': \'Tempo Paralelo\',");
        writer.println("\t\'categories\': \'P_"+nomes[0]+"!$A$2:$A$"
                                            +(2+(rightLimit-leftLimit))+"\',");
        writer.println("\t\'values\': \'P_"+nomes[0]+"!$C$2:$C$"
                                            +(2+(rightLimit-leftLimit))+"\',");
        writer.println("\t\'data_labels\': {\'value\': True, \'num_format\': \'#,##0.00\'},");
        writer.println("\t\'line\': {\'color\': \'green\', \'width\': 2.25},");
        writer.println("\t\'marker\': {");
        writer.println("\t\t\'type\': \'circle\',");
        writer.println("\t\t\'size\': 6,");
        writer.println("\t\t\'border\': {\'color\': \'black\'},");
        writer.println("\t\t\'fill\': {\'color\': \'blue\'},");
        writer.println("\t},");
        writer.println("})");


        writer.println("");
        writer.println("");
        writer.println("chart"+nomes[0]+".set_title({"
                + "\'name\': \'Planilha "+nomes[0]+"(P_"+nomes[0]+")\'})");
        writer.println("chart"+nomes[0]+".set_x_axis({\'name\': \'2^N\'})");
        writer.println("chart"+nomes[0]+".set_y_axis({\'name\': \'Tempo de Execução(mS) \'})");

        writer.println("");
        writer.println("");
        writer.println("chart"+nomes[0]+".set_style(9)");

        writer.println("worksheet"+nomes[0]+".insert_chart(\'E3\',"
                + "chart"+nomes[0]+", {\'x_offset\': 25, \'y_offset\': 10})");
                
        writer.println("");
        writer.println("");

        
        writer.println("workbook.close()");
           
        writer.close();
        
    }
    
    
    
    public static void main(String[] Args) 
            throws InterruptedException, FileNotFoundException, UnsupportedEncodingException {
        
        mainRelease.acquire();
        
        System.out.println("\n\n\t\tWriting Python File...");
        Thread.sleep(1000);
        System.out.print("\n\t\tWriting in 4...");
        Thread.sleep(1000);
        System.out.print(" 3...");
        Thread.sleep(1000);
        System.out.print(" 2...");
        Thread.sleep(1000);
        System.out.print(" 1...");
        Thread.sleep(1000);
        System.out.print("\n\t\tWriting...");
        
        escreverPythonCode(values);
        
        System.out.println("\n\t\tWriting Completed!!\n\n");
        
        
        criar.release();
        
    }
    
    
}
