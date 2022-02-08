package Semana15;

    /*
    *
    *       CLASSE QUE GERA INDENTIFICADORES ÚNICOS.
    *
    */

public class UniqueID {

    private int series = -1;
    private String UID = "UID";

    private void updateUID(){ this.series++; }

    public String getSeriesInString() {
        return String.format("%06d", this.series);
    }

    public String getUID(){

        updateUID();

        return (this.UID+getSeriesInString());

    }

}
