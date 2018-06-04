package com.maga.config.service;

import com.maga.config.entity.Server;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Page;

public interface ServerService {

    Server save(Server server);

    void delete(Long id);

    Server findByApi(String api);

    Server findById(Long id);

    Page<Server> findByApiLike(String api, int page, int size);

    JSONObject toJSONObject(Server server);

    JSONObject toJSONPage(Page<Server> servers);
}
