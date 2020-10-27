package com.scc.fegin;

import com.scc.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("user")
public interface UserFegin {

    @GetMapping("/user/login/{username}/{password}")
    public User login(@PathVariable("username") String username,@PathVariable("password") String password);

    @PostMapping("/user/register")
    public void register(@RequestBody User user);

    @PutMapping("/user/update")
    public void update(@RequestBody User user);

}
