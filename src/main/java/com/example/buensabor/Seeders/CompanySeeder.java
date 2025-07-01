// package com.example.buensabor.Seeders;import org.springframework.boot.CommandLineRunner;
// import org.springframework.core.annotation.Order;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// import com.example.buensabor.Auth.Roles.Roles;
// import com.example.buensabor.entity.Address;
// import com.example.buensabor.entity.City;
// import com.example.buensabor.entity.Company;
// import com.example.buensabor.repository.AddressRepository;
// import com.example.buensabor.repository.CityRepository;
// import com.example.buensabor.repository.CompanyRepository;

// import java.math.BigInteger;
// import java.util.Optional;

// @Component
// @Order(2)
// public class CompanySeeder implements CommandLineRunner {

//     private final CompanyRepository companyRepository;
//     private final CityRepository cityRepository;
//     private final AddressRepository addressRepository;
//     private final PasswordEncoder passwordEncoder;

//     public CompanySeeder(CompanyRepository companyRepository,
//                          CityRepository cityRepository,
//                          AddressRepository addressRepository,
//                          PasswordEncoder passwordEncoder) {
//         this.companyRepository = companyRepository;
//         this.cityRepository = cityRepository;
//         this.addressRepository = addressRepository;
//         this.passwordEncoder = passwordEncoder;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         Optional<Company> existing = companyRepository.findByEmail("contacto@mostaza.com.ar");
//         if (existing.isPresent()) {
//             System.out.println("La empresa Mostaza ya existe.");
//             return;
//         }

//         // Buscamos la ciudad por id = 5
//         Optional<City> cityOpt = cityRepository.findById(5L);
//         if (cityOpt.isEmpty()) {
//             System.err.println("No se encontró la ciudad con id=5. Crea la ciudad primero.");
//             return;
//         }

//         City city = cityOpt.get();

//         // Creamos dirección asociada a la ciudad
//         Address address = new Address();
//         address.setStreet("Av. Siempre Viva");
//         address.setNumber(742);
//         address.setPostalCode(1414);
//         address.setCity(city);

//         addressRepository.save(address);

//         // Creamos la empresa
//         Company mostaza = new Company();
//         mostaza.setName("Mostaza");
//         mostaza.setEmail("contacto@mostaza.com.ar");
//         mostaza.setPhone(new BigInteger("541140000000"));
//         mostaza.setRole(Roles.COMPANY);
//         mostaza.setPassword(passwordEncoder.encode("password"));
//         mostaza.setCuit("30-12345678-9");
//         mostaza.setAddress(address);

//         companyRepository.save(mostaza);

//         System.out.println("Seeder: Empresa Mostaza creada con dirección y ciudad.");
//     }
// }
