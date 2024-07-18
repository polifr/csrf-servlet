package it.poli.csrf.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfServletController {

  @GetMapping("/csrf/test-get")
  public ResponseEntity<Void> testGet() {
    return ResponseEntity.ok().build();
  }

  @PostMapping("/csrf/test-post")
  public ResponseEntity<Void> testPost() {
    return ResponseEntity.ok().build();
  }
}
