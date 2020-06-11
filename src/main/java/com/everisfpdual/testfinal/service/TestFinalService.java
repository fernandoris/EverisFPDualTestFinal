package com.everisfpdual.testfinal.service;

import java.io.ByteArrayInputStream;

public interface TestFinalService {

	public ByteArrayInputStream getExcel();
	public boolean addUsersToDbFromCsvFile(String fileName);
	
}
