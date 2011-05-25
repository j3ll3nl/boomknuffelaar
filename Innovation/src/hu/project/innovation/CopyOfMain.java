package hu.project.innovation;

import hu.project.innovation.configuration.controller.xml.ImportController;

import java.io.File;

public class CopyOfMain {

	public static void main(String[] args) throws Exception {
		ImportController importController = new ImportController();

		importController.read(new File("C:\\Users\\Stefan Kemp\\Desktop\\test.xml"));

		System.out.println("Done!");
	}
}