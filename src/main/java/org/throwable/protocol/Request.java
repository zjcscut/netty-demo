package org.throwable.protocol;

import lombok.Data;

/**
 * @author throwable
 * @version v1.0
 * @since 2017/2/24 14:36
 */
@Data
public class Request {

    private long requestId;
    private Object content;
    private long requestTime;

}
