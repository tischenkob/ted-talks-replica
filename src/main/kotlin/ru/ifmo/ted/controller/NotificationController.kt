package ru.ifmo.ted.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.ifmo.ted.model.Notification
import ru.ifmo.ted.service.NotificationService
import java.security.Principal

@RestController
@RequestMapping("/api/notifications")
class NotificationController(val notificationService: NotificationService) {

    @GetMapping
    fun getNotifications(principal: Principal): Set<Notification> {
        return notificationService.getNotifications(principal)
    }
}
