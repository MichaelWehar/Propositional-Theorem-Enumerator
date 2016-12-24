/**
 * @(#)Main.java
 *
 *
 * @author Michael Wehar
 * @version 1.00 2011/6/16
 * @updated 2016/12/19
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{
    	final int LENGTH = 100; // Represents the search depth
    	final int GAP = 10000; // Increments before printing to show progress
    	final double PROB = 0.1d; // Weighted coin
    	final int MAX = 10000; // Maximum number of theorems
    	int count = 0;
    	
    	
    	/* Collection of theorems */
    	ArrayList<Scheme> array = new ArrayList<Scheme>();

    	
    	/* Axiom System A */
    	String str1 = new String("[A > [B > A]]");
    	String str2 = new String("[[A > [B > C]] > [[A > B] > [A > C]]]");
    	String str3 = new String("[[-A > -B] > [B > A]]");
    	
    	Formula f1 = new Formula(SchemeMethods.convertString(str1));
    	Scheme s1 = new Scheme(f1);
    	Formula f2 = new Formula(SchemeMethods.convertString(str2));
    	Scheme s2 = new Scheme(f2);
    	Formula f3 = new Formula(SchemeMethods.convertString(str3));
    	Scheme s3 = new Scheme(f3);
    	
    	//array.add(s1);
    	//array.add(s2);
    	//array.add(s3);
    	

    	/* Axiom System B */
    	String str4 = new String("[[-A > A] > A]");
    	String str5 = new String("[[A > B] > [[-C > A] > [-B > C]]]");
    	String str6 = new String("[A > [-B > A]]");
    	String str7 = new String("[A > --A]");
    	String str8 = new String("[--A > A]");

    	Formula f4 = new Formula(SchemeMethods.convertString(str4));
    	Scheme s4 = new Scheme(f4);
    	Formula f5 = new Formula(SchemeMethods.convertString(str5));
    	Scheme s5 = new Scheme(f5);
    	Formula f6 = new Formula(SchemeMethods.convertString(str6));
    	Scheme s6 = new Scheme(f6);
    	Formula f7 = new Formula(SchemeMethods.convertString(str7));
    	Scheme s7 = new Scheme(f7);
    	Formula f8 = new Formula(SchemeMethods.convertString(str8));
    	Scheme s8 = new Scheme(f8);
    	
    	//array.add(s4);
    	//array.add(s5);
    	//array.add(s6);
    	//array.add(s7);
    	//array.add(s8);
    	
    	
    	/* Axiom System C */
    	String str9 = new String("[[[[[A > B] > [-C > -D]] > C] > E] > [[E > A] > [D > A]]]");

    	Formula f9 = new Formula(SchemeMethods.convertString(str9));
    	Scheme s9 = new Scheme(f9);
    	
    	array.add(s9);
    	

    	/* Compares all axioms and throws out duplicates and redundancies */
    	for(int i=0; i<array.size(); i++){
    		for(int j=i+1; j<array.size(); j++){
    			if(SchemeMethods.compare(array.get(i),array.get(j))){
    				System.out.print(": ");
    				SchemeMethods.printTest(array.remove(i).getHead());
    				System.out.println();
    			}
    			else if(SchemeMethods.compare(array.get(j),array.get(i))){
    				System.out.print(": ");
    				SchemeMethods.printTest(array.remove(j).getHead());
    				System.out.println();
    			}
    		}
    	}

    	
    	System.out.println("Progress:");
    	
    	/* Iterative process to generate theorems */
    	for(int i=0; i<LENGTH && array.size() < MAX; i++){
    		System.out.println("Trial Initiated: " + i + ", Array Size: " + array.size());
    		
    		int n = array.size();

    		for(int j=0; j<n && array.size() < MAX; j++){
    			for(int k=0; k<n && array.size() < MAX; k++){
    				if(Math.random() < PROB){
						
    					/* Display Progress */
    					count++;
						if(count%GAP == 0)
							System.out.println("-> (" + count + "," + array.size() + ")");
						
						int depth = Math.max(array.get(j).getDepth(), array.get(k).getDepth())+1;
	
	    				SchemeNode jHead = array.get(j).getHead();
	    				SchemeNode kHead = array.get(k).getHead();
	
	    				if(jHead.getType() == 1){
	
	    					SchemeNode copyOfJ = new SchemeNode(jHead.getType());
	    					SchemeMethods.copy(jHead,copyOfJ);
	    					int p = SchemeMethods.relabel(copyOfJ,0);
	
	    					SchemeNode copyOfK = new SchemeNode(kHead.getType());
	    					SchemeMethods.copy(kHead,copyOfK);
	    					int q = SchemeMethods.relabel(copyOfK,p);
	
	
	    					SchemeNode temp = SchemeMethods.intersection(copyOfJ.left(),copyOfK,q);
	
	    					if(temp != null){
								temp = SchemeMethods.fix(copyOfJ,temp);
								SchemeMethods.relabel(temp.right(),0);
	
								Scheme tempScheme = new Scheme();
								tempScheme.setHead(temp.right());
								tempScheme.setDepth(depth);
								
								boolean add=true;
								for(int m=0; m<array.size(); m++){
									if(SchemeMethods.compare(tempScheme,array.get(m))){
										add=false;
	
										/*System.out.print("1: ");
	    								SchemeMethods.printTest(array.get(m).getHead());
	    								System.out.println();
	    								System.out.print("2: ");
	    								SchemeMethods.printTest(temp);
	    								System.out.println();*/
	
										break;
									}
									else if(SchemeMethods.compare(array.get(m),tempScheme)){
										array.set(m,tempScheme);
										add=false;
										break;
									}
								}
	
								if(add)
									array.add(tempScheme);
	    					}
    					}
    				}
    			}
    		}
    	}

    	/* Short theorems based on depth, varcount, and size */
    	SchemeComparator comparator = new SchemeComparator();
    	Scheme[] list = new Scheme[0];
    	list = array.toArray(list);
    	Arrays.sort(list, comparator);
    	
    	/* Writes theorems to file */
    	PrintWriter writer = new PrintWriter(new File("Out" + MAX + "_" + LENGTH + "_" + PROB + ".dat"), "UTF-8");
    	for(int i=0; i<list.length; i++){
    		
    		int varCount = SchemeMethods.varCount(list[i].getHead());
    		int size = SchemeMethods.getSize(list[i].getHead());
    		
    		writer.print(list[i].getDepth() + "," + varCount + "," + size + ": ");
    		SchemeMethods.printTest(writer, list[i].getHead());
    		writer.println();
    	}
    	
    	writer.close();
    	
    }

}