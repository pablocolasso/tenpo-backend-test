package com.tenpo.tenpobackendtest.controllers;

import com.tenpo.tenpobackendtest.exeptions.UnautorizedException;
import com.tenpo.tenpobackendtest.services.AuditService;
import com.tenpo.tenpobackendtest.services.MathService;
import com.tenpo.tenpobackendtest.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/math")
public class MathController {


    @Autowired
    private UserService userService;
    @Autowired
    private AuditService auditService;
    @Autowired
    private MathService mathService;

    @Validated
    @GetMapping("/sum")
    @ApiOperation(value = "${MathController./sum")
    public ResponseEntity<Double> sum(
            @RequestParam(value = "value1") double value1,
            @RequestParam(value = "value2") double value2,
            @RequestHeader(value = "x-auth-token", required = false) String auth) {
        if (userService.validateJwt(auth)) {
            auditService.saveAudit("/math/sum?value1=" + value1 + "&value2=" + value2);
            return ResponseEntity.ok(mathService.sum(value1 , value2));
        } else {
            throw new UnautorizedException("Token is not valid");
        }
    }
}
