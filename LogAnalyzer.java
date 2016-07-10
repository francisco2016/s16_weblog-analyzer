/**
 * Read web server data and analyse
 * hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version 2011.07.31
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts. --- (recuentos de acceso por hora)
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader();
    }

    /**
     *constructor tiene un par�metro consistente en el nombre del archivo de log a analizar.
     *Usa la clase LogFileCreator para crear tu propio archivo de log y comprueba que puedes analizarlo con la
     *clase LogAnalyzer.
     *Nuevo constructor para poder elegir el archivo a analizar. -----------------------------------  0070
     */
    public LogAnalyzer(String name)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(name);
    }

    /**
     *m�todo que se pueda ejecutar despu�s del m�todoanalyzeHourlyData y que devuelva el n�mero total de accesos 
     *al servidor web registrados en el archivo de log. --------------------------------------- 0073
     */
    public int numberOfAccesses(){
        int totalYamadas = 0;
        for(int i = 0; i < hourCounts.length; i++){
            totalYamadas = totalYamadas + hourCounts[i];
        }

        return totalYamadas ;
    }

    /**
     *mt que se pueda ejecutar despu�s del m�todo analyzeHourlyData y que devuelva en qu� hora el servidor tuvo que responder
     *a m�s peticiones. Si hay empate devuelve la �ltima de las horas. Si no ha habido accesos informa del hecho por pantalla 
     *y devuelve -1.  ---------------------------------------------------------------------------------------------  0073
     */
    public int busiestHour(){
        int conMasPeticiones = 0;
        int solucion = -1;
        for(int i = 0; i < hourCounts.length; i ++){
            if(hourCounts[i] >= conMasPeticiones){// -- (>= para que en caso de empate, guarde el segundo valor).
                conMasPeticiones = hourCounts[i];
            }
            if(hourCounts[i] == conMasPeticiones){
                solucion = i;
            }            
        }
        if(solucion == -1){
            System.out.println("Ninguna entrada. ");
        }
        return solucion;
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        //         for(int hour = 0; hour < hourCounts.length; hour++) {
        //             System.out.println(hour + ": " + hourCounts[hour]);
        //         }
        int hour = 0;
        while(hour < hourCounts.length){
            System.out.println(hour + ": " + hourCounts[hour]);
            hour ++;
        }

    }

    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
