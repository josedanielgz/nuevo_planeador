package com.planeador.controlador;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.planeador.modelo.Microcurriculo;
import com.planeador.servicio.MicrocurriculoServicio;

@Controller
@RequestMapping("/microcurriculos")
public class MicrocurriculoControlador {
	
	@Autowired
	private MicrocurriculoServicio microcurriculoService;
	
	@GetMapping("")
	public String casoDefault(HttpServletRequest request, HttpSession session, Model model) {
		return "redirect:/microcurriculos/lista";
	}
	
	@GetMapping("/lista")
	public String listaDeMicrocurriculos(HttpServletRequest request, Model model) {
		List<Microcurriculo> microcurriculos = this.microcurriculoService.listaDeMicrocurriculos();
		model.addAttribute("microcurriculos",microcurriculos);
		return "lista";
	}
	
//	Redirigimos la página actual a donde va a estar el nuevo microcurrículo
	@GetMapping("/nuevo")
	public String formularioDeMicrocurriculos(HttpServletRequest request, HttpSession session, Model model) {
		return "lista";
	}
	
//	Hablamos con el backend para que guarde el microcurriculo
	@PostMapping("/nuevo")
	public String subirMicrocurriculos(HttpServletRequest request, HttpSession session, Model model) {
		return "lista";
	}

}
