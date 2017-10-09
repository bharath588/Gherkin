package generallib;
import java.util.*;

public class RandomDateGenerator {
   
    private Date dMin = null;
    private Date dMax = null;

    public RandomDateGenerator(Date min, Date max) {
        dMin = min;
        dMax = max;
    }
   
    public Date generate() {
        long MILLIS_PER_DAY = 1000*60*60*24;
        GregorianCalendar s = new GregorianCalendar();
        s.setTimeInMillis(dMin.getTime());
        GregorianCalendar e = new GregorianCalendar();
        e.setTimeInMillis(dMax.getTime());
       
        long endL   =  e.getTimeInMillis() +  e.getTimeZone().getOffset(e.getTimeInMillis());
        long startL = s.getTimeInMillis() + s.getTimeZone().getOffset(s.getTimeInMillis());
        long dayDiff = (endL - startL) / MILLIS_PER_DAY;
       
        Calendar cal = Calendar.getInstance();
        cal.setTime(dMin);
        cal.add(Calendar.DATE, new Random().nextInt((int)dayDiff));          
        return cal.getTime();
    }
   
    public static void main(String args[]) {
        Calendar cal = Calendar.getInstance();
        Date dMin = cal.getTime();
        cal.add(Calendar.YEAR, 1); // today plus one year
        Date dMax = cal.getTime();
       
        RandomDateGenerator rnd = new RandomDateGenerator(dMin, dMax);
        RandomDateGenerator rnd1 = new RandomDateGenerator(dMin, dMax);
       
        Date date1 = rnd.generate();
        Date date2 =  rnd1.generate();

        if(DateCompare.compareTwoDates(date1, date2)){
        	System.out.println("Same");
        }
        else
        	System.out.println("not same");
        
    }
}