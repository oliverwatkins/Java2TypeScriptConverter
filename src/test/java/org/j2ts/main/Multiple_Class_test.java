package org.j2ts.main;

import java.io.File;

import org.junit.Test;

public class Multiple_Class_test {

	@Test
	public void myFirstTest() throws Exception {
		System.out.println("iterate through all classes in project");
		
		File f = new File("");
		System.out.println("f.getAbsolutePath() " + f.getCanonicalPath());
		
		Converter.convert(TestUtils.RESOURCE_FOLDER + "miniProject", 
				TestUtils.RESOURCE_FOLDER_DESTINATION + "miniProject\\generated");
		
	}
	
}
