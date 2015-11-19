package controllerTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.datatype.CPF;

import org.junit.Before;
import org.junit.Test;

import controller.StudentClassController;
import exception.PersonException;
import exception.StudentClassException;

public class StudentClassControllerTest {
	
	private StudentClassController studentClassController; 
	private String classId;
	private ArrayList<CPF> students = new ArrayList<CPF>();
	
	@Before
	public void setUp() throws Exception {
		
		studentClassController = new StudentClassController();
		
		classId = "APLICAÇÃO - MA 10/2";
		students.add(new CPF("76658496285"));
		students.add(new CPF("82835356509"));
	}

	@Test
	public void testIfEnrollStudentToClass(){
		
		try{
			studentClassController.enrollStudentToClass(classId, students);
		}
		catch(PersonException | StudentClassException e){
			fail("Should not throw this exception: "+ e.getMessage());
		}
	}

}