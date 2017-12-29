package org.j2ts.main;

import java.io.File;

import org.junit.Test;

public class Basic_Test {

	@Test
	public void myFirstTest() throws Exception {
		System.out.println("my first test");
		
		File f = new File("");
		System.out.println("f.getAbsolutePath() " + f.getCanonicalPath());
		
		Converter.convert(TestUtils.RESOURCE_FOLDER + "singleClassTests\\BasicTest_src.java", 
				TestUtils.RESOURCE_FOLDER_DESTINATION + "singleClassTests\\generated");
	}
}
