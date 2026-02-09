package ParalelismoAula1;

import static ParalelismoAula1.Iniciador.criar;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import static ParalelismoAula1.Iniciador.max;
import static ParalelismoAula1.Iniciador.min;

        
        
public class CriarArquivoPython{

    public static String infoPara_A(String nome, ArrayList<String[]> Aux){
        String stringAuxiliar = "scores"+nome+" = (";
        
        for(int i =0; i < Aux.size(); i++){
            if(i == Aux.size()-1){
                stringAuxiliar += "["+Aux.get(i)[0]+", "+Aux.get(i)[1]+"])";
            }
            else{
                stringAuxiliar += "["+Aux.get(i)[0]+", "+Aux.get(i)[1]+"],";
            }
        }
        
        return stringAuxiliar;
    
    }
    
    public static String info(String nome, ArrayList<String[]> Aux){
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
    
    public static String caminhoMain = ParalelismoAula1.Iniciador.caminho;
    public static String nomeArqPython = ParalelismoAula1.Iniciador.nomeArqPython;
    
    public static void escreverPythonCode(String nomeArqExcel,
                                          ArrayList<String[]> A1,
                                          ArrayList<String[]> B1,
                                          ArrayList<String[]> C1,
                                          ArrayList<String[]> D1,
                                          ArrayList<String[]> A2,
                                          ArrayList<String[]> B2,
                                          ArrayList<String[]> C2,
                                          ArrayList<String[]> D2)
            throws FileNotFoundException, UnsupportedEncodingException {
        
        String path = caminhoMain+"src\\ParalelismoAula1\\"+nomeArqPython;
        
        PrintWriter writer = new PrintWriter(path, "UTF-8");
        
        
        writer.println("");
        writer.println("");
        
        writer.println("#Você deve executar o comando: pip install xlsxwriter no seu prompt"
                + "   se quiser executar este arquivo");
        writer.println("import xlsxwriter");        
        
        writer.println("");
        writer.println("");
        writer.println("");
        
        String caminho = "\""+caminhoMain+"src\\ParalelismoAula1\\"+nomeArqExcel+"\"";
        
        writer.println(
                "workbook = xlsxwriter.Workbook("+caminho.replace("\\", "\\\\")+")");
        
        writer.println("");
        writer.println("");
        
        String[] nomes = new String[8];
        
        nomes[0] = "A1"; nomes[1] = "A2"; nomes[2] = "B1"; nomes[3] = "B2";
        nomes[4] = "C1"; nomes[5] = "C2"; nomes[6] = "D1"; nomes[7] = "D2";
        
        writer.println(infoPara_A(nomes[0],A1));
        writer.println("");
        writer.println(infoPara_A(nomes[1],A2));
        writer.println("");
        writer.println(info(nomes[2],B1));
        writer.println("");
        writer.println(info(nomes[3],B2));
        writer.println("");
        writer.println(info(nomes[4],C1));
        writer.println("");
        writer.println(info(nomes[5],C2));
        writer.println("");
        writer.println(info(nomes[6],D1));
        writer.println("");
        writer.println(info(nomes[7],D2));
        writer.println("");
        writer.println("");
            
        
        for(int i = 0; i < 2; i++){
        
            writer.println("worksheet"+nomes[i]+" = "
                    + "workbook.add_worksheet(\"P_"+nomes[i]+"\")");
            
            writer.println("row"+nomes[i]+" = 0");
            writer.println("col"+nomes[i]+" = 0");
            
            writer.println("for x, y  in (scores"+nomes[i]+"):");
            writer.println("\tcol"+nomes[i]+" = 0");
            writer.println("\tworksheet"+nomes[i]+".write(row"+nomes[i]+
                           ", col"+nomes[i]+", x) ");
            writer.println("\tworksheet"+nomes[i]+".write(row"+nomes[i]+
                           ", col"+nomes[i]+" + 1, y) ");

            writer.println("\trow"+nomes[i]+" += 1");
            writer.println("");
            writer.println("");
            
            writer.println("chart"+nomes[i]+" = workbook.add_chart({\'type\': \'line\'})");
            
            writer.println("");
            
            writer.println("chart"+nomes[i]+".add_series({");
            writer.println("\t\'name\': \'Planilha "+nomes[i]+"(P_"+nomes[i]+")\',");
            writer.println("\t\'categories\': \'P_"+nomes[i]+"!$A$2:$A$"+(2+(max-min))+"\',");
            writer.println("\t\'values\': \'P_"+nomes[i]+"!$B$2:$B$"+(2+(max-min))+"\',");
            writer.println("\t\'data_labels\': {\'value\': True, \'num_format\': \'#,##0.00\'},");
            writer.println("\t\'line\': {\'color\': \'orange\', \'width\': 2.25},");
            writer.println("\t\'marker\': {");
            writer.println("\t\t\'type\': \'circle\',");
            writer.println("\t\t\'size\': 6,");
            writer.println("\t\t\'border\': {\'color\': \'black\'},");
            writer.println("\t\t\'fill\': {\'color\': \'blue\'},");
            writer.println("\t},");
            writer.println("})");
                       
            
            writer.println("");
            writer.println("");
            writer.println("chart"+nomes[i]+".set_title({"
                    + "\'name\': \'Planilha "+nomes[i]+"(P_"+nomes[i]+")\'})");
            writer.println("chart"+nomes[i]+".set_x_axis({\'name\': \'N(2^tamanho)\'})");
            writer.println("chart"+nomes[i]+".set_y_axis({\'name\': \'Tempo de Execução\'})");
            
            writer.println("");
            writer.println("");
            writer.println("chart"+nomes[i]+".set_style(9)");
            
            writer.println("worksheet"+nomes[i]+".insert_chart(\'D2\',"
                    + "chart"+nomes[i]+", {\'x_offset\': 25, \'y_offset\': 10})");
            
            writer.println("");
            writer.println("");
            
        }     
        
        for(int i = 2; i < nomes.length; i++){
        
            writer.println("worksheet"+nomes[i]+" = "
                    + "workbook.add_worksheet(\"P_"+nomes[i]+"\")");
            
            writer.println("row"+nomes[i]+" = 0");
            writer.println("col"+nomes[i]+" = 0");
            
            writer.println("for x, y, z  in (scores"+nomes[i]+"):");
            writer.println("\tcol"+nomes[i]+" = 0");
            writer.println("\tworksheet"+nomes[i]+".write(row"+nomes[i]+
                           ", col"+nomes[i]+", x) ");
            writer.println("\tworksheet"+nomes[i]+".write(row"+nomes[i]+
                           ", col"+nomes[i]+" + 1, y) ");
            writer.println("\tworksheet"+nomes[i]+".write(row"+nomes[i]+
                           ", col"+nomes[i]+" + 2, z) ");
            
            writer.println("\trow"+nomes[i]+" += 1");
            writer.println("");
            writer.println("");
            
            writer.println("chart"+nomes[i]+" = workbook.add_chart({\'type\': \'line\'})");
            
            writer.println("");
            
            writer.println("chart"+nomes[i]+".add_series({");
            writer.println("\t\'name\': \'Planilha "+nomes[i]+"(P_"+nomes[i]+")\',");
            writer.println("\t\'categories\': \'P_"+nomes[i]+"!$B$2:$B$5\',");
            writer.println("\t\'values\': \'P_"+nomes[i]+"!$C$2:$C$5\',");
            writer.println("\t\'data_labels\': {\'value\': True, \'num_format\': \'#,##0.00\'},");
            writer.println("\t\'smooth\': True,");
            writer.println("\t\'line\': {\'color\': \'orange\', \'width\': 2.25},");
            writer.println("\t\'marker\': {");
            writer.println("\t\t\'type\': \'circle\',");
            writer.println("\t\t\'size\': 6,");
            writer.println("\t\t\'border\': {\'color\': \'black\'},");
            writer.println("\t\t\'fill\': {\'color\': \'blue\'},");
            writer.println("\t},");
            
            
            writer.println("})");
            
            writer.println("");
            writer.println("");
            writer.println("chart"+nomes[i]+".set_title({"
                    + "\'name\': \'Planilha "+nomes[i]+"(P_"+nomes[i]+")\'})");
            writer.println("chart"+nomes[i]+".set_x_axis({\'name\': \'P_"+nomes[i]+"!$B$1\'})");
            writer.println("chart"+nomes[i]+".set_y_axis({\'name\': \'P_"+nomes[i]+"!$C$1\'})");
            
            writer.println("");
            writer.println("");
            writer.println("chart"+nomes[i]+".set_style(5)");
            
            writer.println("worksheet"+nomes[i]+".insert_chart(\'E2\',"
                    + "chart"+nomes[i]+", {\'x_offset\': 25, \'y_offset\': 10})");

            writer.println("");
            writer.println("");
            
        }
        
        
        /*
       
        */
        
        
        writer.println("workbook.close()");
                  
        writer.close();
        
        criar.release();
        
        System.out.println("\n\n\n\t\t\tOK\n\n\n");

    }

}
