package com.planeador.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.planeador.servicio.AdministradorServicio;
import com.planeador.servicio.DocenteServicio;
import com.planeador.modelo.Administrador;
import com.planeador.modelo.Docente;

@Controller
@RequestMapping("/admin")
public class AdministradorControlador {

	@Autowired
	private AdministradorServicio administradorService;
	@Autowired
	private DocenteServicio docenteService;

	@GetMapping("")
	public String login(HttpServletRequest request, HttpSession session, Model model) {
		if (request.getSession().getAttribute("admin_id") != null) {
			return "main";
		} else
			return "login_admin";
	}

//	@GetMapping("login")
//	public String loginPage(HttpServletRequest request, HttpSession session, Model model) {
////		request.getSession().setAttribute("es_admin", true);
//		request.getSession().invalidate();
//		return "login_admin";
//	}

	@PostMapping("/login")
	public String validate(RedirectAttributes att, @RequestParam String email, @RequestParam String password,
			HttpServletRequest request, HttpSession session, Model model) {

		Administrador admin = administradorService.select(email, password);

		if (admin != null) {
			request.getSession().setAttribute("admin_id", admin.getId());
//			request.getSession().setAttribute("administrador", admin);
			return "redirect:/admin/main";
		} else {
			att.addFlashAttribute("loginError", "Usuario o contraseña incorrecta");
			return "redirect:/admin";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpSession session, Model model) {
		request.getSession().invalidate();
		return "redirect:/admin/";
	}

	@PostMapping("/save")
	public String insertAdmin(RedirectAttributes att, Administrador admin, Model model) {
		administradorService.save(admin);
		att.addFlashAttribute("accion", "Administrador registrado con éxito!");
		return "redirect:/admin/";
	}

	@GetMapping("/new")
	public String showForm(Model model) {
		return "registeradmin";
	}

//	Esto está basado en ProductoController.java del ejercicio de refencia, luego
//	pensamos bien si moverlo a otro lado, básicamente para recuperar los datos
//	del admin. y mostrarlos en el perfil

	@GetMapping("/main")
	public String perfilAdministrador(HttpServletRequest request, Model model) {
//		Nota, le cambié el nombre de profile a main para probar
		int admin_id = (int) request.getSession().getAttribute("admin_id");
		Administrador adm = this.administradorService.get(admin_id);
		model.addAttribute("admin", adm);
		return "main";
	}

	@GetMapping("/perfil")
	public String elPerfil(HttpServletRequest request, Model model) {
		int admin_id = (int) request.getSession().getAttribute("admin_id");
		Administrador adm = this.administradorService.get(admin_id);
		// Se resolverá después
//		Administrador adm = (Administrador) request.getSession().getAttribute("admin");
		model.addAttribute("admin", adm);
		return "perfil";
	}

	@GetMapping("/solicitudes")
	public String solicitudesRegistro(HttpServletRequest request, Model model) {

//		https://stackoverflow.com/questions/50348166/java-8-list-of-objects-to-mapstring-list-of-values
//		https://www.appsloveworld.com/springboot/100/35/how-can-i-create-dynamic-table-in-thymeleaf
		List<Docente> solicitudes = this.docenteService.findPendingRequests();
		List<String> headers = Arrays.asList("Nombre", "Email", "Aprobación");
		List<Map<String, Object>> rows = solicitudes.stream().map(docente -> {
			Map<String, Object> um = new HashMap<>();
			um.put(headers.get(0), docente.getNombre());
			um.put(headers.get(1), docente.getEmail());
			um.put(headers.get(2), docente.getId());
			return um;
		}).collect(Collectors.toList());

		model.addAttribute("headers", headers);
		model.addAttribute("rows", rows);
		return "solicitudes";
	}
	
	@GetMapping("/aprobar/{id}")
	public String aceptarSolicitud(@PathVariable Integer id, Model model){
		Docente porAprobar = this.docenteService.get(id);
		porAprobar.setAprobado(true);
//		Aparentemente este método también actualiza
		docenteService.save(porAprobar);
		model.addAttribute("msjAprobacion", "Solicitud aprobada con éxito");
		return "redirect:/admin/solicitudes/";
		
	}
	
	@GetMapping("/rechazar/{id}")
	public String rechazarSolicitud(@PathVariable Integer id, Model model){
		this.docenteService.delete(id);
		model.addAttribute("msjRechazo", "Solicitud aprobada con éxito");
		return "redirect:/admin/solicitudes/";
		
	}
}
