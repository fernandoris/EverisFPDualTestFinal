package com.everisfpdual.testfinal.service;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;

public interface TestFinalService {

	public ByteArrayInputStream getExcel() throws SQLException;

	public boolean addUsersToDbFromCsvFile(String fileName);
	
}
