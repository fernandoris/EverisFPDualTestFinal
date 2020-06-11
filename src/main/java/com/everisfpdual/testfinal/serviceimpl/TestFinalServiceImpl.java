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

/**
 * Class that implements getExcel and addUsersToDbFromCsvFile methods.
 * 
 * @author Ana Blanco Escudero
 * @version 1.2
 * @since 1.0
 */
@Service
public class TestFinalServiceImpl implements TestFinalService {

	@Autowired
	UsuarioRepository usuarioRepository;

	/**
	 * Method that reads values from users table in database and writes the users
	 * list into a XLSX file.
	 */
	public ByteArrayInputStream getExcel() {

		// Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para
		// obtener el excel

		// Create workbook structure:
		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(Constant.USUARIOS_SHEET);
		Row header = sheet.createRow(0);
		Cell cell;

		// Create font style and set:
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		font.setBold(true);
		font.setFontHeightInPoints((short) 16);
		style.setFont(font);

		// Create and set content of heading of table columns from ArrayList items:
		String[] columns = { "Id", "Correo", "Nombre", "Apellidos", "Contraseña" };
		for (int i = 0; i < columns.length; i++) {
			cell = header.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(style);
		}

		// Get users list from repository and store on array:
		List<Usuario> usuarios = new ArrayList<>();
		usuarios = usuarioRepository.findAll();

		// Write users list on Excel file rows:
		for (int i = 1; i < usuarios.size(); i++) {
			Row row = sheet.createRow(i);
			row.createCell(0).setCellValue(usuarios.get(i).getId());
			row.createCell(1).setCellValue(usuarios.get(i).getEmail());
			row.createCell(2).setCellValue(usuarios.get(i).getFirstname());
			row.createCell(3).setCellValue(usuarios.get(i).getLastname());
			row.createCell(4).setCellValue(usuarios.get(i).getPassword());
		}

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ByteArrayInputStream inputStreamResource = null;

		// Write Excel content:
		try {
			workbook.write(output);
			byte[] barray = output.toByteArray();
			inputStreamResource = new ByteArrayInputStream(barray);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error en la escritura.");
		}

		// Close stream:
		try {
			workbook.close();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error en el cierre.");
		}

		return inputStreamResource;
	}

	/**
	 * Method that reads users values from a CSV file and inserts them on database
	 * users table.
	 * 
	 * @param fileName name of the CSV file where the users info is stored.
	 */
	@Override
	public boolean addUsersToDbFromCsvFile(String fileName) {
		boolean result = true;
		Resource resource = new ClassPathResource(fileName.concat(Constant.CSV_EXT));

		// Enunciado: Leer archivo csv para obtener los datos de los Usuarios
		// y guardarlos en BBDD
		try {
			// Get users list from CSV file and store on List:
			CSVReader reader = new CSVReader(new FileReader(resource.getFile().getPath()));
			List<String[]> csvRows = reader.readAll();

			List<Usuario> usuarios = new ArrayList<>();

			// Read users list from List and store on ArrayList:
			for (String[] row : csvRows) {
				Usuario usuario = new Usuario();

				usuario.setEmail(row[0]);
				usuario.setFirstname(row[1]);
				usuario.setLastname(row[2]);
				usuario.setPassword(row[3]);

				usuarios.add(usuario);
			}

			// Write user list on repository:
			usuarioRepository.saveAll(usuarios);

			// Close CSVReader:
			reader.close();
		} catch (Exception e) {
			result = false;
		}

		return result;
	}

}
