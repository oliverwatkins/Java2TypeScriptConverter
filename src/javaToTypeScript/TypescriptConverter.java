package javaToTypeScript;

import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.stmt.BlockStmt;

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

						StringBuffer parameterString = new StringBuffer("");
						for (Parameter parameter : parameters) {
							parameterString.append("");
						}

						sb.append("\t" + name + "(" + ")");

//						sb.append(" \n ");
//						sb.append(bodyDeclaration.toString());
					}
				}

			}
		}

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
						sb.append(" \n ");
						sb.append(bodyDeclaration.toString());
					}
				}

			}
		}
	}

}
