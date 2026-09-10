package parallelism;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

/** Writes outputs/make_chart.py (needs `pip install xlsxwriter`): one sheet and one line chart per dataset. */
public class ChartScriptWriter {

    static final String SCRIPT = "make_chart.py", WORKBOOK = "task-parallelism.xlsx";

    /** A named table: a header row plus numeric rows. Two-column tables chart column B against A,
     *  three-column tables chart column C against B. */
    public static class Dataset {
        final String name;
        final String[] header;
        final List<double[]> rows;

        public Dataset(String name, String[] header, List<double[]> rows) {
            this.name = name;
            this.header = header;
            this.rows = rows;
        }
    }

    public static void write(List<Dataset> datasets) throws IOException {
        new File("outputs").mkdirs();
        StringBuilder s = new StringBuilder();
        s.append("import xlsxwriter\n");
        s.append("workbook = xlsxwriter.Workbook('").append(WORKBOOK).append("')\n");
        for (Dataset d : datasets) {
            String sheet = "P_" + d.name;
            s.append("sheet = workbook.add_worksheet('").append(sheet).append("')\n");
            for (int c = 0; c < d.header.length; c++) {
                s.append(String.format("sheet.write(0, %d, '%s')%n", c, d.header[c]));
            }
            int row = 1;
            for (double[] r : d.rows) {
                for (int c = 0; c < r.length; c++) {
                    s.append(String.format(Locale.ROOT, "sheet.write(%d, %d, %s)%n", row, c, r[c]));
                }
                row++;
            }
            int last = d.rows.size() + 1;
            String categories = d.header.length == 2 ? "A" : "B", values = d.header.length == 2 ? "B" : "C";
            s.append("chart = workbook.add_chart({'type': 'line'})\n");
            s.append(String.format("chart.add_series({'name': '%s', 'categories': '=%s!$%s$2:$%s$%d', 'values': '=%s!$%s$2:$%s$%d',%n",
                    sheet, sheet, categories, categories, last, sheet, values, values, last));
            s.append("                  'data_labels': {'value': True, 'num_format': '#,##0.00'}, 'line': {'color': 'orange', 'width': 2.25},\n");
            s.append("                  'marker': {'type': 'circle', 'size': 6, 'border': {'color': 'black'}, 'fill': {'color': 'blue'}}})\n");
            s.append(String.format("chart.set_title({'name': '%s'})%n", sheet));
            s.append(String.format("chart.set_x_axis({'name': '%s'}); chart.set_y_axis({'name': '%s'})%n",
                    d.header[d.header.length - 2], d.header[d.header.length - 1]));
            s.append("chart.set_style(9)\n");
            s.append("sheet.insert_chart('E2', chart, {'x_offset': 25, 'y_offset': 10})\n");
        }
        s.append("workbook.close()\n");
        try (PrintWriter out = new PrintWriter(new File("outputs", SCRIPT), "UTF-8")) { out.print(s); }
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
