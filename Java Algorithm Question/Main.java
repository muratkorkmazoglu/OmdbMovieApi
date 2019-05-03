package com.company;

public class Main {


    public static void main(String[] args) {
        String s = "abbbcccccfddddddd sfsfssss";
        int x = 4;

        char[] chars = s.toCharArray();
        char lastFound = '-';
        int foundIndex = 0;
        int foundCount = 0;

        for (int i = 0; i < s.length(); i++) {
            if (lastFound == chars[i]) {
                foundCount++;
            } else {
                if (foundCount >= x) {
                    for (int j = foundIndex; j < foundIndex + foundCount; j++) {
                        chars[j] = '*';
                    }
                }
                foundCount = 1;
                lastFound = chars[i];
                foundIndex = i;
            }

        }

        if (foundCount >= x) {
            for (int j = foundIndex; j < foundIndex + foundCount; j++) {
                chars[j] = '*';
            }
        }
        System.out.println(chars);
    }
}
