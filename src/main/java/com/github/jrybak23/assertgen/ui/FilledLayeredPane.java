package com.github.jrybak23.assertgen.ui;

import javax.swing.*;
import java.awt.*;

class FilledLayeredPane extends JLayeredPane {
    /**
     * Layout each of the components in this JLayeredPane so that they all fill
     * the entire extents of the layered pane -- from (0,0) to (getWidth(), getHeight())
     */
    @Override
    public void doLayout() {
        // Synchronizing on getTreeLock, because I see other layouts doing that.
        // see BorderLayout::layoutContainer(Container)
        synchronized(getTreeLock()) {
            int w = getWidth();
            int h = getHeight();
            for(Component c : getComponents()) {
                c.setBounds(0, 0, w, h);
            }
        }
    }
}
