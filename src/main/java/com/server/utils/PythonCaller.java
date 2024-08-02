package com.server.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PythonCaller {

    /**
     * Call the python file model.py
     * @param interpreter_path python 3 path
     * @param script_path model.py path
     * @param query query
     * @return search hits
     */
    public StringBuilder call(String interpreter_path, String script_path, String query,
                                    boolean ifUpload) {
        try {
            StringBuilder result = new StringBuilder();
            String[] cmd = new String[]{interpreter_path, script_path, query, String.valueOf(ifUpload)};
            Process pr = Runtime.getRuntime().exec(cmd);
            BufferedReader stdError =
                    new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(pr.getInputStream()));

            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            // print error message from python
            while ((line = stdError.readLine()) != null) {
                System.out.println(line);
            }

            int exitValue = pr.waitFor();
            if (exitValue != 0) {
                System.out.println("Abnormal execution, exit code: " + exitValue);
            }
            return result;
        } catch (IOException | InterruptedException exception) {
            System.out.println("PythonCaller call(): " + exception);
        }
        return null;
    }
}

// CITE:
// https://github.com/OujaKivi/upload-callpython-springboot/blob/master/src/main/java/com/wesley/uploadingfiles/DoPred.java
