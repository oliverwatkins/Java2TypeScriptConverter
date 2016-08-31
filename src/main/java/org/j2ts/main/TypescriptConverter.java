package org.j2ts.main;

import java.io.ObjectInputStream.GetField;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;

public class TypescriptConverter {

	public static void appendClassOrInterfaceName(CompilationUnit cu,
			StringBuffer sb) {

		sb.append(" \n ");
		sb.append("//NOW APPENDING NAME");
		sb.append(" \n ");

		for (Node node : cu.getChildrenNodes()) {
			
			if (node instanceof ClassOrInterfaceDeclaration) {
				ClassOrInterfaceDeclaration c = ((ClassOrInterfaceDeclaration) node);

				sb.append("class " + c.getName() + " { ");
				sb.append("\n");
				break;
			}
		}

	}

	public static void appendFields(CompilationUnit cu, StringBuffer sb) {

		sb.append(" \n ");
		sb.append("//NOW APPENDING FIELDS");
		sb.append(" \n ");
		
		for (Node node : cu.getChildrenNodes()) {

			// ignore imports??
			if (node instanceof ClassOrInterfaceDeclaration) {
				List<Node> kiddies = node.getChildrenNodes();

				List<BodyDeclaration> members = ((ClassOrInterfaceDeclaration) node)
						.getMembers();

				for (BodyDeclaration bodyDeclaration : members) {
					
					if (bodyDeclaration instanceof FieldDeclaration) {
						
						
						FieldDeclaration fd = (FieldDeclaration)bodyDeclaration;
						fd.getType();
						List<VariableDeclarator> vds = fd.getVariables();
						
						sb.append(vds.get(0));
						sb.append(":");
						sb.append(fd.getType());
						sb.append(";");
						sb.append("\n");
						
						for (VariableDeclarator variableDeclarator : vds) {
							
						}
						
						sb.append(" \n ");
						sb.append(bodyDeclaration.toString());
					}
				}

			}
		}
	}

	public static void appendMethods(CompilationUnit cu, StringBuffer sb) {
		sb.append(" \n ");
		sb.append("//NOW APPENDING METHODS");
		sb.append(" \n ");

		List<Node> childs = cu.getChildrenNodes();

		for (Node node : childs) {

			// ignore imports??
			if (node instanceof ClassOrInterfaceDeclaration) {
				List<Node> kiddies = node.getChildrenNodes();

				List<BodyDeclaration> members = ((ClassOrInterfaceDeclaration) node)
						.getMembers();

				
				for (BodyDeclaration bodyDeclaration : members) {
					
					if (bodyDeclaration instanceof MethodDeclaration) {
						
						MethodDeclaration md = (MethodDeclaration)bodyDeclaration;
						List<Parameter> parameters = md.getParameters();
						String name = md.getName();
						BlockStmt bs = md.getBody();

						StringBuffer parameterString = createParameterString(name, parameters);

						sb.append("\t" + name + parameterString + "{");
						
						sb.append("\n");
						appendStatements(sb, bs);
						
						sb.append("\n");
						sb.append("\t}");
						sb.append("\n");
						sb.append("\n");
						

//						sb.append(" \n ");
//						sb.append(bodyDeclaration.toString());
					}
				}

			}
		}

	}

	private static void appendStatements(StringBuffer sb, BlockStmt bs) {
		if (bs != null) {
			
			for (Statement stmt : bs.getStmts()) {
				sb.append("\t\t" + stmt + "\n");
			}
		}
	}

	private static StringBuffer createParameterString(String name, List<Parameter> parameters) {
		StringBuffer parameterString = new StringBuffer("(");
		
		if (parameters.size() != 0) {
			for (Parameter parameter : parameters) {
				parameterString.append("" + parameter.getName() + ":" + parameter.getType() + ",");
			}
			parameterString.setLength(parameterString.length()-1);
		}
		
		parameterString.append(")");
		return parameterString;
	}

	public static void appendConstructors(CompilationUnit cu, StringBuffer sb) {
		sb.append(" \n ");
		sb.append("//NOW APPENDING CONSTRUCTORS");
		sb.append(" \n ");
		
		List<Node> childs = cu.getChildrenNodes();

		for (Node node : childs) {

			// ignore imports??
			if (node instanceof ClassOrInterfaceDeclaration) {
				List<Node> kiddies = node.getChildrenNodes();

				List<BodyDeclaration> members = ((ClassOrInterfaceDeclaration) node)
						.getMembers();

				
				for (BodyDeclaration bodyDeclaration : members) {
					
					if (bodyDeclaration instanceof ConstructorDeclaration) {
						
						ConstructorDeclaration md = (ConstructorDeclaration)bodyDeclaration;
						List<Parameter> parameters = md.getParameters();
						String name = md.getName();
						BlockStmt bs = md.getBlock();

						StringBuffer parameterString = createParameterString(name, parameters);
						
						sb.append("\n");
						sb.append("constructor");
						
						sb.append(parameterString);
						sb.append("{");

						sb.append("\n");
						appendStatements(sb, bs);
						sb.append("\n");
						sb.append("}");
					}
				}

			}
		}
	}

}
