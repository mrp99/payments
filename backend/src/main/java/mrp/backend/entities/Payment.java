package mrp.backend.entities;

import jakarta.persistence.*;
import lombok.ToString;
import mrp.backend.enums.PaymentStatus;
import mrp.backend.enums.PaymentType;

import java.time.LocalDate;

@Entity
@ToString
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private double amount;
    private PaymentType type;
    private PaymentStatus status = PaymentStatus.CREATED;
    private String file;

    @ManyToOne
    private Student student;

    public Payment() {}

    public Payment(Long id, LocalDate date, double amount, PaymentType type, PaymentStatus status, String file, Student student) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.file = file;
        this.student = student;
    }

    public Payment(PaymentType type, LocalDate date, double amount, Student student, PaymentStatus paymentStatus, String string) {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
