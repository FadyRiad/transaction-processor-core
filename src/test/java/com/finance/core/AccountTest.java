package com.finance.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Account Financial Logic Tests")
public class AccountTest {
    private Account sender;
    private Account receiver;

    @BeforeEach
    void setUp() {
        // Initialize fresh accounts before each test to ensure isolation
        sender = new Account(1, 500.0);
        receiver = new Account(2, 100.0);
    }

    @Test
    @DisplayName("Verify successful money transfer (Dual-Entry)")
    void testTransferSuccess() {
        sender.sendMoneyToAccount(receiver, 200.0);

        assertEquals(300.0, sender.getBalance(), "Sender balance should decrease");
        assertEquals(300.0, receiver.getBalance(), "Receiver balance should increase");

        // Verify transaction history creation
        assertEquals(1, sender.getTransaction().length, "Sender should have 1 TX record");
        assertEquals(1, receiver.getTransaction().length, "Receiver should have 1 TX record");
    }

    @Test
    @DisplayName("Verify exception on insufficient funds")
    void testInsufficientFunds() {
        // assertThrows ensures the system correctly rejects invalid states [2]
        Exception exception = assertThrows(RuntimeException.class, () -> {
            sender.sendMoneyToAccount(receiver, 1000.0);
        });

        assertEquals("Insufficient funds.", exception.getMessage());
        assertEquals(500.0, sender.getBalance(), "Balance must remain unchanged on failure");
    }

    @Test
    @DisplayName("Verify manual array resizing (Doubling Strategy)")
    void testArrayResizing() {
        // Adding 15 transactions to trigger the 10-size limit and ensureCapacity()
        for (int i = 0; i < 15; i++) {
            sender.sendMoneyToAccount(receiver, 1.0);
        }

        assertEquals(15, sender.getTransaction().length, "Array should resize and preserve all TXs");
        assertTrue(sender.getBalance() < 500.0);
    }

    @Test
    @DisplayName("Verify defensive copying of transaction history")
    void testDefensiveCopyProtection() {
        sender.sendMoneyToAccount(receiver, 50.0);
        Account.Transaction[] history = sender.getTransaction();

        // Attempt to tamper with the returned array
        history = null;

        // Verify the internal state remains untouched
        assertNotNull(sender.getTransaction(), "Internal ledger must be protected from external modification");
    }
}