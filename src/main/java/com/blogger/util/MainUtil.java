package com.blogger.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MainUtil {
   PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

}
