package com.iktpreobuka.final_project.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.controllers.dto.ChangePasswordDTO;
import com.iktpreobuka.final_project.controllers.dto.UserDTO;
import com.iktpreobuka.final_project.security.Views;
import com.iktpreobuka.final_project.services.AdminDao;



@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminDao adminDao;
		
	//Find all
	@RequestMapping(method=RequestMethod.GET,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getAllAdmin(){
		return adminDao.findAll();
	}
	
	//Find by id
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getAdminById(@PathVariable String id){
		return adminDao.getAdminById(id);
	}
	
	//Find by token 
	@RequestMapping(method=RequestMethod.GET,value="/get_by_token/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getStudentFromToken_student(){
		return adminDao.getAdminFromToken();
	}
	
	//Delete by id
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> deleteAdmin(@PathVariable String id){	
		return adminDao.deleteById(id);	
	}
	
	//Post new admin
	@RequestMapping(method=RequestMethod.POST,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> postNewAdmin(@Valid @RequestBody UserDTO ae){
		return adminDao.postNewAdmin(ae);
	}
	//Put admin by id
	@RequestMapping(method=RequestMethod.PUT,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> putAdminById(@RequestBody UserDTO ae,@PathVariable String id){
		return adminDao.putAdminById(ae,id);
	}
	
	//change admin  password by id
	@RequestMapping(method=RequestMethod.PUT,value="/change_password/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO cpe,@PathVariable String id){
		return adminDao.changePassword(cpe,id);
	}
	
	//reset  password by id
	@RequestMapping(method=RequestMethod.PUT,value="/reset_password/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> resetUserPassword(@PathVariable String id){//@RequestBody ChangePasswordDTO cpe,
		return adminDao.resetUserPassword(id);
	}
	
	//get log as string
	@RequestMapping(method=RequestMethod.GET,value="/log/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getLog(){
		return adminDao.getLog();
	}
	
	
	//get log
	@RequestMapping(method=RequestMethod.GET,value="/log_download/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> downloadLog(HttpServletRequest req,HttpServletResponse res){
			return  adminDao.downloadLog(req, res);
	}
	
	
//@Path("/download/")
//@Produces("application/pdf")
//public byte[] getDownload() {
//   byte[] pdfContents = yourService.getPdfContent();
//   return pdfContents;
//}
//	    @RequestMapping("/test2")
//	    public void downloadPDFResource( HttpServletRequest request,
//	                                     HttpServletResponse response)
//	    {
//	    	
//	    	String fileName="logs/spring-boot-logging.log";
//	        //If user is not authorized - he should be thrown out from here itself
//	         
//	        //Authorized user will download the file
//	        String dataDirectory = request.getServletContext().getRealPath("logs/spring-boot-logging.log");
//	        Path file = Paths.get(dataDirectory, fileName);
//	        if (Files.exists(file))
//	        {
//	            response.setContentType("application/pdf");
//	            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
//	            try
//	            {
//	                Files.copy(file, response.getOutputStream());
//	                response.getOutputStream().flush();
//	            }
//	            catch (IOException ex) {
//	                ex.printStackTrace();
//	            }
//	        }
//	    }
	public static void main(String[] args) {
		
		
		
		FileReader fileRead= null;
		FileWriter fileWrite = null;
		BufferedReader fin=null;
	    //FileOutputStream fout = null;
	    BufferedWriter fout=null;
	    
	    try {
			fileRead=new FileReader("logs/spring-boot-logging.log");
			fileWrite=new FileWriter("C:/Users/Petar/Downloads/iz_projekta.txt");
			fin=new BufferedReader(fileRead);
	    
	        fout = new BufferedWriter(fileWrite);

	        
	        
	     //   final byte data[] = new byte[1024];
	        String r;
	        while ((r=fin.readLine())!=null) {
	            fout.write(r);
	        }
	    }catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
	        if (fin != null) {
	            try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	        if (fout != null) {
	            try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	    }
	
}
	
}
