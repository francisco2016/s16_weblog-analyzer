/**
 * Read web server data and analyse
 * hourly access patterns.
 * 
 * @author David J. Barnes and Michael KÃ¶lling.
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
     *constructor tiene un parámetro consistente en el nombre del archivo de log a analizar.
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
     *método que se pueda ejecutar después del métodoanalyzeHourlyData y que devuelva el número total de accesos 
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
     *mt que se pueda ejecutar después del método analyzeHourlyData y que devuelva en qué hora el servidor tuvo que responder
     *a --más-- peticiones. Si hay empate devuelve la última de las horas. Si no ha habido accesos informa del hecho por pantalla 
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
     *  devuelva la hora a la que el servidor estuvo ---menos--- sobrecargado. Si hay empate devuelve la última de las horas. 
     *  Para testear este método asegúrate de que lo pruebas con un archivo de log en el que ha habido accesos a todas las horas.
     *  Si no ha habido accesos informa del hecho por pantalla y devuelve -1. -------------------------------------- 0705
     */
    public int quietestHour(){
        int conMenosPeticiones = numberOfAccesses();
        int solucion = -1;
        if(numberOfAccesses() == 0){
            System.out.println("Ninguna entrada. ");
        }
        else{
            for(int i = 0; i < hourCounts.length; i ++){
                if(hourCounts[i] <= conMenosPeticiones){// -- (>= para que en caso de empate, guarde el segundo valor).
                    conMenosPeticiones = hourCounts[i];
                }
                if(hourCounts[i] == conMenosPeticiones){
                    solucion = i;
                }            
            }
        }
        return solucion;
    }

    /**
     * muestre por pantalla el período de dos horas --consecutivas-- con más carga del día y devuelva un entero con la primera hora 
     * de dicho periodo. Si hay empate devuelve el último período. Si no ha habido accesos informa del hecho por pantalla y 
     * devuelve -1.  -----------------------------------------------------------------------------------------------  0705
     */
    public int cargaEnDosHoras(){
        int conMasPeticiones = 0;
        int solucion = -1;
        if(numberOfAccesses() == 0){
            System.out.println("Ninguna entrada. ");
        }
        else{
            for(int i = 0; i < hourCounts.length; i ++){
                int z =  ( (i +1)% hourCounts.length) ;
                //System.out.println para hacer las sumas consecutivas y poder comprobar si la solución es correcta.
                System.out.println( i+ ": -- " + hourCounts[i]+ " + " +hourCounts[ z]  + "  =  "+ 
                    (hourCounts[i] +  hourCounts[ z]));

                if( ( hourCounts[i] + hourCounts[ z] ) >= conMasPeticiones){// -- (>= para que en caso de empate, guarde el segundo valor).
                    solucion = i;
                    conMasPeticiones = hourCounts[i] + hourCounts[z];

                }   

            }
        }
        return solucion;
    }

    /**
     * Analyze the hourly accesses only in the given date  ---Analiza sólo los accesos de una hora en la fecha dada.
     * @param day   The given day
     * @param month The given month
     * @param year  The given year
     */
    public void accesosEnUnaHoraDeUnaFechaDada(int day, int month, int year){
        //         int solu = 0;
        //         int index = 0;
        //         boolean encontrado = true;
        //         LogEntry logEntry = null;
        //         while(index < hourCounts.length && encontrado){
        //             if(logEntry.getDay() == day && logEntry.getDay() == month && logEntry.getDay() == year){
        //                 solu = hourCounts[index];
        //                 encontrado = false;
        //             }
        //             index ++;
        //         }
        //         return solu;
        //////////////////////////////////////////////////////////////////
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            if ((entry.getYear() == year) && 
            (entry.getMonth() == month) && 
            (entry.getDay() == day))
            {
                int hour = entry.getHour();
                hourCounts[hour]++;
            }
        }
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
