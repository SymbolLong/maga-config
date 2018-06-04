package com.maga.config.controller;

import com.maga.config.entity.ApiResult;
import com.maga.config.entity.Server;
import com.maga.config.service.ServerService;
import com.maga.config.util.ApiResultBuilder;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server")
public class ServerController {

    @Autowired
    private ServerService serverService;

    private String secret = "zhangshenglong";

    @PostMapping
    public ApiResult create(@RequestHeader String token, @RequestBody JSONObject jsonObject) {
        if (!token.equals(secret)) {
            return ApiResultBuilder.failure("未授权操作");
        }
        String api = jsonObject.getString("api");
        String path = jsonObject.getString("path");
        String name = jsonObject.getString("name");
        String remark = jsonObject.getString("remark");
        Server server = serverService.findByApi(api);
        if (server != null) {
            return ApiResultBuilder.failure("server已存在");
        }
        server = new Server();
        server.setApi(api);
        server.setName(name);
        server.setPath(path);
        server.setRemark(remark);
        serverService.save(server);
        return ApiResultBuilder.success("创建server成功", serverService.toJSONObject(server));
    }

    @PutMapping("{id}")
    public ApiResult update(@RequestHeader String token, @PathVariable Long id, @RequestBody JSONObject jsonObject) {
        if (!token.equals(secret)) {
            return ApiResultBuilder.failure("未授权操作");
        }
        Server server = serverService.findById(id);
        if (server == null) {
            return ApiResultBuilder.failure("server不存在");
        }
        String api = jsonObject.getString("api");
        String path = jsonObject.getString("path");
        String name = jsonObject.getString("name");
        String remark = jsonObject.getString("remark");
        server.setApi(api);
        server.setName(name);
        server.setPath(path);
        server.setRemark(remark);
        serverService.save(server);
        return ApiResultBuilder.success("修改server配置成功", serverService.toJSONObject(server));
    }

    @DeleteMapping("{id}")
    public ApiResult delete(@RequestHeader String token, @PathVariable Long id) {
        if (!token.equals(secret)) {
            return ApiResultBuilder.failure("未授权操作");
        }
        Server server = serverService.findById(id);
        if (server == null) {
            return ApiResultBuilder.failure("server不存在");
        }
        serverService.delete(id);
        return ApiResultBuilder.success("删除server成功");
    }

    @GetMapping("/{id}")
    public ApiResult findById(@RequestHeader String token, @PathVariable Long id) {
        if (!token.equals(secret)) {
            return ApiResultBuilder.failure("未授权操作");
        }
        Server server = serverService.findById(id);
        if (server == null) {
            return ApiResultBuilder.failure("server不存在");
        }
        return ApiResultBuilder.success("查询server成功", serverService.toJSONObject(server));
    }

    @GetMapping("/list")
    public ApiResult list(@RequestHeader String token, @RequestParam(required = false) String api,
                          @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        if (!token.equals(secret)) {
            return ApiResultBuilder.failure("未授权操作");
        }
        Page<Server> servers = serverService.findByApiLike(api, page, size);
        return ApiResultBuilder.success("查询server成功", serverService.toJSONPage(servers));
    }

}
