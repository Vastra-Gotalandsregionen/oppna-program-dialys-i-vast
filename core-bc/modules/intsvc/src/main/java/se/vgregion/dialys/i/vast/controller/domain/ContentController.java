package se.vgregion.dialys.i.vast.controller.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import se.vgregion.dialys.i.vast.jpa.Content;
import se.vgregion.dialys.i.vast.repository.ContentRepository;

@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentRepository contentRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Content getContent(@PathVariable("id") String id) {
        Content content = contentRepository.findOne(id);

        if (content == null) {
            content = new Content(id, "<p>Redigera innehåll</p>");
            contentRepository.save(content);
        }

        return content;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("@authService.hasRole(authentication, 'ADMIN')")
    public ResponseEntity<Content> saveContent(@RequestBody Content content) {
        return ResponseEntity.ok(contentRepository.save(content));
    }

}
