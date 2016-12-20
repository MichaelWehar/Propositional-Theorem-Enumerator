/**
 * @(#)Main.java
 *
 *
 * @author Michael Wehar
 * @version 1.00 2011/6/16
 * @updated 2016/12/19
 */

import java.util.ArrayList;

public class Main {

    public static void main(String[] args){
    	final int LENGTH = 5; // Represents the search depth
    	final int LIST = 20; // Represents the maximum value for the histogram
    	final int GAP = 100; // Increments before printing to show progress
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
    	for(int i=0; i<LENGTH; i++){
    		int n = array.size();

    		for(int j=0; j<n; j++){
    			for(int k=0; k<n; k++){

					count++;
					if(count%GAP == 0)
						System.out.println(count);

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

    	
    	/* Print all theorems */
    	System.out.println();
    	System.out.println("Theorems:");
    	for(int i=0; i<array.size(); i++){
    		System.out.print(": ");
    		SchemeMethods.printTest(array.get(i).getHead());
    		System.out.println();
    	}
    	

    	/* Histogram data for number of theorems of each length or variable count */
		int[] list = new int[LIST];
		int maxLength = 0;
		double totalLength = 0;

		int[] list2 = new int[LIST];
		int maxVarCount = 0;
		double totalVarCount = 0;

		for(int i=0; i<array.size(); i++){
			DynamicInteger j = new DynamicInteger(0);
			SchemeMethods.getSize(array.get(i).getHead(),j);

			int varCount = SchemeMethods.relabel(array.get(i).getHead(),0);

			if(j.getVal() < LIST) list[j.getVal()]++;
			if(varCount < LIST) list2[varCount]++;

			if(j.getVal() > maxLength) maxLength = j.getVal();
			if(varCount > maxVarCount) maxVarCount = varCount;

			totalLength+=j.getVal();
			totalVarCount+=varCount;

			//if(j.getVal() <= 3){
			//	System.out.print(": ");
    		//	SchemeMethods.printTest(array.get(i).getHead());
    		//	System.out.println();
			//}
		}

		/* Statistics 1 */
		System.out.println();
		System.out.println("Counting the number of theorems of given lengths: ");

    	for(int i=0; i<LIST; i++){
    		System.out.println(i + ": " + list[i] + ", " + list2[i]);
    	}
    	
    	
    	/* Statistics 2 */
		double avgLength=totalLength/array.size();
		double avgVarCount=totalVarCount/array.size();
		
		System.out.println();
		System.out.println("Maximum Scheme Length: " + maxLength);
		System.out.println("Maximum Variable Count: " + maxVarCount);
		System.out.println("Average Scheme Length: " + avgLength);
		System.out.println("Average Variable Count: " + avgVarCount);
    	System.out.println("Array Size: " + array.size());
    }

}