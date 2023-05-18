package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.Entity.Assignment;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Repository.AssignmentRepository;
import com.phanlop.khoahoc.Service.AssignmentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentServicesImpl implements AssignmentServices {
    @Autowired
    private AssignmentRepository assignmentRepository;

    @Override
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @Override
    public Assignment getAssignmentById(int assignId) {
        return assignmentRepository.findById(assignId).orElse(null);
    }

    @Override
    public Assignment save(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    @Override
    public void delete(int assignmentId) {
        assignmentRepository.findById(assignmentId).ifPresent(assignment -> assignmentRepository.delete(assignment));
    }

    @Override
    public List<Assignment> getAllAssignmentsByCourse(Course course) {
        return assignmentRepository.findAllAssignmentsByCourse(course);
    }
}
