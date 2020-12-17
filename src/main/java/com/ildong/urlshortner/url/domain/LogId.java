package com.ildong.urlshortner.url.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Embeddable
public class LogId implements Serializable {

    @NotNull
    private long urlId;

    @NotNull
    private String ip;

    private String referer;

    @NotNull
    private String getDtm;

    public LogId(){

    }
    public LogId(@NotNull long urlId, @NotNull String ip, String referer, @NotNull String getDtm) {
        this.urlId = urlId;
        this.ip = ip;
        this.referer = referer;
        this.getDtm = getDtm;
    }
}
