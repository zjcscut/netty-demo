package org.throwable.protocol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author throwable
 * @version v1.0
 * @since 2017/2/24 14:38
 */
@Setter
@Getter
@ToString
public class Response {

    private long requestId;
    private Object response;
    private Throwable throwable;

}
