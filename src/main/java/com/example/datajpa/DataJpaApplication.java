package com.example.datajpa;

import com.example.datajpa.entity.*;
import com.example.datajpa.repository.BookRepository;
import com.example.datajpa.repository.StudentIdCardRepository;
import com.example.datajpa.repository.StudentRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.FunnyName;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class DataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataJpaApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, BookRepository bookRepository) {
        return args -> {

			initDB(studentRepository);


//			getBooksLazily(studentRepository, bookRepository);
		};
    }

	private void getBooksLazily(StudentRepository studentRepository, BookRepository bookRepository) {
		studentRepository.findById(1L)
				.ifPresent(
						s -> {
							System.out.println("fetch book lazy...");
							List<Book> books = bookRepository.findBooksByStudentId(s.getId());
							books.forEach( book -> {
								System.out.println(book.getBookName());
							});
						}
				);
	}

	private void sorting(StudentRepository repository) {
		repository
				.findAll(Sort.by("firstName").descending())
				.forEach(student -> System.out.println(student.getFirstName()));
	}

	private void pagination(StudentRepository repository) {
		PageRequest request = PageRequest.of(0, 5, Sort.by("firstName").ascending());
		Page<Student> studentPage = repository.findAll(request);
		System.out.println(studentPage);
	}

	private void initDB(StudentRepository studentRepository) {
		Faker faker = new Faker();
		for (int i = 0; i < 2; i++) {
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = String.format("%s.%s@mail.com", firstName, lastName);
			Integer age = faker.number().numberBetween(16, 25);
			Student student = new Student(firstName, lastName, email, age);

			student.addBook(new Book("Clean Code", LocalDateTime.now().minusDays(4)));
			student.addBook(new Book("Band of four", LocalDateTime.now().minusDays(5)));
			student.addBook(new Book("The Great Gatsby", LocalDateTime.now().minusDays(7)));

			String cardNumber = Long.toString(faker.number().numberBetween(1000000000L, 10000000000L));

			StudentIdCard studentIdCard = new StudentIdCard(cardNumber, student);

			student.setStudentIdCard(studentIdCard);


			student.addEnrolment(new Enrolment(student, new Course("Computer Sciense", "IT")));
			student.addEnrolment(new Enrolment(student, new Course("Amigoscode Spring Data JPA", "IT")));

			studentRepository.save(student);
		}



	}

}
