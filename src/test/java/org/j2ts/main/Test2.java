package org.j2ts.main;

import java.io.File;

import org.junit.Test;

public class Test2 {

	@Test
	public void myFirstTest() throws Exception {
		System.out.println("my first test");
		
		File f = new File("");
		System.out.println("f.getAbsolutePath() " + f.getCanonicalPath());
		
		Converter.convert("src\\test\\resource\\Test2_src.java", "src\\test\\resource\\generated");
	}
}
