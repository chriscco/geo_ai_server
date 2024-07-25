package com.server.controller;

import com.server.PythonCaller;
import com.server.entity.QueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.Arrays;

@Controller
@PropertySource(value = "classpath:config.properties")
public class QueryController {
    private Environment env;
    private static String interpreter_path = null;
    private static String script_path = null;

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }
    @GetMapping("/ai")
    public String getIndex() {
        interpreter_path = env.getProperty("path.interpreter_path");
        script_path = env.getProperty("path.script_path");
        return "index";
    }
    @PostMapping("/ai/query")
    public String query(@RequestBody QueryRequest request, Model model)
            throws IOException, InterruptedException {
        PythonCaller pythonCaller = new PythonCaller();
        String query = request.getQuery();
        String[] results = pythonCaller.call(interpreter_path, script_path, query);
        model.addAttribute("results", results);
        return "search_result_view";
    }
}

// CITE:
// https://github.com/OujaKivi/upload-callpython-springboot/blob/master/src/main/java/com/wesley/uploadingfiles/UploadingController.java
