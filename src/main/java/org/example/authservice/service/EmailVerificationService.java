package org.example.authservice.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.authservice.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerificationService {
   @Autowired
   private EmailUtil emailUtil;

   private String name;
   private String email;
   private String token;

   public boolean sendVerificationService(String name ,String email,String token){
      this.name=name;
      this.email=email;
      this.token=token;

      try{
         emailUtil.sendVerificationEmail(this.email,this.name,this.token);
         return true;
      }
      catch(Exception e){
         return false;
      }
   }

}
