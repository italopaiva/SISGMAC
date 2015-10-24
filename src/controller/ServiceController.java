package controller;

import java.util.ArrayList;

import dao.PackageDAO;
import dao.ServiceDAO;
import exception.CourseException;
import exception.DateException;
import exception.PaymentException;
import exception.ServiceException;
import model.Payment;
import model.Service;
import model.Student;
import model.datatype.CPF;

public class ServiceController {
	
	private static final String CANT_SAVE_NULL_SERVICE = "Não é possível salvar um serviço nulo";
	private ServiceDAO serviceDAO;
	private PaymentController paymentController;
	
	public ServiceController(){
		serviceDAO = new ServiceDAO();
		paymentController = new PaymentController();
	}
	
	/**
	 * Creates a new service with the requested courses and packages by a student
	 * @param student - The student that requested the services
	 * @param courses - The courses requested
	 * @param packages - The packages requested
	 * @throws ServiceException
	 */	
	public Service newService(Student student, ArrayList<String> courses, ArrayList<String> packages) throws ServiceException{

		Service service = new Service(student, courses, packages);
				
		return service;
	}
	
	/**
	 * Try to save the given service
	 * @param service
	 * @throws ServiceException
	 */
	public void saveService(Service service) throws ServiceException{
		
		if(service != null){
			serviceDAO.save(service);
		}
		else{
			throw new ServiceException(CANT_SAVE_NULL_SERVICE);
		}
	}

	/**
	 * Search a service of a specific student
	 * @param basicDataOfStudent - contains the data of the student
	 * @return and arrayList of services
	 * @throws CourseException
	 * @throws DateException
	 * @throws ServiceException
	 * @throws PaymentException 
	 */
	public ArrayList<Service> searchService(Student basicDataOfStudent) throws CourseException, DateException, ServiceException, PaymentException{
		
		ArrayList<Service> services = new ArrayList<Service>();
		ArrayList<Service> servicesWithPayments = new ArrayList<Service>();

		services = serviceDAO.get(basicDataOfStudent);
		
		int i = 0;
		while(i < services.size()){
			
			Service service = services.get(i);
			
			Payment payment = service.getPayment();
			
			payment = paymentController.searchPayment(payment);

			service = new Service(services.get(i), payment);
			servicesWithPayments.add(service);
			i++;
		}
		return servicesWithPayments;
	
	}
	
	public void setServiceDAO(ServiceDAO serviceDao) {
		this.serviceDAO = serviceDao;
	}
	
	public void setPaymentController(PaymentController paymentController){
		this.paymentController = paymentController;
	}

	
}
