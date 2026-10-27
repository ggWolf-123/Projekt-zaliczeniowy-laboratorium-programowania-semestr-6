package com.si;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gemini/")
public class GeminiController {

    private final GeminiService service;

    public GeminiController(GeminiService service) {
        this.service = service;
    }

    @GetMapping("/{language}/{sentence}")
    public GeminiResult  getMessage(@PathVariable("language") String language,
                                    @PathVariable("sentence") String sentence) {

        return service.getMessage(language,sentence);
    }

    @PostMapping("/plate")
    public ImageResponse isValid(@RequestBody ImageRequest request) {

        System.out.println("Plate endpoint called");
        return service.isCorrect(request.getImage(), request.getNumber());
    }
    @PostMapping("/math")
    public MathResponse isValid(@RequestBody MathRequest request) {

        System.out.println("Math endpoint called");
        return service.isCorrectMath(request.getImageMath(), request.getSenteceToCheckMath());
    }

}
