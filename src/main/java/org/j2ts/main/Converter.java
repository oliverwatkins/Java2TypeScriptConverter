package org.j2ts.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class Converter {

//	public static String path = "C:\\Users\\oliver\\Google Drive\\dev\\iceberg_charts2\\src\\main";

	public static String destPath = "c:\\initial\\dir\\generated\\usually\\here";

	private static String initialPath = "c:\\initial\\dir";

//	public static void main(String[] args) throws Exception {
//	}


//	public static void convert(String path) throws Exception {
//		File f = new File(path);
//		parseFile(f);
//	}
	


	public static void convert(String path, String destPath) throws Exception {
		File f = new File(path);

		deleteDestinationPath(new File(destPath));
		
		Converter.initialPath = path;
		Converter.destPath = destPath;
		
		if (f.isDirectory()) { // iterate
			parseDirectory(f);
		} else {
			parseFile(f);
		}
	}
	
	
	public static void deleteDestinationPath(File file) throws Exception {
		if (file.isDirectory()) { // iterate
			File[] directoryListing = file.listFiles();
			if (directoryListing != null) {
				for (File child : directoryListing) {
					child.delete();
				}
			}
			file.delete();
		}else {
			file.delete();
		}
	}
	
	
	private static void parseDirectory(File f) throws Exception {
		File[] directoryListing = f.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (child.isDirectory()) {
					parseDirectory(child);
					System.out.println(" child.isDirectory() " + child.isDirectory() + " " + child);
				}else {
					parseFile(child);
				}
			}
		}
	}

	/**
	 * process a file.
	 * @param f
	 * @throws Exception
	 */
	private static void parseFile(File f) throws Exception {
		
		System.out.println("parsing file : " + f);
		
		FileInputStream in = new FileInputStream(f);

		StringBuffer sb = new StringBuffer();
		try {

			CompilationUnit cu;
			// parse the file
			cu = JavaParser.parse(in);

			TypescriptConverter.appendClassOrInterfaceName(cu, sb);

			TypescriptConverter.appendFields(cu, sb);

			TypescriptConverter.appendConstructors(cu, sb);

			TypescriptConverter.appendMethods(cu, sb);

			sb.append("};");

			
			String javaFileName = f.getName();
			String typeScriptFileName = f.getName().substring(0, f.getName().indexOf(".")) + ".ts";
			

			//subfolder with filename
			String subFolderBit = f.getPath().substring(initialPath.length(), f.getPath().length());
			
//			System.out.println(" subFolderBit 1 with filename " + subFolderBit);
			
			if (subFolderBit.equals("")) {
				subFolderBit = "\\";
			}else {
				//snip off filename
				subFolderBit = subFolderBit.substring(0, subFolderBit.length() - javaFileName.length());
			}
			
			System.out.println(" subFolderBit 2 without filename " + subFolderBit);
			
					
			System.out.println(" child.isDirectory()  " + typeScriptFileName);

//			System.out.println(" f.getAbsolutePath() " + f.getAbsolutePath());
//			System.out.println(" f.getCanonicalPath()" + f.getCanonicalPath());
			System.out.println(" f.getPath()" + f.getPath());
			
			
			
			System.out.println(" destPath " + Converter.destPath);
			System.out.println(" intialPath " + Converter.initialPath);
			
			
			File file = new File(Converter.destPath + subFolderBit + typeScriptFileName);

			file.getParentFile().mkdirs();
			file.createNewFile();

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "utf-8"));

			writer.write(sb.toString());

			writer.close();

		} catch (Exception e) {
			System.err.println("Error parsing this file " + f);
			throw e;
		} finally {
			in.close();
		}
	}

	private static void appendFields(CompilationUnit cu, StringBuffer sb) {
		// TODO Auto-generated method stub

	}

	private static void appendClassOrInterfaceName(CompilationUnit cu,
			StringBuffer sb) {
		// TODO Auto-generated method stub

	}

	private static class MethodVisitor extends VoidVisitorAdapter {

		@Override
		public void visit(MethodDeclaration n, Object arg) {
			// here you can access the attributes of the method.
			// this method will be called for all methods in this
			// CompilationUnit, including inner class methods
			// System.out.println(n.getName());
			super.visit(n, arg);
		}
	}

}
