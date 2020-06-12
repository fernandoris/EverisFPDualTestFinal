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
import org.apache.poi.xssf.usermodel.XSSFCell;
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

@Service
public class TestFinalServiceImpl implements TestFinalService{

	@Autowired
	UsuarioRepository usuarioRepository;
	
	public ByteArrayInputStream getExcel() {
		
		//Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para obtener el excel
		List<Usuario> usuarios = new ArrayList<>();
		final String[] columns = {"Id","Correo","Nombre","Apellido","Contraseña"};
		ByteArrayInputStream inputStreamResource = null;
		
		usuarios = usuarioRepository.findAll();
		
        XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(Constant.USUARIOS_SHEET);
		
		XSSFFont font= wb.createFont();
		
		CellStyle style = wb.createCellStyle();
		
		XSSFRow row = sheet.createRow(0);

		font.setBold(true);
		font.setFontHeightInPoints((short) 16);
		style.setFont(font);
		style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
		
		int i = 0;
		
		for (String content : columns) {
			XSSFCell cell = row.createCell(i++);
			cell.setCellStyle(style);
			cell.setCellValue(content);
		}
		
		i= 1;
		
        for (Usuario usuario : usuarios) {
			XSSFRow rowContent = sheet.createRow(i++);
			
			int j = 0;
			Cell id = rowContent.createCell(j++);
			id.setCellValue(usuario.getId());
			Cell email = rowContent.createCell(j++);
			email.setCellValue(usuario.getEmail());
			Cell firstname = rowContent.createCell(j++);
			firstname.setCellValue(usuario.getFirstname());
			Cell lastname = rowContent.createCell(j++);
			lastname.setCellValue(usuario.getLastname());
			Cell password = rowContent.createCell(j);
			password.setCellValue(usuario.getPassword());
        }
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        try {
            wb.write(bos);
        } catch (IOException e) {
			e.printStackTrace();
		} finally {
            try {
            	wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        byte[] bytes = bos.toByteArray();
        
        inputStreamResource = new ByteArrayInputStream(bytes);
        
		return inputStreamResource;
	}
	
	@Override
	public boolean addUsersToDbFromCsvFile(String fileName) {
		boolean result = true;
		Resource resource = new ClassPathResource(fileName.concat(Constant.CSV_EXT));
		List<Usuario> usuarios = new ArrayList<>();
		
		//Enunciado: Leer archivo csv para obtener los datos de los Usuarios 
		//y guardarlos en BBDD
		try {
			CSVReader reader = new CSVReader(new FileReader(resource.getFile().getPath()));

			for (String[] object: reader.readAll()) {
				Usuario user = new Usuario();
				user.setEmail(object[0]);
				user.setFirstname(object[1]);
				user.setLastname(object[2]);
				user.setPassword(object[3]);
				usuarios.add(user);
			}       
			
			for (Usuario usuario : usuarios) {
				usuarioRepository.save(usuario);
			}
			usuarioRepository.flush();
			
		} catch (Exception e) {
			result = false;
			System.err.println(e);
		}		
		
		return result;
	}

}
