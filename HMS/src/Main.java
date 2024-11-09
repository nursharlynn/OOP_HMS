package project;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		HMS hms = new HMS();  
		String userDir = System.getProperty("user.dir");
		String patientListPath = userDir + "\\src\\project\\patient_list.txt";
		String staffListPath = userDir + "\\src\\project\\staff_list.txt";
		String medicineListPath = userDir + "\\src\\project\\medicine_list.txt";
		
		TXT_file_reader.importDataFromTxt(patientListPath);
		TXT_file_reader.importDataFromTxt(staffListPath);
		TXT_file_reader.importDataFromTxt(medicineListPath);
		
		Scanner sc = new Scanner(System.in);
		
		HMS.addAdmin(new Administrator("Admin", "admin", 45, "FEMALE", "password"));
		
		while (true) {
		    System.out.println("\nWelcome to the Hospital Management System!\n"
		    + "\nPlease select your role:\n"
		    + "1. Patient\n"
		    + "2. Staff\n"
		    + "3. Pharmacist\n"
		    + "4. Adminstrator\n"
		    + "Enter your choice:");
		    
		    int role = sc.nextInt();
		    sc.nextLine(); 
		    System.out.println();
		    String loginId, password;
		    System.out.println("Please enter your loginId:");
		    loginId = sc.nextLine();
		    System.out.println("Enter password:");
		    password = sc.nextLine();
		    System.out.println();
		
		    switch (role) {
		        case 1: // Patient
		        	Patient patient = hms.patientLogin(loginId, password);
		            if (patient != null) {
		                System.out.println("Login successful");
		                PatientInterface.handler(hms, patient, sc);
		            } else {
		                System.out.println("Invalid login!\n");
		            }
		            break;
		            
		        case 2: // Doctor
		            Doctor doctor = hms.doctorLogin(loginId, password);
		            if (doctor != null) {
		                System.out.println("Login successful!");
		                DoctorInterface.handler(hms, doctor, sc);
		            } else {
		                System.out.println("Invalid login!\n");
		            }
		            break;
		
		        case 3: // Pharmacist
		            Pharmacist pharmacist = hms.pharmacistLogin(loginId, password);
		            if (pharmacist != null) {
		                System.out.println("Login successful!");
		                PharmacistInterface.handler(hms, pharmacist, sc);
		            } else {
		                System.out.println("Invalid login!\n");
		            }
		            break;
		
		        case 4: // Administrator
		            Administrator admin = hms.adminLogin(loginId, password);
		            if (admin != null) {
		                System.out.println("Login successful!");
		                AdminInterface.handler(hms, admin, sc);
		            } else {
		                System.out.println("Invalid login!\n");
		            }
		            break;
		
		        default:
		            System.out.println("Invalid choice. Please enter a number between 1 and 4.\n");
		            break;
	            }
	        }
		}

}
