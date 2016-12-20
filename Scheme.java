/**
 * @(#)Scheme.java
 *
 *
 * @author Michael Wehar
 * @version 1.00 2011/6/16
 */


public class Scheme {

	private SchemeNode head;

	public Scheme(){}

	public Scheme(SchemeNode a){
		head=a;
	}

    public Scheme(Formula f){
    	head = new SchemeNode();

    	if(f.length() != 0)
    		constructTree(f,head);
    }

	public SchemeNode getHead(){
		return head;
	}

	public void setHead(SchemeNode h){
		head = h;
	}

    public void constructTree(Formula f, SchemeNode top){

    	if(f.length() == 1){ // f is a single variable
    		if(f.get(0) > 3){
    			top.setType(f.get(0)-2);
    		}
    		return;
    	}

    	else if(f.get(0) == 0){ // f has a leading negation symbol
    		top.setType(0);
    		top.setRight(new SchemeNode());
    		constructTree(f.trim(1,f.length()), top.right());
    		return;
    	}

		else{
    		int count = 0;
    		for(int i=1; i<f.length(); i++){

    			if(f.get(i) == 2) count++;
    			else if(f.get(i) == 3) count--;

    			if(count == 0 && f.get(i+1) == 1){
    				top.setType(1);
    				top.setLeft(new SchemeNode());
    				top.setRight(new SchemeNode());
    				constructTree(f.trim(1,i+1),top.left());
    				constructTree(f.trim(i+2,f.length()-1),top.right());
    				return;
    			}
    		}
		}
    }

}