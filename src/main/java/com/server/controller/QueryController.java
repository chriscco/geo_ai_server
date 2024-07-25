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

    /**
     * default home page
     * @return index view
     */
    @GetMapping("/ai")
    public String getIndex() {
        return "index";
    }

    /**
     *
     * @param request request body
     * @param model spring mvc model
     * @return result view
     * @throws IOException e
     * @throws InterruptedException e
     */
    @PostMapping("/ai/query")
    public String query(@RequestBody QueryRequest request, Model model)
            throws IOException, InterruptedException {
        path_setup();
        PythonCaller pythonCaller = new PythonCaller();
        String query = request.getQuery();
        String[] results = pythonCaller.call(interpreter_path, script_path, query);
        model.addAttribute("results", results);
        return "search_result_view";
    }

    /**
     * Retrieve paths (paths to python and python script) from properties file
     */
    public void path_setup() {
        interpreter_path = env.getProperty("path.interpreter_path");
        script_path = env.getProperty("path.script_path");
    }
}

// CITE:
// https://github.com/OujaKivi/upload-callpython-springboot/blob/master/src/main/java/com/wesley/uploadingfiles/UploadingController.java
