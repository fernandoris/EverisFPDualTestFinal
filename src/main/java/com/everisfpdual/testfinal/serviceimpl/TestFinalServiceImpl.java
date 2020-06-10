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
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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
//import com.opencsv.CSVReader;

@Service
public class TestFinalServiceImpl implements TestFinalService{

	@Autowired
	UsuarioRepository usuarioRepository;
	
	
	public ByteArrayInputStream getExcel() {

		// Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para
		// obtener el excel

		// Declaración Array de la fila de títulos
		String[] COLUMNAS = { "Id", "Correo", "Nombre", "Apellidos", "Contraseña" };

		// Introducción de datos en el Excel
		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(Constant.USUARIOS_SHEET);

		// Estilo de la hoja
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true); // Negrita
		font.setFontHeightInPoints((short) 16); // Tamaño de la fuente
		style.setFillForegroundColor(IndexedColors.BLUE.getIndex()); // Color azul
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND); // Se aplica el color a las celdas
		style.setFont(font); // Se le aplica la negrita a la fuente

		// Creación de la primera fila
		Row row = sheet.createRow(0);
		Cell cell;

		// Bucle para ir añadiendo las cabeceras de la tabla
		for (int i = 0; i < COLUMNAS.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(COLUMNAS[i]);
			// Se aplica el estilo
			cell.setCellStyle(style);
		}

		// Creación de ArrayList y uso del método findAll
		List<Usuario> usuarios = new ArrayList<>();
		usuarios = usuarioRepository.findAll();
		// Bucle para ir añadiendo los usuarios a la tabla
		for (int i = 1; i < usuarios.size(); i++) {
			row = sheet.createRow(i);
			row.createCell(0).setCellValue(usuarios.get(i).getId());
			row.createCell(1).setCellValue(usuarios.get(i).getEmail());
			row.createCell(2).setCellValue(usuarios.get(i).getFirstname());
			row.createCell(3).setCellValue(usuarios.get(i).getLastname());
			row.createCell(3).setCellValue(usuarios.get(i).getPassword());
		}

		// Escritura 
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ByteArrayInputStream inputStreamResource = null;
		try {
			workbook.write(outStream);
			// Se necesita usar byte[] para pasar de output a input
			byte[] outputToInput = outStream.toByteArray();
			inputStreamResource = new ByteArrayInputStream(outputToInput);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fallo al crear crear el archivo");
		}

		// Cierre
		try {
			workbook.close();
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fallo al cerrar");
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
