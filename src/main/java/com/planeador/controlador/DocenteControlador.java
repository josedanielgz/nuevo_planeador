package com.planeador.controlador;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.planeador.servicio.DocenteServicio;

import com.planeador.modelo.Docente;

@Controller
@RequestMapping("/docente")
public class DocenteControlador {

	@Autowired
	private DocenteServicio DocenteService;

	@GetMapping("")
	public String login(HttpServletRequest request, HttpSession session, Model model) {
		if (request.getSession().getAttribute("docente_id") != null) {
			return "profile";
		} else
			return "login_docente";
	}

	@PostMapping("/login")
	public String validate(RedirectAttributes att, @RequestParam String email, @RequestParam String password,
			HttpServletRequest request, HttpSession session, Model model) {
		
		Docente docente = DocenteService.select(email, password);

		if (docente != null) {
			request.getSession().setAttribute("docente_id", docente.getId());
			return "redirect:/docente/profile";
		} else {
			att.addFlashAttribute("loginError", "Usuario o contraseña incorrecta");
			return "redirect:/docente";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpSession session, Model model) {
		request.getSession().invalidate();
		return "redirect:/docente/";
	}

	@PostMapping("/save")
	public String insertdocente(RedirectAttributes att, Docente docente, Model model) {
		DocenteService.save(docente);
		att.addFlashAttribute("accion", "Docente registrado con éxito!");
		return "redirect:/docente/";
	}
	
	@GetMapping("/new")
	public String showForm(Model model) {
		return "register_docente";
	}
	
//	Esto está basado en ProductoController.java del ejercicio de refencia, luego
//	pensamos bien si moverlo a otro lado, básicamente para recuperar los datos
//	del docente. y mostrarlos en el perfil
	
	@GetMapping("/profile")
	public String perfilDocente(HttpServletRequest request, Model model) {
//		Nota, le cambié el nombre de tables a profile para probar
		int docente_id = (int) request.getSession().getAttribute("docente_id");
		Docente doc = this.DocenteService.get(docente_id);
		model.addAttribute("docente", doc);
		return "profile";
	}
	
	@GetMapping("/perfil")
	public String elPerfil(HttpServletRequest request, Model model) {
		int docente_id = (int) request.getSession().getAttribute("docente_id");
		Docente doc = this.DocenteService.get(docente_id);
		// Se resolverá después
//		Docente doc = (Docente) request.getSession().getAttribute("docente");
		model.addAttribute("docente", doc);
		return "perfil";
	}
	

}
