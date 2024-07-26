package com.server.controller;

import com.server.PythonCaller;
import com.server.entity.QueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
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
     * @return in json
     */
    @GetMapping("/ai")
    public ResponseEntity<String> getIndex() {
        return ResponseEntity.ok("This is home page");
    }

    /**
     *
     * @param request request body
     * @return result in json
     * @throws IOException e
     * @throws InterruptedException e
     */
    @PostMapping("/ai/query")
    public ResponseEntity<List<String>> query(@RequestBody QueryRequest request)
            throws IOException, InterruptedException {
        path_setup();
        PythonCaller pythonCaller = new PythonCaller();
        String query = request.getQuery();
        List<String> result = new ArrayList<>
                (pythonCaller.call(interpreter_path, script_path, query));
        return ResponseEntity.ok(result);
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
