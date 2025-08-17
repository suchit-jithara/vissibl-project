package com.suchit.DemoProject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class JayBhagvanController {

    @GetMapping("/jay-bhagvan")
    public ResponseEntity<String> jayBhagvan() {
        return new ResponseEntity<>("Jay Bhagvan", HttpStatus.OK);
    }

}
