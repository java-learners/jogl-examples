package net.gamedev.nehe;

import com.jogamp.opengl.util.FPSAnimator;

import com.jogamp.opengl.awt.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Create a window using Swing
 */
public class JOGL2Nehe06TextureSwingWindow extends GLCanvas {

    // Define constants for the top-level container
    private static final String TITLE = "JOGL2 Example using Swing";
    private static final int CANVAS_WIDTH = 640;  // width of the drawable
    private static final int CANVAS_HEIGHT = 480; // height of the drawable
    private static final int FPS = 60; // animator's target frames per second


    public static void main(String[] args) {
        // Run the GUI codes in the event-dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                // Create the OpenGL rendering canvas
                GLCanvas canvas = new JOGL2Nehe06TextureSwingWindow();

                canvas.addGLEventListener(new JOGL2Nehe06Texture());

                canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

                // Create a animator that drives canvas' display() at the specified FPS.
                final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);

                // Create the top-level container
                final JFrame frame = new JFrame(); // Swing's JFrame or AWT's Frame
                frame.getContentPane().add(canvas);
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        // Use a dedicate thread to run the stop() to ensure that the
                        // animator stops before program exits.
                        new Thread() {
                            @Override
                            public void run() {
                                if (animator.isStarted()) animator.stop();
                                System.exit(0);
                            }
                        }.start();
                    }
                });
                frame.setTitle(TITLE);
                frame.pack();
                frame.setVisible(true);
                animator.start(); // start the animation loop
            }
        });
    }
}
