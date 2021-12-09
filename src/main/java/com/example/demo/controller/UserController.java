package com.example.demo.controller;

import com.example.demo.controller.repository.PhotoRespository;
import com.example.demo.controller.repository.UserRepository;
import com.example.demo.model.Photo;
import com.example.demo.model.User;
import com.example.demo.storage.Disco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Disco disco;
    @Autowired
    PhotoRespository photoRespository;

    @GetMapping
    public List<User> list(){
        return userRepository.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId){
        return ResponseEntity.ok(userRepository.getById(userId));
    }

    @PostMapping
    public ResponseEntity<?> insert(@Validated @Valid @RequestBody User user){
        if (!userRepository.findByEmail(user.getEmail()).isEmpty())
            return ResponseEntity.badRequest().body("Email já cadastrado");
        return ResponseEntity.ok(userRepository.save(user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> update(@PathVariable Long userId, @RequestBody User user){
        user.setId(userId);
        return ResponseEntity.ok(userRepository.save(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> delete(@PathVariable Long userId){
        if(!userRepository.existsById(userId))
            return ResponseEntity.notFound().build();
        userRepository.deleteById(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/photos")
    public ResponseEntity<?> userAddPhoto(@PathVariable Long userId, @RequestParam MultipartFile filePhoto, @RequestParam String description){
        User user = new User();
        user.setId(userId);
        Photo photo = new Photo();
        photo.setUser(user);
        photo.setDateUpload(LocalDateTime.now());
        photo.setDescription(description);

        String photoUrl = disco.salvarFoto(filePhoto);
        photo.setPhotoUrl(photoUrl);

        return ResponseEntity.ok(photoRespository.save(photo));
    }

    @DeleteMapping("/{userId}/photos")
    public ResponseEntity<?> userRemovePhoto(@PathVariable Long userId, @RequestBody Photo photo){
        photoRespository.delete(photo);
        return ResponseEntity.ok(true);
    }

}
