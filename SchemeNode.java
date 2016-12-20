/**
 * @(#)SchemeNode.java
 *
 *
 * @author Michael Wehar
 * @version 1.00 2011/6/16
 */


public class SchemeNode {

	private int type;

	private SchemeNode left;
	private SchemeNode right;

	public SchemeNode(){
    	type=-1;
    	left=null;
    	right=null;
    }

    public SchemeNode(int t){
		type=t;
    	left=null;
    	right=null;
    }

    public int getType(){
    	return type;
    }

    public void setType(int t){
    	type=t;
    }

    public SchemeNode left(){
    	return left;
    }

    public SchemeNode right(){
    	return right;
    }

    public void setLeft(SchemeNode l){
    	left=l;
    }

    public void setRight(SchemeNode r){
    	right=r;
    }
}