package com.github.jrybak23.assertgen;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class NameGenerator {

    private final Pattern MATCH_METHOD_OR_FIELD_CALL = Pattern.compile("(?:\\w*\\.)*(\\w+)(?:\\(\\))");
    public static final Pattern REMOVE_VERB = Pattern.compile(Stream.of("get", "find", "")
            .map(s -> s + "(\\w+)")
            .collect(joining("|")));
    public static final Map<String, String> EXCEPTIONAL_CASES = Map.of(
            "children", "child",
            "people", "person"
    );
    public static final Map<String, String> LAST_PARTS_TO_REPLACE = getLastPartsToReplace();


    public String generateItemName(String code) {
        String text = findLastMethodOrFieldCall(code);
        text = removeVerb(text);
        text = convertToSingular(text);
        return lowerCaseFirstChar(text);
    }

    private String lowerCaseFirstChar(String text) {
        char[] c = text.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }

    private String convertToSingular(String text) {
        String lowerCase = text.toLowerCase();
        if (EXCEPTIONAL_CASES.containsKey(lowerCase)) {
            return EXCEPTIONAL_CASES.get(lowerCase);
        }
        for (Map.Entry<String, String> entry : LAST_PARTS_TO_REPLACE.entrySet()) {
            if (text.endsWith(entry.getKey())) {
                int index = text.lastIndexOf(entry.getKey());
                return text.substring(0, index) + entry.getValue();
            }
        }
        return text;
    }

    private static Map<String, String> getLastPartsToReplace() {
        Map<String, String> lastPartsToReplace = new LinkedHashMap<>();
        lastPartsToReplace.put("oes", "o");
        lastPartsToReplace.put("es", "e");
        lastPartsToReplace.put("s", "");
        return lastPartsToReplace;
    }

    private static String removeVerb(String call) {
        Matcher matcher = REMOVE_VERB.matcher(call);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new RuntimeException();
        }
    }

    private String findLastMethodOrFieldCall(String code) {
        Matcher matcher = MATCH_METHOD_OR_FIELD_CALL.matcher(code);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new RuntimeException();
        }
    }

}
