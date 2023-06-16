package pass;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePass {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("test1000"));

        List<String> list1 = new ArrayList<>();
        list1.add("1a");
        list1.add("2b");
        list1.add("3c");
        list1.add("4d");
        
        List<List<String>> listList = new ArrayList<>();
        
        for(int i=0; i < list1.size(); i++) {
        	List<String> list2 = new ArrayList<>();
        	list2.add(list1.get(i));
        	list2.add( i + "回目");
        	listList.add(list2);
        }
        System.out.println(listList);
        for(int i=0; i < listList.size(); i++) {
        	System.out.println(listList.get(i));
        	for(int j=0; j < listList.get(i).size(); j++) {
        		System.out.println(listList.get(i).get(j));
        	}
        }
    }
}