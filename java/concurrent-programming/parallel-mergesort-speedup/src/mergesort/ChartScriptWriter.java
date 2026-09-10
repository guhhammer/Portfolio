package mergesort;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

/** Writes outputs/make_chart.py (needs `pip install xlsxwriter`), which builds a spreadsheet with a line chart of
 *  sequential vs parallel times, then runs it and opens the spreadsheet when a desktop is available. */
public class ChartScriptWriter {

    static final String SCRIPT = "make_chart.py", WORKBOOK = "parallel-mergesort.xlsx";

    public static void write(List<double[]> rows, int leftLimit, int rightLimit) throws IOException {
        new File("outputs").mkdirs();
        StringBuilder data = new StringBuilder("scores = (");
        for (double[] r : rows) { data.append(String.format(Locale.ROOT, "[%s, %s, %s],", r[0], r[1], r[2])); }
        data.append(")");
        int last = 2 + (rightLimit - leftLimit);

        String script = String.join("\n",
            "import xlsxwriter",
            "workbook = xlsxwriter.Workbook('" + WORKBOOK + "')",
            data.toString(),
            "sheet = workbook.add_worksheet('P_Nums')",
            "sheet.write(0, 0, '2^N'); sheet.write(0, 1, 'Sequential time'); sheet.write(0, 2, 'Parallel time')",
            "row = 1",
            "for x, y, z in scores:",
            "    sheet.write(row, 0, x); sheet.write(row, 1, y); sheet.write(row, 2, z)",
            "    row += 1",
            "chart = workbook.add_chart({'type': 'line'})",
            "for name, col, color in (('Sequential time', 'B', 'orange'), ('Parallel time', 'C', 'green')):",
            "    chart.add_series({'name': name, 'categories': '=P_Nums!$A$2:$A$" + last + "',",
            "                      'values': '=P_Nums!$' + col + '$2:$' + col + '$" + last + "',",
            "                      'data_labels': {'value': True, 'num_format': '#,##0.00'},",
            "                      'line': {'color': color, 'width': 2.25},",
            "                      'marker': {'type': 'circle', 'size': 6, 'border': {'color': 'black'}, 'fill': {'color': 'blue'}}})",
            "chart.set_title({'name': 'Sequential vs parallel mergesort'})",
            "chart.set_x_axis({'name': '2^N'}); chart.set_y_axis({'name': 'Execution time (ms)'})",
            "chart.set_style(9)",
            "sheet.insert_chart('E3', chart, {'x_offset': 25, 'y_offset': 10})",
            "workbook.close()");
        try (PrintWriter out = new PrintWriter(new File("outputs", SCRIPT), "UTF-8")) { out.println(script); }
        System.out.println("Wrote outputs/" + SCRIPT);
    }

    public static void runAndOpen() {
        for (String python : new String[]{"python3", "python"}) {
            try {
                Process p = new ProcessBuilder(python, SCRIPT).directory(new File("outputs")).inheritIO().start();
                if (p.waitFor() == 0) { break; }
            } catch (Exception e) {
                System.err.println("Could not run " + python + ": " + e.getMessage());
            }
        }
        File workbook = new File("outputs", WORKBOOK);
        try {
            if (workbook.isFile() && java.awt.Desktop.isDesktopSupported()) { java.awt.Desktop.getDesktop().open(workbook); }
        } catch (Exception e) {
            System.err.println("Spreadsheet written to " + workbook + " (could not open it automatically).");
        }
    }
}
