package org.j2ts.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class Converter {

	public static String path = "C:\\Users\\oliver\\Google Drive\\dev\\iceberg_charts2\\src\\main";

	public static String destPath = "C:\\Users\\oliver\\Google Drive\\dev\\iceberg_charts2\\src\\test\\typeScriptTest";

	public static void main(String[] args) throws Exception {
		convert();
	}
	
	public static void convert() throws Exception {

		File file = new File(Converter.path);

		String[] exts = { "java" };

		Iterator it = FileUtils.iterateFiles(new File(path), exts, true);
		while (it.hasNext()) {

			File f = (File) it.next();

			CompilationUnit cu;

			parseFile(f);

		}
		System.out.println("FINISHED PARSING!!!");
	}
	
	public static void convert(String path) throws Exception {
		File f = new File(path);
		parseFile(f);
	}
	
	public static void convert(String path, String string2) throws Exception {
		File f = new File(path);
		
		Converter.destPath = string2;
		parseFile(f);
		
	}
	

	private static void parseFile(File f) throws Exception {

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
			
			String fileName = f.getName().substring(0, f.getName().indexOf(".")) + ".ts";
			
			File file = new File(Converter.destPath + "\\" + fileName);
			
			file.getParentFile().mkdirs();
			file.createNewFile();
			
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream(file), "utf-8"));
			    
			writer.write(sb.toString());
			
			writer.close();

		} catch(Exception e) {
			System.err.println("Error parsing this file " + f);
			throw e;
		}
		finally {
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
//			System.out.println(n.getName());
			super.visit(n, arg);
		}
	}



}
