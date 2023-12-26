package com.example.demo.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Data
@AllArgsConstructor
public class MessageResponse implements Serializable {
    private String message;
}
