package com.iktpreobuka.final_project.services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.controllers.dto.ChangePasswordDTO;
import com.iktpreobuka.final_project.controllers.dto.UserDTO;
import com.iktpreobuka.final_project.controllers.util.EmailObject;
import com.iktpreobuka.final_project.controllers.util.Encryption;
import com.iktpreobuka.final_project.controllers.util.PasswordValidation;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.controllers.util.RegExValidation;
import com.iktpreobuka.final_project.entities.AdminEntity;
import com.iktpreobuka.final_project.repository.AdminRepository;
import com.iktpreobuka.final_project.repository.RoleRepository;

@Service
public class AdminDaoImpl implements AdminDao{
	
	@Autowired
	private AdminRepository adminRep;
	
	@Autowired
	private RoleRepository roleRep;
	
	@Autowired
	private PasswordValidation passwordVaidation;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserDao userDao;
	
	//Find all
	@Override
	public  ResponseEntity<?> findAll(){
		try {
			return new ResponseEntity<>(adminRep.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Find  by id
	@Override
	public ResponseEntity<?> getAdminById(String idString){
		try {
			Integer id=Integer.parseInt(idString);			
			AdminEntity ae=adminRep.findById(id).get();
		
			return new ResponseEntity<>(ae, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Admin not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//Find  by token
	@Override
	public ResponseEntity<?> getAdminFromToken(){
			return userDao.getUserFromToken();	
	}
	
	//Delete by id 
	@Override
	public ResponseEntity<?> deleteById(String idString) {
		try {	
			Integer id=Integer.parseInt(idString);			
			AdminEntity ae=adminRep.findById(id).get();

			adminRep.delete(ae);
			return new ResponseEntity<>(ae, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Admin not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Post new admin
	@Override
	public ResponseEntity<?> postNewAdmin(UserDTO aeBody) {
		try {			
			AdminEntity admin=new AdminEntity();
			String pass=passwordVaidation.generatePass();
			
			if(userDao.checkEmail(aeBody.getEmail()))
				admin.setEmail(aeBody.getEmail());
			else
				return new ResponseEntity<>(new RESTError(2,"Email must be unique."), HttpStatus.BAD_REQUEST);

			
			admin.setName(aeBody.getName());
			admin.setLastname(aeBody.getLastname());
			admin.setUsername(aeBody.getUsername());
			admin.setPassword(Encryption.getPassEncoded(pass));			
			admin.setRole(roleRep.findById(1).get());
			
			adminRep.save(admin);
			
			
			EmailObject eo=new EmailObject();
			String text=emailService.textTemplatePass(pass);

			eo.setTo("fotos1992@gmail.com");//admin.getEmail()
			eo.setSubject("Password notification");
			eo.setText(text);
			
			emailService.sendSimpleMessage(eo);
		
			
			return new ResponseEntity<>(admin, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//POTREBNO ISPRAVITI
	//Put admin by id
	@Override
	public ResponseEntity<?> putAdminById(UserDTO aeBody, String idString){
		try {
			Integer id=Integer.parseInt(idString);			
			AdminEntity ae=adminRep.findById(id).get();
			
			if(aeBody.getName()!=null)
				if(aeBody.getName().length()>=3 && aeBody.getName().length()<=15)
					if(RegExValidation.validateFirstLetter(aeBody.getName()))
						ae.setName(aeBody.getName());	
					else
						return new ResponseEntity<>(new RESTError(2,"First name format must be first letter uppercase then lowercase(e.g. Name)"), HttpStatus.BAD_REQUEST);
				else	
					return new ResponseEntity<>(new RESTError(2,"First name must be between 3 and 15 characters long."), HttpStatus.BAD_REQUEST);
			
			
			if(aeBody.getLastname()!=null)
				if(aeBody.getLastname().length()>=3 && aeBody.getLastname().length()<=15)
					if(RegExValidation.validateFirstLetter(aeBody.getLastname()))
						ae.setLastname(aeBody.getLastname());	
					else
						return new ResponseEntity<>(new RESTError(2,"Lastname format must be first letter uppercase then lowercase(e.g. Name)"), HttpStatus.BAD_REQUEST);
				else	
					return new ResponseEntity<>(new RESTError(2,"Last name must be between 3 and 15 characters long."), HttpStatus.BAD_REQUEST);
			
			if(aeBody.getUsername()!=null)
				if(aeBody.getUsername().length()>=3 && aeBody.getUsername().length()<=15)
					ae.setUsername(aeBody.getUsername());	
				else	
					return new ResponseEntity<>(new RESTError(2,"Username must be between 3 and 15 characters long."), HttpStatus.BAD_REQUEST);
			
			if(aeBody.getEmail()!=null) {
				String email=aeBody.getEmail();
				if(!email.equals(ae.getEmail())){
					if (RegExValidation.validateEmail(email))
						if(userDao.checkEmail(email))
							ae.setEmail(email);
						else
							return new ResponseEntity<>(new RESTError(2,"Email must be unique."), HttpStatus.BAD_REQUEST);
					else
						return new ResponseEntity<>(new RESTError(2,"Email must be exemple@gmail.com."), HttpStatus.BAD_REQUEST);
				}
			}
				
			adminRep.save(ae);
			return new ResponseEntity<>(ae, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Admin not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//change admin password by id
	@Override
	public ResponseEntity<?> changePassword(ChangePasswordDTO cpBody,String idString){
		return userDao.changePassword(cpBody,idString);
	}
	
	//reset user password by id
	@Override
	public ResponseEntity<?> resetUserPassword(String idString){
		return userDao.resetUserPassword(idString);
	}
	
	//get Log as string
	@Override
	public ResponseEntity<?> getLog(){
		FileReader fr=null;
		BufferedReader br=null;
		try {
// buff reader 7000linija 35 sec   ......scanner 50 sec		
// 4500linije concat() = 8 sec ....""+ ""=16 sec... string builder=6
			fr=new FileReader("logs/spring-boot-logging.log");
			br=new BufferedReader(fr);
			StringBuilder sb=new StringBuilder("");
			String r;
			while((r=br.readLine())!=null)
				sb.append(r).append("\n");
			
			List<StringBuilder> list= new ArrayList<StringBuilder>();
			list.add(sb);
			return new ResponseEntity<>(list, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}finally {
			try {
				if(fr!=null)
					fr.close();
				if(br!=null)
					br.close();
			} catch (IOException e) {
				return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
	
	
	
	//get download Log 
		@Override
		public ResponseEntity<?> downloadLog(HttpServletRequest req,HttpServletResponse res){
			 try {
			      
			      String fileName = "spring-boot-logging.log";
			      res.setContentType("application/octet-stream");//octet-stream   application/pdf
			      res.setHeader("Expires", "0");
			      res.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
			      res.setHeader("Content-Disposition","attachment;filename=" + fileName);
			      res.setHeader("Accept-Ranges", "bytes");
			      File nfsPDF = new File("logs/"+fileName);
			      FileInputStream fis = new FileInputStream(nfsPDF);
			      BufferedInputStream bis = new BufferedInputStream(fis);
			      ServletOutputStream sos = res.getOutputStream();
			      byte[] buffer = new byte[2048];
			      
			      while (true) {
			        int bytesRead = bis.read(buffer, 0, buffer.length);
			        if (bytesRead < 0) {
			          break;
			        }
			      sos.write(buffer, 0, bytesRead);
			      sos.flush();
			      }
			      sos.flush();
			      bis.close();
			      
				    List<String> msg=new ArrayList<String>(); 
				    msg.add("Log file successfully downloaded");//prebaceno u listu da bi se front snasao
			      
			      return new ResponseEntity<>(msg, HttpStatus.OK);
			      
			    } catch (Exception e) {
			    	return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
			    }
			
		}
	
}
