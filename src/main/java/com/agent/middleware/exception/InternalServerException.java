//package com.agent.middleware.exception;
//
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//
//public class CustomException {
//    @Getter
//    @RequiredArgsConstructor
//    public static class ABServiceException extends RuntimeException {
//        private final int code;
//        private final String message;
//    }
//
//    public static class AuthenticationException extends ABServiceException {
//        public AuthenticationException(String message) {
//            super(HttpStatus.UNAUTHORIZED.value(), message);
//        }
//    }
//    public static class BadRequestException extends ABServiceException {
//        public BadRequestException(String message) {
//            super(HttpStatus.BAD_REQUEST.value(), message);
//        }
//    }
//
//    public static class AlreadyExistsException extends ABServiceException {
//        public AlreadyExistsException(String message) {
//            super(HttpStatus.CONFLICT.value(), message);
//        }
//    }
//    public static class NotFoundException extends ABServiceException {
//        public NotFoundException(String message) {
//            super(HttpStatus.NOT_FOUND.value(), message);
//        }
//    }
//    public static class InvalidDateFormatException extends ABServiceException {
//        public InvalidDateFormatException(String message) {
//            super(HttpStatus.BAD_REQUEST.value(), message);
//        }
//    }
//
//    public static class InvalidDataException extends ABServiceException {
//        public InvalidDataException(String message) {
//            super(HttpStatus.BAD_REQUEST.value(), message);
//        }
//    }
//
//
//    public static class GeneralException extends ABServiceException {
//        public GeneralException(int code,String message) {
//            super(code, message);
//        }
//    }
//}
