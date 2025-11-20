package tobyspring.splearn.adapter.integration;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;
import tobyspring.splearn.domain.Email;

import static org.assertj.core.api.Assertions.assertThat;

class DummyEmailSenderTest {

    @Test
    @StdIo
    void dummyEmailSender(StdOut out) {
        DummyEmailSender emailSender = new DummyEmailSender();
        emailSender.send(new Email("soono@splearn.app"), "Subject", "Body");
        assertThat(out.capturedLines()[0]).isEqualTo("DummyEmailSender email to: Email[value=soono@splearn.app]");
    }

}