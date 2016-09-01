package org.j2ts.main;

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
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.type.Type;

public class TypescriptConverter {

	public static void appendClassOrInterfaceName(CompilationUnit cu,
			StringBuffer sb) {

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

		for (Node node : cu.getChildrenNodes()) {

			// ignore imports??
			if (node instanceof ClassOrInterfaceDeclaration) {

				List<BodyDeclaration> members = ((ClassOrInterfaceDeclaration) node).getMembers();

				for (BodyDeclaration bodyDeclaration : members) {

					if (bodyDeclaration instanceof FieldDeclaration) {

						processField(sb, bodyDeclaration);
						// sb.append(bodyDeclaration.toString());
					}
				}

			}
		}
	}
	
	public static void appendMethods(CompilationUnit cu, StringBuffer sb) {

		List<Node> childs = cu.getChildrenNodes();

		for (Node node : childs) {

			// ignore imports??
			if (node instanceof ClassOrInterfaceDeclaration) {

				List<BodyDeclaration> members = ((ClassOrInterfaceDeclaration) node)
						.getMembers();

				for (BodyDeclaration bodyDeclaration : members) {

					if (bodyDeclaration instanceof MethodDeclaration) {
						
						processMethod(sb, (MethodDeclaration)bodyDeclaration);
					}
				}
			}
		}
	}
	
	public static void appendConstructors(CompilationUnit cu, StringBuffer sb) {

		List<Node> childs = cu.getChildrenNodes();

		for (Node node : childs) {

			// ignore imports??
			if (node instanceof ClassOrInterfaceDeclaration) {

				List<BodyDeclaration> members = ((ClassOrInterfaceDeclaration) node)
						.getMembers();

				for (BodyDeclaration bodyDeclaration : members) {

					if (bodyDeclaration instanceof ConstructorDeclaration) {
						
						processConstructor(sb, bodyDeclaration);

					}
				}

			}
		}
	}

	private static void processField(StringBuffer sb,
			BodyDeclaration bodyDeclaration) {
		FieldDeclaration fd = (FieldDeclaration) bodyDeclaration;
		fd.getType();
		List<VariableDeclarator> vds = fd.getVariables();

		sb.append("\t");
		sb.append(vds.get(0).toString().trim());
		sb.append(":");
		sb.append(getType(fd.getType()));
		sb.append(";");
		sb.append("\n");

		for (VariableDeclarator variableDeclarator : vds) {

			//TODO for now we just take the first variable.
			//currently cannot deal with things like : "int var1, var2;"
		}

		sb.append(" \n ");
	}



	private static void processMethod(StringBuffer sb,
			MethodDeclaration bodyDeclaration) {
		int indentationLevel = 1;
		
		MethodDeclaration md = bodyDeclaration;
		List<Parameter> parameters = md.getParameters();
		String name = md.getName();
		BlockStmt bs = md.getBody();

		StringBuffer parameterString = createParameterString(
				name, parameters);

		String returnStr = "";

		if (md.getType() != null) {
			returnStr = getType(md.getType());
		}

		sb.append("\t" + name + parameterString + " " + returnStr + "{");

		sb.append("\n");
		
		processBlockStmt(bs, sb, indentationLevel);

		sb.append("\n");
		sb.append("\t}");
		sb.append("\n");
	}
	


	private static void processConstructor(StringBuffer sb,
			BodyDeclaration bodyDeclaration) {
		int indentationLevel = 1;
		
		ConstructorDeclaration md = (ConstructorDeclaration) bodyDeclaration;
		List<Parameter> parameters = md.getParameters();
		String name = md.getName();
		BlockStmt bs = md.getBlock();

		StringBuffer parameterString = createParameterString(
				name, parameters);

		sb.append("\n");
		sb.append("\t");
		sb.append("constructor");

		sb.append(parameterString);
		sb.append("{");

		sb.append("\n");
		processBlockStmt(bs, sb, indentationLevel);
//		appendStatements(sb, bs, indentationLevel);
		sb.append("\n");
		sb.append("\t");
		sb.append("}");
		sb.append("\n");
		sb.append("\n");
	}
	
	private static String indent(int indentationLevel) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < indentationLevel; i++) {
			sb.append("\t");
		}
		return sb.toString();
	}

	private static void appendStatements(StringBuffer sb, BlockStmt bs, int indentationLevel) {
		if (bs != null) {
			
			for (Statement stmt : bs.getStmts()) {
				
				if (stmt instanceof ExpressionStmt) {
					sb.append(indent(indentationLevel) + stmt.toString().trim() + "\n");
					
				}else if (stmt instanceof IfStmt) {
					processIfStmt((IfStmt)stmt, sb, indentationLevel);
				}else if (stmt instanceof WhileStmt) {
					processWhileStmt((WhileStmt)stmt, sb, indentationLevel);
				}
			}
		}
	}

	private static void processWhileStmt(WhileStmt stmt, StringBuffer sb, int indentationLevel) {
		
		System.out.println("Processing IF statement");
		
		Expression condition = stmt.getCondition();
		Statement bodyStmt = stmt.getBody();
		
		sb.append(indent(indentationLevel) + "while(");
		sb.append(condition);
		sb.append("){");
		sb.append("\n");
		processStatement(bodyStmt, sb, indentationLevel);
		sb.append(indent(indentationLevel) + "}");
		sb.append("\n");
		
	}

	private static void processIfStmt(IfStmt stmt, StringBuffer sb, int indentationLevel) {
		
		
		System.out.println("Processing IF statement");
		
		Expression condition = stmt.getCondition();
		Statement thenStmt = stmt.getThenStmt();
		Statement elseStmt = stmt.getElseStmt();
		
		sb.append(indent(indentationLevel) + "if(");
		sb.append(condition);
		sb.append("){");
		sb.append("\n");
		processStatement(thenStmt, sb, indentationLevel);
		sb.append(indent(indentationLevel) + "}");
		sb.append("\n");
	}



	private static void processStatement(Statement statement, StringBuffer sb,
			int indentationLevel) {

		if (statement instanceof BlockStmt)  {
			processBlockStmt((BlockStmt)statement, sb, indentationLevel);
		}if (statement instanceof ExpressionStmt)  {
			processExpressionStmt((ExpressionStmt)statement, sb, indentationLevel);
		} else if (statement instanceof IfStmt)  {
			processIfStmt((IfStmt)statement, sb, indentationLevel);
		} else if (statement instanceof WhileStmt)  {
			processWhileStmt((WhileStmt)statement, sb, indentationLevel);
		} else if (statement instanceof ReturnStmt)  {
			processReturnStmt((ReturnStmt)statement, sb, indentationLevel);
		}
		
		
	}

	


	private static void processBlockStmt(BlockStmt blockStmt,
			StringBuffer sb, int indentationLevel) {
		System.out.println("BLOCK statmemt");
		indentationLevel++;
		
		List<Statement> stmts = blockStmt.getStmts();
		for (Statement statement : stmts) {
			
			processStatement(statement, sb, indentationLevel);
			
			System.out.println("statement " + statement);
		}
	}

	private static void processExpressionStmt(ExpressionStmt statement,
			StringBuffer sb, int indentationLevel) {
		
		sb.append(indent(indentationLevel) + statement.toString());
		sb.append("\n");
	}
	
	private static void processReturnStmt(ReturnStmt statement,
			StringBuffer sb, int indentationLevel) {
		sb.append(indent(indentationLevel) + statement.toString());
		sb.append("\n");
		
	}
	

	private static StringBuffer createParameterString(String name,
			List<Parameter> parameters) {
		StringBuffer parameterString = new StringBuffer("(");

		if (parameters.size() != 0) {
			for (Parameter parameter : parameters) {

				String type = getType(parameter.getType());

				parameterString.append("" + parameter.getName() + ":" + type
						+ ",");
			}
			parameterString.setLength(parameterString.length() - 1);
		}

		parameterString.append(")");
		return parameterString;
	}

	private static String getType(Type type) {

		String tStr = type.toString();

		String returnVal = "";

		switch (tStr) {
		case "String":
			returnVal = "string";
			break;
		case "int":
			returnVal = "number";
			break;
		case "Integer":
			returnVal = "number";
			break;
		case "Double":
			returnVal = "number";
			break;
		case "double":
			returnVal = "number";
			break;
		default:
			returnVal = tStr;
			break;
		}

		return returnVal;
	}



}
