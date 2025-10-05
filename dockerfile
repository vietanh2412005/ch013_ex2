# Sử dụng image Tomcat 9 có sẵn Java 17+
FROM tomcat:9.0-jdk17

# Xóa các ứng dụng mặc định trong Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR đã export vào Tomcat
COPY ch13_ex2.war /usr/local/tomcat/webapps/ROOT.war

# Mở port 8080
EXPOSE 8080

# Khởi chạy Tomcat
CMD ["catalina.sh", "run"]
