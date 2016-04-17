package by.training.xls_colorer;

import java.io.IOException;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class App 
{
    public static void main( String[] args ) {
    	
    	String message = "Program is using 2 paths one for Input xls another for output. "
    			+ "For example H:\\haha.xls H:\\haha2.xls";
    	
    	if (args.length != 2) {
    		System.out.println(message);
    		System.exit(0);
    	}
    	
    	
        try {
			Colorer colorer = new Colorer(args[0], args[1]);
			colorer.setColor(Colour.GREEN, Colour.RED, Colour.YELLOW);
		} catch (BiffException e) {
			System.out.println("Unable to recognize XML");
			System.out.println(message);
			System.exit(0);
		} catch (IOException e) {
			System.out.println("Problem with Reading/Writing file");
			System.out.println(message);
			System.exit(0);
		} catch (WriteException e) {
			System.out.println("Problem with Reading/Writing XML");
			System.out.println(message);
			System.exit(0);
		}
    }
}
