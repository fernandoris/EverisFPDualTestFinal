package com.everisfpdual.testfinal.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
/*
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
*/
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
		ByteArrayInputStream inputStreamResource = null;
		
		final String[] columnas = {"Id","Correo","Nombre","Apellido","Contraseña"};
		
		Connection con = null;
		String host = "localhost:8081";
		String user = "root";
		String pass = "root";
		String dtbs = "jdbc:h2:mem:test";
		String newConnectionURL = "jdbc:mysql://" + host + "/" + dtbs
		                    + "?" + "user=" + user + "&password=" + pass;
		try {
			FileOutputStream fos = new FileOutputStream("");
			Class.forName("com.mysql.jdbc.Driver");
			con = (java.sql.Connection) DriverManager.getConnection(newConnectionURL);
			ps = con.prepareStatement("SELECT * FROM USERS");
			ResultSet rs = ps.exe
			
			
		} catch (SQLException e) {
			System.out.println("Error en la conexión:");
			e.printStackTrace();
		}		
		
		//Generar la excel con los datos de los usuarios 
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet hoja = workbook.createSheet();		
		
		for (int i = 0; i < (usuarios.size() + 1); i++) {
			
			HSSFRow fila = hoja.createRow(i);
			
			for (int j = 0; j < columnas.length; j++) {
				
				HSSFCell celda = fila.createCell(j);
				
				if(i == 0) {
					
					celda.setCellValue(columnas[j]);
					
				} else {
					
					if(columnas[j] == "Id") {
						celda.setCellValue(usuarios.get(i - 1).getId());
					} else if(columnas[j] == "Correo") {
						celda.setCellValue(usuarios.get(i - 1).getEmail());
					} else if(columnas[j] == "Nombre") {
						celda.setCellValue(usuarios.get(i - 1).getFirstname());
					} else if(columnas[j] == "Apellido") {
						celda.setCellValue(usuarios.get(i - 1).getLastname());
					} else if(columnas[j] == "Contraseña") {
						celda.setCellValue(usuarios.get(i - 1).getPassword());
					}
					
					
				}
				
			}
			
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
			//CSVReader reader = new CSVReader(new FileReader(resource.getFile().getPath()));
			
		} catch (Exception e) {
			result = false;
		}		
		
		return result;
	}

}
