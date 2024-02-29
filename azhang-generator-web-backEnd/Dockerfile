# 使用包含已经构建好的 JAR 文件的基础镜像
FROM openjdk:8-jdk-slim

# 切换为国内软件源
RUN sed -i 's/deb.debian.org/mirrors.aliyun.com/g' /etc/apt/sources.list

# 安装必要的软件
RUN apt-get update && \
    apt-get install -y wget gnupg && \
    rm -rf /var/lib/apt/lists/*

# 下载并安装Maven
ENV MAVEN_VERSION 3.8.8
ENV MAVEN_HOME /opt/maven
ENV MAVEN_URL https://mirrors.aliyun.com/apache/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz?spm=a2c6h.25603864.0.0.7b812f5c1ca4NT
RUN mkdir -p /opt && \
    wget $MAVEN_URL -O /tmp/apache-maven.tar.gz && \
    tar xzvf /tmp/apache-maven.tar.gz -C /opt/ && \
    ln -s /opt/apache-maven-$MAVEN_VERSION $MAVEN_HOME && \
    rm -f /tmp/apache-maven.tar.gz
ENV PATH $MAVEN_HOME/bin:$PATH

# 修改Maven的镜像源为阿里云
RUN echo '<settings><mirrors><mirror><id>alimaven</id><mirrorOf>central</mirrorOf><url>https://maven.aliyun.com/repository/central</url></mirror></mirrors></settings>' > /tmp/settings.xml && \
    mv /tmp/settings.xml $MAVEN_HOME/conf/

ENV PATH $MAVEN_HOME/bin:$PATH


# 设置工作目录
WORKDIR ./

# 复制已构建好的 JAR 文件到镜像中
COPY ./azhang-generator-web-backEnd-0.0.1-SNAPSHOT.jar app.jar


# 设置环境变量
ENV PARAMS=""
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 容器启动命令
ENTRYPOINT ["sh", "-c", "java -jar ./app.jar $PARAMS --spring.profiles.active=prod"]