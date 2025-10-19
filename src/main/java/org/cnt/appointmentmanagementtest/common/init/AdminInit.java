package org.cnt.appointmentmanagementtest.common.init;

import org.cnt.appointmentmanagementtest.helper.model.db.entities.Helper;
import org.cnt.appointmentmanagementtest.helper.model.db.entities.Role;
import org.cnt.appointmentmanagementtest.helper.model.db.repositories.HelperRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminInit implements CommandLineRunner {
    private final BCryptPasswordEncoder passwordEncoder;
    private final HelperRepository helperRepository;

    public AdminInit(BCryptPasswordEncoder passwordEncoder, HelperRepository helperRepository) {
        this.passwordEncoder = passwordEncoder;
        this.helperRepository = helperRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (helperRepository.count() == 0) {
            Helper helper = new Helper();
            helper.setName("cnt-admin");
            helper.setPhone("+34600500178");
            helper.setEmail("nuevastecnologias@sevilla.cnt.es");
            helper.setPasswordHashed(passwordEncoder.encode("4p0y0mutu0!"));
            helper.setRoles(Set.of(Role.ADMIN));
            helper.setUsername("cnt-admin");

            helperRepository.save(helper);
        }
    }
}
