package com.prc391.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.prc391.models.Activation;
import com.prc391.models.User;
import com.prc391.repositories.ActivationRepository;
import com.prc391.repositories.UserRepository;
import com.prc391.service.ActivationServiceImpl;
import com.prc391.utils.EncryptedPasswordUtils;
import com.prc391.utils.MailActivator;
import com.prc391.validator.UserValidator;

@RequestMapping(value = "/a")
@Controller
public class CommonController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private ActivationRepository activationRepo;

	@Autowired
	private ActivationServiceImpl activationService;
	
	@Value("${user.imagelink.default}")
	private String defaultImageLink;

	@GetMapping("/activation")
	public String activate(Model model, @RequestParam(required = true, name = "code") String code,
			@RequestParam(required = true, name = "email") String email) {
		try {
			Activation activationDTO = activationRepo.getOne(email);
			if (activationDTO != null) {
				if (activationDTO.getCode().equals(code)) {
					activationRepo.delete(activationDTO);
					userRepo.updateStatus(email, true);
					return "redirect:/login?message=" + "Your account '" + email + "' is activated!";
				}
			}
			return "redirect:/login?imessage=" + "Bad request!";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Your action is invalid!");
			return "error";
		}
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("userForm", new User());
		return "register";
	}
		
	@PostMapping("/register")
	public String registerNew(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {

		try {
			userValidator.validate(userForm, bindingResult);

			if (bindingResult.hasErrors()) {
				return "register";
			}

			userForm.setPassword(EncryptedPasswordUtils.encrytePassword(userForm.getPassword()));
			userForm.setRole("user");
			userForm.setStatus(false);
			userForm.setAvatarLink(defaultImageLink);
			userRepo.save(userForm);

			String activationCode = activationService.createActivationRecord(userForm.getUsername());
			MailActivator.sendEmail(userForm.getUsername(), activationCode);

			return "redirect:/login?message=" + "Please check email '" + userForm.getUsername()
					+ "' to activate account!";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Register Failed!");
			return "error";
		}
	}
}
