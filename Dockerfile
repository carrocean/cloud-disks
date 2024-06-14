# 使用基础的Java镜像
FROM openjdk:17

# 将构建好的Spring Boot JAR文件复制到容器中
COPY cloud-disks-boot/target/cloud-disks-boot-0.0.1-SNAPSHOT.jar /usr/src/myapp/app.jar

# 设置工作目录
WORKDIR /usr/src/myapp

# 配置环境变量，例如设置端口号
ENV PORT=30001

# 暴露 Spring Boot 应用程序的端口
EXPOSE 30001

# 运行 Spring Boot 应用程序
CMD ["java", "-jar", "app.jar"]
