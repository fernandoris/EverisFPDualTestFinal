package com.everisfpdual.testfinal.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.everisfpdual.testfinal.service.TestFinalService;
import com.everisfpdual.testfinal.util.FileUtil;

@RestController
public class TestFinalController {
	
	@Autowired
	TestFinalService testFinalService;

	@GetMapping(path = "/getexcel")
    public ResponseEntity<InputStreamResource> downloadExcel() throws IOException{
		
		ByteArrayInputStream excel = testFinalService.getExcel();

        return FileUtil.okWithFile(excel);
    
	}
	
	@GetMapping(path = "/addusersfromcsv")
    public ResponseEntity<String> addUsers(@RequestParam(required = true) String filename) throws IOException{
		String msg;
		HttpStatus status;
		if(testFinalService.addUsersToDbFromCsvFile(filename)) {
			msg = "Usuarios cargados en la base de datos";
			status = HttpStatus.OK;
		}else {
			msg = "Se ha producidio un error. No se ha podido cargar el fichero "+filename;
			status = HttpStatus.NOT_FOUND;
		}

        return new ResponseEntity<>(msg,status);
    
	}
	
}
