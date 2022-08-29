package com.github.jrybak23.assertgen.ui;

import com.github.jrybak23.assertgen.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UI {

    private static final String WINDOW_TITLE = "AssertGen";
    private static final ImageIcon DEFAULT_ICON = createIcon("/copy-default.png");
    private static final ImageIcon ONHOVER_ICON = createIcon("/copy-onhover.png");

    private static ImageIcon createIcon(String name) {
        return new ImageIcon(UI.class.getResource(name));
    }

    private final JFrame frame;
    private final Controller controller;

    public UI(Controller controller) {
        this.controller = controller;
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = initFrame();
        JButton copyButton = createCopyButton();
        JTextPane resultTextPane = createResultTextPanel();

        JScrollPane scrollPane = new JScrollPane(resultTextPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JLayeredPane layeredPane = new FilledLayeredPane();
        addButtonInsideResultPane(copyButton, layeredPane);
        layeredPane.add(scrollPane);
        frame.add(layeredPane, BorderLayout.CENTER);
    }

    private static JFrame initFrame() {
        JFrame frame = new JFrame(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        Dimension maxScreenSize = getScreenSize();
        frame.setMaximumSize(maxScreenSize);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());
        return frame;
    }

    private JButton createCopyButton() {
        JButton copyButton = new JButton(DEFAULT_ICON);
        copyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyButton.setSize(50, 50);
        copyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                copyButton.setIcon(ONHOVER_ICON);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                copyButton.setIcon(DEFAULT_ICON);
            }
        });
        return copyButton;
    }

    private static void addButtonInsideResultPane(JButton copyButton, Container container) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(15, 0, 0, 30);
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.NORTHEAST;

        JLayeredPane jLayeredPane = new JLayeredPane();
        jLayeredPane.setLayout(new GridBagLayout());
        jLayeredPane.add(copyButton, constraints);
        container.add(jLayeredPane);
    }

    private JTextPane createResultTextPanel() {
        JTextPane textPane = new JTextPane() {

            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getUI().getPreferredSize(this).width <= getParent().getSize().width;
            }

        };
        textPane.setText(this.controller.getResultCode());
        return textPane;
    }

    private static Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    public void makeVisible() {
        this.frame.setVisible(true);
    }
}
