package ast;

import visitor.ASTVisitor;

public class VoidTypeTree extends AST{
	
	public VoidTypeTree(){
	}
	
	public Object accept(ASTVisitor v) {
        return v.visitBoolTypeTree(this);
    }

}
