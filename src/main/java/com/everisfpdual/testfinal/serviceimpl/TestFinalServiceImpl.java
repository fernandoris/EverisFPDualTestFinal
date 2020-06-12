package com.everisfpdual.testfinal.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;

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
//import com.opencsv.CSVReader;

@Service
public class TestFinalServiceImpl implements TestFinalService {

	@Autowired
	UsuarioRepository usuarioRepository;

	public ByteArrayInputStream getExcel() {

		// Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para
		// obtener el excel
		String[] columna = { "Id", "Email", "Firstname", "Lastname", "Password" };
		
		
		Workbook workbook = new XSSFWorkbook();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ByteArrayInputStream inputStreamResource=null;
		Sheet sheet = workbook.createSheet(Constant.USUARIOS_SHEET);
		Row row = sheet.createRow(0);

		for (int i = 0; i < columna.length; i++) {
			row.createCell(i).setCellValue(columna[i]);
		}
		List<Usuario> usuarios = new ArrayList<>();
		
		usuarios=usuarioRepository.findAll();

		for(int i=0; i<usuarios.size(); i++) {
			row= sheet.createRow(i+1);
			row.createCell(0).setCellValue(usuarios.get(i).getId());
			row.createCell(1).setCellValue(usuarios.get(i).getEmail());
			row.createCell(2).setCellValue(usuarios.get(i).getFirstname());
			row.createCell(3).setCellValue(usuarios.get(i).getLastname());
			row.createCell(4).setCellValue(usuarios.get(i).getPassword());
		}

		try {
			workbook.write(baos);
			inputStreamResource= new ByteArrayInputStream(baos.toByteArray());
			workbook.close();
		} catch (IOException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inputStreamResource= new ByteArrayInputStream(baos.toByteArray());
		return inputStreamResource;

	}

	 @Override
	    public boolean addUsersToDbFromCsvFile(String fileName) {
	        boolean result = true;
	        Resource resource = new ClassPathResource(fileName.concat(Constant.CSV_EXT));
	        // Enunciado: Leer archivo csv para obtener los datos de los Usuarios
	        // y guardarlos en BBDD
	        
	        try {
	           CSVReader reader = new CSVReader(new FileReader(resource.getFile().getPath()));
	           String[] nextLine;
	           List<Usuario> usuarios = new ArrayList<Usuario>();
	           while((nextLine=reader.readNext()) != null) {
	        	   usuarios.add(new Usuario(nextLine[0],nextLine[1],nextLine[2],nextLine[3]));
	           }
            usuarioRepository.saveAll(usuarios);
	        } catch (Exception e) {
	            result = false;
	        }
	        return result;
	    }
}
