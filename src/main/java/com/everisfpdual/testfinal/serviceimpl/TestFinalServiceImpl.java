package com.everisfpdual.testfinal.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
 * @author ngalindg
 *
 */
@Service
public class TestFinalServiceImpl implements TestFinalService {

	@Autowired
	UsuarioRepository usuarioRepository;

	/**
	 * @return inputStreamResource
	 */

	public ByteArrayInputStream getExcel() throws IOException {

		// Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para
		// obtener el excel
		String[] columns = { "Id", "Correo", "Nombre", "Apellido", "Contraseña" };

		Workbook workbook = new XSSFWorkbook();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Sheet sheet = workbook.createSheet(Constant.USUARIOS_SHEET);
		Row row = sheet.createRow(0);

		for (int i = 0; i < columns.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columns[i]);

			// estilo de la celda
			CellStyle style = workbook.createCellStyle();

			// negrita
			// short size = 16;
			Font font = workbook.createFont();
			font.setBold(true);
			font.setFontHeight((short) 400);
			style.setFont(font);

			// color de fondo
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());

			// tamaño de la fuente

			cell.setCellStyle(style);
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
		ByteArrayInputStream inputStreamResource = new ByteArrayInputStream(baos.toByteArray());

		return inputStreamResource;
	}

	/**
	 * Metodo para leer archivo csv y obtener los datos de los usuarios y guardarlos en BBDD
	 * @return result
	 * @throws IOException
	 */

	@Override
	public boolean addUsersToDbFromCsvFile(String fileName) throws IOException {
		boolean result = false;
		Resource resource = new ClassPathResource(fileName.concat(Constant.CSV_EXT));
		CSVReader reader = null;

		// Enunciado: Leer archivo csv para obtener los datos de los Usuarios
		// y guardarlos en BBDD

		try {
			reader = new CSVReader(new FileReader(resource.getFile().getPath()));
			String[] nextLine = null;
			
			//crear lista de usuarios fuera del while
			//recorrer el while, en cada linea crea usuario y luego guardar en la lista

			List<Usuario> usuarios = new ArrayList<>();
			
			while ((nextLine = reader.readNext()) != null) {
				
				Usuario usuario = new Usuario();
				
				String email = nextLine[0];
				String nombre = nextLine[1];
				String apellido = nextLine[2];
				String password = nextLine[3];
				
				usuario.setEmail(email);
				usuario.setFirstname(nombre);
				usuario.setLastname(apellido);
				usuario.setPassword(password);
				
				usuarios.add(usuario);

			}
			
			usuarioRepository.saveAll(usuarios);
			result = true;
			
		} catch (Exception e) {
			result = false;
		} finally {
			if (null != reader) {
				reader.close();
			}
		}

			return result;
		}

	}
