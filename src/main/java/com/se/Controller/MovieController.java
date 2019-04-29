package com.se.Controller;

import com.se.service.MovieService;
import com.se.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Mark Angrish
 * @author Michael J. Simons
 */
@RestController
@RequestMapping("/movie")
public class MovieController {

	private final MovieService movieService;

	@Autowired
	private UserService userService;
	
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}



    @GetMapping("/graph")
	public Map<String, Object> graph(@RequestParam(value = "limit",required = false) Integer limit) {
		System.out.println(userService.getFriends(1000000).size());
		return movieService.graph(limit == null ? 100 : limit);
	}
}
