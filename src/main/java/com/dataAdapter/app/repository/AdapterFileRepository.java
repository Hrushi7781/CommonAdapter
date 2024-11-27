package com.dataAdapter.app.repository;


import com.dataAdapter.app.model.FileModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdapterFileRepository extends MongoRepository<FileModel,String> {

    FileModel findByFileName(String fileName);
}
