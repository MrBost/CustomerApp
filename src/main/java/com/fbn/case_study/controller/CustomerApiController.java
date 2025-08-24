package com.fbn.case_study.controller;

import com.fbn.case_study.dto.CustomerDto;
import com.fbn.case_study.service.CustomerService;
import com.fbn.case_study.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fbn.case_study.utils.ResponseUtils.createSuccessResponse;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerApiController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerDto.Response>> create(@Valid @RequestBody CustomerDto.Request req) {
        CustomerDto.Response response = customerService.create(req);
        ApiResponse<CustomerDto.Response> apiResponse = createSuccessResponse(response,"Customer created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerDto.Response>>> list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<CustomerDto.Response> pagedResponse = customerService.list(page, size);
        ApiResponse<List<CustomerDto.Response>> apiResponse = createSuccessResponse(pagedResponse.getContent(), "Customers retrieved successfully");
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDto.Response>> get(@PathVariable String id) {
        CustomerDto.Response response = customerService.get(id);
        ApiResponse<CustomerDto.Response> apiResponse = createSuccessResponse(response, "Customer retrieved successfully");
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDto.Response>> update(@PathVariable String id, @Valid @RequestBody CustomerDto.Request req) {
        CustomerDto.Response response = customerService.update(id, req);
        ApiResponse<CustomerDto.Response> apiResponse = createSuccessResponse(response, "Customer updated successfully");
        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        customerService.delete(id);
    }
}
