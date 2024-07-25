package com.server;

import java.io.*;

public class PythonCaller {
    public String[] call(String interpreter_path, String script_path, String query) {
        try {
            String[] cmd = new String[]{interpreter_path, script_path, query};
            Process pr = Runtime.getRuntime().exec(cmd);

            BufferedReader stdError =
                    new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(pr.getInputStream()));

            StringBuilder buf = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null) {
                buf.append(line).append('\n');
                System.out.println("line: " + line);
            }

            while ((line = stdError.readLine()) != null) {
                System.out.println(line);
            }

            int exitValue = pr.waitFor();
            if (exitValue != 0) {
                System.out.println("Abnormal execution, exit code: " + exitValue);
            }
            return buf.toString().split("\n");
        } catch (IOException | InterruptedException exception) {
            System.out.println("PythonCaller call(): " + exception);
        }
        return null;
    }
}

// CITE:
// https://github.com/OujaKivi/upload-callpython-springboot/blob/master/src/main/java/com/wesley/uploadingfiles/DoPred.java
