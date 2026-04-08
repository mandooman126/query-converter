package com.banca.queryconverter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class QueryApiController {
	
	private final QueryConverterService queryConverterService;
	
	public QueryApiController(QueryConverterService queryConverterService) {
		this.queryConverterService = queryConverterService;
	}
	
	@PostMapping("/convert")
	@ResponseBody
    public ResponseEntity<ConvertResult> convert(@RequestBody ConvertRequest request) {
        ConvertResult result = queryConverterService.convert(request.getQuery(), request.getMappings());

        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }
}
