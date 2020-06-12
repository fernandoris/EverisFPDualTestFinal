package com.everisfpdual.testfinal.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.aspectj.util.FileUtil;
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
public class TestFinalServiceImpl implements TestFinalService {

	@Autowired
	UsuarioRepository usuarioRepository;

	public ByteArrayInputStream getExcel() {
		
		//Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para obtener el excel
		List<Usuario> usuarios = new ArrayList<>();
		String[] columnas = {"Id", "Correo", "Nombre", "Apellido", "Contraseña"};
		Statement st;
		ResultSet rs;
		Connection con = null;
		try {
			con = DriverManager.getConnection(
			"jdbc:h2:mem:test","root","root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query = "SELECT * FROM users";
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				int id = rs.getInt(1);
				String email = rs.getString(2);
				String fs = rs.getString(3);
				String ls = rs.getString(4);
				String pwd = rs.getString(5);
				
				Usuario u = new Usuario(id,email,fs,ls,pwd);
				usuarios.add(u);
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		for(Usuario u : usuarios)
			System.out.println(u.getId() + " " + u.getEmail() + " " + u.getFirstname() + " " + u.getLastname() + " " + u.getPassword());
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		 HSSFSheet hoja = workbook.createSheet();
		 CellStyle negrita = workbook.createCellStyle();
		 negrita.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		 negrita.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		 Font fuente = workbook.createFont();
		 fuente.setBold(true);
		 fuente.setFontHeightInPoints((short)16);
		 fuente.setFontName("Calibri");
		 negrita.setFont(fuente);
		
		 
		
		 int f = 0;
		 for (int i = 0; i < usuarios.size()+1; i++) {
			 HSSFRow fila = hoja.createRow(f++);	
			 int c = 0;
			for (int j = 0; j < columnas.length; j++) {
				HSSFCell celda = fila.createCell(c++);
				if(f == 1) {
					celda.setCellStyle(negrita);
					celda.setCellValue(columnas[j]);
				}
				else {
					if( j == 0)
						celda.setCellValue(usuarios.get(i-1).getId());
					else if( j == 1)
						celda.setCellValue(usuarios.get(i-1).getEmail());
					else if( j == 2)
						celda.setCellValue(usuarios.get(i-1).getFirstname());
					else if( j == 3)
						celda.setCellValue(usuarios.get(i-1).getLastname());
					else if( j == 4)
						celda.setCellValue(usuarios.get(i-1).getPassword());
				}					
			}

		 }
			ByteArrayInputStream inputStreamResource = null;
			FileOutputStream fos;
			try {
				File excel = new File("usuarios.xls");
				//fos = new FileOutputStream("C:\\Users\\fgarcmac\\Desktop\\lista-final-alumnos.xls");
				//IOUtils.copy(inputStreamResource, new FileOutputStream("C:\\Users\\fgarcmac\\Desktop\\lista-final-alumnos.xls"));
				//System.out.println("Escribiendo...");
				//System.out.println("fin");
				fos = new FileOutputStream(excel);
				 workbook.write(fos);
				 fos.close();
				 workbook.close();
				 inputStreamResource = new ByteArrayInputStream(FileUtil.readAsByteArray(excel));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return inputStreamResource;
			
	}

	@Override
	public boolean addUsersToDbFromCsvFile(String fileName) {
		boolean result = true;
		Resource resource = new ClassPathResource(fileName.concat(Constant.CSV_EXT));
		
		PreparedStatement ps;
		Connection con = null;

		try {
			con = DriverManager.getConnection(
			"jdbc:h2:mem:test","root","root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		// Enunciado: Leer archivo csv para obtener los datos de los Usuarios
		// y guardarlos en BBDD
		
		try {
			CSVReader reader = new CSVReader(new FileReader(resource.getFile().getPath()));
			String[] siguiente;
			String query = "INSERT INTO users (email,firstname,lastname,password) VALUES (?,?,?,?)";
			ps = con.prepareStatement(query);
			
			while((siguiente = reader.readNext()) != null) {
				String email = siguiente[0];
				//System.out.println(email);
				String fs = siguiente[1];
				//System.out.println(fs);
				String ls = siguiente[2];
				//System.out.println(ls);
				String psw = siguiente[3];
				//System.out.println(psw);
				
				ps.setString(1, email);	ps.setString(2, fs); 
				ps.setString(3, ls);	ps.setString(4, psw);
				
				ps.addBatch();
			}
			
				ps.executeBatch();
				con.close();
				
		} catch (Exception e) {
			result = false;
		}

		return result;
	}

}
