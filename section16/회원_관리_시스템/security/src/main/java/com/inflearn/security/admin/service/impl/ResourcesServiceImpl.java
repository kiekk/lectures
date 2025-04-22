package com.inflearn.security.admin.service.impl;

import com.inflearn.security.admin.repository.ResourcesRepository;
import com.inflearn.security.admin.service.ResourcesService;
import com.inflearn.security.domain.entity.Resources;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourcesServiceImpl implements ResourcesService {

    private final ResourcesRepository resourcesRepository;

    @Transactional(readOnly = true)
    @Override
    public Resources getResources(long id) {
        return resourcesRepository.findById(id).orElse(new Resources());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Resources> getResources() {
        return resourcesRepository.findAll(Sort.by(Sort.Order.asc("orderNum")));
    }

    @Transactional
    @Override
    public void createResources(Resources resources) {
        resourcesRepository.save(resources);
    }

    @Transactional
    @Override
    public void deleteResources(long id) {
        resourcesRepository.deleteById(id);
    }
}
