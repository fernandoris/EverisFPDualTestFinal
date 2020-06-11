package com.everisfpdual.testfinal.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import com.everisfpdual.testfinal.domain.Usuario;
import com.everisfpdual.testfinal.repository.UsuarioRepository;
import com.everisfpdual.testfinal.service.TestFinalService;
import com.everisfpdual.testfinal.util.Constant;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.query.Query;
import com.opencsv.CSVReader;
import com.opencsv.CSVReader;

@SuppressWarnings("unused")
@Service
public class TestFinalServiceImpl implements TestFinalService{

	@Autowired
	UsuarioRepository usuarioRepository;
	
	public ByteArrayInputStream getExcel(){
			//Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para obtener el excel
			String[] columns = {"id","email", "firstname", "lastname", "password"};

			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet(Constant.USUARIOS_SHEET);
			Row row = sheet.createRow(0);

			for (int i = 0; i < columns.length; i++) {
				Cell celda = row.createCell(i);
				celda.setCellValue(columns[i]);
			}
			
			List<Usuario> usuarios = new ArrayList<>();
			usuarios = usuarioRepository.findAll();
			int fila = 1;
			for(Usuario usuario : usuarios) {
				row = sheet.createRow(fila);
				row.createCell(0).setCellValue(usuario.getId());
				row.createCell(1).setCellValue(usuario.getEmail());
				row.createCell(2).setCellValue(usuario.getFirstName());
				row.createCell(3).setCellValue(usuario.getLastName());
				row.createCell(4).setCellValue(usuario.getPassword());
				fila++;
			}
			
	        try {
	            workbook.write(baos);
	            ByteArrayInputStream inputStreamResource = new ByteArrayInputStream(baos.toByteArray());
	        } catch (Exception e) {
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
			String[] lineas = reader.readNext();
			while(lineas != null) {
				Usuario users = new Usuario(0, lineas[1],lineas[2],lineas[3],lineas[4]);
				usuarioRepository.save(users);
				lineas = reader.readNext();
			}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
}