package mrp.backend.services;

import mrp.backend.entities.Payment;
import mrp.backend.entities.Student;
import mrp.backend.enums.PaymentStatus;
import mrp.backend.enums.PaymentType;
import mrp.backend.repositories.PaymentRepository;
import mrp.backend.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StudentRepository studentRepository;

    public PaymentService(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    public Payment savePayment(
            MultipartFile file,
            LocalDate date,
            double amount,
            PaymentType type,
            String studentCode
    ) throws IOException {
        Path path = Paths.get(System.getProperty("user.home"), "students-app-files", "payments");
        if (!Files.exists(path)) Files.createDirectories(path);
        String paymentId = UUID.randomUUID().toString();
        Path filePath =  Paths.get(System.getProperty("user.home"), "students-app-files", "payments", paymentId+".pdf");
        Files.copy(file.getInputStream(),filePath);
        Student student = studentRepository.findByCode(studentCode);
        Payment payment =  new Payment();
        payment.setType(type);
        payment.setDate(date);
        payment.setAmount(amount);
        payment.setStudent(student);
        payment.setStatus(PaymentStatus.CREATED);
        payment.setFile(filePath.toUri().toString());
        Payment savedPayments = paymentRepository.save(payment);
        return savedPayments;
    }

    public Payment updatePaymentStatus( PaymentStatus status, Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }
}
