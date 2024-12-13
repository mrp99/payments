package mrp.backend.controllers;

import mrp.backend.entities.Payment;
import mrp.backend.entities.Student;
import mrp.backend.enums.PaymentStatus;
import mrp.backend.enums.PaymentType;
import mrp.backend.repositories.PaymentRepository;
import mrp.backend.repositories.StudentRepository;
import mrp.backend.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PaymentService paymentService;

    public PaymentController() {}

    public PaymentController(
            PaymentRepository paymentRepository,
            StudentRepository studentRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/payments")
    public List<Payment> allPayments() {
        return paymentRepository.findAll();
    }

    @GetMapping("/payments/{id}")
    private Payment findByid(@PathVariable Long id) {
        return paymentRepository.findById(id).get();
    }

    @GetMapping("/students")
    private List<Student> allStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/students/{id}")
    private Student findById(@PathVariable String id) {
        return studentRepository.findById(id).get();
    }

    @GetMapping("/students/{code}/payments")
    private List<Payment> findByCode(@PathVariable String code) {
        return paymentRepository.findByStudentCode(code);
    }

    @PostMapping(value = "/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(
            @RequestParam MultipartFile file,
            @RequestParam LocalDate date,
            @RequestParam double amount,
            @RequestParam PaymentType type,
            @RequestParam String studentCode
    ) throws IOException {
    return paymentService.savePayment(file, date, amount, type, studentCode);
    }

    @GetMapping(value = "/paymentFile/{paymentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).get();
        String filePath = payment.getFile();
        return Files.readAllBytes(Path.of(URI.create(filePath)));
    }

    @PutMapping("/payments/updateStatus/{paymentId}")
    public Payment updatePaymentStatus(@RequestParam PaymentStatus status, @PathVariable Long paymentId) {
        return paymentService.updatePaymentStatus(status, paymentId);
    }
}
