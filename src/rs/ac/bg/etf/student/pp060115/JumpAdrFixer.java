package rs.ac.bg.etf.student.pp060115;

import java.util.Stack;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

/** 
 * This is class that helps me to deal with branches and loops.<br />
 *
 * On begining of loop/branch we don't know where is destination, so we call
 * newAdrLevel to make new stack of addresses. After that we add every address
 * that need fixing.<br />
 * 
 * After we finished with branch we call fixCurAdrLevel(curAdr), and it fixes
 * all addresses that we put...<br />
 *
 * HACK: It uses Code static class, which is nasty, but that's how system
 * is built.
 */
class JumpAdrFixer {
    
    // I used stack and linked list because of shortcut &&, || operators
    // every should have his own jmpAddress, then we have need for this.
    // In homework we would be satisfied with simplest implemenation, just
    // one stack of address to fix.
    /** 
     * This is just simple struct for keeping all addresses we have to
     * fix. It is linked list that points to prev level <previousLevel>
     */
    private class AddressLevel {
        public Stack<Integer> addressesToFix;
        public AddressLevel   previousLevel = null;

        public AddressLevel() {
            addressesToFix = new Stack<Integer>();
        }
    }

    private AddressLevel currentAdrLevel;

    public JumpAdrFixer() {
        currentAdrLevel = null;
    }

    /** 
     * Create new level, every address that is put in this level will be fixed
     * with fixCurAdrLevel.
     */
    public void newAdrLevel(int addrToFix) {
        if (currentAdrLevel == null) {
            // Make head of a list
            currentAdrLevel               = new AddressLevel();
            currentAdrLevel.previousLevel = null;
        }
        else {
            // Append to list
            AddressLevel tmp              = currentAdrLevel;
            currentAdrLevel               = new AddressLevel();
            currentAdrLevel.previousLevel = tmp;
        }

        currentAdrLevel.addressesToFix.push(addrToFix);
    }
    
    /** 
     * It returns first address that was put in this level. 
     * 
     */
    public int getTopAdr() {
        return currentAdrLevel.addressesToFix.firstElement();
    }

    public void fixCurAdrLevel(int absAdrOfBlockEnd) {
        for (Integer i: currentAdrLevel.addressesToFix) {
            int jmpSrc = i.intValue();
            int addDst = i.intValue() + 1; // 1 is jmp op length
            // For all jumps we use relative addressing
            Code.put2(addDst, absAdrOfBlockEnd - jmpSrc);
        }
        currentAdrLevel = currentAdrLevel.previousLevel;
    }

    public void dumpFor(String command) {
        System.out.println("# " + command + ":");
        System.out.println("DUMP:");
        AddressLevel tmp = currentAdrLevel;
        while (tmp != null) {
            if (tmp.addressesToFix.size() == 0)
                System.out.println("[]");
            else
                System.out.println(tmp.addressesToFix);
            tmp = tmp.previousLevel;
        }
    }
}