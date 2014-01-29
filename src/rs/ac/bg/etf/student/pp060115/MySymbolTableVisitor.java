package rs.ac.bg.etf.student.pp060115;
import rs.etf.pp1.symboltable.visitors.*;
import rs.etf.pp1.symboltable.concepts.*;


/**
 * Fixes when the dumping table read "this", and enter infinite loop.
 *
 */
 
class MySymbolTableVisitor extends SymbolTableVisitor {
	private StringBuilder output = new StringBuilder();
	private final String indent = "   ";
	private StringBuilder currentIndent = new StringBuilder();
  private boolean inClassStruct = false;
  private boolean firstTime = true;

	protected void nextIndentationLevel() {
		currentIndent.append(indent);
	}

	protected void previousIndentationLevel() {
		if (currentIndent.length() > 0)
			currentIndent.setLength(currentIndent.length()-indent.length());
	}
	
	protected StringBuilder currentIndentationLevel(){
		 return currentIndent;
	}

	@Override
	public void visitObjNode(Obj objToVisit) {
		output.append(currentIndentationLevel());
		switch (objToVisit.getKind()) {
		case Obj.Con:  output.append("Con "); break;
		case Obj.Var:  output.append("Var "); break;
		case Obj.Type: output.append("Type "); break;
		case Obj.Meth: output.append("Meth "); break;
		case Obj.Fld:  output.append("Fld "); break;
		case Obj.Prog: output.append("Prog "); break;
		}

		output.append(objToVisit.getName());
		output.append(": ");

        objToVisit.getType().accept(this);

		output.append(", ");
		output.append(objToVisit.getAdr());
		output.append(", ");
		output.append(objToVisit.getLevel() + " ");

		if (objToVisit.getKind() == Obj.Prog || objToVisit.getKind() == Obj.Meth) {
			output.append("\n");
			nextIndentationLevel();
		}


		for (Obj o : objToVisit.getLocalSymbols()) {
      // Jump over this parameter in method parameter list
      if (o.getName().equals("this") && objToVisit.getKind() == Obj.Meth)
          continue;

			output.append(currentIndent.toString());
			o.accept(this);
			output.append("\n");
		}

		if (objToVisit.getKind() == Obj.Prog || objToVisit.getKind() == Obj.Meth)
			previousIndentationLevel();

	}

	@Override
	public void visitScopeNode(Scope scope) {
		for (Obj o : scope.values()) {
            o.accept(this);
			output.append("\n");
		}
	}

	@Override
	public void visitStructNode(Struct structToVisit) {
		switch (structToVisit.getKind()) {
		case Struct.None:
			output.append("notype");
			break;
		case Struct.Int:
			output.append("int");
			break;
		case Struct.Char:
			output.append("char");
			break;
		case Struct.Array:
			output.append("Arr of ");
			
			switch (structToVisit.getElemType().getKind()) {
			case Struct.None:
				output.append("notype");
				break;
			case Struct.Int:
				output.append("int");
				break;
			case Struct.Char:
				output.append("char");
				break;
			case Struct.Class:
				output.append("Class");
				break;
			case 5 :
				output.append("Bool");
				break;
			case 6 :
				output.append("String");
				break;
			}
			break;
		case Struct.Class:
		      if (!inClassStruct) {
		          output.append("Class [");
		          inClassStruct = true;
		          
		          for (Obj obj : structToVisit.getMembers().symbols()) {
		        	 if( obj.getKind() == Obj.Meth){
		        	  output.append("\n\t");
		        	  firstTime=false;
		        	 }
		              obj.accept(this);
		          }
		         if(firstTime) output.append("]");
		         else output.append(currentIndent + "\t]");
		          inClassStruct = false;
		         
		      }
			break;
		case 5 :
			output.append("Bool");
			break;
		case 6 :
			output.append("String");
			break;
		}

	}

	public String getOutput() {
		return output.toString();
	}
}
