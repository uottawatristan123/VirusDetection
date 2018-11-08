import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
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
	    		        		String data = "";
								try {
									data = new String(Files.readAllBytes(path));
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
	    		        		if(data.contains(virus))
	    		        		{
		 	    		    	    String cleansedFile = data.replaceAll(Pattern.quote(virus), replaceWith);
		 	    		    	    byte[] cleansedByteArray = cleansedFile.getBytes();
		 	    		    	    try {
										Files.write(Paths.get("src\\InfectedFiles\\Infected" + path.getFileName()), cleansedByteArray);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
	    		        		}
	    		        });	    		    
	    		} 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }    
	     
	}

}
