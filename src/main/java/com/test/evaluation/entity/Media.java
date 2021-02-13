package com.test.evaluation.entity;

import com.test.evaluation.constant.FileType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "media")
public class Media implements Serializable {
    private static final long serialVersionUID = 9178661439383356177L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private FileType type;

    @Lob
    private byte[] file;

    @Column(name = "metadata", columnDefinition = "json")
    private String metadata;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn (name = "saved_user_id",updatable = false)
    private User savedUser;
}
