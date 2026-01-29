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
            Helper admin = new Helper();
            admin.setName("cnt-admin");
            admin.setPhone("+34600500178");
            admin.setEmail("nuevastecnologias@sevilla.cnt.es");
            admin.setPasswordHashed(passwordEncoder.encode("4p0y0mutu0!"));
            admin.setRoles(Set.of(Role.ADMIN));
            admin.setUsername("cnt-admin");

            helperRepository.save(admin);

            Helper system = new Helper();
            system.setName("[SYSTEM]");
            system.setPhone("+34600500178");
            system.setEmail("system@inventado.tnc.se");
            system.setPasswordHashed(passwordEncoder.encode("ENESTACUENTAUNONOSEPUEDELOGEAR"));
            system.setRoles(Set.of(Role.SYSTEM));
            system.setUsername("[SYSTEM]");

            helperRepository.save(system);
        }
    }
}
