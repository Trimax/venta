package com.gesoftware.venta.math.trees;

import com.gesoftware.venta.logging.LoggingUtility;
import com.gesoftware.venta.math.shapes.Rectangle;
import com.gesoftware.venta.math.vectors.Vec2i;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public final class QuadTreeTest extends JFrame {
    private final static Random c_Random = new Random();
    private final static Vec2i c_Size = new Vec2i(800, 600);
    private final static int c_Side = 20;

    private class Demo extends Panel {
        private final QuadTree<Rectangle> m_Tree;
        private final Rectangle m_Test;

        public Demo(final QuadTree<Rectangle> tree, final Rectangle test) {
            m_Tree = tree;
            m_Test = test;
        }

        private void paintRectangle(final Graphics g, final Rectangle rectangle) {
            g.fillRect((int)rectangle.getPosition().getX(), (int)rectangle.getPosition().getY(), (int)rectangle.getSize().getX(), (int)rectangle.getSize().getY());
        }

        private void paintList(final Graphics g, final List<Rectangle> rectangles) {
            for (final Rectangle rectangle : rectangles)
                paintRectangle(g, rectangle);
        }

        @Override
        public void paint(final Graphics g) {
            super.paint(g);

            /* Draw all objects */
            g.setColor(new Color(100, 100, 100, 155));
            paintList(g, m_Tree.getAll());

            /* Draw test area */
            g.setColor(new Color(50, 200, 50, 155));
            paintRectangle(g, m_Test);

            /* Draw collided objects */
            g.setColor(new Color(200, 50, 50, 155));
            paintList(g, m_Tree.retrieve(m_Test));
        }
    }

    private QuadTreeTest() {
        super();

        /* Setup default window style */
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quad-tree test");

        /* Create tree and fill it with random objects */
        final Rectangle area = new Rectangle(0, 0, c_Size.getX(), c_Size.getY());
        final QuadTree<Rectangle> tree = new QuadTree<Rectangle>(area);
        for (int i = 0; i < 100; i++)
            tree.insert(new Rectangle(c_Random.nextInt(c_Size.getX()), c_Random.nextInt(c_Size.getY()), c_Side, c_Side));

        /* Generate random test area */
        final Rectangle test = new Rectangle(c_Random.nextInt(c_Size.getX()), c_Random.nextInt(c_Size.getY()), 3 * c_Side, 3 * c_Side);

        /* Set demo panel */
        getContentPane().add(new Demo(tree, test));

        pack();
        setVisible(true);
        setSize(c_Size.getX(), c_Size.getY());
    }

    public static void main(final String args[]) {
        LoggingUtility.setLoggingLevel(LoggingUtility.LoggingLevel.LEVEL_CORE);

        new QuadTreeTest();
    }
}
