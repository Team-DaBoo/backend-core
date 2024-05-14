package b172.challenging.common.controller;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class CustomErrorController implements ErrorController {

	private final ErrorAttributes errorAttributes;

	public CustomErrorController(ErrorAttributes errorAttributes) {
		this.errorAttributes = errorAttributes;
	}

	@GetMapping("/error")
	public String handleError(WebRequest webRequest, Model model) {
		// 오류 속성 가져오기
		Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest,
			ErrorAttributeOptions.defaults());

		// 모델에 오류 속성 추가
		System.out.println(errorAttributes.entrySet());
		model.addAttribute("timestamp", errorAttributes.get("timestamp"));
		model.addAttribute("status", errorAttributes.get("status"));
		model.addAttribute("error", errorAttributes.get("error"));
		model.addAttribute("message", errorAttributes.get("message"));
		model.addAttribute("path", errorAttributes.get("path"));
		model.addAttribute("exception", errorAttributes.get("exception"));

		// 에러 페이지로 이동
		return "error";
	}
}
