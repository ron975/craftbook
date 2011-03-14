package com.sk89q.craftbook.ic.families;

import org.bukkit.block.*;

import com.sk89q.craftbook.ic.*;
import com.sk89q.craftbook.ic.logic.*;
import com.sk89q.craftbook.util.*;

public class FZISO extends ICFamily.LogicICFamily {
    /**
     * Package-visible: we expect to always be able to just use the singleton instance in ICFamily.
     */
    FZISO() {
        super(new PPM());
    }
    
    public LogicChipState getState(Block center) {
        return new LCS(new ChipState.Basic(center, PPM));
    }
    
    public void applyState(LogicChipState state, Block center) {
        /* no-op for ZISOs, since they don't have any output. */
    }
    
    
    
    
    
    public static class PPM implements PinPositionMap {
        private PPM() {}
        
        public int getSize() {
            return 1;
        }
        
        public Block getBlock(Block center, int pin) {
            switch (pin) {
            case 1:     // output 1
                return center.getFace(SignUtil.getBack(center), 2);
            default:
                return null;
            }
        }
    }
    
    
    
    public static class LCS implements LogicChipState {
        private LCS(ChipState cs) {
            super();
            this.cs = cs;
        }
        
        private final ChipState cs;
        
        public boolean getIn(int n) {
            return get(n-1);
        }
        
        public int inputSize() {
            return 1;
        }
        
        public boolean getOut(int n) {
            return false;
        }
        
        public void setOut(int n, boolean value) {
            ;
        }
        
        /** 
         * Delegates directly to the backing ChipState. Use of LogicChipState's
         * more specific {@link #getIn(int)} and {@link #getOut(int)} methods is
         * preferred.
         */
        public boolean get(int pin) {
            return cs.get(pin);
        }

        /**
         * Delegates directly to the backing ChipState. Use of LogicChipState's
         * more specific {@link #setOut(int, boolean)} method is preferred.
         */
        public void set(int pin, boolean value) {
            cs.set(pin, value);
        }
    }
}