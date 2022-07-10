package io.github.dbcar.common;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Logger {
    private String style;
    private Integer length = Constants.PRINT_LENGHT;

    public Logger(String style) {
        this.style = style;
    }

    public Logger(String style, Integer length) {
        this.style = style;
        this.length = length;
    }

    public void out(String texto) {
        System.out.print(texto);
    }

    public void outBar() {
        System.out.println(style.repeat(length) + "\n");
    }

    public void outLine() {
        System.out.println();
    }

    public void outLine(String texto) {
        System.out.println(configLogger(texto));
    }

    public void outLoading(String texto) {
        int time = 2000;
        String loading = ".".repeat(length - texto.length());

        System.out.print(texto);

        Arrays.asList(loading.split("")).stream().forEach((c) -> {
            System.out.print(c);
            try {
                TimeUnit.MILLISECONDS.sleep(time / loading.length());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        reset();
    }

    public void reset() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private String configLogger(String s) {
        StringBuilder builder = new StringBuilder();

        int partial = ((length - s.length() - 2) / 2);
        int complement = 0;

        if (s.length() % 2 != 0) {
            complement = 1;
        }

        builder.append(style.repeat(partial));
        builder.append(" ");
        builder.append(s);
        builder.append(" ");
        builder.append(style.repeat(partial + complement));

        return builder.toString();
    }
}
