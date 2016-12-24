import java.util.Comparator;


public class SchemeComparator implements Comparator<Scheme> {

	@Override
	public int compare(Scheme s1, Scheme s2) {
		
		int depth1 = s1.getDepth();
		int depth2 = s2.getDepth();
		if(depth1 < depth2) return -1;
		else if(depth1 > depth2) return 1;
		
		int count1 = SchemeMethods.varCount(s1.getHead());
		int count2 = SchemeMethods.varCount(s2.getHead());
		if(count1 < count2) return -1;
		else if(count1 > count2) return 1;
		
		int size1 = SchemeMethods.getSize(s1.getHead());
		int size2 = SchemeMethods.getSize(s2.getHead());
		if(size1 < size2) return -1;
		else if(size1 > size2) return 1;
		
		return 0;
	}

}
