package controller;

import java.sql.ResultSet;

import dao.CourseDAO;
import exception.CourseException;
import model.Course;

public class CourseController {
	
	public CourseController(){}
		
	/**
	 * Create a new course with the given information
	 * @param courseName - Name of the course
	 * @param courseDescription - Description of the course
	 * @param courseDuration - Duration of the course
	 * @param courseValue - Value of the course
	 * @return TRUE if the course was created or FALSE if it does not
	 * @throws CourseException
	 */
	public boolean newCourse(String courseName, String courseDescription,
							 Integer courseDuration, Integer courseValue)
							 throws CourseException{
		
		Course course = new Course(courseName, courseDescription, courseDuration, courseValue);
		
		boolean wasSaved =  false;
		
		CourseDAO courseDao = new CourseDAO();
		
		wasSaved = courseDao.save(course);
		
		return wasSaved;
	}
	
	/**
	 * Update a given course with the new information
	 * @param courseId - The course id to be updated
	 * @param courseName - The name of the course
	 * @param courseDescription - The new description of the course
	 * @param courseDuration - The new duration of the course
	 * @param courseValue - The new value of the course
	 * @return TRUE if the course was updated or FALSE if it does not
	 * @throws CourseException
	 */
	public boolean updateCourse(Integer courseId, String courseName, String courseDescription,
								 Integer courseDuration, Integer courseValue)
								 throws CourseException{
		
		Course course = new Course(courseName, courseDescription, courseDuration, courseValue);
		
		boolean wasSaved = false;
		
		CourseDAO courseDao = new CourseDAO();
		
		wasSaved = courseDao.update(courseId, course);
		
		return wasSaved;
	}
	
	public ResultSet showCourse(String searchedCourse) throws CourseException{
		
		ResultSet resultOfSearch;
		CourseDAO courseDao = new CourseDAO();
		Course course = new Course(searchedCourse);
		
		resultOfSearch = courseDao.get(course);
				
		return resultOfSearch;
	}
	
	public ResultSet showCourse(){
		
		ResultSet resultOfTheSelect;
		CourseDAO courseDao = new CourseDAO();		
		resultOfTheSelect = courseDao.getAll();
		
		return resultOfTheSelect;
		
	}

}
