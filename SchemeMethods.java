/**
 * @(#)SchemeMethods.java
 *
 *
 * @author Michael Wehar
 * @version 1.00 2011/6/18
 */
import java.util.ArrayList;

public final class SchemeMethods {

///////////// PRINT /////////////

	public static void printTest(SchemeNode top){
		if(top == null) return;

		if(top.getType() == 0){
			System.out.print("-");
			printTest(top.right());
		}
		else if(top.getType() == 1){
			System.out.print("[");
			printTest(top.left());
			System.out.print(" > ");
			printTest(top.right());
			System.out.print("]");
		}
		else{
			System.out.print((char)(top.getType()-2+65));
		}
	}

	public static void getSize(SchemeNode top, DynamicInteger temp){
		if(top == null) return;

		if(top.getType() == 0){
			temp.setVal(temp.getVal()+1);
			getSize(top.right(),temp);
		}
		else if(top.getType() == 1){
			temp.setVal(temp.getVal()+1);
			getSize(top.left(),temp);
			getSize(top.right(),temp);
		}
		//else{
		//	temp.setVal(temp.getVal()+1);
		//}
	}

	public static ArrayList<Integer> convertString(String s){
   		ArrayList<Integer> string = new ArrayList<Integer>();

   		for(int i=0; i<s.length(); i++){
   			if((int)s.charAt(i) >= 65 && (int)s.charAt(i) <= 90)
   				string.add((int)s.charAt(i)-65+4);
   			else if(s.charAt(i) == '-')
   				string.add(0);
   			else if(s.charAt(i) == '>')
   				string.add(1);
   			else if(s.charAt(i) == '[')
   				string.add(2);
   			else if(s.charAt(i) == ']')
   				string.add(3);
   		}

   		return string;
   	}

///////////// COPY AND SUBSTITUTION /////////////

   	public static Scheme copyScheme(Scheme temp){
   		Scheme s = new Scheme();

   		SchemeNode a = temp.getHead();
   		SchemeNode b = new SchemeNode(a.getType());

   		s.setHead(b);

   		copy(a,b);

   		return s;
   	}

	public static void substitution(SchemeNode a, SchemeNode b, int type){ // type's are replaced by b in a
		int n = a.getType();

		if(n == 0){
			substitution(a.right(),b,type);
		}
		else if(n == 1){
			substitution(a.left(),b,type);
			substitution(a.right(),b,type);
		}
		else if(a.getType() == type){
   			a.setType(b.getType());
   			copy(b,a);
   		}
   	}

   	public static void copy(SchemeNode a, SchemeNode b){ // b is the copy
   		int temp = a.getType();

   		if(temp==0){
   			b.setRight(new SchemeNode(a.right().getType()));
   			copy(a.right(),b.right());
   		}
   		else if(temp==1){
   			b.setLeft(new SchemeNode(a.left().getType()));
   			b.setRight(new SchemeNode(a.right().getType()));
   			copy(a.left(),b.left());
   			copy(a.right(),b.right());
   		}
   	}

///////////// COMPARE /////////////

   	public static boolean compare(Scheme x, Scheme y){
   		// is a a subscheme of b?  This algorithm could be faster.
		SchemeNode a = x.getHead();
		SchemeNode b = y.getHead();

		ArrayList<SchemeNode> array = new ArrayList<SchemeNode>();

		return buildArray(a,b,array);
   	}

   	public static boolean buildArray(SchemeNode a, SchemeNode b, ArrayList<SchemeNode> array){
   		// if(a==null) return false; not needed anymore

   		int type = b.getType();

		if(type >= 2){
			if(array.size() <= type)
				ensureCapacity(array,type+1);

			if(array.get(type-2) == null){
				array.set(type-2,a);
				return true;
			}
			else
				return equals(array.get(type-2),a);
		}
		else if(type == 0){
			if(a.getType() == type){
				return buildArray(a.right(),b.right(),array);
			}
			else
				return false;
		}
		else{
			if(a.getType() == type){
				return buildArray(a.left(),b.left(),array) && buildArray(a.right(),b.right(),array);
			}
			else
				return false;
		}
   	}

   	public static boolean equals(SchemeNode a, SchemeNode b){
   		if(a.getType() == b.getType()){
   			if(a.getType() >= 2)
   				return true;
   			else if(a.getType() == 0)
   				return equals(a.right(),b.right());
   			else
   				return equals(a.left(),b.left()) && equals(a.right(),b.right());
   		}
   		else return false;
   	}

   	public static boolean contains(SchemeNode a, int n){
   		if(a.getType() == n)
   			return true;
   		else if(a.getType() == 0)
   			return contains(a.right(),n);
   		else if(a.getType() == 1)
   			return contains(a.left(),n) || contains(a.right(),n);
   		else
   			return false;
   	}

///////////// INTERSECTION /////////////

	public static SchemeNode intersection(SchemeNode a, SchemeNode b, int q){
   		DynamicInteger cap = new DynamicInteger(q);

   		SchemeNode temp = new SchemeNode(b.getType());
   		copy(b,temp);

   		if(!resize(a,temp,temp,cap)) return null;

		//int count=0;

		//System.out.print(": ");
    	//SchemeMethods.printTest(temp);
    	//System.out.println();

		int x=1;
		while(x>0){
			//count++;
			//System.out.println(count);
			x=constructIntersection(a,temp);
		}

   		if(x == 0)
   			return temp;
   		else
   			return null;
   	}

   	public static boolean resize(SchemeNode a, SchemeNode b, SchemeNode topOfB, DynamicInteger cap){
   		int n = a.getType();
   		int m = b.getType();

   		if(n < 2 && m >= 2){
   			SchemeNode temp = new SchemeNode(a.getType());
   			copy(a,temp);
   			cap.setVal(relabel(temp,cap.getVal()));
   			substitution(topOfB,temp,m);
   			return true;
   		}
   		else if(n == 1){
   			if(m != 1)
   				return false;
   			else
   				return resize(a.left(),b.left(),topOfB,cap) && resize(a.right(),b.right(),topOfB,cap);
   		}
   		else if(n == 0){
   			if(m != 0)
   				return false;
   			else
   				return resize(a.right(),b.right(),topOfB,cap);
   		}
   		else
   			return true;
   	}

	public static int constructIntersection(SchemeNode a, SchemeNode b){
   		ArrayList<SchemeNode> x = new ArrayList<SchemeNode>();
   		DynamicInteger p = new DynamicInteger(0);

   		buildFixingArray(x,b,a,b,p);

   		return p.getVal();
   	}

	public static void buildFixingArray(ArrayList<SchemeNode> x, SchemeNode temp, SchemeNode a, SchemeNode top, DynamicInteger p){
		if(p.getVal() != -1){
			int n = a.getType();
			if(n==1){
				buildFixingArray(x,temp.left(),a.left(),top,p);
				buildFixingArray(x,temp.right(),a.right(),top,p);
			}
			else if(n==0){
				buildFixingArray(x,temp.right(),a.right(),top,p);
			}
			else{
				if(x.size() <= n-2 || x.get(n-2) == null){
					ensureCapacity(x,n-2+1);
					x.set(n-2,temp);
				}
				else if(!equals(x.get(n-2),temp)){
					fixPair(x.get(n-2),temp,top,p);
				}
			}
		}
	}

	public static void fixPair(SchemeNode a, SchemeNode b, SchemeNode top, DynamicInteger p){
		if(p.getVal() != -1){
			int n = a.getType();
			int m = b.getType();

			if(n==1 && m==1){
				fixPair(a.left(),b.left(),top,p);
				fixPair(a.right(),b.right(),top,p);
			}
			else if(n==0 && m==0){
				fixPair(a.right(),b.right(),top,p);
			}
			else if(n>=2){
				if(n!=m){
					if(contains(b,n)){
						p.setVal(-1);
					}
					else{
						substitution(top,b,n);
						p.setVal(1);
					}
				}
			}
			else if(m>=2){
				if(n!=m){
					if(contains(a,m)){
						p.setVal(-1);
					}
					else{
						substitution(top,a,m);
						p.setVal(1);
					}
				}
			}
			else{
				p.setVal(-1);
			}
		}
	}

   	public static void buildSubstitutionArray(ArrayList<SchemeNode> x, SchemeNode temp, SchemeNode a){
		int n = a.getType();
		if(n==1){
			buildSubstitutionArray(x,temp.left(),a.left());
			buildSubstitutionArray(x,temp.right(),a.right());
		}
		else if(n==0){
			buildSubstitutionArray(x,temp.right(),a.right());
		}
		else{
			if(x.size() <= n-2 || x.get(n-2) == null){
				SchemeNode temp2 = new SchemeNode();
				temp2.setType(temp.getType());
				copy(temp,temp2);
				ensureCapacity(x,n-2+1);
				x.set(n-2,temp2);
			}
		}
   	}

   	public static void buildNode(ArrayList<SchemeNode> x, SchemeNode temp, SchemeNode a){
		int n = a.getType();
		if(n==1){
			temp.setType(1);
			temp.setLeft(new SchemeNode());
			temp.setRight(new SchemeNode());
			buildNode(x,temp.left(),a.left());
			buildNode(x,temp.right(),a.right());
		}
		else if(n==0){
			temp.setType(0);
			temp.setRight(new SchemeNode());
			buildNode(x,temp.right(),a.right());
		}
		else{
			if(x.size() <= n-2 || x.get(n-2) == null){
				temp.setType(n);
			}
			else{
				temp.setType(x.get(n-2).getType());
				copy(x.get(n-2),temp);
			}
		}
   	}

///////////// IMPLICATION PART OF INTERSECTION /////////////

   	public static SchemeNode fix(SchemeNode node, SchemeNode temp){
   		ArrayList<SchemeNode> array = new ArrayList<SchemeNode>();
   		buildSubstitutionArray(array,temp,node.left());

   		SchemeNode temp2 = new SchemeNode(node.right().getType());
		buildNode(array,temp2,node.right());

		SchemeNode result = new SchemeNode(1);
		result.setLeft(new SchemeNode(temp.getType()));
		result.setRight(new SchemeNode(temp2.getType()));
		copy(temp,result.left());
		copy(temp2,result.right());

		return result;
   	}

///////////// RELABELING /////////////

   	public static int relabel(SchemeNode a, int n){
   		ArrayList<Integer> temp = new ArrayList<Integer>();
   		DynamicInteger cap = new DynamicInteger(n);
   		label(temp,cap,a);
   		return cap.getVal();
   	}

	public static void label(ArrayList<Integer> array, DynamicInteger cap, SchemeNode a){
		int type = a.getType();
		if(type == 1){
			label(array,cap,a.left());
			label(array,cap,a.right());
		}
		else if(type == 0){
			label(array,cap,a.right());
		}
		else{
			if(type-2 >= array.size() || array.get(type-2) == null){
				ensureCapacity(array,type-2+1);
				array.set(type-2,cap.getVal()+2);
				a.setType(cap.getVal()+2);
				cap.setVal(cap.getVal()+1);
			}
			else{
				a.setType(array.get(type-2));
			}
		}
	}

///////////// CAPACITY /////////////

	public static void ensureCapacity(ArrayList a, int n){
		int size = a.size();

		if(n > size){
			for(int i=n-size; i>0; i--){
				a.add(null);
			}
		}
	}
}