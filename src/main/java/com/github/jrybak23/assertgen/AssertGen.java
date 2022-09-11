package com.github.jrybak23.assertgen;

import com.github.jrybak23.assertgen.ui.UI;

public class AssertGen {

    public static void generate(Object object, String reference) {
        CodeGenerationService codeGenerationService = new CodeGenerationService(reference);
        Controller controller = new Controller(codeGenerationService, object);
        System.setProperty("java.awt.headless", "false");
        UI ui = new UI(controller);
        ui.makeVisible();
        infiniteLoop();
    }

    public static void generate(Object object) {
        generate(object, "result");
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
