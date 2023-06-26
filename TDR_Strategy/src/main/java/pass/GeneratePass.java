package pass;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.domain.Schedules;

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
        
        String useScenesList;
        List<Integer> scenes = new ArrayList<>();
        scenes.add(1);
        scenes.add(2);
        scenes.add(3);
        scenes.add(4);
		StringBuilder sb = new StringBuilder();
		Integer useScenesCount = 0;
		if(scenes.size() != 0) {
			for (int i = 0; i < scenes.size(); i++) {
				sb.append(scenes.get(i));
				if(i != scenes.size() -1) {
					sb.append(", ");
				}
				useScenesCount ++;
			}
//			System.out.println("if");
			useScenesList = sb.toString();
			
		} else {
			useScenesList = null;
		}
		System.out.println(useScenesList);
		System.out.println(useScenesCount);
		
		List<Schedules> searchResults = new ArrayList<>();
		if(searchResults.size() != 0) {
			System.out.println(searchResults);
		}
		
    }
}