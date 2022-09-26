package com.demo.controller;

import com.demo.domain.Example;
import com.demo.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class ExampleController {

    @Autowired
    private ExampleService exampleService;

    @PostMapping(path = "examples", produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> request(@RequestBody Example example) {
        if (example.getId() == null) {
            example.setId(UUID.randomUUID().toString());
        }
        exampleService.requestExample(example);

        return new ResponseEntity<>(example.getId(), HttpStatus.ACCEPTED);
    }
}
