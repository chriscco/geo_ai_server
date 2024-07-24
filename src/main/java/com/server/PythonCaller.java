package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PythonCaller {
    public String[] call(String interpreter_path, String script_path, String query) throws IOException {
        String cmd = interpreter_path + " " + script_path + " " + query;
        Process pr = Runtime.getRuntime().exec(new String[]{cmd});

        InputStream is = pr.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder buf = new StringBuilder();
        String line = null;
        while((line = br.readLine()) != null) buf.append(line);

        return buf.toString().split(";");
    }
}

// CITE:
// https://github.com/OujaKivi/upload-callpython-springboot/blob/master/src/main/java/com/wesley/uploadingfiles/DoPred.java
