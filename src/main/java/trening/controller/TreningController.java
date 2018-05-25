package trening.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import trening.service.TreningService;

@RestController
@RequestMapping(value = "/api/trening")
public class TreningController {

	@Autowired
	private TreningService treningService;
}
