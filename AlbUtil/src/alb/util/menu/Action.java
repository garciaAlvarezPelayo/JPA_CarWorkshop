package alb.util.menu;

/**
 * It represents each menu option invoked by the user  
 * 
 * Each action will be responsible for user interaction 
 * (display options, read selected option, display result
 * and input validation) as well as for invoking service layer. 
 * 
 * @author alb
 */
public interface Action {
	void execute() throws Exception;
}
