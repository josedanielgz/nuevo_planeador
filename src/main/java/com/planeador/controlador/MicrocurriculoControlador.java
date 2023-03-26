package com.planeador.controlador;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.planeador.servicio.MicrocurriculoServicio;

@Controller
@RequestMapping("/microcurriculos")
public class MicrocurriculoControlador {
	
	@Autowired
	private MicrocurriculoServicio microcurriculoService;
	
	@GetMapping("")
	public String casoDefault(HttpServletRequest request, HttpSession session, Model model) {
		return "lista";
	}

}
