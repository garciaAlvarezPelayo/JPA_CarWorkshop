package alb.util.console;

import java.io.PrintStream;

/**
 * Utility methods to print in console.
 * Decorations should be here 
 * 
 * @author alb
 */
public class Printer {
	private static PrintStream con = System.out;
	
	public static void printHeading(String string) {
		con.println(string);
	}

	/**
	 * Warns about logic error, probably due to a user mistake of because some circumstances
	 * have changed during user think time (optimistic control and so)
	 * 
	 * @param e
	 */
	public static void printBusinessException(Exception e) {
		con.println("There were an error trying to process your option: ");
		con.println("\t- " + e.getLocalizedMessage());
	}

	/**
	 * Warns about unrecoverable error 
	 * @param string
	 * @param e
	 */
	public static void printRuntimeException(RuntimeException e) {
		con.println("An internal error occurred and " +
				"program will terminate.\n" +
				"[Next, the full stack trace of the error is printed]\n" +
				"[This trace should not be visible to the end-user;] \n" + 
				"[production deployments should consider turning off stack trace \n" + 
				"[information writing it into system log instead ]");

		e.printStackTrace();
	}

	public static void print(String msg) {
		con.println(msg);
	}

	public static void printException(String string, Exception e) {
		con.println(string);
		con.println("\t- " + e.getLocalizedMessage());
	}

}