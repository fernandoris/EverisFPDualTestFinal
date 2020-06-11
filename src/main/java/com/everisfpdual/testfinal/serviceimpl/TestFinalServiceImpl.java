package com.everisfpdual.testfinal.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
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
		
		// Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para obtener el excel
		
		// Array con los títulos de las columnas
		String[] columnas = { "id", "email", "firstname", "lastname", "password"};
			
		
		// GENERAR EXCEL
		
		System.out.println("Rellenando el excel...");
		
		// ByteArrayOutputStream permite que se escriban los datos en una matriz de bytes
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
		
		// objetos para leer o escribir
		Workbook workbook = new XSSFWorkbook();
		Sheet hoja = workbook.createSheet();
		
		// creación de la primera fila
		Row fila = hoja.createRow(0);
				
		// declaración de la celda
		Cell celda;

		// estilo de la tabla
		
		// fuente
		Font font = workbook.createFont(); // Crear fuente
		font.setBold(true); // poner la fuente en negrita
		font.setFontHeightInPoints((short)18); // tamaño de la fuente
		
		// celdas
		CellStyle style = workbook.createCellStyle(); // Crear estilo de celda	
		style.setFont(font); // aplicar el estilo a la fuente
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND); // es necesario crear un patrón para que la celda coja el color de fondo
		style.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex()); // color de fondo de la celda
		CellStyle styleCelda = workbook.createCellStyle(); // nuevo estilo para cada una de las celdas
		styleCelda.setWrapText(true);
		

		// recorremos el Array columnas para ir rellenando cada celda
		for (int i = 0; i < columnas.length; i++) {
			celda = fila.createCell(i);
			celda.setCellValue(columnas[i]);
			celda.setCellStyle(style);
		}

		
		List<Usuario> usuarios = new ArrayList<>();// arrayList para almacenar usuarios
		usuarios = usuarioRepository.findAll(); // obtenemos los usuarios de la base de datos
		
		// recorremos el ArrayList de usuarios para rellenar cada una de las celdas
		int indice = 1;
		for (Usuario usuario : usuarios) {
			
			fila = hoja.createRow(indice);
			fila.setHeight((short) 600); // altura de la fila
			celda = fila.createCell(0);
			celda.setCellValue(usuario.getId());
			celda.setCellStyle(styleCelda);
			celda = fila.createCell(1);
			celda.setCellValue(usuario.getEmail());
			celda.setCellStyle(styleCelda);
			celda = fila.createCell(2);
			celda.setCellValue(usuario.getFirstname());
			celda.setCellStyle(styleCelda);
			celda = fila.createCell(3);
			celda.setCellValue(usuario.getLastname());
			celda.setCellStyle(styleCelda);
			celda = fila.createCell(4);
			celda.setCellValue(usuario.getPassword());
			celda.setCellStyle(styleCelda);
			indice++;
		}

		// escritura del fichero y cierre
		System.out.println("escribiendo el fichero...");
		try {
			workbook.write(bos);
			bos.close();
			workbook.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		// ByteArrayInputStream es un contenedor de bytes que pueden ser leidos
		// toByteArray recupera los datos
		ByteArrayInputStream inputStreamResource = new ByteArrayInputStream(bos.toByteArray()); 
		
		return inputStreamResource;
	}
	
	@Override
	public boolean addUsersToDbFromCsvFile(String fileName) {
		boolean result = true;
		// Objeto de tipo Resource que recibe un fichero con su extensión y ruta
		Resource resource = new ClassPathResource(fileName.concat(Constant.CSV_EXT));
		
		
		//Enunciado: Leer archivo csv para obtener los datos de los Usuarios 
		//y guardarlos en BBDD
		try {
			// Objeto de tipo lectura que recibe un FileReader para leer
			CSVReader reader = new CSVReader(new FileReader(resource.getFile().getPath()));
		
		// recorrido del CSVReader para obtener los campos
		for (String[] lectura : reader) { // podemos hacer este foreach gracias a que CSVReader tiene un metodo para iterar: iterator()
				String email = lectura[0];
				String firstname = lectura[1];
				String lastname = lectura[2];
				String password = lectura[3];
				
				// instancia de nuevo usuario
				Usuario usuario = new Usuario(email,firstname,lastname,password);
				
				// subida a base de datos
				usuarioRepository.save(usuario);			
			}	
		
		} catch (Exception e) {
			result = false;
		}		
		
		return result;
	}

}
