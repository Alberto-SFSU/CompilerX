package ast;

import visitor.ASTVisitor;

public class FloatTypeTree extends AST{
	
	public FloatTypeTree() {
	}
	
	public Object accept(ASTVisitor v) {
        return v.visitBoolTypeTree(this);
    }

}
