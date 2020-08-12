package concurrency.test;

import java.util.*;

public class SynTest1 {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			list.add(Integer.toString(i));
		}
		for (Iterator<String> it = list.iterator(); it.hasNext(); ) {
			String val = it.next();
			if (val.equals("5")) {
				//list.add(val); //报错
				list.remove(val);   //报错
//				it.remove();
			}
		}
	}
}
