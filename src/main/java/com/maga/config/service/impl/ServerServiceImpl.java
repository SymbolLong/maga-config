package com.maga.config.service.impl;

import com.maga.config.entity.Server;
import com.maga.config.repository.ServerRepository;
import com.maga.config.service.ServerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServerServiceImpl implements ServerService {

    @Autowired
    private ServerRepository serverRepository;

    @Override
    public Server save(Server server) {
        return serverRepository.saveAndFlush(server);
    }

    @Override
    public void delete(Long id) {
        serverRepository.deleteById(id);
    }

    @Override
    public Server findByApi(String api) {
        return serverRepository.findByApi(api);
    }

    @Override
    public Server findById(Long id) {
        Optional<Server> server = serverRepository.findById(id);
        return server.isPresent() ? server.get() : null;
    }

    @Override
    public Page<Server> findByApiLike(String api, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        if (StringUtils.isEmpty(api)) {
            api = "%%";
        } else {
            api = "%" + api + "%";
        }
        return serverRepository.findByApiLike(api, pageRequest);
    }

    @Override
    public JSONObject toJSONObject(Server server) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", server.getId());
        jsonObject.put("api", server.getApi());
        jsonObject.put("name", server.getName());
        jsonObject.put("path", server.getPath());
        jsonObject.put("remark", server.getRemark());
        return jsonObject;
    }

    @Override
    public JSONObject toJSONPage(Page<Server> servers) {
        JSONArray array = new JSONArray();
        for (Server server : servers.getContent()) {
            array.add(toJSONObject(server));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page", servers.getNumber() + 1);
        jsonObject.put("size", servers.getSize());
        jsonObject.put("total", servers.getTotalElements());
        jsonObject.put("servers", array);
        return jsonObject;
    }
}
