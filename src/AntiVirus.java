import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AntiVirus {

	public static void main(String[] args) {
		String replaceWith = "xxxxxxxx";
		// pass the path to the file as a parameter 
	    File file = 
	      new File("src\\VirusDefinitions.txt"); 
	    Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	  
		List<String> virusDefinitions = new LinkedList<String>();
	    while (sc.hasNextLine()) 
	      virusDefinitions.add(sc.nextLine());
	    
	    
	    for(String virus : virusDefinitions)
	    {
	    	try {
	    		try (Stream<Path> paths = Files.walk(Paths.get("src\\FilesToVerify"))) {
	    		    paths
	    		        .filter(Files::isRegularFile)
	    		        .forEach(path -> {
	    		        	try (Stream<String> lines = Files.lines(path)) {
	 	    		    	   List<String> replaced = lines
	 	    		    	       .map(line-> line.replaceAll(virus, replaceWith))
	 	    		    	       .collect(Collectors.toList());
	 	    		    	   Files.write(Paths.get("src\\InfectedFiles" + path.getFileName()), replaced);
	 	    		    	} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	    		        });	    		    
	    		} 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }    
	     
	}
	
	public static String readFileAsString(String fileName)throws Exception 
    { 
      String data = ""; 
      data = new String(Files.readAllBytes(Paths.get(fileName))); 
      return data; 
    }

}
