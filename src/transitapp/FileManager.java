package transitapp;

import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class FileManager {
	
	public abstract void initialize(String file) throws FileNotFoundException, IOException;
}
