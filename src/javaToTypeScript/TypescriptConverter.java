package javaToTypeScript;

import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.NameExpr;

public class TypescriptConverter {

	public static void appendClassOrInterfaceName(CompilationUnit cu,
			StringBuffer sb) {

		sb.append(" ");
		sb.append("NOW APPENDING NAME");
		sb.append(" ");

		for (Node node : cu.getChildrenNodes()) {

			ClassOrInterfaceDeclaration c = ((ClassOrInterfaceDeclaration) node);

			sb.append(c.getName());
			break;

		}

	}

	public static void appendFields(CompilationUnit cu, StringBuffer sb) {

		sb.append(" ");
		sb.append("NOW APPENDING FIELDS");
		sb.append(" ");

		for (Node node : cu.getChildrenNodes()) {

			// ignore imports??
			if (node instanceof ClassOrInterfaceDeclaration) {
				List<Node> kiddies = node.getChildrenNodes();
				for (Node node2 : kiddies) {
					if (node2 instanceof NameExpr) {
						NameExpr n = (NameExpr) node2;
						sb.append(n.toString());
					}
				}
			}
		}
	}

	public static void appendMethods(CompilationUnit cu, StringBuffer sb) {
		sb.append(" ");
		sb.append("NOW APPENDING METHODS");
		sb.append(" ");

		List<Node> childs = cu.getChildrenNodes();

		for (Node node : childs) {

			// ignore imports??
			if (node instanceof ClassOrInterfaceDeclaration) {
				List<Node> kiddies = node.getChildrenNodes();

				List<BodyDeclaration> members = ((ClassOrInterfaceDeclaration) node)
						.getMembers();

				for (BodyDeclaration bodyDeclaration : members) {
					System.out.println("member : ");
					System.out.println(bodyDeclaration);
					System.out.println(" / member");

					sb.append(bodyDeclaration.toString());
				}

				for (Node node2 : kiddies) {

					if (node2 instanceof NameExpr) {

						NameExpr n = (NameExpr) node2;

						sb.append(n.toString());

						// node2

					}

					// System.out.println("node2 = " + node2);
				}
			}
		}

	}

	public static void appendConstructors(CompilationUnit cu, StringBuffer sb) {
		sb.append(" ");
		sb.append("NOW APPENDING CONSTRUCTORS");
		sb.append(" ");
	}

}
