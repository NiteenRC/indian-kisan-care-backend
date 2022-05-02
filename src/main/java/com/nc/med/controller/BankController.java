package com.nc.med.controller;

import com.nc.med.auth.payload.MessageResponse;
import com.nc.med.model.BankAccount;
import com.nc.med.model.Image;
import com.nc.med.repo.BankAccountRepo;
import com.nc.med.repo.ImageRepository;
import com.nc.med.util.ImageUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/bank")
@AllArgsConstructor
@CrossOrigin("*")
public class BankController {
    private final BankAccountRepo bankAccountRepo;
    private final ImageRepository imageRepository;

    @PostMapping
    public ResponseEntity<?> registerBank(@RequestBody BankAccount bankAccount) {
        try {
            bankAccount.setId(1L);
            bankAccountRepo.save(bankAccount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(new MessageResponse("Bank details added successfully!"));
    }

    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestBody MultipartFile image) throws IOException {
        imageRepository.save(Image.builder()
                .id(1L)
                .name(image.getOriginalFilename())
                .type(image.getContentType())
                .image(ImageUtility.compressImage(image.getBytes())).build());
        return ResponseEntity.status(HttpStatus.OK)
                .body("asd");
    }

    @GetMapping(path = "/image")
    public Image getImages() {
        final Optional<Image> dbImage = imageRepository.findById(1L);
        return Image.builder()
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .image(ImageUtility.decompressImage(dbImage.get().getImage())).build();
    }

    @GetMapping(path = {"/image/info/{name}"})
    public Image getImageDetails(@PathVariable("name") String name) throws IOException {
        final Optional<Image> dbImage = imageRepository.findByName(name);
        return Image.builder()
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .image(ImageUtility.decompressImage(dbImage.get().getImage())).build();
    }

    @GetMapping(path = {"/image/{name}"})
    public ResponseEntity<byte[]> getImage(@PathVariable String name) throws IOException {
        final Optional<Image> dbImage = imageRepository.findByName(name);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(dbImage.get().getType()))
                .body(ImageUtility.decompressImage(dbImage.get().getImage()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBankDetails(@PathVariable Long id) {
        final Optional<Image> dbImage = imageRepository.findById(1L);
        Image image = Image.builder()
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .image(ImageUtility.decompressImage(dbImage.get().getImage())).build();

        final Optional<BankAccount> bankAccount = bankAccountRepo.findById(id);
        if (bankAccount.isPresent()) {
            BankAccount bankAccount1 = bankAccount.get();
            bankAccount1.setImage(image);
            return new ResponseEntity<>(bankAccount1, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
