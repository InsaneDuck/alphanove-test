package dev.insaneduck.alphanovetest.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {

    @GetMapping("/trigger-batch")
    ResponseEntity<String> triggerBatch()
    {
        //trigger batch
        return ResponseEntity.ok("started");
    }
}
