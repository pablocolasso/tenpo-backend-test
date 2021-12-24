package com.tenpo.tenpobackendtest.controllers;


import com.tenpo.tenpobackendtest.entities.Audit;
import com.tenpo.tenpobackendtest.exeptions.UnautorizedException;
import com.tenpo.tenpobackendtest.services.AuditService;
import com.tenpo.tenpobackendtest.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/history")
@RestController
@Tag(name = "History")
public class AuditController {

    @Autowired
    AuditService auditService;
    @Autowired
    UserService userService;

    @GetMapping("/get/all")
    @Operation(summary = "Get url history")
    public ResponseEntity<Page<Audit>> getHistory(
            @RequestParam(value = "page-number") Integer page_number,
            @RequestParam(value = "size") Integer size,
            @RequestHeader(value = "x-auth-token", required = false) String auth) {
        if (userService.validateJwt(auth)) {
            Page<Audit> auditServiceHistory = auditService.getHistory(page_number, size);
            return new ResponseEntity<>(auditServiceHistory, HttpStatus.OK);
        } else {
            throw new UnautorizedException("User is not authorized to get history");
        }
    }

}

