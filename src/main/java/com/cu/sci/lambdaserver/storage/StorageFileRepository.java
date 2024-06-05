package com.cu.sci.lambdaserver.storage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageFileRepository extends JpaRepository<StorageFile, Long> {
}