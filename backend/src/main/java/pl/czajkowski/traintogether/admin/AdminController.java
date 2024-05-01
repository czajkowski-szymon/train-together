package pl.czajkowski.traintogether.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @GetMapping
    public String get() {
        return "GET:: admin controller";
    }

    @PatchMapping()
    public ResponseEntity<?> blockUser() {
        return ResponseEntity.ok("ADMIN::UPDATE");
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser() {
        return ResponseEntity.ok("ADMIN::DELETE");
    }
}
