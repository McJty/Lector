package mcjty.lector.books.parser;

import java.util.*;

public class FormatParser {

    private static class Rdr {
        private final char[] chars;
        private int index;

        public Rdr(String input) {
            this.chars = input.toCharArray();
            index = 0;
        }

        public boolean hasNext() {
            return index <= chars.length;
        }

        public char read() {
            if (index > chars.length) {
                return 0;
            }
            index++;
            if (index >= chars.length) {
                return 0;
            }
            return chars[index-1];
        }

        public char current() {
            if (index >= chars.length) {
                return 0;
            }
            return chars[index];
        }
    }

    public List<FormatToken> parse(String contents) throws ParserException {
        List<FormatToken> tokens = new ArrayList<>();
        Rdr rdr = new Rdr(contents);
        while (rdr.hasNext()) {
            char c = rdr.read();
            if (c == '{') {
                FormatToken token = parseToken(rdr);
            } else if (!Character.isWhitespace(c)) {
                throw new ParserException("Unexpected character: '" + c + "'!");
            }
        }

        return tokens;
    }

    private static enum Mode {
        SKIPPING_SPACES,
        PERHAPS_PARSING_TAG,
        WAITING_FOR_EQUALS,
        PARSING_TAGVALUE,
        PARSING_VALUE
    }

    private static final Map<String, Token> TOKENS = new HashMap<>();

    private static Token token(String id) {
        if (!TOKENS.containsKey(id)) {
            Token tok = null;
            String idl = id.toLowerCase();
            if ("---".equals(idl)) {
                tok = Token.PAGE;
            } else if ("t".equals(idl) || "text".equals(idl)) {
                tok = Token.TEXT;
            } else if ("title".equals(idl)) {
                tok = Token.TITLE;
            } else if ("toc".equals(idl)) {
                tok = Token.TOC;
            } else if ("img".equals(idl) || "image".equals(idl)) {
                tok = Token.IMG;
            } else if ("p".equals(idl)) {
                tok = Token.PARAGRAPH;
            } else if ("section".equals(idl)) {
                tok = Token.SECTION;
            } else if ("item".equals(idl)) {
                tok = Token.ITEM;
            }
            TOKENS.put(id, tok);
        }
        return TOKENS.get(id);
    }

    private FormatToken parseToken(Rdr rdr) throws ParserException {
        StringBuilder id = new StringBuilder();
        while (rdr.hasNext()) {
            char c = rdr.read();
            if (c == '\\') {
                c = rdr.read();
                id.append(c);
            } else if (Character.isWhitespace(c)) {
                break;
            } else if (c == 0) {
                throw new ParserException("Unexpected end of text: missing '}'!");
            } else if (c == '}') {
                return new FormatToken(token(id.toString()), Collections.emptyMap(), "");
            } else {
                id.append(c);
            }
        }

        Mode mode = Mode.SKIPPING_SPACES;
        Map<String, String> tags = new HashMap<>();

        StringBuilder tag = new StringBuilder();
        StringBuilder tagvalue = new StringBuilder();
        StringBuilder value = new StringBuilder();
        while (rdr.hasNext()) {
            char c = rdr.read();
            switch (mode) {
                case SKIPPING_SPACES:
                    if (Character.isLetter(c) || c == '-') {
                        tag.append(c);
                        value.append(c);
                        mode = Mode.PERHAPS_PARSING_TAG;
                    } else if (Character.isWhitespace(c)) {
                    } else if (c == '}') {
                        return new FormatToken(token(id.toString()), tags, value.toString());
                    } else if (c == 0) {
                        throw new ParserException("Unexpected end of text: missing '}'!");
                    } else {
                        mode = Mode.PARSING_VALUE;
                        value.append(c);
                    }
                    break;

                case PERHAPS_PARSING_TAG:
                    if (Character.isLetter(c) || c == '-') {
                        tag.append(c);
                        value.append(c);
                    } else if (Character.isWhitespace(c)) {
                        value.append(c);
                        mode = Mode.WAITING_FOR_EQUALS;
                    } else if (c == '\\') {
                        c = rdr.read();
                        if (c != 0) {
                            value.append(c);
                        }
                        mode = Mode.PARSING_VALUE;
                    } else if (c == '=') {
                        mode = Mode.PARSING_TAGVALUE;
                    } else if (c == 0) {
                        throw new ParserException("Unexpected end of text: missing '}'!");
                    } else if (c == '}') {
                        return new FormatToken(token(id.toString()), tags, value.toString());
                    } else {
                        value.append(c);
                        mode = Mode.PARSING_VALUE;
                    }
                    break;

                case WAITING_FOR_EQUALS:
                    if (Character.isWhitespace(c)) {
                        value.append(c);
                    } else if (c == '=') {
                        mode = Mode.PARSING_TAGVALUE;
                    } else if (c == 0) {
                        throw new ParserException("Unexpected end of text: missing '}'!");
                    } else if (c == '}') {
                        return new FormatToken(token(id.toString()), tags, value.toString());
                    } else {
                        value.append(c);
                        mode = Mode.PARSING_VALUE;
                    }
                    break;

                case PARSING_TAGVALUE:
                    if (c == '\\') {
                        c = rdr.read();
                        if (c != 0) {
                            tagvalue.append(c);
                        }
                    } else if (c == 0) {
                        throw new ParserException("Unexpected end of text: missing '}'!");
                    } else if (c == '}') {
                        tags.put(tag.toString(), tagvalue.toString());
                        return new FormatToken(token(id.toString()), tags, value.toString());
                    } else if (Character.isWhitespace(c)) {
                        tags.put(tag.toString(), tagvalue.toString());
                        tag = new StringBuilder();
                        tagvalue = new StringBuilder();
                        value = new StringBuilder();
                        mode = Mode.SKIPPING_SPACES;
                    } else {
                        tagvalue.append(c);
                    }
                    break;

                case PARSING_VALUE:
                    if (c == '\\') {
                        c = rdr.read();
                        if (c != 0) {
                            value.append(c);
                        }
                    } else if (c == 0) {
                        throw new ParserException("Unexpected end of text: missing '}'!");
                    } else if (c == '}') {
                        return new FormatToken(token(id.toString()), tags, value.toString());
                    } else {
                        value.append(c);
                    }
                    break;
            }
        }
        throw new ParserException("Unexpected end of text!");
    }
}
