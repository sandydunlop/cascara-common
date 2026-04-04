package io.github.qishr.cascara.common.util;

public class RunAfter {
    private CodeToRun codeToRun = null;

    public RunAfter() {
        // This method intentionally left blank
    }

    public void thenRun(CodeToRun codeToRun) {
        this.codeToRun = codeToRun;
    }

    public void run(Object o) {
        if (codeToRun != null) {
            codeToRun.run(o);
        }
    }
}
