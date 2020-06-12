package com.everisfpdual.testfinal.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.util.FileUtil;
/*
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.everisfpdual.testfinal.domain.Usuario;
import com.everisfpdual.testfinal.repository.UsuarioRepository;
import com.everisfpdual.testfinal.service.TestFinalService;
import com.everisfpdual.testfinal.util.Constant;
import com.opencsv.CSVReader;
//import com.opencsv.CSVReader;

@Service
public class TestFinalServiceImpl implements TestFinalService{

	@Autowired
	UsuarioRepository usuarioRepository;
	
	public ByteArrayInputStream getExcel() {
		
		//Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para obtener el excel
		List<Usuario> usuarios = new ArrayList<>();
		ByteArrayInputStream inputStreamResource = null;
		
		usuarios = usuarioRepository.findAll();
		
		final String[] columnas = {"ID", "EMAIL", "FIRSTNAME", "LASTNAME", "PASSWORD"};
		try {
			Workbook wb = new XSSFWorkbook();
			
			Sheet sheet = wb.createSheet("Usuarios");
			
			Font headerFont = wb.createFont();
			headerFont.setBold(true);
			
			CellStyle headerCellStyle = wb.createCellStyle();
			headerCellStyle.setFont(headerFont);
			
			Row headerRow = sheet.createRow(0);
			
			for (int i = 0; i < columnas.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columnas[i]);
				cell.setCellStyle(headerCellStyle);
			}
			
			int num = 1;
			for (Usuario usuario : usuarios) {
				Row row = sheet.createRow(num++);
				row.createCell(0).setCellValue(usuario.getId());
				row.createCell(1).setCellValue(usuario.getEmail());
				row.createCell(2).setCellValue(usuario.getFirstName());
				row.createCell(3).setCellValue(usuario.getLastName());
				row.createCell(4).setCellValue(usuario.getPassword());
			}
			
			for (int i = 0; i < columnas.length; i++) {
				sheet.autoSizeColumn(i);
			}
			File f = new File("usuarios.xlsx");
			FileOutputStream fos = new FileOutputStream(f);
			wb.write(fos);
			fos.close();
			wb.close();
			inputStreamResource = new ByteArrayInputStream(FileUtil.readAsByteArray(f));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		
		return inputStreamResource;
	}
	
	@Override
	public boolean addUsersToDbFromCsvFile(String fileName) {
		boolean result = true;
		Resource resource = new ClassPathResource(fileName.concat(Constant.CSV_EXT));
		
		//Enunciado: Leer archivo csv para obtener los datos de los Usuarios 
		//y guardarlos en BBDD
		try {
			CSVReader reader = new CSVReader(new FileReader(resource.getFile().getPath()));
			String[] s = reader.readNext();
			while(s != null) {
				Usuario u = new Usuario(s[0],s[1],s[2],s[3]);
				usuarioRepository.save(u);
				s = reader.readNext();
			}
		} catch (Exception e) {
			result = false;
		}
		
		return result;
	}

}
