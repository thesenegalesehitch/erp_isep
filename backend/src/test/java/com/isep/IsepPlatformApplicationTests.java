package com.isep;

import com.isep.model.User;
import com.isep.model.Service;
import com.isep.model.Message;
import com.isep.model.Conversation;
import com.isep.model.Bus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IsepPlatformApplicationTests {

    @Test
    void contextLoads() {
        // Test that Spring context loads successfully
    }

    @Test
    void testUserCreation() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password123");
        user.setRole(User.Role.STUDENT);
        user.setIsActive(true);

        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals(User.Role.STUDENT, user.getRole());
        assertEquals("John Doe", user.getFullName());
    }

    @Test
    void testServiceCreation() {
        Service service = new Service();
        service.setTitle("Cours de Mathématiques");
        service.setDescription("Cours particuliers de mathématiques pour tous niveaux");
        service.setCategory(Service.ServiceCategory.TUTORING);
        service.setPrice(new BigDecimal("5000"));
        service.setLocation("Dakar");
        service.setIsAvailable(true);
        service.setAverageRating(4.5);
        service.setTotalRatings(10);

        assertNotNull(service);
        assertEquals("Cours de Mathématiques", service.getTitle());
        assertEquals(Service.ServiceCategory.TUTORING, service.getCategory());
        assertEquals(new BigDecimal("5000"), service.getPrice());
        assertTrue(service.getIsAvailable());
    }

    @Test
    void testMessageCreation() {
        Message message = new Message();
        message.setContent("Bonjour, comment allez-vous?");
        message.setType(Message.MessageType.TEXT);
        message.setIsRead(false);

        assertNotNull(message);
        assertEquals("Bonjour, comment allez-vous?", message.getContent());
        assertEquals(Message.MessageType.TEXT, message.getType());
        assertFalse(message.getIsRead());
    }

    @Test
    void testConversationCreation() {
        Conversation conversation = new Conversation();
        conversation.setName("Discussion entre John et Jane");
        conversation.setType(Conversation.ConversationType.PRIVATE);

        assertNotNull(conversation);
        assertEquals("Discussion entre John et Jane", conversation.getName());
        assertEquals(Conversation.ConversationType.PRIVATE, conversation.getType());
    }

    @Test
    void testBusCreation() {
        Bus bus = new Bus();
        bus.setLineNumber("Ligne 1");
        bus.setDriverName("Mamadou Diop");
        bus.setDriverPhone("+221 77 123 45 67");
        bus.setTotalSeats(50);
        bus.setStatus(Bus.BusStatus.IN_TRANSIT);
        bus.setCurrentLocationLat(14.7167);
        bus.setCurrentLocationLng(-17.4677);

        assertNotNull(bus);
        assertEquals("Ligne 1", bus.getLineNumber());
        assertEquals(50, bus.getTotalSeats());
        assertEquals(Bus.BusStatus.IN_TRANSIT, bus.getStatus());
        assertEquals(50, bus.getAvailableSeats()); // No reservations yet
    }

    @Test
    void testUserRoleEnum() {
        assertEquals(3, User.Role.values().length);
        assertEquals(User.Role.STUDENT, User.Role.valueOf("STUDENT"));
        assertEquals(User.Role.TEACHER, User.Role.valueOf("TEACHER"));
        assertEquals(User.Role.ADMIN, User.Role.valueOf("ADMIN"));
    }

    @Test
    void testServiceCategoryEnum() {
        assertEquals(7, Service.ServiceCategory.values().length);
        assertEquals(Service.ServiceCategory.MECHANICS, Service.ServiceCategory.valueOf("MECHANICS"));
        assertEquals(Service.ServiceCategory.IT, Service.ServiceCategory.valueOf("IT"));
        assertEquals(Service.ServiceCategory.TUTORING, Service.ServiceCategory.valueOf("TUTORING"));
    }

    @Test
    void testBusStatusEnum() {
        assertEquals(5, Bus.BusStatus.values().length);
        assertEquals(Bus.BusStatus.STATIONARY, Bus.BusStatus.valueOf("STATIONARY"));
        assertEquals(Bus.BusStatus.IN_TRANSIT, Bus.BusStatus.valueOf("IN_TRANSIT"));
        assertEquals(Bus.BusStatus.DELAYED, Bus.BusStatus.valueOf("DELAYED"));
    }

    @Test
    void testMessageTypeEnum() {
        assertEquals(4, Message.MessageType.values().length);
        assertEquals(Message.MessageType.TEXT, Message.MessageType.valueOf("TEXT"));
        assertEquals(Message.MessageType.IMAGE, Message.MessageType.valueOf("IMAGE"));
        assertEquals(Message.MessageType.FILE, Message.MessageType.valueOf("FILE"));
        assertEquals(Message.MessageType.SYSTEM, Message.MessageType.valueOf("SYSTEM"));
    }
}
