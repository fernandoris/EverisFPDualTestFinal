package com.everisfpdual.testfinal.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
//import com.opencsv.CSVReader;

@Service
public class TestFinalServiceImpl implements TestFinalService{

	@Autowired
	UsuarioRepository usuarioRepository;
	
	public ByteArrayInputStream getExcel() {
		//Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para obtener el excel
		String[] columnas = { "Id", "Correo", "Nombre", "Apellido", "Contraseña" };
		
		List<Usuario> usuarios = new ArrayList<>();
		usuarios=usuarioRepository.findAll();
		
		ByteArrayInputStream inputStreamResource = null;
		
		// Se genera excel
		System.out.print("Generando excel...");
		XSSFWorkbook workbook = new XSSFWorkbook();
		

		// Se genera hoja en el excel y se comunica
		System.out.print("Generando hoja de trabajo...");
		XSSFSheet sheet = workbook.createSheet("Usuarios");
		
		// Ahora se va a rellenar la hoja de trabajo
		System.out.print("Rellenando hoja de trabajo...");

		// Se crea estilo para los nombres de la primera columna: negrita, letra Arial, color de fondo azul
		XSSFCellStyle titleStyle = workbook.createCellStyle();
		XSSFFont titleFont = workbook.createFont();
		
		titleFont.setBold(true);
		titleFont.setFontName("Arial");
		titleFont.setFontHeight(16);
		
		titleStyle.setFont(titleFont);
		titleStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		

		//Se crea estilo para ajustar alto de celda segun tamaño del texto
		XSSFCellStyle otherStyle = workbook.createCellStyle();
		otherStyle.setWrapText(true);


		// Se crea primera fila
		XSSFRow row = sheet.createRow(0);
		for (int i = 0; i < columnas.length; i++) {
			// Se lee nombre de la columna y se rellena cada celda
			row.createCell(i).setCellValue(columnas[i]);
			//Se le da estilo a la celda
			row.getCell(i).setCellStyle(titleStyle);
		}

		// Se rellena con los datos de los usuarios
		for (int i = 0; i < usuarios.size(); i++) {	
			row.createCell(0).setCellValue(usuarios.get(i).getId());
			row.createCell(1).setCellValue(usuarios.get(i).getEmail());
			row.createCell(2).setCellValue(usuarios.get(i).getFirstName());
			row.createCell(3).setCellValue(usuarios.get(i).getLastName());
			row.createCell(4).setCellValue(usuarios.get(i).getPassword());
			
			//Se recorren de nuevo todas las celdas y se le da estilo para ajustar altura
			for (int j = 0; j <=4; j++) {
				row.getCell(j).setCellStyle(otherStyle);
			}
			
		}

		// Se ajusta el ancho de las columnas 1 y 2 para que se vea mejor su contenido
				sheet.setColumnWidth(0, 30*256);
				sheet.setColumnWidth(1, 15*256);
				

		// Se prueba a pasar del excel a un array de tipo byte
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
				workbook.write(out);
			byte[] array = out.toByteArray();
			inputStreamResource = new ByteArrayInputStream(array);
			System.out.print("Paso de excel a array");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Se prueba a cerrar conexion de workbook
		try {
		System.out.println("--->Hecho");
			workbook.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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

		// Enunciado: Leer archivo csv para obtener los datos de los Usuarios
		// y guardarlos en BBDD
		try {
			//Se instancia el fichero a leer
			CSVReader reader = new CSVReader(new FileReader(resource.getFile().getPath()));

			//Se crea array donde guardar datos de cada linea del archivo
			String[] fila=null;
			
			//Se lee el archivo linea por linea
			while((fila = reader.readNext()) != null) {
				
				Usuario usuario= new Usuario(fila[0],fila[1],fila[2],fila[3]);
			   
				usuarioRepository.save(usuario);
			    
			}
		
			//Cierre de la instancia para evitar errores
			reader.close();
			
		} catch (Exception e) {
			result = false;
		}	
		
		return result;
	}

}
