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
		List<Usuario> usuarios = new ArrayList<>();
		ByteArrayInputStream inputStreamResource = null;
				
		//Llenamos el ArrayList con los datos de la base de datos
		usuarios = usuarioRepository.findAll();
		String[] columns = {"Id","Email", "FirstName","LastName","Password"};
				
		//Creamos el libro, la hoja y la primera fila
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(Constant.USUARIOS_SHEET);
		Row row = sheet.createRow(0);
				
		// Instanciamos la celda
		Cell cell;
		        
		// Creamos estilo negrita para la cabecera
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(true);
		style.setFont(font);
		        
		// Creamos la cabecera
		for (int i = 0; i < columns.length; i++) {
			// Se crea una celda dentro de la fila
			cell = row.createCell(i);
			//Se le aplica el estilo creado
			cell.setCellStyle(style);
			// Se crea el contenido de la celda y se mete en ella.
			cell.setCellValue(columns[i]);
		}
		        
		// Recorremos la lista de alumnos y lo escribimos en una columna i
		int i = 1;
		for (Usuario usuario : usuarios) {
			row = sheet.createRow(i);
			cell = row.createCell(0);
			cell.setCellValue(usuario.getId());
			cell = row.createCell(1);
			cell.setCellValue(usuario.getEmail());
			cell = row.createCell(2);
			cell.setCellValue(usuario.getFirstName());
			cell = row.createCell(3);
			cell.setCellValue(usuario.getLastName());
			cell = row.createCell(4);
			cell.setCellValue(usuario.getPassword());
			i++;
		}
		        
		// Se guarda el libro.
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			workbook.write(baos);
			inputStreamResource = new ByteArrayInputStream(baos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
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
			//CSVReader reader = new CSVReader(new FileReader(resource.getFile().getPath()));
			
		} catch (Exception e) {
			result = false;
		}		
		
		return result;
	}

}
