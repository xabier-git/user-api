package cl.sentra.service;

import cl.sentra.dto.UserRequest;
import cl.sentra.entity.Phone;
import cl.sentra.entity.User;
import cl.sentra.exception.InvalidEmailFormatException;
import cl.sentra.exception.InvalidPasswordFormatException;
import cl.sentra.repository.UserRepository;
import cl.sentra.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${password.regex}")
    private String passwordRegex;

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public User createUser(UserRequest request) {

              // Validar formato del correo
        if (!Pattern.matches(EMAIL_REGEX, request.getEmail())) {
            throw new InvalidEmailFormatException("El correo no tiene un formato válido");
        }

                // Validar formato de la contraseña
        if (!Pattern.matches(passwordRegex, request.getPassword())) {
            throw new InvalidPasswordFormatException("La contraseña no cumple con el formato requerido");
        }

        // Validar si el correo ya está registrado
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);

        // Generar y asignar el token JWT
        String token = jwtUtil.generateToken(user.getId());
        user.setToken(token);

        // Mapear los teléfonos
        List<Phone> phones = request.getPhones().stream()
                .map(req -> new Phone(req.getNumber(), req.getCitycode(), req.getContrycode()))       
                .collect(Collectors.toList());
        user.setPhones(phones);

        return userRepository.save(user);
    }

    public User modifyUser(UserRequest request) {

        // Validar formato del correo
        if (!Pattern.matches(EMAIL_REGEX, request.getEmail())) {
            throw new InvalidEmailFormatException("El correo no tiene un formato válido");
        }
    
        // Validar formato de la contraseña
        if (!Pattern.matches(passwordRegex, request.getPassword())) {
            throw new InvalidPasswordFormatException("La contraseña no cumple con el formato requerido");
        }
    
        // Buscar el usuario por email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("El usuario con el correo proporcionado no existe"));
    
        // Modificar los campos permitidos
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setModified(LocalDateTime.now());
    
        // Actualizar la colección de teléfonos
        user.getPhones().clear(); // Limpia la lista existente
        List<Phone> phones = request.getPhones().stream()
                .map(req -> new Phone( req.getNumber(), req.getCitycode(), req.getContrycode())) // Asocia el usuario a cada teléfono nuevo
                .collect(Collectors.toList());
        user.getPhones().addAll(phones);
    
        // Guardar y retornar el usuario modificado
        return userRepository.save(user);
    }
}