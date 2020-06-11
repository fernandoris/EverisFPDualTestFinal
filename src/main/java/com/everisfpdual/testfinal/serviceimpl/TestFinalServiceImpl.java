package com.everisfpdual.testfinal.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
	
	public ByteArrayInputStream getExcel() throws IOException {
		
		//Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para obtener el excel
		String[] columns = {"Id","Email","Firstime","Lastname","Password"};
		
		Workbook workbook = new XSSFWorkbook();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		Sheet sheet = workbook.createSheet(Constant.USUARIOS_SHEET);
		Row row = sheet.createRow(0);
        
        for (int i = 0; i < columns.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columns[i]);
		}
		
		List<Usuario> usuarios = new ArrayList<>();
		usuarios = usuarioRepository.findAll();
		int fila = 1;
		for (Usuario usuario : usuarios) {
			row = sheet.createRow(fila);
			row.createCell(0).setCellValue(usuario.getId());
			row.createCell(1).setCellValue(usuario.getEmail());
			row.createCell(2).setCellValue(usuario.getFirstname());
			row.createCell(3).setCellValue(usuario.getLastname());
			row.createCell(4).setCellValue(usuario.getPassword());
			fila++;
		}
		
		workbook.write(baos);
		workbook.close();
		ByteArrayInputStream inputStreamResource = null;
		inputStreamResource = new ByteArrayInputStream(baos.toByteArray());
		return inputStreamResource;
	}
	
	@Override
	public boolean addUsersToDbFromCsvFile(String fileName) {
		boolean result = true;
		Resource resource = new ClassPathResource(fileName.concat(Constant.CSV_EXT));
		
		//Enunciado: Leer archivo csv para obtener los datos de los Usuarios 
		//y guardarlos en BBDD
		try {
			//CSVReader reader = new CSVReader(new FileReader(resource.getFile().getPath()));
			
		} catch (Exception e) {
			result = false;
		}		
		
		return result;
	}

}
