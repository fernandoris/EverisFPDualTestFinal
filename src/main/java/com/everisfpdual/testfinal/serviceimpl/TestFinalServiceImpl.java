package com.everisfpdual.testfinal.serviceimpl;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
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

@Service
public class TestFinalServiceImpl implements TestFinalService{

	@Autowired
	UsuarioRepository usuarioRepository;
	
	private static final String[] columnas = {"Id", "Email","Nombre", "Apellido", "Contaseña"};
	
	public ByteArrayInputStream getExcel() {
		
		//Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para obtener el excel
		List<Usuario> usuarios = new ArrayList<>();
		ByteArrayInputStream inputStreamResource = null;
		
		usuarios = usuarioRepository.findAll();		//trae todos los datos de la base
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet hoja = workbook.createSheet(Constant.USUARIOS_SHEET);
		hoja.setDefaultColumnWidth(20);
		
		Row fila = hoja.createRow(0);
		Cell celda;
		
		XSSFCellStyle styleTitle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(18);
		font.setBold(true);
		styleTitle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		styleTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleTitle.setFont(font);
		
		for (int i = 0; i < columnas.length; i++) {
			celda = fila.createCell(i);
			celda.setCellStyle(styleTitle);
			celda.setCellValue(columnas[i]);
		}
		
		for (int i = 0; i < usuarios.size(); i++) {
			Row filaNueva = hoja.createRow(i + 1);
			
			filaNueva.createCell(0).setCellValue(usuarios.get(i).getId());
			filaNueva.createCell(1).setCellValue(usuarios.get(i).getEmail());
			filaNueva.createCell(2).setCellValue(usuarios.get(i).getFirstname());
			filaNueva.createCell(3).setCellValue(usuarios.get(i).getLastname());
			filaNueva.createCell(4).setCellValue(usuarios.get(i).getPassword());
		}
		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			workbook.write(baos);											//Escribimos
			
			byte[] array = baos.toByteArray();
			inputStreamResource = new ByteArrayInputStream(array);		//Pasamos el objeto al input
			
			workbook.close();
			baos.close();
		}
		catch(IOException e) {
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
		try {
			CSVReader reader = new CSVReader(new FileReader(resource.getFile().getPath()));
			
			String[] fila = null;
			
			while((fila = reader.readNext()) != null) {
				String[] datos = fila[0].split(",");		//Recibimos usuarios y separamos sus valores con la coma
				
				Usuario usuario = new Usuario(fila[0],fila[1],fila[2],fila[3]);		//Guardamos usuarios uno a uno
				
				usuarioRepository.save(usuario);		//Guardamos usuario
			}
			
			reader.close();
			
		} 
		
		catch (Exception e) {
			result = false;
		}
		
		return result;
	}

}
