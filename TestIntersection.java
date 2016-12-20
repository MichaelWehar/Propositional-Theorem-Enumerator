/**
 * @(#)TestIntersection.java
 *
 *
 * @author Michael Wehar
 * @version 1.00 2011/6/24
 */


public class TestIntersection {

 	public static void main(String[] args){
    	String str1 = new String("[[B > [A > A]] > C]");
    	String str2 = new String("[[A > A] > C]");

    	Formula f1 = new Formula(SchemeMethods.convertString(str1));
    	Scheme s1 = new Scheme(f1);
    	Formula f2 = new Formula(SchemeMethods.convertString(str2));
    	Scheme s2 = new Scheme(f2);

		SchemeNode jHead = s1.getHead();
    	SchemeNode kHead = s2.getHead();

		System.out.print(": ");
    	SchemeMethods.printTest(jHead);
    	System.out.println();

		System.out.print(": ");
    	SchemeMethods.printTest(kHead);
    	System.out.println();

    	SchemeNode copyOfJ = new SchemeNode(jHead.getType());
    	SchemeMethods.copy(jHead,copyOfJ);
    	int p = SchemeMethods.relabel(copyOfJ,0);

		SchemeNode copyOfK = new SchemeNode(kHead.getType());
    	SchemeMethods.copy(kHead,copyOfK);
    	int q = SchemeMethods.relabel(copyOfK,p);

    	SchemeNode temp = SchemeMethods.intersection(copyOfJ,copyOfK,q);

    	if(temp != null){
			SchemeMethods.relabel(temp,0);

			Scheme tempScheme = new Scheme();
			tempScheme.setHead(temp);

    		System.out.print(": ");
    		SchemeMethods.printTest(tempScheme.getHead());
    		System.out.println();
    	}
    	else{
    		System.out.println("Failed.");
    	}
    }

}