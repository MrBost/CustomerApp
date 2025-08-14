package com.fbn.case_study.controller;

import com.fbn.case_study.dto.CustomerDto;
import com.fbn.case_study.mapper.CustomerMapper;
import com.fbn.case_study.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerApiController {
    private final CustomerService customerService;
    private final CustomerMapper mapper;

    @PostMapping
    public ResponseEntity<CustomerDto.Response> create(@Valid @RequestBody CustomerDto.Request req,
                                                       @AuthenticationPrincipal(expression = "name") String principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(req, mapper, principal == null ? "api" : principal));
    }

    @GetMapping
    public Page<CustomerDto.Response> list(@RequestParam(defaultValue = "0") int page,  @RequestParam(defaultValue = "10") int size) {
        return customerService.list(page, size);
    }

    @GetMapping("/{id}")
    public CustomerDto.Response get(@PathVariable String id) {
        return customerService.get(id);
    }

    @PutMapping("/{id}")
    public CustomerDto.Response update(@PathVariable String id, @Valid @RequestBody CustomerDto.Request req,
                                   @AuthenticationPrincipal(expression = "name") String principal) {
        return customerService.update(id, req, mapper, principal == null ? "api" : principal);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        customerService.delete(id);
    }
}
