package com.everisfpdual.testfinal.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
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
//import com.opencsv.CSVReader;

@Service
public class TestFinalServiceImpl implements TestFinalService{

	@Autowired
	UsuarioRepository usuarioRepository;
	
	public ByteArrayInputStream getExcel() {
		//Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para obtener el excel
				String[] columns = {"Id", "email", "Firstname", "Lastname", "Password"};
				
				List<Usuario> usuarios = new ArrayList<>();
				usuarios=usuarioRepository.findAll();
				
				ByteArrayInputStream inputStreamResource = null;
				
				// Se genera excel
				System.out.print("Generando excel...");
				XSSFWorkbook workbook = new XSSFWorkbook();
				

				// Se genera hoja en el excel y se comunica
				System.out.print("Generando hoja de trabajo...");
				XSSFSheet sheet = workbook.createSheet("Users");
				// Ahora se va a rellenar la hoja de trabajo
				System.out.print("Rellenando hoja de trabajo...");

				// Se crea estilo negrita y centrado para los nombres de las columnas
				XSSFCellStyle titleStyle = workbook.createCellStyle();
				XSSFFont titleFont = workbook.createFont();
				titleFont.setBold(true);
				titleStyle.setFont(titleFont);
				titleStyle.setAlignment(HorizontalAlignment.CENTER);

				// Se crea primera fila
				XSSFRow row = sheet.createRow(0);

				// Se rellena primera fila con el estilo creado anteriormente
				for (int i = 0; i < columns.length; i++) {
					// Se lee nombre de la columna y se rellena cada celda
					row.createCell(i).setCellValue(columns[i]);
					row.getCell(i).setCellStyle(titleStyle);
				}

				// Se rellena con los datos de los alumnos
				for (int i = 0; i < usuarios.size(); i++) {
					// Se crea fila para el alumno a introducir
					row = sheet.createRow(i + 1);
					// Se rellenan las celdas de la fila con sus datos
					row.createCell(0).setCellValue(usuarios.get(i).getId());
					row.createCell(1).setCellValue(usuarios.get(i).getEmail());
					row.createCell(2).setCellValue(usuarios.get(i).getFirstName());
					row.createCell(3).setCellValue(usuarios.get(i).getLastName());
					row.createCell(3).setCellValue(usuarios.get(i).getPassword());
				}

				// Se ajusta el ancho de las columnas para que se vea mejor su contenido
				for (int i = 0; i < columns.length; i++) {
					sheet.autoSizeColumn(i);
				}

				
		//
//				ByteArrayOutputStream ms=new ByteArrayOutputStream();
//				workbook.save(ms, FileFormatType.DEFAULT);
//				byte b[] = ms.toByteArray();
//				
				
				//Se pasa el excel a un array byte
				try {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
			        workbook.write(out);
			        byte[] array = out.toByteArray();
			        inputStreamResource = new ByteArrayInputStream(array);
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
				
				

				// Se guarda el archivo en la ruta especificada al principio de la clase
				System.out.print("Guardando excel..");
				try {
					//workbook.write(new FileOutputStream("C:\\Users\\irengelr\\Desktop\\archivo.xls"));
					System.out.println("--->Hecho");
					workbook.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//ByteArrayInputStream BAIS = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File(workbook.write(new FileOutputStream("archivo.xls")))));
				
					
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
