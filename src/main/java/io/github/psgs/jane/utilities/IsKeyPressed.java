package io.github.psgs.jane.utilities;

import java.awt.*;
import java.awt.event.KeyEvent;

public class IsKeyPressed {
    private static boolean tildePressed = false;

    /**
     * Returns whether the Tilde '`' key is pressed
     * @return Whether the tilde key is pressed
     */
    public static boolean isTildePressed() {
        synchronized (IsKeyPressed.class) {
            return tildePressed;
        }
    }

    public static void main(String[] args) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                synchronized (IsKeyPressed.class) {
                    switch (event.getID()) {
                        case KeyEvent.KEY_PRESSED:
                            if (event.getKeyCode() == KeyEvent.VK_DEAD_TILDE) {
                                tildePressed = true;
                            }
                            break;

                        case KeyEvent.KEY_RELEASED:
                            if (event.getKeyCode() == KeyEvent.VK_DEAD_TILDE) {
                                tildePressed = false;
                            }
                            break;
                    }
                    return false;
                }
            }
        });
    }
}