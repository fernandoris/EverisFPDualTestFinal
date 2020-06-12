package com.everisfpdual.testfinal.serviceimpl;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.FillPatternType;
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

/**
 * 
 * @author irengelr
 *
 */
@Service
public class TestFinalServiceImpl implements TestFinalService {

	@Autowired
	UsuarioRepository usuarioRepository;

	/*
	 * Método que lee datos de la tabla usuario y los guarda en un excel
	 */
	public ByteArrayInputStream getExcel() {

		String[] columnas = { "Id", "Correo", "Nombre", "Apellido", "Contraseña" };

		List<Usuario> usuarios = new ArrayList<>();
		usuarios = usuarioRepository.findAll();

		ByteArrayInputStream inputStreamResource = null;

		// Se genera excel
		System.out.print("Generando excel...");
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Se genera hoja en el excel y se comunica
		System.out.print("Generando hoja de trabajo...");
		XSSFSheet sheet = workbook.createSheet("Usuarios");

		// Ahora se va a rellenar la hoja de trabajo
		System.out.print("Rellenando hoja de trabajo...");

		// Se crea estilo para los nombres de la primera columna: negrita, letra Arial y tamaño 16, color de fondo
		XSSFCellStyle titleStyle = workbook.createCellStyle();
		XSSFFont titleFont = workbook.createFont();

		// Se aplica fuente en negrita
		titleFont.setBold(true);
		// Se especifica nombre de la fuente
		titleFont.setFontName("Arial");
		// Se especifica tamaño de la fuente
		titleFont.setFontHeight(16);

		//Se aplica fuente
		titleStyle.setFont(titleFont);
		// Se aplica color de fondo, azul
		titleStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// Se crea estilo para ajustar alto de celda segun tamaño del texto
		XSSFCellStyle otherStyle = workbook.createCellStyle();
		otherStyle.setWrapText(true);

		// Se crea primera fila
		XSSFRow row = sheet.createRow(0);

		// Se rellena primera fila con el estilo creado anteriormente
		for (int i = 0; i < columnas.length; i++) {
			// Se lee nombre de la columna y se rellena cada celda
			row.createCell(i).setCellValue(columnas[i]);
			// Se le da estilo a la celda
			row.getCell(i).setCellStyle(titleStyle);
		}

		// Se rellena con los datos de los usuarios
		for (int i = 0; i < usuarios.size(); i++) {
			// Se crea fila para el alumno a introducir
			row = sheet.createRow(i + 1);

			// Se rellenan las celdas de la fila con sus datos
			row.createCell(0).setCellValue(usuarios.get(i).getId());
			row.createCell(1).setCellValue(usuarios.get(i).getEmail());
			row.createCell(2).setCellValue(usuarios.get(i).getFirstName());
			row.createCell(3).setCellValue(usuarios.get(i).getLastName());
			row.createCell(4).setCellValue(usuarios.get(i).getPassword());

			// Se recorren de nuevo todas las celdas y se le da estilo para ajustar altura
			for (int j = 0; j <= 4; j++) {
				row.getCell(j).setCellStyle(otherStyle);
			}

		}

		// Se ajusta el ancho de las columnas 1 y 2 para que se vea mejor su contenido
		sheet.setColumnWidth(0, 30 * 256);
		sheet.setColumnWidth(1, 15 * 256);

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
			// Se imprime por pantalla para informar que ya está hecho
			System.out.println("--->Hecho");
			// Se cierra la instancia de workbook
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// El método devuelve un array con los datos
		return inputStreamResource;
	}

	/*
	 * Método: Lee archivo csv para obtener los datos de los Usuarios y guardarlos
	 * en BBDD
	 * 
	 * @param fileName: nombre del archivo csv a leer
	 */
	@Override
	public boolean addUsersToDbFromCsvFile(String fileName) {
		boolean result = true;
		Resource resource = new ClassPathResource(fileName.concat(Constant.CSV_EXT));

		try {

			// Se instancia el fichero a leer
			CSVReader reader = new CSVReader(new FileReader(resource.getFile().getPath()));

			// Se crea array donde guardar datos de cada linea del archivo
			String[] fila = null;

			// Se lee el archivo linea por linea
			while ((fila = reader.readNext()) != null) {

				//Se instancia usuario nuevo con esos datos
				Usuario usuario = new Usuario(fila[0], fila[1], fila[2], fila[3]);

				// Se introduce usuario creado
				usuarioRepository.save(usuario);

			}

			// Cierre de la instancia para evitar errores
			reader.close();

		} catch (Exception e) {
			// Si no se hace correctamente el método devuelve false
			result = false;
		}
		// Si se hace correctamente el método devuelve true
		return result;
	}

}