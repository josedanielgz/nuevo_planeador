package com.planeador.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PruebaControlador {

	@GetMapping("/")
	public String probar() {
		return "redirect:/admin/";
	}
}
