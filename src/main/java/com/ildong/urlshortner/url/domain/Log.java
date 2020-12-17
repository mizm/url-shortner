package com.ildong.urlshortner.url.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "LOG_URL_SHORTNER")
public class Log {

    @EmbeddedId
    private LogId logid;
    public Log() {

    }
    public Log(@NotNull LogId logId) {
        this.logid = logId;
    }
}
