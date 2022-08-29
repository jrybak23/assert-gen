package com.github.jrybak23.assertgen;

import com.github.jrybak23.assertgen.ui.UI;

public class AssertGen {

    public static void generate(Object object) {
        CodeGenerationService codeGenerationService = new CodeGenerationService();
        Controller controller = new Controller(codeGenerationService, object);
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
