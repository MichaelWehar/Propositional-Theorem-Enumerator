/**
 * @(#)Formula.java
 *
 *
 * @author Michael Wehar
 * @version 1.00 2011/6/16
 */
import java.util.ArrayList;

public class Formula {

	ArrayList<Integer> string;

    public Formula(ArrayList<Integer> s) {
    	string=s;
    }

    public int get(int i){
    	return string.get(i);
    }

    public int length(){
    	return string.size();
    }

    public Formula trim(int start, int end){
    	ArrayList<Integer> s = new ArrayList<Integer>();
    	for(int i=start; i<end; i++)
    		s.add(string.get(i));

    	return new Formula(s);
    }

}