documentUVR-Gradle

http://localhost:8080/pdf/upload?username=sample
Under Body of Postman -> form-data: "file" and select PDF document.

http://localhost:8080/pdf/view?username=sample
Provide the username as sample or accordingly.

http://localhost:8080/pdf/delete/2
Provide the id to delete the required PDF Document.

Used Technologies:
1. Java
2. Spring boot Application
3. Gradle
4. Database - use any External
5. JPARepository
6. REST Apis
7. Junit4 - Mockito
8. JACOCO - code coverage (path: build/jacocoHtml/index.html)
