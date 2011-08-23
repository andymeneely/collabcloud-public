package org.chaoticbits.collabcloud.codeprocessor.java;

import japa.parser.ast.BlockComment;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.LineComment;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.AnnotationDeclaration;
import japa.parser.ast.body.AnnotationMemberDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.EmptyMemberDeclaration;
import japa.parser.ast.body.EmptyTypeDeclaration;
import japa.parser.ast.body.EnumConstantDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.InitializerDeclaration;
import japa.parser.ast.body.JavadocComment;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.ArrayAccessExpr;
import japa.parser.ast.expr.ArrayCreationExpr;
import japa.parser.ast.expr.ArrayInitializerExpr;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.BooleanLiteralExpr;
import japa.parser.ast.expr.CastExpr;
import japa.parser.ast.expr.CharLiteralExpr;
import japa.parser.ast.expr.ClassExpr;
import japa.parser.ast.expr.ConditionalExpr;
import japa.parser.ast.expr.DoubleLiteralExpr;
import japa.parser.ast.expr.EnclosedExpr;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.InstanceOfExpr;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.IntegerLiteralMinValueExpr;
import japa.parser.ast.expr.LongLiteralExpr;
import japa.parser.ast.expr.LongLiteralMinValueExpr;
import japa.parser.ast.expr.MarkerAnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.NullLiteralExpr;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.expr.QualifiedNameExpr;
import japa.parser.ast.expr.SingleMemberAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.expr.SuperExpr;
import japa.parser.ast.expr.ThisExpr;
import japa.parser.ast.expr.UnaryExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.AssertStmt;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.BreakStmt;
import japa.parser.ast.stmt.CatchClause;
import japa.parser.ast.stmt.ContinueStmt;
import japa.parser.ast.stmt.DoStmt;
import japa.parser.ast.stmt.EmptyStmt;
import japa.parser.ast.stmt.ExplicitConstructorInvocationStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.ForeachStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.LabeledStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.stmt.SwitchEntryStmt;
import japa.parser.ast.stmt.SwitchStmt;
import japa.parser.ast.stmt.SynchronizedStmt;
import japa.parser.ast.stmt.ThrowStmt;
import japa.parser.ast.stmt.TryStmt;
import japa.parser.ast.stmt.TypeDeclarationStmt;
import japa.parser.ast.stmt.WhileStmt;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.VoidType;
import japa.parser.ast.type.WildcardType;
import japa.parser.ast.visitor.GenericVisitorAdapter;

/**
 * A basic modification to the adapter where R=A (arg type is the same as the return), and you just return
 * that arg. Good for traversing the AST and collecting things.
 * @author andy
 * 
 * @param <R>
 */
public class ReturnArgVisitorAdapter<R> extends GenericVisitorAdapter<R, R> {

	public R visit(CompilationUnit n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(PackageDeclaration n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ImportDeclaration n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(TypeParameter n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(LineComment n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(BlockComment n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ClassOrInterfaceDeclaration n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(EnumDeclaration n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(EmptyTypeDeclaration n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(EnumConstantDeclaration n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(AnnotationDeclaration n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(AnnotationMemberDeclaration n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(FieldDeclaration n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(VariableDeclarator n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(VariableDeclaratorId n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ConstructorDeclaration n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(MethodDeclaration n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(Parameter n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(EmptyMemberDeclaration n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(InitializerDeclaration n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(JavadocComment n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ClassOrInterfaceType n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(PrimitiveType n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ReferenceType n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(VoidType n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(WildcardType n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ArrayAccessExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ArrayCreationExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ArrayInitializerExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(AssignExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(BinaryExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(CastExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ClassExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ConditionalExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(EnclosedExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(FieldAccessExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(InstanceOfExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(StringLiteralExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(IntegerLiteralExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(LongLiteralExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(IntegerLiteralMinValueExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(LongLiteralMinValueExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(CharLiteralExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(DoubleLiteralExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(BooleanLiteralExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(NullLiteralExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(MethodCallExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(NameExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ObjectCreationExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(QualifiedNameExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ThisExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(SuperExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(UnaryExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(VariableDeclarationExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(MarkerAnnotationExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(SingleMemberAnnotationExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(NormalAnnotationExpr n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(MemberValuePair n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ExplicitConstructorInvocationStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(TypeDeclarationStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(AssertStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(BlockStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(LabeledStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(EmptyStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ExpressionStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(SwitchStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(SwitchEntryStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(BreakStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ReturnStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(IfStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(WhileStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ContinueStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(DoStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ForeachStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ForStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(ThrowStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(SynchronizedStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(TryStmt n, R arg) {
		super.visit(n, arg);
		return arg;
	}

	public R visit(CatchClause n, R arg) {
		super.visit(n, arg);
		return arg;
	}

}
