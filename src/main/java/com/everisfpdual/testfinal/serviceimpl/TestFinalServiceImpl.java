package com.everisfpdual.testfinal.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
 * @author Manuel C.
 *
 */
@Service
public class TestFinalServiceImpl implements TestFinalService{

	@Autowired
	UsuarioRepository usuarioRepository;
	
	public ByteArrayInputStream getExcel() throws IOException{
		
		//Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para obtener el excel
		
		
		String[] columns = {"Id","Email","Firstname","Lastname","Password"};
		
		//creamos el woorbook
		Workbook workbook = new XSSFWorkbook();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		//creamos la hoja
		Sheet sheet = workbook.createSheet(Constant.USUARIOS_SHEET);
		
		//Creamos el Stylo
		CellStyle style = workbook.createCellStyle();
		
		//Editamos la letra(Font)
		Font font = workbook.createFont();
		font.setBold(true);
		style.setFont(font);
		
		//Con esto escribimos la cabecera
		Row row = sheet.createRow(0);
		
		for (int i = 0; i < columns.length; i++) {
			Cell cell = row.createCell(i);
			//Con esto le ponemos la negrita a la cabecera
			cell.setCellStyle(style);
			cell.setCellValue(columns[i]);
		}
		
		//creamos la lista de usuarios
		List<Usuario> usuarios = new ArrayList<>();
		//el repositorio para la busqueda de los usuarios.
		usuarios = usuarioRepository.findAll();
		
		int initRow =1;
		for (Usuario usuario : usuarios) {
			row = sheet.createRow(initRow);
			row.createCell(0).setCellValue(usuario.getId());
			row.createCell(1).setCellValue(usuario.getEmail());
			row.createCell(2).setCellValue(usuario.getFirstname());
			row.createCell(3).setCellValue(usuario.getLastname());
			row.createCell(4).setCellValue(usuario.getPassword());
			
			initRow++;
		}
		
		workbook.write(stream);
		workbook.close();
		
		ByteArrayInputStream inputStreamResource = new ByteArrayInputStream(stream.toByteArray());
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