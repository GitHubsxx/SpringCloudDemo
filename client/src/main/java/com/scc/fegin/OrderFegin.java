package com.scc.fegin;

import com.scc.domain.OrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("order")
public interface OrderFegin {

    @GetMapping("/order/save/{uid}/{vid}")
    public void save(@PathVariable("uid") Integer uid,@PathVariable("vid") Integer vid);

    @GetMapping("/order/findAll")
    @ResponseBody
    public OrderVo findAll(@RequestParam("page") int page, @RequestParam("limit") int limit);

    @GetMapping("/order/findByName")
    @ResponseBody
    public OrderVo findByName(@RequestParam("page") int page, @RequestParam("limit") int limit,@RequestParam("username") String username);

    @DeleteMapping("/order/delete/{id}")
    public void delete(@PathVariable("id") Integer id);
}
