package com.planeador.controlador;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/docente")
public class DocenteControlador {

	@Autowired
	private DocenteServicio DocenteService;

	@Autowired
	private MateriaServicio MateriaServicio;

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
	public String login(HttpServletRequest request, HttpSession session, Model model) {
		if (request.getSession().getAttribute("docente_id") != null) {
			return "redirect:/docente/main";
		} else
			return "login_docente";
	}

	@PostMapping("/login")
	public String validate(RedirectAttributes att, @RequestParam String email, @RequestParam String password,
			HttpServletRequest request, HttpSession session, Model model) {

		Docente docente = DocenteService.select(email, password);

		if (docente != null) {

			if (docente.getAprobado()) {
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
	public String logout(HttpServletRequest request, HttpSession session, Model model) {
		request.getSession().invalidate();
		return "redirect:/docente/";
	}

	@PostMapping("/save")
	public String insertdocente(RedirectAttributes att, Docente docente, Model model) {
		docente.setAprobado(false);
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

	@GetMapping("/main")
	public String perfilDocente(HttpServletRequest request, Model model) {
//		Probando si el ModelAttribute funciona
//		int docente_id = (int) request.getSession().getAttribute("docente_id");
//		Docente doc = this.DocenteService.get(docente_id);
//		model.addAttribute("docente", doc);
		return "main";
	}

	@GetMapping("/perfil")
	public String elPerfil(HttpServletRequest request, Model model) {
//		Probando si el ModelAttribute funciona
//		int docente_id = (int) request.getSession().getAttribute("docente_id");
//		Docente doc = this.DocenteService.get(docente_id);
//		model.addAttribute("docente", doc);
		return "perfil";
	}

	@GetMapping("/materias")
	public String nuevaPaginaDeMaterias(
			@RequestParam(value = "pagina", required = false, defaultValue = "1") int pagina,
			@RequestParam(value = "nroDeElementos", required = false, defaultValue = "5") int nroDeElementos,
			Model model) {
		model.addAttribute("rows", MateriaServicio.paginaDeMaterias(pagina, nroDeElementos));
		return "";
	}

}
