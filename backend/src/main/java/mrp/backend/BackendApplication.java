package mrp.backend;

import mrp.backend.entities.Payment;
import mrp.backend.entities.Student;
import mrp.backend.enums.PaymentStatus;
import mrp.backend.enums.PaymentType;
import mrp.backend.repositories.StudentRepository;
import mrp.backend.repositories.PaymentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			StudentRepository studentRepository,
			PaymentRepository paymentRepository) {
		return args -> {
			Student student = new Student(
					UUID.randomUUID().toString(),
					"112233",
					"Test01",
					"Doe",
					"test01@example.com",
					"photo.jpg");
			studentRepository.save(student);

			Random random = new Random();
			for (int i = 0; i < 10; i++) {
				Payment payment = new Payment();
				payment.setDate(LocalDate.now());
				payment.setAmount(1000 + random.nextInt(9001));
				payment.setType(PaymentType.values()[random.nextInt(PaymentType.values().length)]);
				payment.setStatus(PaymentStatus.CREATED);
				payment.setFile("payment_" + UUID.randomUUID() + ".pdf");
				payment.setStudent(student);
				paymentRepository.save(payment);

			}
		};
	}


}
