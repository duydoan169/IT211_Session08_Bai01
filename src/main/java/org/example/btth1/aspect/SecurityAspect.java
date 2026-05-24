package org.example.btth1.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

    @Before("execution(* org.example.btth1.service.InventoryService.deleteProduct(..)) && args(id, username, role)")
    public void checkAdminRole(Long id, String username, String role) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            throw new SecurityException("Truy cập bị từ chối. Người dùng '" + username + "' với quyền '" + role + "' không được phép xóa sản phẩm.");
        }
    }
}