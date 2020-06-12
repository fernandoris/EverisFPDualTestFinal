package com.everisfpdual.testfinal.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface TestFinalService {

	public ByteArrayInputStream getExcel() throws IOException;

	public boolean addUsersToDbFromCsvFile(String fileName) throws IOException;
	
}
