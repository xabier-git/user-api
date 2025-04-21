package cl.sentra.service;

import cl.sentra.dto.UserRequest;
import cl.sentra.dto.PhoneRequest;
import cl.sentra.entity.User;
import cl.sentra.exception.InvalidEmailFormatException;
import cl.sentra.exception.InvalidPasswordFormatException;
import cl.sentra.repository.UserRepository;
import cl.sentra.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private UserRequest validRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validRequest = UserRequest.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.org")
                .password("Hunter2@")
                .phones(List.of(
                    new PhoneRequest("1234567", "1", "57"),
                    new PhoneRequest("7654321", "2", "58")
                ))
                .build();

        // Simular las propiedades configurables
        ReflectionTestUtils.setField(userService, "passwordRegex", "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
    
    @Test
    void createUser_ShouldThrowInvalidEmailFormatException_WhenEmailIsInvalid() {
        validRequest.setEmail("invalid-email");

        InvalidEmailFormatException exception = assertThrows(
                InvalidEmailFormatException.class,
                () -> userService.createUser(validRequest)
        );

        assertEquals("El correo no tiene un formato válido", exception.getMessage());
    }

    @Test
    void createUser_ShouldThrowInvalidPasswordFormatException_WhenPasswordIsInvalid() {
        validRequest.setPassword("weakpassword");

        InvalidPasswordFormatException exception = assertThrows(
                InvalidPasswordFormatException.class,
                () -> userService.createUser(validRequest)
        );

        assertEquals("La contraseña no cumple con el formato requerido", exception.getMessage());
    }

    @Test
    void createUser_ShouldThrowIllegalArgumentException_WhenEmailAlreadyExists() {
        when(userRepository.findByEmail(validRequest.getEmail())).thenReturn(Optional.of(new User()));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(validRequest)
        );

        assertEquals("El correo ya está registrado", exception.getMessage());
    }

    @Test
    void createUser_ShouldCreateUserSuccessfully_WhenRequestIsValid() {
        // Simular que el correo no existe en la base de datos
        when(userRepository.findByEmail(validRequest.getEmail())).thenReturn(Optional.empty());

        // Simular la generación del token JWT
        when(jwtUtil.generateToken(any(UUID.class))).thenReturn("mocked-jwt-token");

        // Simular el guardado del usuario en el repositorio
        User mockUser = User.builder()
                .id(UUID.randomUUID())
                .name(validRequest.getName())
                .email(validRequest.getEmail())
                .password(validRequest.getPassword())
                .token("mocked-jwt-token")
                .build();
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Llamar al método a probar
        User createdUser = userService.createUser(validRequest);

        // Verificar los resultados
        assertNotNull(createdUser);
        assertEquals(validRequest.getName(), createdUser.getName());
        assertEquals(validRequest.getEmail(), createdUser.getEmail());
        assertEquals(validRequest.getPassword(), createdUser.getPassword());
        assertEquals("mocked-jwt-token", createdUser.getToken());

        // Verificar que el método save fue llamado una vez
        verify(userRepository, times(1)).save(any(User.class));
    }
}