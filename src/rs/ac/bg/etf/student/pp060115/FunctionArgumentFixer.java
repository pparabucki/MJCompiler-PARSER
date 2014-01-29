package rs.ac.bg.etf.student.pp060115;


import java.util.LinkedList;
import java.util.Iterator;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

/**
 * Fixes function calling in argument checking of another function.
 * example: foo(a,b,foo2(z,y),c);
 */

 public class FunctionArgumentFixer {
  private class Function {
      Obj functionObj;
      int index = 0;

      public Function(Obj functionObj){
        this.functionObj = functionObj;
      }

      public Function(){
        this(Tab.noObj);
      }
  }

  private LinkedList<Function> functionStack = new LinkedList<Function>();
  private Function currentFunction = new Function();

  public void functionStart(Obj newFunctionObj){
    functionStack.push(currentFunction);
    currentFunction = new Function(newFunctionObj);
  }

  public void functionEnd(){
    currentFunction = functionStack.pop();
  }

  public boolean existsFunctionObj(){
    return currentFunction.functionObj != Tab.noObj;
  }

  public void incParameterIndex(){
    currentFunction.index++;
    //System.out.println("Func: " + currentFunction.functionObj.getName() + ", New index: " + currentFunction.index);
  }

  public boolean isIndexLowerThenArgumentNum (){
    return currentFunction.index < currentFunction.functionObj.getLevel();
  }

  public Obj getFunctionObj(){
    return currentFunction.functionObj;
  }

  public int getParameterIndex(){
    return currentFunction.index;
  }

  public void setFunctionObj(Obj newFunctionObj){
    currentFunction.functionObj = newFunctionObj;
  }

  public Obj getArgument(){
    Obj arg = Tab.noObj;
    for(Iterator it = currentFunction.functionObj.getLocalSymbols().iterator(); it.hasNext();){
      Obj tempObj = (Obj) it.next();
      if(tempObj.getFpPos()==currentFunction.index){
        arg = tempObj;
        break;
      }
     }
     return arg;
  }

 }