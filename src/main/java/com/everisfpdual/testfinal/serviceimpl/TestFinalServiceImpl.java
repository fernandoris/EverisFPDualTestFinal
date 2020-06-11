package com.everisfpdual.testfinal.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

		// Enunciado: Obtener lista de Usuarios e implementar la llamada al metodo para
		// obtener el excel
		List<Usuario> usuarios = new ArrayList<>();
		ByteArrayInputStream inputStreamResource = null;
		usuarios = usuarioRepository.findAll();

		final String[] columnas = { "Id", "Correo", "Nombre", "Apellido", "Contraseña" };

		// Generar la excel con los datos de los usuarios

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet hoja = workbook.createSheet(Constant.USUARIOS_SHEET);

		for (int i = 0; i < (usuarios.size() + 1); i++) {

			XSSFRow fila = hoja.createRow(i);

			for (int j = 0; j < columnas.length; j++) {

				XSSFCell celda = fila.createCell(j);

				if (i == 0) {

					celda.setCellValue(columnas[j]);

				} else {

					if (columnas[j] == "Id") {
						celda.setCellValue(usuarios.get(i - 1).getId());
					} else if (columnas[j] == "Correo") {
						celda.setCellValue(usuarios.get(i - 1).getEmail());
					} else if (columnas[j] == "Nombre") {
						celda.setCellValue(usuarios.get(i - 1).getFirstname());
					} else if (columnas[j] == "Apellido") {
						celda.setCellValue(usuarios.get(i - 1).getLastname());
					} else if (columnas[j] == "Contraseña") {
						celda.setCellValue(usuarios.get(i - 1).getPassword());
					}

				}

			}

		}

		try {
			File file = new File("excelGenerado.xlsx");
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
			fos.close();
			workbook.close();
			inputStreamResource = new ByteArrayInputStream(FileUtil.readAsByteArray(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

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

			List<String[]> usuarios = new ArrayList<>();
			usuarios = reader.readAll();
			
			//Iterator it = usuarios.iterator();
		
			for (String[] strings : usuarios) {
				Usuario usu = new Usuario();
				usu.setEmail(strings[0]);
				usu.setFirstname(strings[1]);
				usu.setLastname(strings[2]);
				usu.setPassword(strings[3]);
				usuarioRepository.save(usu);
			}

		} catch (Exception e) {
			result = false;
		}

		return result;
	}

}
