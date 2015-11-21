package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import model.Teacher;
import view.decorator.EnrollStudentDecorator;
import view.decorator.NewCourseDecorator;
import view.decorator.NewPackageDecorator;
import view.decorator.NewTeacherDecorator;
import view.decorator.ServiceItemDecorator;
import view.forms.ServiceItemForm;
import view.forms.StudentForm;
import view.forms.TeacherForm;
import exception.AuthenticationException;
import exception.CourseException;
import exception.PackageException;

@SuppressWarnings("serial")
public class View extends JFrame {
	
	protected JMenuBar menuBar;
	protected static JFrame frame = new JFrame();
	private PersonView teacherFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected void instantiateMenuBar(){
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		addCourseOptionsToMenu();
		
		addPackageOptionsToMenu();
		
		addStudentsOptionsToMenu();
		
		addTeacherOptionsToMenu();
		
	}
	
	private void addTeacherOptionsToMenu(){
		JMenu teacherMenu = new JMenu("Professores");
		menuBar.add(teacherMenu);
		
		JMenuItem newTeacher = new JMenuItem("Cadastrar professor");
		newTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				teacherFrame = new NewTeacherDecorator(new TeacherForm());
				teacherFrame.buildScreen(teacherFrame, null);
				teacherFrame.setVisible(true);
			}
		});
		teacherMenu.add(newTeacher);
		
		JMenuItem searchTeacher = new JMenuItem("Visualizar professor");
		searchTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				teacherFrame = new SearchTeacher();
				teacherFrame.buildScreen(teacherFrame, null);
				teacherFrame.setVisible(true);
			}
		});
		teacherMenu.add(searchTeacher);
		
	}

	private void addStudentsOptionsToMenu(){
		JMenu studentMenu = new JMenu("Alunos");
		menuBar.add(studentMenu);
		
		JMenuItem newStudent = new JMenuItem("Cadastrar aluno");
		newStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				PersonView newStudentFrame = new EnrollStudentDecorator(new StudentForm());
				newStudentFrame.buildScreen(newStudentFrame, null);
				newStudentFrame.setVisible(true);
			}
		});
		studentMenu.add(newStudent);

		JMenuItem searchStudent = new JMenuItem("Visualizar Aluno");
		searchStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				PersonView searchStudentFrame = new SearchStudent();
				searchStudentFrame.buildScreen(searchStudentFrame, null);
				searchStudentFrame.setVisible(true);
			}
		});
		studentMenu.add(searchStudent);
		
	}

	private void addPackageOptionsToMenu(){

		JMenu packageMenu = new JMenu("Pacotes");
		menuBar.add(packageMenu);
		
		JMenuItem registerPackage = new JMenuItem("Cadastrar Pacote");
		registerPackage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean permissionToAccess = false;
				
				permissionToAccess = getPermissionToAccess();
				if(permissionToAccess == true){
					dispose();
					ServiceItemDecorator newPackageFrame = new NewPackageDecorator(new ServiceItemForm());
					newPackageFrame.buildScreen(newPackageFrame, null);
					newPackageFrame.setVisible(true);

				}
				else{
					View frame = new View();
					frame.setVisible(true);
				}
			}
		});
		packageMenu.add(registerPackage);
		
		JMenuItem searchPackage = new JMenuItem("Visualizar Pacote");
		searchPackage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();

				SearchPackage searchPackageFrame = new SearchPackage();
				searchPackageFrame.buildScreen(searchPackageFrame, null);
				searchPackageFrame.setVisible(true);				
			}
		});
		packageMenu.add(searchPackage);		
	}

	private void addCourseOptionsToMenu(){
		JMenu courseMenu = new JMenu("Cursos");
		menuBar.add(courseMenu);
		
		JMenuItem registerCourse = new JMenuItem("Cadastrar Curso");
		registerCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean permissionToAccess = false;
				
				permissionToAccess = getPermissionToAccess();
				if(permissionToAccess == true){
					dispose();
					NewCourseDecorator newCourseFrame = new NewCourseDecorator(new ServiceItemForm());
					newCourseFrame.buildScreen(newCourseFrame, null);
					newCourseFrame.setVisible(true);
				}
				else{
					View frame = new View();
					frame.setVisible(true);
				}
			}
		});
		courseMenu.add(registerCourse);
		
		JMenuItem searchCourse = new JMenuItem("Visualizar Curso");
		searchCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();			
				ServiceItemView searchCourseFrame = new SearchCourse();
				searchCourseFrame.buildScreen(searchCourseFrame, null);
				searchCourseFrame.setVisible(true);
			}
		});
		courseMenu.add(searchCourse);
		
	}
	protected boolean getPermissionToAccess(){
		
		boolean canAccess = false;
		String messageToUser = "";
		String enteredPassword = "senha digitada";
		final Object [] inputPassword;
		final JPasswordField enteredPasswordField;
		final JLabel passwordLabel;
							
		passwordLabel = new JLabel("Digite a senha:");
		enteredPasswordField = new JPasswordField();
		inputPassword = new Object[]{passwordLabel, enteredPasswordField};
		
		while(canAccess == false){
			AuthenticationView authenticationFrame = new AuthenticationView();
			int verify = authenticationFrame.showConfirmDialog(null, inputPassword, "Senha:",
											  authenticationFrame.OK_CANCEL_OPTION,
											  authenticationFrame.PLAIN_MESSAGE);
			enteredPassword = enteredPasswordField.getText();
			if (verify == JOptionPane.OK_OPTION) {
				if(enteredPassword != null){
					try {
						canAccess = authenticationFrame.authenticateUser(enteredPassword);
					} 
					catch(AuthenticationException | SQLException e){	
						messageToUser = e.toString();
						int indexToSepare = messageToUser.indexOf(":");
						messageToUser = messageToUser.substring(indexToSepare + 2);
						authenticationFrame.showMessageDialog(null, messageToUser);
					}
				}
			}
			else if (verify == JOptionPane.CANCEL_OPTION) {
				break;
			}
		}

		return canAccess;
	}
	
	/**
	 * Create the frame.
	 */
	public View(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 1248);
		
		instantiateMenuBar();
	}
	
	protected void showInfoMessage(String message){
		
		JOptionPane.showMessageDialog(null, message, "", JOptionPane.INFORMATION_MESSAGE);
	}
}
