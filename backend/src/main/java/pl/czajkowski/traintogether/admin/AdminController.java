package pl.czajkowski.traintogether.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @PatchMapping("/users/block/{userId}")
    public ResponseEntity<?> blockUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/users/{usersId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer usersId) {
        return ResponseEntity.noContent().build();
    }
}
