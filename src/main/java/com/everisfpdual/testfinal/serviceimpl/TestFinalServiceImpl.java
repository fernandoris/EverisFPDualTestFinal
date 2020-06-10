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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.result.NoMoreReturnsException;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.validator.internal.util.Contracts;
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
		String[] columnas = {"id"+"correo"+"nombre"+"apellidos"+"contraseña"};
		
		ByteArrayInputStream inputStreamResource = new ByteArrayInputStream();
		
		HSSFWorkbook libro = new HSSFWorkbook();
		HSSFSheet hoja = libro.createSheet("alumnos");
		HSSFRow fila = hoja.createRow(0);
		HSSFCell celda;
		
		for (int i = 0; i < columnas.length; i++) {
			celda = fila.createCell(i);
			CellStyle style=libro.createCellStyle();
			HSSFFont font=libro.createFont();
			font.setFontHeightInPoints((short) 8);
		    font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);
			style.setFont(font);
			celda.setCellStyle(style);
			celda.setCellValue(columnas[i]);
			
		}
		for (int i = 1; i < usuarios.size()+1; i++) {
			fila = hoja.createRow(i);
			celda = fila.createCell(0);
			celda.setCellValue(usuarios.get(i-1).getId());
			celda = fila.createCell(1);
			celda.setCellValue(usuarios.get(i-1).getCorreo());
			celda = fila.createCell(2);
			celda.setCellValue(usuarios.get(i-1).getApellidos());
			celda = fila.createCell(3);
			celda.setCellValue(usuarios.get(i-1).getNombre());
			celda = fila.createCell(4);
			celda.setCellValue(usuarios.get(i-1).getContrasena());
		}
		
		System.out.println("Escribiendo");
		
		FileOutputStream sol=new FileOutputStream("C:\\Users\\srobleri\\Desktop\\Ficheros\\alumnos.xls");
		libro.write(sol);
		
		libro.close();
		
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
			String[] fila = null;
			int i=0;
			
			String id = null;
			String correo = null;
			String nombre = null;
			String apellidos = null;
			String contraseña = null;
			
			while((fila = reader.readNext()) != null) {
				for (int j = 0; j < fila[i].length(); j++) {
					
					int coma=0;
					if (fila[i].charAt(j)==',') {
						coma++;
					}
					else if(coma==0) {
						id+=fila[i].charAt(j);
					}
					else if(coma==0) {
						correo+=fila[i].charAt(j);
					}
					else if(coma==0) {
						nombre+=fila[i].charAt(j);
					}
					else if(coma==3) {
						apellidos+=fila[i].charAt(j);
					}
					else {
						contraseña+=fila[i].charAt(j);
					}
				}
				usuarios.add(new Usuario(Integer.parseInt(id),correo,nombre,apellidos,Integer.parseInt(contraseña)));
			}

			reader.close();
		} catch (Exception e) {
			result = false;
		}		
		
		return result;
	}

}
