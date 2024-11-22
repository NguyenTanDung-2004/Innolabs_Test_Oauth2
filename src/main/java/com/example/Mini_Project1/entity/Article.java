package com.example.Mini_Project1.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "article")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Column(columnDefinition = "TEXT")
	private String title;
	private String categorize; //
	@Column(columnDefinition = "LONGTEXT")
	private String content;

	@Column(columnDefinition = "BLOB")
	private List<String> referenceLink;

	@JsonSerialize(using = DateSerializer.class)
    private Date createdDate;

	@JsonSerialize(using = DateSerializer.class)
    private Date updatedDate;

	private int statusApproved; // 1 - approved, 0 - yet

	@ManyToOne
	@JoinColumn(name = "author_id")
	private User user;
	
	private int flagDelete;
}
