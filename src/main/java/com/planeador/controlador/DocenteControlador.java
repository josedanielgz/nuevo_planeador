package com.planeador.controlador;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.planeador.servicio.DocenteServicio;
import com.planeador.servicio.MateriaServicio;
import com.planeador.modelo.Docente;
import com.planeador.modelo.Materia;

//Este controlador administra todas las rutas del aplicativo que se van como /docente/{lo que sea}
@Controller
@RequestMapping("/docente")
public class DocenteControlador {

	@Autowired
	private DocenteServicio DocenteService;

//	@Autowired
//	private MateriaServicio MateriaServicio;

//	Esto vendría a sustituir lo que estaba en ProductoController.java del ejercicio de refencia, 
//	básicamente para recuperar los datos del docente. y mostrarlos en la topbar a lo largo de la
//	sesión
	
	@ModelAttribute("docente")
	public Docente getDocenteActual(Model model, HttpServletRequest request) {
		Docente docente = (Docente) model.getAttribute("docente");
		if (docente == null || docente.isEmpty()) {
			int docente_id;
			try {
				docente_id = (int) request.getSession().getAttribute("docente_id");
			} catch (NullPointerException e) {
				docente_id = 0;
			}
			if (docente_id == 0) {
				docente = new Docente();
			} else {
				docente = this.DocenteService.get(docente_id);
			}
			model.addAttribute("docente", docente);
		}
		return docente;
	}

	@GetMapping("")
	public String rutaPorDefectoDocente(HttpServletRequest request, HttpSession session, Model model) {
		if (request.getSession().getAttribute("docente_id") != null) {
			return "redirect:/docente/main";
		} else
			return "login_docente";
	}

	@PostMapping("/login")
	public String validarInicioSesionDocente(RedirectAttributes att, @RequestParam String email, @RequestParam String password,
			HttpServletRequest request, HttpSession session, Model model) {

		Docente docente = DocenteService.select(email, password);

		if (docente != null) {

			// Tuve que resetear el valor "admin_id" para solucionar un bug extraño con los inicios de sesión, pendiente
			// revisar a profundidad
			if (docente.getAprobado()) {
				request.getSession().setAttribute("admin_id", 0);
				request.getSession().setAttribute("docente_id", docente.getId());
				model.addAttribute("docente", docente);
				return "redirect:/docente/main";
			}

			att.addFlashAttribute("loginError", "El docente aún no se encuentra autorizado para acceder");
			return "redirect:/docente";

		} else {
			att.addFlashAttribute("loginError", "Usuario o contraseña incorrecta");
			return "redirect:/docente";
		}
	}

	@GetMapping("/logout")
	public String cerrarSesionDocente(HttpServletRequest request, HttpSession session, Model model) {
		request.getSession().invalidate();
		return "redirect:/docente/";
	}

	@PostMapping("/save")
	public String guardarDocente(RedirectAttributes att, Docente docente, Model model) {
		docente.setAprobado(false);
		DocenteService.save(docente);
		att.addFlashAttribute("accion", "Docente registrado con éxito!");
		return "redirect:/docente/";
	}

	@GetMapping("/new")
	public String formularioDeRegistroDocente(Model model) {
		return "register_docente";
	}

	@GetMapping("/main")
	public String seccionPrincipalDocente(HttpServletRequest request, Model model) {
		return "main";
	}

//EN PAUSA
//El siguiente código se utilizó en algún punto temprano y se conserva como referencia, pero posiblemente
//sea desechado en el futuro
	
//	@GetMapping("/perfil")
//	public String elPerfil(HttpServletRequest request, Model model) {
//		return "perfil";
//	}

}
