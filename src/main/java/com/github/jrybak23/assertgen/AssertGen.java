package com.github.jrybak23.assertgen;

public class AssertGen {

    public static void generate(Object object) {
        Controller controller = new Controller();
        UI ui = new UI(controller);
        ui.makeVisible();
        infiniteLoop();
    }

    private static void infiniteLoop() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
