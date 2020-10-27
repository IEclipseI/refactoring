package ru.akirakozov.sd.refactoring.html;

public class HtmlProcessor {
    private final StringBuilder sb = new StringBuilder();
    private static final String begin = "<html><body>\n";
    private static final String end = "</body></html>\n";
    private boolean withSurrounding = true;

    public String toHtmlWithSurroundingTags() {
        return begin + sb.toString() + end;
    }

    public String toHtml() {
        if (withSurrounding)
            return toHtmlWithSurroundingTags();
        else
            return sb.toString();
    }

    public HtmlProcessor headerH1(String headerText) {
        sb.append("<h1>").append(headerText).append("</h1>").append('\n');
        return this;
    }

    public HtmlProcessor line(String line) {
        sb.append(line).append('\n');
        return this;
    }

    public HtmlProcessor text(String text) {
        sb.append(text);
        return this;
    }

    public void withNoSurrounding() {
        withSurrounding = false;
    }
}
