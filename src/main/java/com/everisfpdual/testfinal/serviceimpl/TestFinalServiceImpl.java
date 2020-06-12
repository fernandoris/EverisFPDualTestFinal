package com.everisfpdual.testfinal.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

@Service
public class TestFinalServiceImpl implements TestFinalService{
	
	@Autowired
	UsuarioRepository usuarioRepository;

	
	public ByteArrayInputStream getExcel() throws IOException{
	String [] columns = {"id","email","firstname","lastname","password"};	
	
	Workbook work = new XSSFWorkbook();
	ByteArrayOutputStream baos = new ByteArrayOutputStream();	
	ByteArrayInputStream inputStreamResource = null;
	Sheet sheet = work.createSheet(Constant.USUARIOS_SHEET);
	
	Row row= sheet.createRow(0);
	Cell celda = row.createCell(0);
	

	for (int i = 0; i < columns.length; i++) {
		celda = row.createCell(i);
		celda.setCellValue(columns[i]);
	}	
	List<Usuario> usuarios = new ArrayList<>();
	usuarios=usuarioRepository.findAll();

	
	for (int i = 0; i < usuarios.size(); i++) {
		Row filaa= sheet.createRow(i);
		filaa.createCell(0).setCellValue(usuarios.get(i).getId());
		filaa.createCell(1).setCellValue(usuarios.get(i).getEmail());
		filaa.createCell(2).setCellValue(usuarios.get(i).getFirstname());
		filaa.createCell(3).setCellValue(usuarios.get(i).getLastname());
		filaa.createCell(4).setCellValue(usuarios.get(i).getPassword());				
	}
	
	// Se guarda el libro.
    try {
      baos = new ByteArrayOutputStream();
        work.write(baos);
        inputStreamResource = new ByteArrayInputStream(baos.toByteArray());
    } catch (Exception e) {
        e.printStackTrace();
    }	
		//Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para obtener el excel
			return  inputStreamResource ;
	}



	@Override
	public boolean addUsersToDbFromCsvFile(String fileName) {
		boolean result = true;
		Resource resource = new ClassPathResource(fileName.concat(Constant.CSV_EXT));
		
		//Enunciado: Leer archivo csv para obtener los datos de los Usuarios 
		//y guardarlos en BBDD
		
		try {
			
			CSVReader reader = new CSVReader(new FileReader(resource.getFile().getPath()));
			String [] linea;
			List<Usuario> usuarios =new ArrayList<Usuario>();
			while ((linea = reader.readNext()) !=null ) {
				Usuario usuarioNew=new Usuario(0,linea[0],linea[1],linea[2],linea[3]);
				usuarios.add(usuarioNew);
			}
			
			usuarioRepository.saveAll(usuarios);
			
		} catch (Exception e) {
			result = false;
		}		
		
		return result;
	}

}
