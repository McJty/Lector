package mcjty.lector.books.parser;

import java.util.Map;

public class FormatToken {

    private final Token token;
    private final Map<String, String> tags;
    private final String value;

    public FormatToken(Token token, Map<String, String> tags, String value) {
        this.token = token;
        this.tags = tags;
        this.value = value;
    }

    public Token getToken() {
        return token;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public String getValue() {
        return value;
    }
}
