package com.planeador.controlador;

//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/fragments")

//PENDIENTE: Quizás no necesite esta clase de aquí después de todo, revisar la documentación
//después y refactorizar
public class FragmentsControlador {
	
//	@GetMapping("/header")
	public String cabecera() {
//		https://stackoverflow.com/a/42581812
		return "fragments/header";
	}

}
