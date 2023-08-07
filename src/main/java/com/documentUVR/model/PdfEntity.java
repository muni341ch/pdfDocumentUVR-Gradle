package com.documentUVR.model;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
public class PdfEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @Lob
        private byte[] data;

        private String userName;

        public PdfEntity() {
        }

        public String getName() {
                return name;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public void setName(String name) {
                this.name = name;
        }

        public void setData(byte[] data) {
                this.data = data;
        }

        public void setUserName(String userName) {
                this.userName = userName;
        }

        @Override
        public String toString() {
                return "PdfModel{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", data=" + Arrays.toString(data) +
                        ", userName='" + userName + '\'' +
                        '}';
        }
}