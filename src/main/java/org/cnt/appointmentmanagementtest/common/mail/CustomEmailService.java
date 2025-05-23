package org.cnt.appointmentmanagementtest.common.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CustomEmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public CustomEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendAppointmentEmail(String to, String personInNeedName, String helperName, ZonedDateTime datetime, String helperPhone) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy HH:mm:ss");

        // Format the ZonedDateTime
        String formattedDate = datetime.format(formatter);

        helper.setFrom("testcnt@steelcode.team");
        helper.setTo(to);
        helper.setSubject("[CNT Sevilla] Nueva cita agendada");
        helper.setText(buildAppointmentEmail(personInNeedName, helperName, formattedDate, helperPhone), true);

        mailSender.send(message);
    }

    private String buildAppointmentEmail(String personInNeedName, String helperName, String datetime, String helperPhone) {
        return """
            <!DOCTYPE html>
            <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Nueva Cita Agendada</title>
                </head>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 0;">
                    <table align="center" width="600"
                        style="background-color: #ffffff; border: 1px solid #ddd; border-radius: 8px; overflow: hidden; box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);">
                        <thead>
                            <tr style="background-color: #180000; color: #ffffff; text-align: center;">
                                <th colspan="2" style="padding: 20px; font-size: 24px; font-weight: bold; text-transform: uppercase;">
                                    Nueva Cita Agendada
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td colspan="2" style="padding: 20px; font-size: 16px; text-align: left; color: #333;">
                                    Hola <strong>%s</strong>,
                                    <br><br>
                                    Se ha agendado una nueva cita. A continuación, se presentan los detalles:
                                </td>
                            </tr>
                            <tr>
                                <td style="padding: 10px; font-weight: bold; border-bottom: 1px solid #ddd; text-align: right;"
                                    width="40%%">Te atenderá:</td>
                                <td style="padding: 10px; border-bottom: 1px solid #ddd; text-align: left;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding: 10px; font-weight: bold; border-bottom: 1px solid #ddd; text-align: right;">Fecha y
                                    Hora:</td>
                                <td style="padding: 10px; border-bottom: 1px solid #ddd; text-align: left;">%s</td>
                            </tr>
                            <tr>
                                <td colspan="2" style="padding: 20px; font-size: 16px; text-align: left; color: #333;">
                                    Si no puedes asistir, cancela tu cita llamando al <i>%s</i>.
                                    <br><br>
                                    <strong>CNT Sevilla</strong>
                                </td>
                            </tr>
                        </tbody>
                        <tfoot>
                            <tr style="background-color: #f4f4f9; color: #666; text-align: center; font-size: 12px;">
                                <td colspan="2" style="padding: 10px;">
                                    Este es un correo automatizado, por favor no responder a este mensaje.
                                </td>
                            </tr>
                        </tfoot>
                    </table>
                </body>
            </html>
            """.formatted(personInNeedName, helperName, datetime, helperPhone);
    }


    private String buildReminderPersonInNeedEmail(String personInNeedName, String helperName, String datetime, String helperPhone) {
        return """
            <!DOCTYPE html>
            <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Recordatorio de Cita Agendada</title>
                </head>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 0;">
                    <table align="center" width="600"
                        style="background-color: #ffffff; border: 1px solid #ddd; border-radius: 8px; overflow: hidden; box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);">
                        <thead>
                            <tr style="background-color: #180000; color: #ffffff; text-align: center;">
                                <th colspan="2" style="padding: 20px; font-size: 24px; font-weight: bold; text-transform: uppercase;">
                                    Recordatorio de Cita Agendada
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td colspan="2" style="padding: 20px; font-size: 16px; text-align: left; color: #333;">
                                    Hola <strong>%s</strong>,
                                    <br><br>
                                    Le recordamos su proxima cita:
                                </td>
                            </tr>
                            <tr>
                                <td style="padding: 10px; font-weight: bold; border-bottom: 1px solid #ddd; text-align: right;"
                                    width="40%%">Te atenderá:</td>
                                <td style="padding: 10px; border-bottom: 1px solid #ddd; text-align: left;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding: 10px; font-weight: bold; border-bottom: 1px solid #ddd; text-align: right;">Fecha y
                                    Hora:</td>
                                <td style="padding: 10px; border-bottom: 1px solid #ddd; text-align: left;">%s</td>
                            </tr>
                            <tr>
                                <td colspan="2" style="padding: 20px; font-size: 16px; text-align: left; color: #333;">
                                    Si no puedes asistir, cancela tu cita llamando al <i>%s</i>.
                                    <br><br>
                                    <strong>CNT Sevilla</strong>
                                </td>
                            </tr>
                        </tbody>
                        <tfoot>
                            <tr style="background-color: #f4f4f9; color: #666; text-align: center; font-size: 12px;">
                                <td colspan="2" style="padding: 10px;">
                                    Este es un correo automatizado, por favor no responder a este mensaje.
                                </td>
                            </tr>
                        </tfoot>
                    </table>
                </body>
            </html>
            """.formatted(personInNeedName, helperName, datetime, helperPhone);
    }

    private String buildReminderHelperEmail(String personInNeedName, String helperName, String datetime, String helperPhone) {
        return """
            <!DOCTYPE html>
            <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Recordatorio de citas</title>
                </head>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 0;">
                    <table align="center" width="600"
                        style="background-color: #ffffff; border: 1px solid #ddd; border-radius: 8px; overflow: hidden; box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);">
                        <thead>
                            <tr style="background-color: #180000; color: #ffffff; text-align: center;">
                                <th colspan="2" style="padding: 20px; font-size: 24px; font-weight: bold; text-transform: uppercase;">
                                    Recordatorio de citas
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td colspan="2" style="padding: 20px; font-size: 16px; text-align: left; color: #333;">
                                    Hola <strong>%s</strong>,
                                    <br><br>
                                    Le recordamos sus proximas citas:
                                </td>
                            </tr>
                            %s
                            <tr>
                                <td colspan="2" style="padding: 20px; font-size: 16px; text-align: left; color: #333;">
                                    Si no puedes asistir, cancela tu cita llamando al <i>%s</i>.
                                    <br><br>
                                    <strong>CNT Sevilla</strong>
                                </td>
                            </tr>
                        </tbody>
                        <tfoot>
                            <tr style="background-color: #f4f4f9; color: #666; text-align: center; font-size: 12px;">
                                <td colspan="2" style="padding: 10px;">
                                    Este es un correo automatizado, por favor no responder a este mensaje.
                                </td>
                            </tr>
                        </tfoot>
                    </table>
                </body>
            </html>
            """.formatted(personInNeedName, helperName, datetime, helperPhone);
    }
    private String buildAppointmentForHelper(String personInNeedName, String helperName, String datetime, String helperPhone) {
        return """
                <tr>
                                                <td style="padding: 10px; font-weight: bold; border-bottom: 1px solid #ddd; text-align: right;"
                    width="40%%">Atenderá a:</td>
                                                <td style="padding: 10px; border-bottom: 1px solid #ddd; text-align: left;">%s</td>
                                            </tr>
                                            <tr>
                                                <td style="padding: 10px; font-weight: bold; border-bottom: 1px solid #ddd; text-align: right;">Fecha y
                    Hora:</td>
                                                <td style="padding: 10px; border-bottom: 1px solid #ddd; text-align: left;">%s</td>
                                            </tr>
                """;
    }


    public void sendReminderToPersonInNeed(String email, String name, String name1, ZonedDateTime dateTime) {
        // TODO email
    }

    public void sendReminderToHelper(String email, String name, String name1, ZonedDateTime dateTime) {
        // TODO email
    }
}
