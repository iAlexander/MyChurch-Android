package com.d2.pcu.ui.error;

public enum HTTPCode {
    HTTP_200(200, "OK", "The request has succeeded. The meaning of a success varies depending on the HTTP method:\n" +
            "GET: The resource has been fetched and is transmitted in the message body\n" +
            "HEAD: The entity headers are in the message body\n" +
            "POST: The resource describing the result of the action is transmitted in the message body\n" +
            "TRACE: The message body contains the request message as received by the server"),
    HTTP_201(201, "Created", "The request has succeeded and a new resource has been created as a result of it. This is typically the response sent after a PUT request"),
    HTTP_202(202, "Accepted", "The request has been received but not yet acted upon. It is non-committal, meaning that there is no way in HTTP to later send an asynchronous response indicating the outcome of processing the request. It is intended for cases where another process or server handles the request, or for batch processing"),
    HTTP_203(203, "Non-Authoritative Information", "This response code means returned meta-information set is not exact set as available from the origin server, but collected from a local or a third party copy. Except this condition, 200 OK response should be preferred instead of this response"),
    HTTP_204(204, "No Content", "There is no content to send for this request, but the headers may be useful. The user-agent may update its cached headers for this resource with the new ones"),
    HTTP_205(205, "Reset Content", "This response code is sent after accomplishing request to tell user agent reset document view which sent this request"),
    HTTP_206(206, "Partial Content", "This response code is used because of range header sent by the client to separate download into multiple streams"),
    HTTP_207(207, "Multi-Status (WebDAV)", "A Multi-Status response conveys information about multiple resources in situations where multiple status codes might be appropriate"),
    HTTP_208(208, "Multi-Status (WebDAV)", "A Multi-Status response conveys information about multiple resources in situations where multiple status codes might be appropriate"),
    HTTP_226(226, "IM Used (HTTP Delta encoding)", "The server has fulfilled a GET request for the resource, and the response is a representation of the result of one or more instance-manipulations applied to the current instance"),

    HTTP_300(300, "Multiple Choice", "The request has more than one possible responses. User-agent or user should choose one of them. There is no standardized way to choose one of the responses"),
    HTTP_301(301, "Moved Permanently", "This response code means that URI of requested resource has been changed. Probably, new URI would be given in the response"),
    HTTP_302(302, "Found", "This response code means that URI of requested resource has been changed temporarily. New changes in the URI might be made in the future. Therefore, this same URI should be used by the client in future requests"),
    HTTP_303(303, "See Other", "Server sent this response to directing client to get requested resource to another URI with an GET request"),
    HTTP_304(304, "Not Modified", "This is used for caching purposes. It is telling to client that response has not been modified. So, client can continue to use same cached version of response"),
    HTTP_305(305, "Use Proxy", "Was defined in a previous version of the HTTP specification to indicate that a requested response must be accessed by a proxy. It has been deprecated due to security concerns regarding in-band configuration of a proxy"),
    HTTP_306(306, "Unused", "This response code is no longer used, it is just reserved currently. It was used in a previous version of the HTTP 1.1 specification"),
    HTTP_307(307, "Temporary Redirect", "Server sent this response to directing client to get requested resource to another URI with same method that used prior request. This has the same semantic than the 302 Found HTTP response code, with the exception that the user agent must not change the HTTP method used: if a POST was used in the first request, a POST must be used in the second request"),
    HTTP_308(308, "Permanent Redirect", "This means that the resource is now permanently located at another URI, specified by the Location: HTTP Response header. This has the same semantics as the 301 Moved Permanently HTTP response code, with the exception that the user agent must not change the HTTP method used: if a POST was used in the first request, a POST must be used in the second request"),

    HTTP_400(400, "Bad request", "This response means that server could not understand the request due to invalid syntax"),
    HTTP_401(401, "Unauthorized", "Although the HTTP standard specifies \"unauthorized\", semantically this response means \"unauthenticated\". That is, the client must authenticate itself to get the requested response"),
    HTTP_402(402, "Payment Required", "This response code is reserved for future use. Initial aim for creating this code was using it for digital payment systems however this is not used currently"),
    HTTP_403(403, "Forbidden", "The client does not have access rights to the content, i.e. they are unauthorized, so server is rejecting to give proper response. Unlike 401, the client's identity is known to the server"),
    HTTP_404(404, "Not Found", "The server can not find requested resource. In the browser, this means the URL is not recognized. In an API, this can also mean that the endpoint is valid but the resource itself does not exist. Servers may also send this response instead of 403 to hide the existence of a resource from an unauthorized client. This response code is probably the most famous one due to its frequent occurence on the web"),
    HTTP_405(405, "Method Not Allowed", "The request method is known by the server but has been disabled and cannot be used. For example, an API may forbid DELETE-ing a resource. The two mandatory methods, GET and HEAD, must never be disabled and should not return this error code"),
    HTTP_406(406, "Not Acceptable", "This response is sent when the web server, after performing server-driven content negotiation, doesn't find any content following the criteria given by the user agent"),
    HTTP_407(407, "Proxy Authentication Required", "This is similar to 401 but authentication is needed to be done by a proxy"),
    HTTP_408(408, "Request Timeout", "This response is sent on an idle connection by some servers, even without any previous request by the client. It means that the server would like to shut down this unused connection. This response is used much more since some browsers, like Chrome, Firefox 27+, or IE9, use HTTP pre-connection mechanisms to speed up surfing. Also note that some servers merely shut down the connection without sending this message"),
    HTTP_409(409, "Conflict", "This response is sent when a request conflicts with the current state of the server"),
    HTTP_410(410, "Gone", "This response would be sent when the requested content has been permenantly deleted from server, with no forwarding address. Clients are expected to remove their caches and links to the resource. The HTTP specification intends this status code to be used for \"limited-time, promotional services\". APIs should not feel compelled to indicate resources that have been deleted with this status code"),
    HTTP_411(411, "Length Required", "Server rejected the request because the Content-Length header field is not defined and the server requires it"),
    HTTP_412(412, "Precondition Failed", "The client has indicated preconditions in its headers which the server does not meet"),
    HTTP_413(413, "Payload Too Large", "Request entity is larger than limits defined by server; the server might close the connection or return an Retry-After header field"),
    HTTP_414(414, "URI Too Long", "The URI requested by the client is longer than the server is willing to interpret"),
    HTTP_415(415, "Unsupported Media Type", "The media format of the requested data is not supported by the server, so the server is rejecting the request"),
    HTTP_416(416, "Requested Range Not Satisfiable", "The range specified by the Range header field in the request can't be fulfilled; it's possible that the range is outside the size of the target URI's data"),
    HTTP_417(417, "Expectation Failed", "This response code means the expectation indicated by the Expect request header field can't be met by the server"),
    HTTP_418(418, "I'm a teapot", "The server refuses the attempt to brew coffee with a teapot"),

    HTTP_421(421, "Misdirected Request", "The request was directed at a server that is not able to produce a response. This can be sent by a server that is not configured to produce responses for the combination of scheme and authority that are included in the request URI"),
    HTTP_422(422, "Unprocessable Entity (WebDAV)", "The request was well-formed but was unable to be followed due to semantic errors"),
    HTTP_423(423, "Locked (WebDAV)", "The resource that is being accessed is locked"),
    HTTP_424(424, "Failed Dependency (WebDAV)", "The request failed due to failure of a previous request"),
    HTTP_426(426, "Upgrade Required", "The server refuses to perform the request using the current protocol but might be willing to do so after the client upgrades to a different protocol. The server sends an Upgrade header in a 426 response to indicate the required protocol(s)"),
    HTTP_428(428, "Precondition Required", "The origin server requires the request to be conditional. Intended to prevent the 'lost update' problem, where a client GETs a resource's state, modifies it, and PUTs it back to the server, when meanwhile a third party has modified the state on the server, leading to a conflict"),
    HTTP_429(429, "Too Many Requests", "The user has sent too many requests in a given amount of time (\"rate limiting\")"),
    HTTP_431(431, "Request Header Fields Too Large", "The server is unwilling to process the request because its header fields are too large. The request MAY be resubmitted after reducing the size of the request header fields"),
    HTTP_451(451, "Unavailable For Legal Reasons", "The user requests an illegal resource, such as a web page censored by a government"),

    HTTP_500(500, "Internal Server Error", "The server has encountered a situation it doesn't know how to handle"),
    HTTP_501(501, "Not Implemented", "The request method is not supported by the server and cannot be handled. The only methods that servers are required to support (and therefore that must not return this code) are GET and HEAD"),
    HTTP_502(502, "Bad Gateway", "This error response means that the server, while working as a gateway to get a response needed to handle the request, got an invalid response"),
    HTTP_503(503, "Service Unavailable", "The server is not ready to handle the request. Common causes are a server that is down for maintenance or that is overloaded. Note that together with this response, a user-friendly page explaining the problem should be sent. This responses should be used for temporary conditions and the Retry-After: HTTP header should, if possible, contain the estimated time before the recovery of the service. The webmaster must also take care about the caching-related headers that are sent along with this response, as these temporary condition responses should usually not be cached"),
    HTTP_504(504, "Gateway Timeout", "This error response is given when the server is acting as a gateway and cannot get a response in time"),
    HTTP_505(505, "HTTP Version Not Supported", "The HTTP version used in the request is not supported by the server"),
    HTTP_506(506, "Variant Also Negotiates", "The server has an internal configuration error: transparent content negotiation for the request results in a circular reference"),
    HTTP_507(507, "Insufficient Storage", "The server has an internal configuration error: the chosen variant resource is configured to engage in transparent content negotiation itself, and is therefore not a proper end point in the negotiation process"),
    HTTP_508(508, "Loop Detected (WebDAV)", "The server detected an infinite loop while processing the request"),
    HTTP_510(510, "Not Extended", "Further extensions to the request are required for the server to fulfill it"),
    HTTP_511(511, "Network Authentication Required", "The 511 status code indicates that the client needs to authenticate to gain network access"),

    HTTP_UNDEFINED(-1, "Undefined error", "");


    private int code;
    private String message;
    private String extension;

    private HTTPCode(int code, String message, String extension) {
        this.code = code;
        this.message = message;
        this.extension = extension;
    }

    public static HTTPCode findByCode(int code) {
        switch (code) {

            case 200:
                return HTTP_200;
            case 201:
                return HTTP_201;
            case 202:
                return HTTP_202;
            case 203:
                return HTTP_203;
            case 204:
                return HTTP_204;
            case 205:
                return HTTP_205;
            case 206:
                return HTTP_206;
            case 207:
                return HTTP_207;
            case 208:
                return HTTP_208;
            case 226:
                return HTTP_226;

            case 300:
                return HTTP_300;
            case 301:
                return HTTP_301;
            case 302:
                return HTTP_302;
            case 303:
                return HTTP_303;
            case 304:
                return HTTP_304;
            case 305:
                return HTTP_305;
            case 306:
                return HTTP_306;
            case 307:
                return HTTP_307;
            case 308:
                return HTTP_308;

            case 400:
                return HTTP_400;
            case 401:
                return HTTP_401;
            case 402:
                return HTTP_402;
            case 403:
                return HTTP_403;
            case 404:
                return HTTP_404;
            case 405:
                return HTTP_405;
            case 406:
                return HTTP_406;
            case 407:
                return HTTP_407;
            case 408:
                return HTTP_408;
            case 409:
                return HTTP_409;
            case 410:
                return HTTP_410;
            case 411:
                return HTTP_411;
            case 412:
                return HTTP_412;
            case 413:
                return HTTP_413;
            case 414:
                return HTTP_414;
            case 415:
                return HTTP_415;
            case 416:
                return HTTP_416;
            case 417:
                return HTTP_417;
            case 418:
                return HTTP_418;

            case 500:
                return HTTP_500;
            case 501:
                return HTTP_501;
            case 502:
                return HTTP_502;
            case 503:
                return HTTP_503;
            case 504:
                return HTTP_504;
            case 505:
                return HTTP_505;
            case 506:
                return HTTP_506;
            case 507:
                return HTTP_507;
            case 508:
                return HTTP_508;
            case 510:
                return HTTP_510;
            case 511:
                return HTTP_511;
            default:
                return HTTP_UNDEFINED;
        }
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getExtension() {
        return extension;
    }
}
