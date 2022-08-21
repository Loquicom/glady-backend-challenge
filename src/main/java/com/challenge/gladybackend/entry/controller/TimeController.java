package com.challenge.gladybackend.entry.controller;

import com.challenge.gladybackend.config.TimeConfig;
import com.challenge.gladybackend.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("${app.api.root-path}/time")
public class TimeController {

    /**
     * Return the actual time for the server
     *
     * @return Actual server time
     */
    @GetMapping
    public ResponseEntity<String> getTime() {
        log.info("Call get time");
        return ResponseUtils.response(TimeConfig.getTime().toString());
    }

    /**
     * Set the time for the server
     *
     * @param year  New year
     * @param month New month (value between 1 and 12)
     * @param day   Day of thz month
     * @return The new date
     */
    @PutMapping("year/{year}/month/{month}/day/{day}")
    public ResponseEntity<String> setTime(@PathVariable String year, @PathVariable String month, @PathVariable String day) {
        log.info("Call set time: date={}-{}-{}", year, month, day);
        TimeConfig.setTime(year, month, day);
        return getTime();
    }

}
