package net.jlxxw.link.controller;

import net.jlxxw.link.component.IdCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chunyang.leng
 * @date 2022-09-05 3:00 PM
 */
@RestController
public class IdController {
    @Autowired
    private IdCenter idCenter;

    @GetMapping("getId")
    public Long getId() {
        return idCenter.getId();
    }
}
