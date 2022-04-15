package com.example.proyecto_dws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    GradeRepository gradeRepository;
}
