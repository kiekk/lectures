package io.security.corespringsecurity.controller.admin;

import io.security.corespringsecurity.domain.dto.ResourcesDto;
import io.security.corespringsecurity.domain.entity.Resources;
import io.security.corespringsecurity.domain.entity.Role;
import io.security.corespringsecurity.repository.RoleRepository;
import io.security.corespringsecurity.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import io.security.corespringsecurity.service.MethodSecurityService;
import io.security.corespringsecurity.service.ResourcesService;
import io.security.corespringsecurity.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/resources")
public class ResourcesController {

    private final ResourcesService resourcesService;

    private final RoleRepository roleRepository;

    private final RoleService roleService;

    private final UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;

    private final MethodSecurityService methodSecurityService;

    public ResourcesController(ResourcesService resourcesService,
                               RoleRepository roleRepository,
                               RoleService roleService,
                               UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource,
                               MethodSecurityService methodSecurityService) {
        this.resourcesService = resourcesService;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.urlFilterInvocationSecurityMetadataSource = urlFilterInvocationSecurityMetadataSource;
        this.methodSecurityService = methodSecurityService;
    }

    @GetMapping("")
    public String getResources(Model model) throws Exception {

        List<Resources> resources = resourcesService.getResources();
        model.addAttribute("resources", resources);

        return "admin/resource/list";
    }

    @PostMapping("")
    public String createResources(ResourcesDto resourcesDto) throws Exception {

        ModelMapper modelMapper = new ModelMapper();
        Role role = roleRepository.findByRoleName(resourcesDto.getRoleName());
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        Resources resources = modelMapper.map(resourcesDto, Resources.class);
        resources.setRoleSet(roles);

        resourcesService.createResources(resources);

        if ("url".equals(resourcesDto.getResourceType())) {
            urlFilterInvocationSecurityMetadataSource.reload();
        } else {
            methodSecurityService.addMethodSecured(resourcesDto.getResourceName(), resourcesDto.getRoleName());
        }

        return "redirect:/admin/resources";
    }

    @GetMapping("register")
    public String viewRoles(Model model) throws Exception {

        List<Role> roleList = roleService.getRoles();
        model.addAttribute("roleList", roleList);

        ResourcesDto resources = new ResourcesDto();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role());
        resources.setRoleSet(roleSet);
        model.addAttribute("resources", resources);

        return "admin/resource/detail";
    }

    @GetMapping("{id}")
    public String getResources(@PathVariable String id, Model model) throws Exception {

        List<Role> roleList = roleService.getRoles();
        model.addAttribute("roleList", roleList);
        Resources resources = resourcesService.getResources(Long.parseLong(id));

        ModelMapper modelMapper = new ModelMapper();
        ResourcesDto resourcesDto = modelMapper.map(resources, ResourcesDto.class);
        model.addAttribute("resources", resourcesDto);

        return "admin/resource/detail";
    }

    @GetMapping("delete/{id}")
    public String removeResources(@PathVariable String id, Model model) throws Exception {

        Resources resources = resourcesService.getResources(Long.parseLong(id));
        resourcesService.deleteResources(Long.parseLong(id));

        if ("url".equals(resources.getResourceType())) {
            urlFilterInvocationSecurityMetadataSource.reload();
        } else {
            methodSecurityService.removeMethodSecured(resources.getResourceName());
        }

        return "redirect:/admin/resources";
    }
}