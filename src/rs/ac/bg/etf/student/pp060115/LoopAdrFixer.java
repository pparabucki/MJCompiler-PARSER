package rs.ac.bg.etf.student.pp060115;

import java.util.Stack;

/** 
 * This is just simple wrapup for stack, so we can save loop top addresses.
 */
class LoopAdrFixer {
    private Stack<Integer> savedTopLoopAdrs;

    public LoopAdrFixer() {
        savedTopLoopAdrs = new Stack<Integer>();
    }

    public void saveLoopTop(int loopTopAddress) {
        savedTopLoopAdrs.push(loopTopAddress);
    }

    public int popCurrentLoopTop() {
        return savedTopLoopAdrs.pop();
    }
}