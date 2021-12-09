package com.example.demo.controller.repository;

import com.example.demo.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRespository extends JpaRepository<Photo, Long> {
}
