package com.everisfpdual.testfinal.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.everisfpdual.testfinal.domain.Usuario;
import com.everisfpdual.testfinal.repository.UsuarioRepository;
import com.everisfpdual.testfinal.service.TestFinalService;
import com.everisfpdual.testfinal.util.Constant;
import com.opencsv.CSVReader;

@Service
public class TestFinalServiceImpl implements TestFinalService{

	@Autowired
	UsuarioRepository usuarioRepository;
	
	public ByteArrayInputStream getExcel() {
		
		//Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para obtener el excel
		String[] columns = {"" + "id","email","firstname","lastname","password"};
		  XSSFWorkbook workbook=new XSSFWorkbook();
		  CellStyle style = workbook.createCellStyle();
		  XSSFFont font= workbook.createFont();
		  font.setFontHeightInPoints((short)10);
		  font.setColor(IndexedColors.BLACK.getIndex());
		  font.setBold(true);
		  style.setFont(font);
		  
		 
		  Sheet sheet = workbook.createSheet(Constant.USUARIOS_SHEET);
		  Row cabecera = sheet.createRow(0);
		  
		  for(int i = 0;i<columns.length;i++) {
			  Cell cell = cabecera.createCell(i);
			  cell.setCellValue(columns[i]);
			  cell.setCellStyle(style);
		  }
		  
		List<Usuario> usuarios = new ArrayList<>();
		int filaCount = 0;
	/*	for(Usuario usuario : usuarios) {
			row = sheet.createRow(filaCount);
			row.createCell(0).setCellValue(usuario.getId());
			row.createCell(0).setCellValue(usuario.getEmail());
			row.createCell(0).setCellValue(usuario.getFirstName());
			row.createCell(0).setCellValue(usuario.getLastName());
			row.createCell(0).setCellValue(usuario.getPassword());
			filaCount++;
			
		}*/
		for(int i=0;i<usuarioRepository.count();i++) {
			usuarios=usuarioRepository.findAll();
		}
		for(int i=0; i<usuarios.size();i++) {
			System.out.println(usuarios.get(i).getFirstName());
		}
		Row row;
		for(int i=0;i<usuarios.size();i++) {
			row = sheet.createRow(i+1);
			row.createCell(0).setCellValue(usuarios.get(i).getId());
			row.createCell(1).setCellValue(usuarios.get(i).getEmail());
			row.createCell(2).setCellValue(usuarios.get(i).getFirstName());
			row.createCell(3).setCellValue(usuarios.get(i).getLastName());
			row.createCell(4).setCellValue(usuarios.get(i).getPassword());
		}
		
	
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				workbook.write(baos);
				workbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		ByteArrayInputStream inputStreamResource = new ByteArrayInputStream(baos.toByteArray());
		
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
			  String[] record; 
				while ((record = reader.readNext()) != null) {
					Usuario usuario = new Usuario(record[0], record[1], record[2], record[3]);
					usuarioRepository.save(usuario);
				}
				reader.close();
		} catch (Exception e) {
			result = false;
		}		
	
		return result;
	}

}