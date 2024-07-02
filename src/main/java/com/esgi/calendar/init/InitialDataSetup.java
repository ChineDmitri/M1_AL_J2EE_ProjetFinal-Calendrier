package com.esgi.calendar.init;

import com.esgi.calendar.business.DayOfActualMonth;
import com.esgi.calendar.business.Emoji;
import com.esgi.calendar.business.Theme;
import com.esgi.calendar.business.UserCustomer;
import com.esgi.calendar.repository.DayRepository;
import com.esgi.calendar.repository.EmojiRepository;
import com.esgi.calendar.repository.ThemeRepository;
import com.esgi.calendar.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@AllArgsConstructor
public class InitialDataSetup {
    private ThemeRepository themeRepository;
    private DayRepository   dayRepository;
    private EmojiRepository emojiRepository;
    private UserRepository  userRepository;

    private PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        Theme.ThemeBuilder themeBuilder = new Theme().builder();

        // Add themes
        Theme lightTheme = themeBuilder.name("Light")
                                       .build();
        Theme darkTheme = themeBuilder.name("Dark")
                                      .build();
        this.themeRepository.saveAll(Arrays.asList(lightTheme, darkTheme));

        UserCustomer userTest = new UserCustomer().builder()
                                                  .firstName("TestFirstName")
                                                  .lastName("TestLastName")
                                                  .email("t@t.t")
                                                  .password(
                                                          this.passwordEncoder
                                                                  .encode("t")
                                                  )
                                                  .theme(darkTheme)
                                                  .build();

        userRepository.save(userTest);

        // Add days of the current month
        LocalDate currentDate  = LocalDate.now();
        Month     currentMonth = currentDate.getMonth();
        int       currentYear  = currentDate.getYear();
        int daysInMonth = currentDate.getMonth()
                                     .length(currentDate.isLeapYear());

        for (int i = 1; i <= daysInMonth; i++) {
            LocalDate date = LocalDate.of(currentYear, currentMonth, i);
            DayOfActualMonth day = new DayOfActualMonth().builder()
                                                         .date(date) // Convert LocalDate to java.sql.Date
                                                         .costGif(
                                                                 new Random()
                                                                         .nextInt(31) + 31
                                                         )
                                                         .build();
            this.dayRepository.save(day);
        }

        Emoji.EmojiBuilder emojiBuilder = new Emoji().builder();

        // Add emotions
        List<Emoji> emotions = Arrays.asList(emojiBuilder.name("grinning face")
                                                         .unicode("😀")
                                                         .build(),
                                             emojiBuilder.name("winking face")
                                                         .unicode("😉")
                                                         .build()
        );
        this.emojiRepository.saveAll(emotions);
    }
}
