package com.banca.queryconverter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banca.queryconverter.model.ConvertRequest;
import com.banca.queryconverter.model.ConvertResult;
import com.banca.queryconverter.service.QueryConverterService;

@Controller
@RequestMapping("/api")	// class에 붙이면 공통 url
public class QueryApiController {
	
	private final QueryConverterService queryConverterService;
	
	public QueryApiController(QueryConverterService queryConverterService) {
		this.queryConverterService = queryConverterService;
	}
	
	@PostMapping("/convert")	// 메서드에 붙이면 개별 url
	@ResponseBody
    public ResponseEntity<ConvertResult> convert(@RequestBody ConvertRequest request) {
        ConvertResult result = queryConverterService.convert(request.getQuery(), request.getMappings());

        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }
}
