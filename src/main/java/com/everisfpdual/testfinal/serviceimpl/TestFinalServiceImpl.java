package com.everisfpdual.testfinal.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
	
	public ByteArrayInputStream getExcel() throws SQLException {
		
		//Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para obtener el excel
		
		String[] columns= {"id", "email", "firstName", "lastName", "password"};
		
		List<Usuario> usuarios = new ArrayList<>();
		usuarios=usuarioRepository.findAll();
		
		ByteArrayInputStream inputStreamResource =null;
		
		XSSFWorkbook wb= new XSSFWorkbook();
		XSSFSheet hoja=wb.createSheet();
		
		 CellStyle style = wb.createCellStyle();
	     Font font = wb.createFont();
	     font.setBold(true);
	     style.setFont(font);
	     
		for(int i=0; i<usuarios.size()+1; i++) {
			XSSFRow fila = hoja.createRow(i);
			
			for(int j=0; j<5; j++) {
		
		XSSFCell celda=fila.createCell(j);
		if(i==0) {
		celda.setCellStyle(style);
		celda.setCellValue(columns[j]);
		}else if(j==0)
			celda.setCellValue(usuarios.get(i-1).getId());
		else if(j==1)
			celda.setCellValue(usuarios.get(i-1).getEmail());
		else if(j==2)
			celda.setCellValue(usuarios.get(i-1).getFirstname());
		else if(j==3)
			celda.setCellValue(usuarios.get(i-1).getLastname());
		else if(j==4)
			celda.setCellValue(usuarios.get(i-1).getPassword());
		}
		}
		
		try {
			ByteArrayOutputStream ops=new ByteArrayOutputStream();
			wb.write(ops);
			byte[] array=ops.toByteArray();
			inputStreamResource= new ByteArrayInputStream(array);
		}catch(IOException e) {
			e.printStackTrace();
			
		}
		try {
			wb.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		System.out.println("excel creado");
		return inputStreamResource;
	}
	
	@Override
	public boolean addUsersToDbFromCsvFile(String fileName) {
		boolean result = true;
		Resource resource = new ClassPathResource(fileName.concat(Constant.CSV_EXT));
		
		//Enunciado: Leer archivo csv para obtener los datos de los Usuarios 
		//y guardarlos en BBD
		
	
		
		
		try {
			CSVReader reader = new CSVReader(new FileReader(resource.getFile().getPath()));
			String[]fila;
			
			
			while((fila=reader.readNext()) != null) {
				if(fila != null) {
					System.out.println("añandiendo usuario :"+Arrays.deepToString(fila));
					Usuario u=new Usuario(fila[0], fila[1], fila[2], fila[3]);
					usuarioRepository.save(u);
					
					
				}
			}

			
		} catch (Exception e) {
			result = false;
		}	
		
		
		
		return result;
	}

}
