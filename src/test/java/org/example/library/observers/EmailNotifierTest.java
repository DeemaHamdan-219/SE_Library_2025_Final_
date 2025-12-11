package org.example.library.observers;

import org.example.library.models.User;
import org.example.library.services.EmailService1;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for {@link EmailNotifier}.
 *
 * <p>This test verifies that when {@code notify()} is called,
 * the EmailNotifier uses EmailService1 correctly by sending an email
 * to the user's email address with the correct subject and message.</p>
 *
 * <p>The test uses Mockito to:</p>
 * <ul>
 *     <li>Mock User object</li>
 *     <li>Mock EmailService1</li>
 *     <li>Inject the mocked service using reflection</li>
 *     <li>Verify sendEmail() was called exactly once</li>
 * </ul>
 */
public class EmailNotifierTest {

    @Test
    void testNotifySendsEmailSuccessfully() {

        // Mock EmailService1
        EmailService1 mockService = mock(EmailService1.class);

        // Mock User
        User mockUser = mock(User.class);
        when(mockUser.getEmail()).thenReturn("test@example.com");

        // Create notifier instance
        EmailNotifier notifier = new EmailNotifier();

        // Inject mock service using reflection
        try {
            var field = EmailNotifier.class.getDeclaredField("emailService");
            field.setAccessible(true);
            field.set(notifier, mockService);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Reflection injection failed");
        }

        // Act
        notifier.notify(mockUser, "Hello!");

        // Assert — verify sendEmail was called with correct values
        verify(mockService, times(1))
                .sendEmail("test@example.com", "Overdue Reminder", "Hello!");
    }
}
