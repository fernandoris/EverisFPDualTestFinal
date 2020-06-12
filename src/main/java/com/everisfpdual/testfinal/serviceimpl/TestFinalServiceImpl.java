package com.everisfpdual.testfinal.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
//import com.opencsv.CSVReader;
import com.opencsv.CSVReader;

@Service
public class TestFinalServiceImpl implements TestFinalService {

	@Autowired
	UsuarioRepository usuarioRepository;

	public ByteArrayInputStream getExcel() {

		// Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para
		// obtener el excel
		String[] cabecera = { "id", "email", "firstname", "lastname", "password" };
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		List<Usuario> usuarios = new ArrayList<>();
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(Constant.USUARIOS_SHEET);
		Row row = sheet.createRow(0);

		for (int i = 0; i < cabecera.length; i++) {
			Cell celda = row.createCell(i);
			celda.setCellValue(cabecera[i]);
		}

		for (int i = 0; i < usuarioRepository.count(); i++) {
			usuarios = usuarioRepository.findAll();
		}
		for (int i = 0; i < usuarios.size(); i++) {
			System.out.println(usuarios.get(i).getId());
		}
		for (int i = 0; i < usuarios.size(); i++) {
			row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(usuarios.get(i).getId());
			row.createCell(1).setCellValue(usuarios.get(i).getEmail());
			row.createCell(2).setCellValue(usuarios.get(i).getFirstname());
			row.createCell(3).setCellValue(usuarios.get(i).getLastname());
			row.createCell(4).setCellValue(usuarios.get(i).getPassword());
		}

		try {
			workbook.write(baos);
			ByteArrayInputStream inputStreamResource = new ByteArrayInputStream(baos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}

		ByteArrayInputStream inputStreamResource = new ByteArrayInputStream(baos.toByteArray());
		return inputStreamResource;
	}// fin get Excel

	@Override
	public boolean addUsersToDbFromCsvFile(String fileName) {
		boolean result = true;
		Resource resource = new ClassPathResource(fileName.concat(Constant.CSV_EXT));

		// Enunciado: Leer archivo csv para obtener los datos de los Usuarios
		// y guardarlos en BBDD
		try {

			CSVReader reader = new CSVReader(new FileReader(resource.getFile().getPath()));

			String[] datos = null;

			while ((datos = reader.readNext()) != null) {

				Usuario usuario = new Usuario(datos[0], datos[1], datos[2], datos[3]);

				usuarioRepository.save(usuario);

			}

		} catch (Exception e) {
			result = false;
		}

		return result;
	}// fin addUsers

}
